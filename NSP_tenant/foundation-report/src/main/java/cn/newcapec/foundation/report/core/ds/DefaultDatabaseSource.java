package cn.newcapec.foundation.report.core.ds;

import java.sql.Connection;
import java.sql.SQLException;

import cn.newcapec.foundation.report.core.DatabaseType;
import cn.newcapec.foundation.report.util.DataBaseConnection;
import cn.newcapec.foundation.report.util.OracleDataBaseConnection;

/**
 * 系统默认提供的数据源实现，这个是个非常基础的，提供测试使用
 * @author shikeying
 * @date 2013-7-16
 *
 */
public class DefaultDatabaseSource extends AbstractDatabaseSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1897573210723913626L;

	private DataBaseConnection dbc = new OracleDataBaseConnection();
	
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return dbc.getConnection();
	}

	public void releaseConnection(Connection conn) {
		// TODO Auto-generated method stub
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		}
	}

	@Override
	public DatabaseType getDatabaseType() {
		// TODO Auto-generated method stub
		return DatabaseType.ORACLE;
	}
}
