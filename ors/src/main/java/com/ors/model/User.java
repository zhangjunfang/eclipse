package com.ors.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = 1274802950768424974L;
	/**
	 * 主键
	 * */
	private Integer id;//
	/**
	 * 姓名
	 * */
	private String name;
	/**
	 * 性别
	 * */
	private String sex;
	/**
	 * 身份证号
	 * */
	private String ids;
	/**
	 * 政治面貌
	 * */
	private String politicsStatus;
	/**
	 * 民族
	 * */
	private String nation;
	/**
	 * 籍贯
	 * */
	private String address;
	/**
	 * 毕业学校
	 * */
	private String graduation;
	/**
	 * 毕业时间
	 * */
	private Date date;
	/**
	 * 学历
	 * */
	private String education;
	/**
	 * 专业
	 * */
	private String specialty;
	/**
	 * 工作单位
	 * */
	private String unit;
	/**
	 * 所在部门
	 * */
	private String department;
	/**
	 * 从事专业
	 * */
	private String workoccupation;
	/**
	 * 职位
	 * */
	private String job;
	/**
	 * 职称
	 * */
	private String professional;
	/**
	 * 联系电话
	 * */
	private String telephone;
	/**
	 * 联系地址
	 * */
	private String contactaddress;

	/**
	 *
	 */
	public User() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param sex
	 * @param ids
	 * @param politicsStatus
	 * @param nation
	 * @param address
	 * @param graduation
	 * @param date
	 * @param education
	 * @param specialty
	 * @param unit
	 * @param department
	 * @param workoccupation
	 * @param job
	 * @param professional
	 * @param telephone
	 * @param contactaddress
	 */
	public User(Integer id, String name, String sex, String ids,
			String politicsStatus, String nation, String address,
			String graduation, Date date, String education, String specialty,
			String unit, String department, String workoccupation, String job,
			String professional, String telephone, String contactaddress) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.ids = ids;
		this.politicsStatus = politicsStatus;
		this.nation = nation;
		this.address = address;
		this.graduation = graduation;
		this.date = date;
		this.education = education;
		this.specialty = specialty;
		this.unit = unit;
		this.department = department;
		this.workoccupation = workoccupation;
		this.job = job;
		this.professional = professional;
		this.telephone = telephone;
		this.contactaddress = contactaddress;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(String politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGraduation() {
		return graduation;
	}

	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getWorkoccupation() {
		return workoccupation;
	}

	public void setWorkoccupation(String workoccupation) {
		this.workoccupation = workoccupation;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContactaddress() {
		return contactaddress;
	}

	public void setContactaddress(String contactaddress) {
		this.contactaddress = contactaddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((contactaddress == null) ? 0 : contactaddress.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		result = prime * result
				+ ((education == null) ? 0 : education.hashCode());
		result = prime * result
				+ ((graduation == null) ? 0 : graduation.hashCode());
		result = prime * result + id;
		result = prime * result + ((ids == null) ? 0 : ids.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nation == null) ? 0 : nation.hashCode());
		result = prime * result
				+ ((politicsStatus == null) ? 0 : politicsStatus.hashCode());
		result = prime * result
				+ ((professional == null) ? 0 : professional.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((specialty == null) ? 0 : specialty.hashCode());
		result = prime * result
				+ ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result
				+ ((workoccupation == null) ? 0 : workoccupation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (contactaddress == null) {
			if (other.contactaddress != null)
				return false;
		} else if (!contactaddress.equals(other.contactaddress))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (education == null) {
			if (other.education != null)
				return false;
		} else if (!education.equals(other.education))
			return false;
		if (graduation == null) {
			if (other.graduation != null)
				return false;
		} else if (!graduation.equals(other.graduation))
			return false;
		if (id != other.id)
			return false;
		if (ids == null) {
			if (other.ids != null)
				return false;
		} else if (!ids.equals(other.ids))
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nation == null) {
			if (other.nation != null)
				return false;
		} else if (!nation.equals(other.nation))
			return false;
		if (politicsStatus == null) {
			if (other.politicsStatus != null)
				return false;
		} else if (!politicsStatus.equals(other.politicsStatus))
			return false;
		if (professional == null) {
			if (other.professional != null)
				return false;
		} else if (!professional.equals(other.professional))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (specialty == null) {
			if (other.specialty != null)
				return false;
		} else if (!specialty.equals(other.specialty))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (workoccupation == null) {
			if (other.workoccupation != null)
				return false;
		} else if (!workoccupation.equals(other.workoccupation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"id\":\"");
		builder.append(id);
		builder.append("\", \"name\":\"");
		builder.append(name);
		builder.append("\", \"sex\":\"");
		builder.append(sex);
		builder.append("\", \"ids\":\"");
		builder.append(ids);
		builder.append("\", \"politicsStatus\":\"");
		builder.append(politicsStatus);
		builder.append("\", \"nation\":\"");
		builder.append(nation);
		builder.append("\", \"address\":\"");
		builder.append(address);
		builder.append("\", \"graduation\":\"");
		builder.append(graduation);
		builder.append("\", \"date\":\"");
		builder.append(date);
		builder.append("\", \"education\":\"");
		builder.append(education);
		builder.append("\", \"specialty\":\"");
		builder.append(specialty);
		builder.append("\", \"unit\":\"");
		builder.append(unit);
		builder.append("\", \"department\":\"");
		builder.append(department);
		builder.append("\", \"workoccupation\":\"");
		builder.append(workoccupation);
		builder.append("\", \"job\":\"");
		builder.append(job);
		builder.append("\", \"professional\":\"");
		builder.append(professional);
		builder.append("\", \"telephone\":\"");
		builder.append(telephone);
		builder.append("\", \"contactaddress\":\"");
		builder.append(contactaddress);
		builder.append("\"}");
		return builder.toString();
	}

}