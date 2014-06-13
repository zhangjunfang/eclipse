package cn.newcapec.foundation.tenant.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.foundation.model.AppBaseModel;

/**
 * 数据源实体类
 * 
 * @author: andy.li
 */
@Entity
@Table(name = "t_data_source")
public class DataSource extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = 8101378185861361450L;
	/*租户编号*/
	private String tenantId;
	/* 数据源名称 */
	private String name;
	/*登录名称*/
	private String userName;
	/*密码*/
	private String password;
	/*链接地址*/
	private String url;
	/*驱动名称*/
	private String driverClassName;
	
	/*是否可见 0不可见 1 可见*/
	private String visible;


	@Column(name = "name", length = 62)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	@Column(name = "tenant_id", length = 32)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	
	@Column(name = "user_name", length = 32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", length = 45)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "url", length = 80)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "driver_class_name", length = 80)
	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	@Column(name = "visible", length = 20)
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	

}
