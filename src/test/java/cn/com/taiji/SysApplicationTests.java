package cn.com.taiji;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.taiji.domain.Department;
import cn.com.taiji.domain.DepartmentRepository;
import cn.com.taiji.dto.DepartmentDto;
import cn.com.taiji.dto.MenuDto;
import cn.com.taiji.dto.RoleDto;
import cn.com.taiji.dto.UserDto;
import cn.com.taiji.service.DepartmentService;
import cn.com.taiji.service.MenuService;
import cn.com.taiji.service.RoleService;
import cn.com.taiji.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysApplicationTests {
	
	@Autowired
	UserService us;
	@Autowired
	RoleService rs;
	@Autowired
	MenuService ms;
	@Autowired
	DepartmentService ds;
	@Autowired
	DepartmentRepository dr;
	/**
	 * 
	 * @Description:  userService的测试
	 * @throws
	 * @author anyulong
	 * @date 2017年12月10日
	 */
	@Test
	public void userTest() {
		//System.out.println(us.findAllUsers());
		
		
		UserDto u = new UserDto();
		u.setAge(18);
		u.setUserName("大傻子");
		us.updUser(u);
	//	System.out.println(us.getPage(1, 3, null, null).get("users"));
	}
	/**
	 * 
	 * @Description: roleService的测试  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月10日
	 */
	@Test
	public void roleTest() {
		RoleDto r = new RoleDto();
		r.setId("002");
		r.setState(1);
		r.setRoleName("管理员");
		rs.updRole(r);
		rs.findAllRoles();
		rs.deleteRole("001");
		//rs.getPage(1, 3, null, null);
		
	}
	
	/**
	 * 
	 * @Description: menuService的测试  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月10日
	 */
	@Test
	public void menuTest() {
		MenuDto m = new MenuDto();
		m.setId("001");
		m.setState(1);
		m.setUrl("www.baidu.com");
		m.setMenuDescription("我的列表");
		m.setLevel("1");
		ms.updMenu(m);
		ms.getPage(1, 3, null, null);
	}
	
	@Test
	public void deptTest() {
		
	/*	DepartmentDto d = new DepartmentDto();
		d.setId("12");
		d.setDepartmentDtoName("鲁东大学");
		ds.updDepartment(d);*/
		/*Department dept=dr.findDepartmentById("1");
		System.out.println(dept.getDepartments().size());*/
		DepartmentDto d=ds.findDepartmentById("12");
		System.out.println(d.getDepartment().getDepartmentName());
	}

}
