package cn.com.taiji.dto;

import java.util.ArrayList;
import java.util.List;

import cn.com.taiji.domain.Department;

public class TreeDto {
	
	private String text;
	private String icon;
	private String selectedIcon;
	private String color;
	private String backColor;
	private String href;
	private List<TreeDto> nodes;
	
	public TreeDto(Department department) {
		
		this.text = department.getDepartmentName();
		
		List<Department> departments =  department.getDepartments();
		nodes=new ArrayList<TreeDto>();
		if(null!=departments) {
		for (Department d : departments) {
			String name = d.getDepartmentName();
			TreeDto treeDto = new TreeDto(d);
			nodes.add(treeDto);
		} 
		}
	}

	@Override
	public String toString() {
		return "TreeDto [text=" + text + ", nodes=" + nodes + "]";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSelectedIcon() {
		return selectedIcon;
	}

	public void setSelectedIcon(String selectedIcon) {
		this.selectedIcon = selectedIcon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<TreeDto> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeDto> nodes) {
		this.nodes = nodes;
	} 


		
}
