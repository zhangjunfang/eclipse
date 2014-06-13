/**
 * 
 */
package cn.newcapec.function.ecardcity.om.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.AppBaseModel;

/**
 * @author ocean
 * email: zhangjunfang0505@163.com
 * QQ: 419692181
 * date: 2014-3-14
 */
@Table(name = "TERM_INFO")
@Entity
public class TermInfo extends AppBaseModel  implements Serializable   {

	private static final long serialVersionUID = -1248873243264221832L;

	//属性
	private String termname;
	private String merchantcode;
	private String poscode;
	private String isuse;
	private String paramgroupid;
	private String typeid;
	private String status;
	private java.util.Date lastcollectdate;
	private Double psamcardno;
	private java.util.Date lastupdatedate;
	private String collectid;
	private String siteid;
	private Integer isauth;
	private String workingkey;
	private String kekcode;
	private Integer appid;
	private String kekencryptkey;
	private String isdelete;
	private Integer ver;
	
	
	public TermInfo() {
		super();
	}


	/**
	 * @param termname
	 * @param merchantcode
	 * @param poscode
	 * @param isuse
	 * @param paramgroupid
	 * @param typeid
	 * @param status
	 * @param lastcollectdate
	 * @param psamcardno
	 * @param lastupdatedate
	 * @param collectid
	 * @param siteid
	 * @param isauth
	 * @param workingkey
	 * @param kekcode
	 * @param appid
	 * @param kekencryptkey
	 * @param isdelete
	 * @param ver 
	 */
	public TermInfo(String termname, String merchantcode, String poscode,
			String isuse, String paramgroupid, String typeid, String status,
			Date lastcollectdate, Double psamcardno, Date lastupdatedate,
			String collectid, String siteid, Integer isauth, String workingkey,
			String kekcode, Integer appid, String kekencryptkey,
			String isdelete, Integer ver) {
		super();
		this.termname = termname;
		this.merchantcode = merchantcode;
		this.poscode = poscode;
		this.isuse = isuse;
		this.paramgroupid = paramgroupid;
		this.typeid = typeid;
		this.status = status;
		this.lastcollectdate = lastcollectdate;
		this.psamcardno = psamcardno;
		this.lastupdatedate = lastupdatedate;
		this.collectid = collectid;
		this.siteid = siteid;
		this.isauth = isauth;
		this.workingkey = workingkey;
		this.kekcode = kekcode;
		this.appid = appid;
		this.kekencryptkey = kekencryptkey;
		this.isdelete = isdelete;
		this.ver = ver;
	}


	public String getTermname() {
		return termname;
	}


	public void setTermname(String termname) {
		this.termname = termname;
	}


	public String getMerchantcode() {
		return merchantcode;
	}


	public void setMerchantcode(String merchantcode) {
		this.merchantcode = merchantcode;
	}


	public String getPoscode() {
		return poscode;
	}


	public void setPoscode(String poscode) {
		this.poscode = poscode;
	}


	public String getIsuse() {
		return isuse;
	}


	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}


	public String getParamgroupid() {
		return paramgroupid;
	}


	public void setParamgroupid(String paramgroupid) {
		this.paramgroupid = paramgroupid;
	}


	public String getTypeid() {
		return typeid;
	}


	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public java.util.Date getLastcollectdate() {
		return lastcollectdate;
	}


	public void setLastcollectdate(java.util.Date lastcollectdate) {
		this.lastcollectdate = lastcollectdate;
	}

   
	public Double getPsamcardno() {
		return psamcardno;
	}


	public void setPsamcardno(Double  psamcardno) {
		this.psamcardno = psamcardno;
	}


	public java.util.Date getLastupdatedate() {
		return lastupdatedate;
	}


	public void setLastupdatedate(java.util.Date lastupdatedate) {
		this.lastupdatedate = lastupdatedate;
	}


	public String getCollectid() {
		return collectid;
	}


	public void setCollectid(String collectid) {
		this.collectid = collectid;
	}


	public String getSiteid() {
		return siteid;
	}


	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}


	public Integer getIsauth() {
		return isauth;
	}


	public void setIsauth(Integer isauth) {
		this.isauth = isauth;
	}


	public String getWorkingkey() {
		return workingkey;
	}


	public void setWorkingkey(String workingkey) {
		this.workingkey = workingkey;
	}


	public String getKekcode() {
		return kekcode;
	}


	public void setKekcode(String kekcode) {
		this.kekcode = kekcode;
	}


	public Integer getAppid() {
		return appid;
	}


	public void setAppid(Integer appid) {
		this.appid = appid;
	}


	public String getKekencryptkey() {
		return kekencryptkey;
	}


	public void setKekencryptkey(String kekencryptkey) {
		this.kekencryptkey = kekencryptkey;
	}


	public String getIsdelete() {
		return isdelete;
	}


	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}


	public Integer getVer() {
		return ver;
	}


	public void setVer(Integer ver) {
		this.ver = ver;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appid == null) ? 0 : appid.hashCode());
		result = prime * result
				+ ((collectid == null) ? 0 : collectid.hashCode());
		result = prime * result
				+ ((merchantcode == null) ? 0 : merchantcode.hashCode());
		result = prime * result
				+ ((psamcardno == null) ? 0 : psamcardno.hashCode());
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
		TermInfo other = (TermInfo) obj;
		if (appid == null) {
			if (other.appid != null)
				return false;
		} else if (!appid.equals(other.appid))
			return false;
		if (collectid == null) {
			if (other.collectid != null)
				return false;
		} else if (!collectid.equals(other.collectid))
			return false;
		if (merchantcode == null) {
			if (other.merchantcode != null)
				return false;
		} else if (!merchantcode.equals(other.merchantcode))
			return false;
		if (psamcardno == null) {
			if (other.psamcardno != null)
				return false;
		} else if (!psamcardno.equals(other.psamcardno))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TermInfo [termname=");
		builder.append(termname);
		builder.append(", merchantcode=");
		builder.append(merchantcode);
		builder.append(", poscode=");
		builder.append(poscode);
		builder.append(", isuse=");
		builder.append(isuse);
		builder.append(", paramgroupid=");
		builder.append(paramgroupid);
		builder.append(", typeid=");
		builder.append(typeid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", lastcollectdate=");
		builder.append(lastcollectdate);
		builder.append(", psamcardno=");
		builder.append(psamcardno);
		builder.append(", lastupdatedate=");
		builder.append(lastupdatedate);
		builder.append(", collectid=");
		builder.append(collectid);
		builder.append(", siteid=");
		builder.append(siteid);
		builder.append(", isauth=");
		builder.append(isauth);
		builder.append(", workingkey=");
		builder.append(workingkey);
		builder.append(", kekcode=");
		builder.append(kekcode);
		builder.append(", appid=");
		builder.append(appid);
		builder.append(", kekencryptkey=");
		builder.append(kekencryptkey);
		builder.append(", isdelete=");
		builder.append(isdelete);
		builder.append(", ver=");
		builder.append(ver);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
