package cn.com.taiji.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.Menu;
import cn.com.taiji.domain.MenuRepository;
import cn.com.taiji.dto.MenuDto;
@Service
public class MenuService {
	
	@Autowired
	private MenuRepository menuRepository;
	
	/**
	 * 
	 * @Description: 查询所有菜单
	 * @return List<Menu>  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public List<Menu> findAllMenus(){
		return menuRepository.findAll();
	}
	/**
	 * 
	 * @Description: 根据ID查询菜单
	 * @param id
	 * @return Menu  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public Menu findMenuById(int id){
		return menuRepository.findOne(id);
	}
	/**
	 * 
	 * @Description: 插入或更新一个菜单
	 * @param menuDto void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void updMenu(MenuDto menuDto){
		Menu menu = new Menu();
		BeanUtils.copyProperties(menuDto,menu);
		menu.setState(1);
		menuRepository.saveAndFlush(menu);
	}
	/**
	 * 
	 * @Description: 逻辑删除
	 * @param id void  
	 * @throws
	 * @author anyulong
	 * @date 2017年12月8日
	 */
	public void deleteMenu(String id) {
		menuRepository.deleteMenu(id);
	}
	
	
}
