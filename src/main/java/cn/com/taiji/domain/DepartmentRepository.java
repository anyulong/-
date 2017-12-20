package cn.com.taiji.domain;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface DepartmentRepository extends JpaRepository<Department,String>,JpaSpecificationExecutor<Department>,PagingAndSortingRepository<Department,String> {
	/**
	 * 
	 * @Description: 逻辑查询所有用户
	 * @return List<Department>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	@Query("SELECT u FROM Department u where u.state = 1")
	List<Department> queryDepartments();
	/**
	 * 
	 * @Description: 逻辑删除
	 * @param id
	 * @return List<Department>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月10日
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Department u SET u.state = 0 WHERE u.id = :id")
	void deleteDepartment(@Param("id") String id);
	
	/**
	 * 
	 * @Description: 根据ID进行查询
	 * @param id
	 * @return Department  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	@Query("SELECT u FROM Department u where u.state = 1 and u.id = :id")
	Department findDepartmentById(@Param("id") String id);
	
	/**
	 * 
	 * @Description: 根据name查询
	 * @param name
	 * @return Department  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月20日
	 */
	@Query("SELECT u FROM Department u where u.state = 1 and u.departmentName = :name")
	Department findDepartmentByName(@Param("name") String name);	
}

