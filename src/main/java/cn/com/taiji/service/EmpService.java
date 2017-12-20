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
import cn.com.taiji.domain.Emp;
import cn.com.taiji.domain.EmpRepository;
import cn.com.taiji.dto.DepartmentDto;
import cn.com.taiji.dto.EmpDto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@Service
public class EmpService {
	
	@Autowired
	private EmpRepository empRepository;
	@Autowired
	DepartmentService departmentService;
	
	
	
	/**
	 * 
	 * @Description: 查询所有用户
	 * @return List<Emp>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public List<Emp> findAllEmps(){
		return empRepository.findAll();
		
	}
	/**
	 * 
	 * @Description: 根据ID查询用户
	 * @param id
	 * @return Emp  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public EmpDto findEmpById(String id){
		return entity2Dto(empRepository.findEmpById(id));
	}
	/**
	 * 
	 * @Description: 插入或更新一个用户
	 * @param empDto void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void updEmp(EmpDto empDto){
		Calendar now = Calendar.getInstance();
		Emp emp = new Emp();
		BeanUtils.copyProperties(empDto,emp);
		SecurityContext context = SecurityContextHolder.getContext();
		if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
			UserDetails empDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			
			if((null!=emp.getId()&&null!=empDetails)) {
				emp.setChanger(empDetails.getUsername());
				emp.setChangeTime(now.getTime());
			}
			if(null==emp.getId()&&null!=empDetails) {
				emp.setCreater(empDetails.getUsername());
				emp.setCreateTime(now.getTime());
			}
	
		}
		System.out.println("serviceqian"+emp.getId());
		System.out.println(emp.getId()==null);
		if(emp.getId()==null) {
			emp.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		System.out.println(emp.getDepartments());
		if(null!=emp.getDepartments()) {
			Department d = departmentService.findByName(emp.getDepartments().get(0).getDepartmentName());
			emp.getDepartments().clear();
			emp.getDepartments().add(d);
			
		}
		System.out.println("hou"+emp.getId());
		
		emp.setState(1);
		empRepository.saveAndFlush(emp);
	} 
	/**
	 * 
	 * @Description: 删除
	 * @param id void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void deleteEmp(String id) {
		empRepository.deleteEmp(id);
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
		Page<Emp> pageList;
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
			Specification<Emp> spec = new Specification<Emp>() {
				@Override
				public Predicate toPredicate(Root<Emp> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					
					for (Map m : filters) {
						if(null!=m.get("value")&&""!=m.get("value")) {
						String field = m.get("field").toString().trim();
						String value = m.get("value").toString().trim();
						
						if ("empName".equalsIgnoreCase(field)) {	
						 pl.add(cb.equal(root.get(field), value));	
						} 
						if ("age".equalsIgnoreCase(field)) {
							 pl.add(cb.equal(root.get(field), Integer.parseInt(value)));	
							} 
					}}
					return cb.and(pl.toArray(new Predicate[0]));
					}
			};
			pageList = empRepository.findAll(spec, pageable);
		} else {
			pageList = empRepository.findAll(pageable);
		}
		
		map.put("total", pageList.getTotalElements());
		map.put("rows", accountPage2Dto(pageList));
		
		return map;
	}
	private List<EmpDto> accountPage2Dto(Page<Emp> pageContent) {
		List<EmpDto> empDtos = new ArrayList<EmpDto>();
		List<Emp> content = pageContent.getContent();
		for (Iterator iterator = content.iterator(); iterator.hasNext();) {
			Emp emp = (Emp) iterator.next();
			empDtos.add(entity2Dto(emp));
		}
		return empDtos;
	}
	
	
	/**
	 * 
	 * @Description: 实体类转Dto
	 * @param emp
	 * @return EmpDto  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	private EmpDto entity2Dto(Emp emp) {
		EmpDto empDto = new EmpDto();
		BeanUtils.copyProperties(emp,empDto);
		return empDto;
	}
	private DepartmentDto entity2Dto(Department department) {
		DepartmentDto departmentDto = new DepartmentDto();
		//System.out.println(department);
		BeanUtils.copyProperties(department,departmentDto);
		return departmentDto;
	}
}
