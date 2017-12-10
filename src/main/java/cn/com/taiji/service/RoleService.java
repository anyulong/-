package cn.com.taiji.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.RoleRepository;
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
	 * @Description: 根据ID查询角色
	 * @param id
	 * @return role  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public Role findRoleById(int id){
		return roleRepository.findOne(id);
	}
	/**
	 * 
	 * @Description: 插入或更新一个角色
	 * @param roleDto void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void updRole(RoleDto roleDto){
		Role role = new Role();
		BeanUtils.copyProperties(roleDto,role);
		role.setState(1);
		roleRepository.saveAndFlush(role);
	}
	/**
	 * 
	 * @Description: 逻辑删除
	 * @param id void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void deleteRole(String id) {
		roleRepository.deleteRole(id);
	}
	
	
}

