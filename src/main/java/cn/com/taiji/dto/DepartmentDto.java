package cn.com.taiji.dto;

import java.io.Serializable;
import javax.persistence.*;

import cn.com.taiji.domain.Department;
import cn.com.taiji.domain.Emp;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the departmentDtoDto database table.
 * 
 */

public class DepartmentDto implements Serializable {
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

	@Column(name="department_name")
	private String departmentName;

	private String description;

	private int state;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Department department;

	//bi-directional many-to-one association to Department
	@OneToMany(mappedBy="department")
	private List<Department> departments;

	//bi-directional many-to-many association to Emp
	@ManyToMany(mappedBy="departments")
	private List<Emp> emps;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getChanger() {
		return changer;
	}

	public void setChanger(String changer) {
		this.changer = changer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public List<Emp> getEmps() {
		return emps;
	}

	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}

	@Override
	public String toString() {
		return "DepartmentDto [id=" + id + ", departmentName=" + departmentName + ", department=" + department
				+ ", departments=" + departments + "]";
	}


}