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

import cn.com.taiji.domain.Department;
import cn.com.taiji.domain.DepartmentRepository;
import cn.com.taiji.dto.DepartmentDto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	/**
	 * 
	 * @Description: 查询所有用户
	 * @return List<Department>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public List<Department> findAllDepartments(){
		return departmentRepository.findAll();
		
	}
	/**
	 * 
	 * @Description: 根据ID查询用户
	 * @param id
	 * @return Department  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public DepartmentDto findDepartmentById(String id){
		Department depts=departmentRepository.findDepartmentById(id);
		return entity2Dto(depts);
	}
	/**
	 * 
	 * @Description: 插入或更新一个用户
	 * @param departmentDto void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void updDepartment(DepartmentDto departmentDto){
		Calendar now = Calendar.getInstance();
		Department department = new Department();
		BeanUtils.copyProperties(departmentDto,department);
		SecurityContext context = SecurityContextHolder.getContext();
		if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
			UserDetails departmentDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			
			if((null!=department.getId()&&null!=departmentDetails)) {
				department.setChanger(departmentDetails.getUsername());
				department.setChangeTime(now.getTime());
			}
			if(null==department.getId()&&null!=departmentDetails) {
				department.setCreater(departmentDetails.getUsername());
				department.setCreateTime(now.getTime());
			}
	
		}
		System.out.println("serviceqian"+department.getId());
		System.out.println(department.getId()==null);
		if(department.getId()==null) {
			department.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		System.out.println("hou"+department.getId());
		department.setState(1);
		departmentRepository.saveAndFlush(department);
	} 
	/**
	 * 
	 * @Description: 删除
	 * @param id void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void deleteDepartment(String id) {
		departmentRepository.deleteDepartment(id);
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
		Page<Department> pageList;
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
			Specification<Department> spec = new Specification<Department>() {
				@Override
				public Predicate toPredicate(Root<Department> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					
					for (Map m : filters) {
						if(null!=m.get("value")&&""!=m.get("value")) {
						String field = m.get("field").toString().trim();
						String value = m.get("value").toString().trim();
						
						if ("departmentName".equalsIgnoreCase(field)) {	
						 pl.add(cb.equal(root.get(field), value));	
						} 
						if ("age".equalsIgnoreCase(field)) {
							 pl.add(cb.equal(root.get(field), Integer.parseInt(value)));	
							} 
					}}
					return cb.and(pl.toArray(new Predicate[0]));
					}
			};
			pageList = departmentRepository.findAll(spec, pageable);
		} else {
			pageList = departmentRepository.findAll(pageable);
		}
		
		map.put("total", pageList.getTotalElements());
		map.put("rows", accountPage2Dto(pageList));
		
		return map;
	}
	private List<DepartmentDto> accountPage2Dto(Page<Department> pageContent) {
		List<DepartmentDto> departmentDtos = new ArrayList<DepartmentDto>();
		List<Department> content = pageContent.getContent();
		for (Iterator iterator = content.iterator(); iterator.hasNext();) {
			Department department = (Department) iterator.next();
			departmentDtos.add(entity2Dto(department));
		}
		return departmentDtos;
	}
	
	/**
	 * 
	 * @Description: 实体类转Dto
	 * @param department
	 * @return DepartmentDto  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	private DepartmentDto entity2Dto(Department department) {
		DepartmentDto departmentDto = new DepartmentDto();
		//System.out.println(department);
		BeanUtils.copyProperties(department,departmentDto);
		return departmentDto;
	}
	/**
	 * 
	 * @Description: 根据name查询
	 * @param name
	 * @return Department  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月20日
	 */
	public Department findByName(String name) {
		return departmentRepository.findDepartmentByName(name);
	}
}

