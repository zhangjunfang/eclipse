package cn.newcapec.function.ecardcity.om.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.newcapec.framework.core.handler.springDatebind.CustomDateSerializer;
import cn.newcapec.framework.core.model.CommonModel;

/**
 * 客户实体类
 * 
 * @author wangjian
 * 
 */
@Table(name = "wwww")
@Entity
public class Customer extends CommonModel {
    private static final long serialVersionUID = -7308119212639431688L;
   
    @Column(name = "address_eeeee", length = 100)
    private String address;
  
    private String rremark;
    @Column(name = "edit_person_wwww", length = 32)
    private String editPerson;
    

    /** 生日日期 */
    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthday")
    private Date birthday;
    /** 编辑日期 */
    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "edit_date")
    private Date editDate;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "rremark_fffff", length = 200)
	public String getRremark() {
		return rremark;
	}
	public void setRremark(String rremark) {
		this.rremark = rremark;
	}
	public String getEditPerson() {
		return editPerson;
	}
	public void setEditPerson(String editPerson) {
		this.editPerson = editPerson;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

   
  

}
