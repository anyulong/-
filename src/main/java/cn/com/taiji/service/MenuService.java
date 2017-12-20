package cn.com.taiji.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.Menu;
import cn.com.taiji.domain.MenuRepository;
import cn.com.taiji.domain.Menu;
import cn.com.taiji.dto.MenuDto;
import cn.com.taiji.dto.MenuDto;
@Service
public class MenuService {
	
	@Autowired
	private MenuRepository menuRepository;
	
	/**
	 * 
	 * @Description: 查询所有菜单
	 * @return List<Menu>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public List<Menu> findAllMenus(){
		return menuRepository.findAll();
	}
	/**
	 * 
	 * @Description: 根据ID查询用户
	 * @param id
	 * @return Menu  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public MenuDto findMenuById(String id){
		return entity2Dto(menuRepository.findMenuById(id));
	}
	/**
	 * 
	 * @Description: 插入或更新一个用户
	 * @param menuDto void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void updMenu(MenuDto menuDto){
		Calendar now = Calendar.getInstance();
		Menu menu = new Menu();
		BeanUtils.copyProperties(menuDto,menu);
		SecurityContext context = SecurityContextHolder.getContext();
		if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
			UserDetails menuDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			
			if(null!=menu.getId()&&null!=menuDetails) {
				menu.setChanger(menuDetails.getUsername());
				menu.setChangeTime(now.getTime());
			}
			if(null==menu.getId()&&null!=menuDetails) {
				menu.setCreater(menuDetails.getUsername());
				menu.setCreateTime(now.getTime());
			}
	
		}
		
		if(menu.getId()==null) {
			menu.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		menu.setState(1);
		menuRepository.saveAndFlush(menu);
	} 
	/**
	 * 
	 * @Description: 删除
	 * @param id void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void deleteMenu(String id) {
		menuRepository.deleteMenu(id);
	}
	/**
	 * 
	 * @Description: 分页
	 * @param page
	 * @param pageSize
	 * @param orderMaps
	 * @param filters
	 * @return Map  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	public Map getPage(int page,int pageSize,
			HashMap<String,String> orderMaps,HashMap<String,String> filters) {
		Page<Menu> pageContent;
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;

		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (String key : orderMaps.keySet()) {
				if ("DESC".equalsIgnoreCase(orderMaps.get(key))) {
					orders.add(new Order(Direction.DESC, key));
				} else {
					orders.add(new Order(Direction.ASC, key));
				}
			}
			
		}
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
			pageable = new PageRequest(page, pageSize);
		}

		if (filters != null) {
			Specification<Menu> spec = new Specification<Menu>() {
				@Override
				public Predicate toPredicate(Root<Menu> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					for (String key : filters.keySet()) {
						// TODO should change with operator, and according to value
						String value = filters.get(key);
						if ("enabled".equalsIgnoreCase(key)) {
							if ("true".equalsIgnoreCase(value)) {
								pl.add(cb.equal(root.get(key), true));
							} else if ("false".equalsIgnoreCase(value)) {
								pl.add(cb.equal(root.get(key), false));
							}
						} else if ("code".equalsIgnoreCase(key)) {
							if (value.length() > 0)
								pl.add(cb.like(root.<String> get(key), value + "%"));
						} else if ("name".equalsIgnoreCase(key)) {
							if (value.length() > 0)
								pl.add(cb.like(root.get(key), value));
						}
					}
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageContent = menuRepository.findAll(spec, pageable);
		} else {
			pageContent = menuRepository.findAll(pageable);
		}
		Map map = new HashMap();
		map.put("total", pageContent.getTotalElements());
		map.put("rows", accountPage2Dto(pageContent));
		
		return map;
	}
	private List<MenuDto> accountPage2Dto(Page<Menu> pageContent) {
		List<MenuDto> menuDtos = new ArrayList<MenuDto>();
		List<Menu> content = pageContent.getContent();
		for (Iterator iterator = content.iterator(); iterator.hasNext();) {
			Menu menu = (Menu) iterator.next();
			;
			menuDtos.add(entity2Dto(menu));
		}
		return menuDtos;
	}
	
	/**
	 * 
	 * @Description: 实体类转Dto
	 * @param menu
	 * @return MenuDto  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	private MenuDto entity2Dto(Menu menu) {
		MenuDto menuDto = new MenuDto();
		BeanUtils.copyProperties(menu,menuDto);
		return menuDto;
	}
}
