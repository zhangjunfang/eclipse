package cn.newcapec.foundation.report.core.ds;

import java.io.Serializable;

/**
 * 数据源对象定义
 * @author shikeying
 * @date 2013-7-16
 *
 */
public interface DataSource extends Serializable {

	String getAddress();
	
	void setAddress(String address);
	
	String getService();
	
	void setService(String service);
	
	String getUsername();
	
	void setUsername(String username);
	
	String getPassword();
	
	void setPassword(String password);
}
