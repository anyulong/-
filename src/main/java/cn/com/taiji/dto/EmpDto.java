package cn.com.taiji.dto;


import java.io.Serializable;
import javax.persistence.*;

import cn.com.taiji.domain.Department;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the empDto database table.
 * 
 */

@NamedQuery(name="Emp.findAll", query="SELECT e FROM Emp e")
public class EmpDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private int age;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@Temporal(TemporalType.DATE)
	@Column(name="change_time")
	private Date changeTime;

	private String changer;

	@Temporal(TemporalType.DATE)
	@Column(name="create_time")
	private Date createTime;

	private String creater;

	@Column(name="education_background")
	private String educationBackground;

	private String email;

	@Column(name="empDto_city")
	private String empDtoCity;

	@Column(name="empDto_name")
	private String empDtoName;

	@Column(name="empDto_number")
	private String empDtoNumber;

	@Column(name="empDto_type")
	private String empDtoType;

	@Temporal(TemporalType.DATE)
	@Column(name="entry_date")
	private Date entryDate;

	private String gender;

	@Column(name="head_portrait")
	private byte[] headPortrait;

	@Column(name="login_name")
	private String loginName;

	private String password;

	private String phone;

	@Column(name="position_level")
	private String positionLevel;

	@Column(name="position_name")
	private String positionName;

	@Column(name="position_sequence")
	private String positionSequence;

	private int state;

	//bi-directional many-to-many association to DepartmentDto
	@ManyToMany
	@JoinTable(
		name="empDto_departmentDto"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="dept_id")
			}
		)
	private List<Department> departments;

	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public String getEducationBackground() {
		return this.educationBackground;
	}

	public void setEducationBackground(String educationBackground) {
		this.educationBackground = educationBackground;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpCity() {
		return this.empDtoCity;
	}

	public void setEmpCity(String empDtoCity) {
		this.empDtoCity = empDtoCity;
	}

	public String getEmpName() {
		return this.empDtoName;
	}

	public void setEmpName(String empDtoName) {
		this.empDtoName = empDtoName;
	}

	public String getEmpNumber() {
		return this.empDtoNumber;
	}

	public void setEmpNumber(String empDtoNumber) {
		this.empDtoNumber = empDtoNumber;
	}

	public String getEmpType() {
		return this.empDtoType;
	}

	public void setEmpType(String empDtoType) {
		this.empDtoType = empDtoType;
	}

	public Date getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public byte[] getHeadPortrait() {
		return this.headPortrait;
	}

	public void setHeadPortrait(byte[] headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPositionLevel() {
		return this.positionLevel;
	}

	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionSequence() {
		return this.positionSequence;
	}

	public void setPositionSequence(String positionSequence) {
		this.positionSequence = positionSequence;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	

}