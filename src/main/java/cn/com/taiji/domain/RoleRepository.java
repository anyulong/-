package cn.com.taiji.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{
	
	/**
	 * 
	 * @Description: 逻辑查询所有用户
	 * @return List<Role>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	@Query("SELECT r FROM Role r where r.state = 1")
	List<Role> queryRoles();
	/**
	 * 
	 * @Description: 逻辑删除
	 * @param id
	 * @return List<Role>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月10日
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Role r SET r.state = 0 WHERE r.id = :id")
	void deleteRole(@Param("id") String id);
}
