package cn.com.taiji.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */

public class MenuDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_time")
	private Date changeTime;

	private String changer;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String creater;

	private String level;

	@Column(name="menu_description")
	private String menuDescription;

	private int state;

	private String url;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="parent_id")
	private MenuDto menu;

	//bi-directional many-to-one association to Menu
	@OneToMany(mappedBy="menu")
	private List<MenuDto> menus;

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="menus")
	private List<RoleDto> roles;

	public MenuDto() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getChangeTime() {
		return this.changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getChanger() {
		return this.changer;
	}

	public void setChanger(String changer) {
		this.changer = changer;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMenuDescription() {
		return this.menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MenuDto getMenu() {
		return this.menu;
	}

	public void setMenu(MenuDto menu) {
		this.menu = menu;
	}

	public List<MenuDto> getMenus() {
		return this.menus;
	}

	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}

	public MenuDto addMenus(MenuDto menus) {
		getMenus().add(menus);
		menus.setMenu(this);

		return menus;
	}

	public MenuDto removeMenus(MenuDto menus) {
		getMenus().remove(menus);
		menus.setMenu(null);

		return menus;
	}

	public List<RoleDto> getRoles() {
		return this.roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

}