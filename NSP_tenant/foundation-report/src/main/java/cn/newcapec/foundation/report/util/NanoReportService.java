package cn.newcapec.foundation.report.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

/**
 * 报表服务对象，它维护报表系统常用的各种对象，并提供相应服务
 * @author shikeying
 * @date 2013-8-1
 *
 */
public abstract class NanoReportService {

	private static final transient Log logger = LogFactory.getLog(NanoReportService.class);
	
	private static DataSource injectedDataSource = null;
	
	private static JdbcTemplate jdbcTemplate = null;
	
	private static final Map<String, String> transactionServiceClazz = new HashMap<String, String>(8);
	
	private static Map<String, Object> serviceCachedMap;
	
	private static Properties properties;
	
	public static final String CONFIG_FILE_NAME = "walker-nano-report.properties";
	
	/**
	 * 返回配置文件信息</br>
	 * 如果还未加载，就初始化配置文件
	 * @return
	 */
	public static final Properties getSystemConfig(){
		if(properties == null){
			Resource resource = new ClassPathResource("cn/newcapec/foundation/report/" + CONFIG_FILE_NAME);
//			Resource resource = new ClassPathResource("com/walker/" + CONFIG_FILE_NAME);
			properties = new Properties();
			try {
				properties.load(resource.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new Error("config file not found: " + CONFIG_FILE_NAME);
			}
		}
		logger.debug("============ " + CONFIG_FILE_NAME + " ============");
		logger.debug(properties);
		return properties;
	}
	
	/**
	 * 根据名字，返回配置的带事务控制的服务对象
	 * @param serviceName
	 * @return
	 */
	public static final Object getServiceProxy(String serviceName){
		Assert.notNull(serviceName);
		Assert.notNull(serviceCachedMap);
		return serviceCachedMap.get(serviceName);
	}
	
	/**
	 * 返回系统中配置的所有事务控制<code>Service</code>对象的类名集合
	 * @return
	 */
	public static final Map<String, String> getTransactionServiceClazz(){
		return transactionServiceClazz;
	}
	
	/**
	 * 返回业务系统注入的数据源对象，报表后台管理的数据库连接都依赖此对象
	 * @return
	 */
	public static final DataSource getInjectedDataSource(){
		if(injectedDataSource == null)
			throw new IllegalStateException("injectedDataSource is required!");
		return injectedDataSource;
	}
	
	public static final synchronized void setInjectedDataSource(DataSource ds){
		if(injectedDataSource == null){
			assert (ds != null);
			injectedDataSource = ds;
			
			jdbcTemplate = new JdbcTemplate(injectedDataSource);
			logger.info("#----------------------------------------------");
			logger.info("# create managed JdbcTemplate: " + jdbcTemplate);
			logger.info("#----------------------------------------------");
		}
	}
	
	public static final synchronized void setServiceCachedMap(Map<String, Object> map){
		if(serviceCachedMap == null){
			assert (map != null);
			serviceCachedMap = map;
			logger.info("#----------------------------------------------");
			logger.info("# set service proxy object map: " + map);
			logger.info("#----------------------------------------------");
		}
	}
	
	/**
	 * 返回报表后台管理功能所需要的 <code>JdbcTemplate</code> 对象
	 * @return
	 */
	public static final JdbcTemplate getManagedJdbcTemplate(){
		if(jdbcTemplate == null)
			throw new IllegalStateException("no one JdbcTemplate has been created in NanoReport startup.");
		return jdbcTemplate;
	}
	
	/**
	 * 初始化系统所有需要事务控制的<code>Service</code>对象
	 */
	static {
		transactionServiceClazz.put("testService", "com.newcapec.nanoreport.persistance.TestService");
		transactionServiceClazz.put("dsService", "com.newcapec.nanoreport.persistance.DatasourceService");
		transactionServiceClazz.put("datasetService", "com.newcapec.nanoreport.persistance.DatasetService");
		transactionServiceClazz.put("reportManageService", "com.newcapec.nanoreport.persistance.ReportManageService");
		transactionServiceClazz.put("widgetService", "com.newcapec.nanoreport.persistance.WidgetService");
		transactionServiceClazz.put("queryFromService", "com.newcapec.nanoreport.persistance.QueryFormService");

		//		transactionServiceClazz.put("aaa", "com.newcapec.nanoreport.persistance.TestService");
	}
}
