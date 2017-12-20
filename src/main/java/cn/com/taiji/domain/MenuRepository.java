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
public interface MenuRepository extends JpaRepository<Menu,String>,JpaSpecificationExecutor<Menu>,PagingAndSortingRepository<Menu,String>{

	
	
	/**
	 * 
	 * @Description: 逻辑查询所有用户
	 * @return List<Menu>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	@Query("SELECT m FROM Menu m where m.state = 1")
	List<Menu> queryMenus();
	/**
	 * 
	 * @Description: 逻辑删除
	 * @param id
	 * @return List<Menu>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月10日
	 */
	@Transactional
	@Modifying
	@Query("UPDATE Menu m SET m.state = 0 WHERE m.id = :id")
	void deleteMenu(@Param("id") String id);
	/**
	 * 
	 * @Description: 根据ID进行查询
	 * @param id
	 * @return Menu  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月11日
	 */
	@Query("SELECT m FROM Menu m where m.state = 1 and m.id = :id")
	Menu findMenuById(@Param("id") String id);
}
