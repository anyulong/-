package cn.com.taiji.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private int age;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_time")
	private Date changeTime;

	private String changer;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String creater;

	private String department;

	@Column(name="education_background")
	private String educationBackground;

	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="entry_date")
	private Date entryDate;

	private String gender;

	@Lob
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

	@Column(name="secondary_department")
	private String secondaryDepartment;

	private int state;

	@Column(name="user_city")
	private String userCity;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_number")
	private String userNumber;

	@Column(name="user_type")
	private String userType;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="user_role"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id")
			}
		)
	private List<Role> roles;

	public User() {
	}

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

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public String getSecondaryDepartment() {
		return this.secondaryDepartment;
	}

	public void setSecondaryDepartment(String secondaryDepartment) {
		this.secondaryDepartment = secondaryDepartment;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUserCity() {
		return this.userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNumber() {
		return this.userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}