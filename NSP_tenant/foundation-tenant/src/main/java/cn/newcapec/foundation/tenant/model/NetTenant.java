package cn.newcapec.foundation.tenant.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.foundation.model.AppBaseModel;

/**
 * 租户实体类
 * 
 * @author: andy.li
 */
@Entity
@Table(name = "t_net_tenant")
public class NetTenant extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = 8101378185861361450L;
	/* 域名编号 */
	private String netId;
	/* 租户编号 */
	private String tenantId;

	@Column(name = "net_id", length = 32)
	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	@Column(name = "tenant_id", length = 32)
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
