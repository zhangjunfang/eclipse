package cn.newcapec.foundation.tenant.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.newcapec.foundation.model.AppBaseModel;

/**
 * 租户实体类
 * 
 * @author: andy.li
 */
@Entity
@Table(name = "t_tenant")
public class Tenant extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = 8101378185861361450L;
	/* 租户名称 */
	private String name;
	/*开通日期*/
	private Date OpeningDate;
	/*结束日期*/
	private Date closeDate;
	/*是否可见*/
	private String visible;


	@Column(name = "name", length = 62)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "visible", length = 20)
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Opening_date")
	public Date getOpeningDate() {
		return OpeningDate;
	}

	public void setOpeningDate(Date openingDate) {
		OpeningDate = openingDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_date")
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	
	

}
