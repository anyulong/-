package cn.com.taiji.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.thymeleaf.util.StringUtils;

import cn.com.taiji.domain.User;
import cn.com.taiji.domain.UserRepository;
import cn.com.taiji.dto.UserDto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 
	 * @Description: 查询所有用户
	 * @return List<User>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public List<User> findAllUsers(){
		return userRepository.findAll();
		
	}
	/**
	 * 
	 * @Description: 根据ID查询用户
	 * @param id
	 * @return User  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public UserDto findUserById(String id){
		return entity2Dto(userRepository.findUserById(id));
	}
	/**
	 * 
	 * @Description: 插入或更新一个用户
	 * @param userDto void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void updUser(UserDto userDto){
		Calendar now = Calendar.getInstance();
		User user = new User();
		BeanUtils.copyProperties(userDto,user);
		SecurityContext context = SecurityContextHolder.getContext();
		if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			
			if((null!=user.getId()&&null!=userDetails)) {
				user.setChanger(userDetails.getUsername());
				user.setChangeTime(now.getTime());
			}
			if(null==user.getId()&&null!=userDetails) {
				user.setCreater(userDetails.getUsername());
				user.setCreateTime(now.getTime());
			}
	
		}
		System.out.println("serviceqian"+user.getId());
		System.out.println(user.getId()==null);
		if(user.getId()==null) {
			user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		System.out.println("hou"+user.getId());
		user.setState(1);
		userRepository.saveAndFlush(user);
	} 
	/**
	 * 
	 * @Description: 删除
	 * @param id void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void deleteUser(String id) {
		userRepository.deleteUser(id);
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
	public Map getPage(Map searchParameters) {
		Map map = new HashMap();
		int page = 0;
		int pageSize = 10;
		Page<User> pageList;
		if (searchParameters != null && searchParameters.size() > 0
				&& searchParameters.get("page") != null) {
			page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
		}
		if (searchParameters != null && searchParameters.size() > 0
				&& searchParameters.get("pageSize") != null) {
			pageSize = Integer.parseInt(searchParameters.get("pageSize")
					.toString());
		}
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;
		List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (Map m : orderMaps) {
				if (m.get("field") == null)
					continue;
				String field = m.get("field").toString();
				if (!StringUtils.isEmpty(field)) {
					String dir = m.get("dir").toString();
					if ("DESC".equalsIgnoreCase(dir)) {
						orders.add(new Order(Direction.DESC, field));
					} else {
						orders.add(new Order(Direction.ASC, field));
					}
				}
			}
		}
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
			Sort s = new Sort(Direction.ASC, "id");
			pageable = new PageRequest(page, pageSize, s);
		}
		
		Map filter = (Map) searchParameters.get("filter");
		/*String field = filter.get("filters").toString().trim();
		String value = filter.get("value").toString().trim();*/
		final List<Map> filters = (List<Map>) filter.get("filters");
		
		if (null != filter) {
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					
					for (Map m : filters) {
						if(null!=m.get("value")&&""!=m.get("value")) {
						String field = m.get("field").toString().trim();
						String value = m.get("value").toString().trim();
						
						if ("userName".equalsIgnoreCase(field)) {	
						 pl.add(cb.equal(root.get(field), value));	
						} 
						if ("age".equalsIgnoreCase(field)) {
							 pl.add(cb.equal(root.get(field), Integer.parseInt(value)));	
							} 
					}}
					return cb.and(pl.toArray(new Predicate[0]));
					}
			};
			pageList = userRepository.findAll(spec, pageable);
		} else {
			pageList = userRepository.findAll(pageable);
		}
		
		map.put("total", pageList.getTotalElements());
		map.put("rows", accountPage2Dto(pageList));
		
		return map;
	}
	private List<UserDto> accountPage2Dto(Page<User> pageContent) {
		List<UserDto> userDtos = new ArrayList<UserDto>();
		List<User> content = pageContent.getContent();
		for (Iterator iterator = content.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			userDtos.add(entity2Dto(user));
		}
		return userDtos;
	}
	
	/**
	 * 
	 * @Description: 实体类转Dto
	 * @param user
	 * @return UserDto  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	private UserDto entity2Dto(User user) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user,userDto);
		return userDto;
	}
}
