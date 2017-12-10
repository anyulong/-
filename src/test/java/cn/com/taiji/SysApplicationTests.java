package cn.com.taiji;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.User;
import cn.com.taiji.dto.MenuDto;
import cn.com.taiji.dto.RoleDto;
import cn.com.taiji.dto.UserDto;
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
		u.setId("1221");
		u.setAge(18);
		u.setUserName("安玉龙");
		us.updUser(u);
		us.deleteUser("1213");
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
	}

}
