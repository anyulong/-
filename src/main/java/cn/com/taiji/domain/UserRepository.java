package cn.com.taiji.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	/**
	 * 
	 * @Description: 逻辑查询所有用户
	 * @return List<User>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	@Query("SELECT u FROM User u where u.state = 1")
	List<User> queryUsers();
	/**
	 * 
	 * @Description: 逻辑删除
	 * @param id
	 * @return List<User>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月10日
	 */
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.state = 0 WHERE u.id = :id")
	void deleteUser(@Param("id") String id);
		
}
