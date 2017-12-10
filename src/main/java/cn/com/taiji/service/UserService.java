package cn.com.taiji.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.User;
import cn.com.taiji.domain.UserRepository;
import cn.com.taiji.dto.UserDto;
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
	public User findUserById(int id){
		return userRepository.findOne(id);
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
		User user = new User();
		BeanUtils.copyProperties(userDto,user);
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
	
	
}
