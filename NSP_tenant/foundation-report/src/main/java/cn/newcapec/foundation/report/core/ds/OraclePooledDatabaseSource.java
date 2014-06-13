package cn.newcapec.foundation.report.core.ds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newcapec.foundation.report.core.DatabaseType;
import cn.newcapec.foundation.report.util.NanoReportService;

public class OraclePooledDatabaseSource extends AbstractDatabaseSource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3912957668386197892L;
	
	private static final Log logger = LogFactory.getLog(OraclePooledDatabaseSource.class);
	
	private OracleDataSource ods = null;
	
	private StringBuilder urlPrefix = new StringBuilder("jdbc:oracle:thin:@");
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// define configuration parameter for OracleDataSource
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final String                NAME_MIN_LIMITED = "pooled.datasource.oracle.MinLimit";
	private static final String                NAME_MAX_LIMITED = "pooled.datasource.oracle.MaxLimit";
	private static final String            NAME_INITIAL_LIMITED = "pooled.datasource.oracle.InitialLimit";
	private static final String          NAME_INACTIVITYTIMEOUT = "pooled.datasource.oracle.InactivityTimeout";
	private static final String NAME_ABANDONEDCONNECTIONTIMEOUT = "pooled.datasource.oracle.AbandonedConnectionTimeout";
	private static final String         NAME_MAXSTATEMENTSLIMIT = "pooled.datasource.oracle.MaxStatementsLimit";
	private static final String      NAME_PROPERTYCHECKINTERVAL = "pooled.datasource.oracle.PropertyCheckInterval";
	
	public OraclePooledDatabaseSource() {
		super();
		try {
			this.ods = new OracleDataSource();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Error("init '" + this.getClass().getName() + "' failed.");
		}
	}
	
	private void configODS(){
		Properties properties = NanoReportService.getSystemConfig();
		if(properties == null)
			throw new NullPointerException(NanoReportService.CONFIG_FILE_NAME + " has not been initialized!");
		
		Properties prop = new Properties();
	    prop.setProperty("MinLimit", properties.getProperty(NAME_MIN_LIMITED));
	    prop.setProperty("MaxLimit", properties.getProperty(NAME_MAX_LIMITED));
	    prop.setProperty("InitialLimit", properties.getProperty(NAME_INITIAL_LIMITED)); // create 3 connections at startup
	    prop.setProperty("InactivityTimeout", properties.getProperty(NAME_INACTIVITYTIMEOUT));    //  seconds
	    prop.setProperty("AbandonedConnectionTimeout", properties.getProperty(NAME_ABANDONEDCONNECTIONTIMEOUT));  //  seconds
	    prop.setProperty("MaxStatementsLimit", properties.getProperty(NAME_MAXSTATEMENTSLIMIT));
	    prop.setProperty("PropertyCheckInterval", properties.getProperty(NAME_PROPERTYCHECKINTERVAL)); // seconds

	    // set DataSource properties
//	    String url = "jdbc:oracle:thin:@192.168.131.92:1521:orcl";
//	    ods.setURL(url);
	    try {
	    	
	    	OracleConnectionCacheManager occm =  OracleConnectionCacheManager.getConnectionCacheManagerInstance();
			if(occm.existsCache("ImplicitCache01")) return;
	    	
			ods.setConnectionCachingEnabled(true);
			ods.setConnectionCacheProperties (prop);
			ods.setConnectionCacheName("ImplicitCache01"); // this cache's name
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Error("create OracleDataSource failed!");
		}
	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		try {
			return ods.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return getEmergencyConnection();
		}
	}
	
	private Connection getEmergencyConnection(){
		// 后面继续补充
		throw new UnsupportedOperationException("创建紧急 Connection 对象代码未实现!");
	}

	@Override
	public void releaseConnection(Connection conn) {
		// TODO Auto-generated method stub
		try {
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("close connection failed! errorCode = " + e.getErrorCode());
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public DatabaseType getDatabaseType() {
		// TODO Auto-generated method stub
		return DatabaseType.ORACLE;
	}
	
	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub
		assert (address != null && !address.equals(""));
		super.setAddress(address);
		urlPrefix.append(address).append(":1521");
	}
	
	@Override
	public void setService(String service) {
		// TODO Auto-generated method stub
		super.setService(service);
		urlPrefix.append(":").append(service);
	}
	
	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		super.setUsername(username);
		ods.setUser(username);
	}
	
	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		super.setPassword(password);
		ods.setPassword(password);
		
		configODS();
		ods.setURL(urlPrefix.toString());
	}
	
	public static void main(String[] args) throws SQLException {
		
//		// Get singleton ConnectionCacheManager instance
//		  OracleConnectionCacheManager occm =
//		  OracleConnectionCacheManager.getConnectionCacheManagerInstance();
//		  String cacheName = "foo";  // Look for a specific cache
		  // Use Cache Manager to check # of available connections 
		  // and active connections
//		  System.out.println(occm.getNumberOfAvailableConnections(cacheName)+
//		      " connections are available in cache " + cacheName);
//		 
//		  System.out.println(occm.getNumberOfActiveConnections(cacheName)
//		      + " connections are active");
//		  // Refresh all connections in cache 
//		  occm.refreshCache(cacheName,
//		    OracleConnectionCacheManager.REFRESH_ALL_CONNECTIONS);
//		  // Reinitialize cache, closing all connections
//		  java.util.Properties newProp = new java.util.Properties();
//		  newProp.setProperty("MaxLimit", "50");
//		  occm.reinitializeCache(cacheName, newProp);
		
		// create a DataSource
	    OracleDataSource ods = new OracleDataSource();

	    // set cache properties
	    java.util.Properties prop = new java.util.Properties();
	    prop.setProperty("MinLimit", "2");
	    prop.setProperty("MaxLimit", "4");
	    prop.setProperty("InitialLimit", "3"); // create 3 connections at startup
	    prop.setProperty("InactivityTimeout", "1800");    //  seconds
	    prop.setProperty("AbandonedConnectionTimeout", "900");  //  seconds
	    prop.setProperty("MaxStatementsLimit", "10");
	    prop.setProperty("PropertyCheckInterval", "60"); // seconds

	    // set DataSource properties
	    String url = "jdbc:oracle:thin:@192.168.131.92:1521:orcl";
	    ods.setURL(url);
	    ods.setUser("test");
	    ods.setPassword("test");
	    ods.setConnectionCachingEnabled(true); // be sure set to true
	    ods.setConnectionCacheProperties (prop);
	    ods.setConnectionCacheName("ImplicitCache01"); // this cache's name
	    
	    Connection conn1 = null;
	    conn1 = ods.getConnection();
	    if (conn1 != null)
	      System.out.println("Connection 1 " + " Succeeded!");
	    else
	      System.out.println("Connection 1 " + " Failed !!!");

	    Connection conn2 = null;
	    conn2 = ods.getConnection();
	    if (conn2 != null)
	      System.out.println("Connection 2 " + " Succeeded!");
	    else
	      System.out.println("Connection 2 " + " Failed !!!");

	    conn1.close();
	    conn2.close();
	    ods.close();
//	    ods.close();
//	    Connection conn3 = null;
//	    conn3 = ods.getConnection();
//	    if (conn3 != null)
//	      System.out.println("Connection 3 " + " Succeeded!");
//	    else
//	      System.out.println("Connection 3 " + " Failed !!!");
//
//	    Connection conn4 = null;
//	    conn4 = ods.getConnection();
//	    if (conn4 != null)
//	      System.out.println("Connection 4 " + " Succeeded!");
//	    else
//	      System.out.println("Connection 4 " + " Failed !!!");
//
//	    Connection conn5 = null;
//	    conn5 = ods.getConnection();
//	    if (conn5 != null)
//	      System.out.println("Connection 5 " + " Succeeded!");
//	    else
//	      System.out.println("Connection 5 " + " Failed !!!");
//	    
//	    Connection conn6 = null;
//	    conn6 = ods.getConnection();
//	    if (conn6 != null)
//	      System.out.println("Connection 6 " + " Succeeded!");
//	    else
//	      System.out.println("Connection 6 " + " Failed !!!");
	    
	    OracleConnectionCacheManager occm =  OracleConnectionCacheManager.getConnectionCacheManagerInstance();
//	    occm.existsCache(arg0)
	  		  String cacheName = "ImplicitCache01";  // Look for a specific cache
	  		  // Use Cache Manager to check # of available connections 
	  		  // and active connections
	  		  System.out.println(occm.getNumberOfAvailableConnections(cacheName)+
	  		      " connections are available in cache " + cacheName);
	  		 
	  		  System.out.println(occm.getNumberOfActiveConnections(cacheName)
	  		      + " connections are active");

//
//	    System.out.println("Active size : " + ((OracleConnectionCacheImpl) ods).getActiveSize());
//	    System.out.println("Cache Size is " + ((OracleConnectionCacheImpl) ods).getCacheSize());
//
//	    // Close 3 logical Connections
//	    conn1.close();
//	    conn2.close();
//	    conn3.close();
//
//	    System.out.println("Active size : " + ((OracleConnectionCacheImpl) ods).getActiveSize());
//	    System.out.println("Cache Size is " + ((OracleConnectionCacheImpl) ods).getCacheSize());
//
//	    // close the Data Source
//	    ods.close();
//
//	    System.out.println("Active size : " + ((OracleConnectionCacheImpl) ods).getActiveSize());
//	    System.out.println("Cache Size is " + ((OracleConnectionCacheImpl) ods).getCacheSize());
	}

}
