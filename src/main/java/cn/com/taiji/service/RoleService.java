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

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.RoleRepository;
import cn.com.taiji.domain.Role;
import cn.com.taiji.dto.RoleDto;
import cn.com.taiji.dto.RoleDto;


@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	/**
	 * 
	 * @Description: 查询所有角色
	 * @return List<role>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public List<Role> findAllRoles(){
		return roleRepository.findAll();
	}
	/**
	 * 
	 * @Description: 根据ID查询用户
	 * @param id
	 * @return Role  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public RoleDto findRoleById(String id){
		return entity2Dto(roleRepository.findRoleById(id));
	}
	/**
	 * 
	 * @Description: 插入或更新一个用户
	 * @param roleDto void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void updRole(RoleDto roleDto){
		Calendar now = Calendar.getInstance();
		Role role = new Role();
		BeanUtils.copyProperties(roleDto,role);
		SecurityContext context = SecurityContextHolder.getContext();
		if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
			UserDetails roleDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			
			if(null!=role.getId()&&null!=roleDetails) {
				role.setChanger(roleDetails.getUsername());
				role.setChangeTime(now.getTime());
			}
			if(null==role.getId()&&null!=roleDetails) {
				role.setCreater(roleDetails.getUsername());
				role.setCreateTime(now.getTime());
			}
	
		}
		
		if(role.getId()==null) {
			role.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		role.setState(1);
		roleRepository.saveAndFlush(role);
	} 
	/**
	 * 
	 * @Description: 删除
	 * @param id void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void deleteRole(String id) {
		roleRepository.deleteRole(id);
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
		Page<Role> pageContent;
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
			Specification<Role> spec = new Specification<Role>() {
				@Override
				public Predicate toPredicate(Root<Role> root,
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
			pageContent = roleRepository.findAll(spec, pageable);
		} else {
			pageContent = roleRepository.findAll(pageable);
		}
		Map map = new HashMap();
		map.put("total", pageContent.getTotalElements());
		map.put("rows", accountPage2Dto(pageContent));
		
		return map;
	}
	private List<RoleDto> accountPage2Dto(Page<Role> pageContent) {
		List<RoleDto> roleDtos = new ArrayList<RoleDto>();
		List<Role> content = pageContent.getContent();
		for (Iterator iterator = content.iterator(); iterator.hasNext();) {
			Role role = (Role) iterator.next();
			;
			roleDtos.add(entity2Dto(role));
		}
		return roleDtos;
	}
	
	/**
	 * 
	 * @Description: 实体类转Dto
	 * @param role
	 * @return RoleDto  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	private RoleDto entity2Dto(Role role) {
		RoleDto roleDto = new RoleDto();
		BeanUtils.copyProperties(role,roleDto);
		return roleDto;
	}
	
}

