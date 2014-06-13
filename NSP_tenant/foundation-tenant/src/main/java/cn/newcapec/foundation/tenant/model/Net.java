package cn.newcapec.foundation.tenant.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.foundation.model.AppBaseModel;

/**
 * 域名实体类
 * 
 * @author: andy.li
 */
@Entity
@Table(name = "t_net")
public class Net extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = 8101378185861361450L;
	/* 域名名称 */
	private String name;
	/* 域名地址 */
	private String url;
	/* 是否开通 1开通 0未开通 */
	private String visible;

	@Column(name = "name", length = 62)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", length = 62)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "visible", length = 20)
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

}
