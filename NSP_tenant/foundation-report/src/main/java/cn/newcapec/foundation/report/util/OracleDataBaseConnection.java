package cn.newcapec.foundation.report.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDataBaseConnection implements DataBaseConnection {

//	private String url = "jdbc:oracle:thin:@192.168.131.118:1521:orcl";
	private String url = "jdbc:oracle:thin:@192.168.0.206:1521:newbus";
	private String user = "cppt";
	private String pass = "cppt";
	
//	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
//	private String user = "xyenterprise";
//	private String pass = "xyenterprise";
	
	public OracleDataBaseConnection(){
		setDriverClass("oracle.jdbc.driver.OracleDriver");
	}
	
	public Connection getConnection() {
		// TODO Auto-generated method stub
		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void setDriverClass(String clazz) {
		// TODO Auto-generated method stub
		assert (clazz != null && !clazz.equals(""));
		try {
			Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setUrl(String url) {
		// TODO Auto-generated method stub
		this.url = url;
	}

	public void setSecurity(String user, String pass) {
		// TODO Auto-generated method stub
		this.user = user;
		this.pass = pass;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
