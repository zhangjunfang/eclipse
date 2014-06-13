package cn.newcapec.framework.core.model;

/**
 * 账户基类
 * 
 * @author Administrator
 * 
 */
public class BaseAccount implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 242416230124446179L;
	public String name;
	private String password;
	private String operatorId;
	private Boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}