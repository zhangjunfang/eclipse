package cn.newcapec.foundation.report.core.ds;

import java.sql.Connection;

import cn.newcapec.foundation.report.core.DatabaseType;

/**
 * 数据库类型的数据源
 * @author shikeying
 *
 */
public interface DatabaseSource extends DataSource {

	Connection getConnection();
	
	void releaseConnection(Connection conn);
	
	DatabaseType getDatabaseType();
	
	void setDatabaseType(DatabaseType type);
}
