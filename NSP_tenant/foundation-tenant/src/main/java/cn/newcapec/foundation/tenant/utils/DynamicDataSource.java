package cn.newcapec.foundation.tenant.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import cn.newcapec.foundation.tenant.biz.DataSourceService;
import cn.newcapec.foundation.tenant.model.DataSource;
import cn.newcapec.framework.base.log.LogEnabled;
import cn.newcapec.framework.utils.SpringConext;
import cn.newcapec.framework.utils.context.HttpNewcapecContext;
import cn.newcapec.framework.utils.context.Keys;

import com.mchange.v2.c3p0.ComboPooledDataSource;


@SuppressWarnings("all")
public class DynamicDataSource extends AbstractRoutingDataSource implements LogEnabled{
	
//	@Override
//    protected Object determineCurrentLookupKey() { 
//		log.info("DynamicDataSource:"+HttpNewcapecContext.getContext().getAttribute(Keys.DATA_SOURCE_URL));
//        return HttpNewcapecContext.getContext().getAttribute(Keys.DATA_SOURCE_URL);//MultiDatasourceContextHelper.getDatasource();    
//     }
//
	/*数据源服务接口*/
	static  DataSourceService dataSourceService ;//= SpringBusinessConext.getApplicationContext().getBean(DataSourceService.class);
	/*数据源Cache集合*/
     private Map<Object, Object> _dataSourcesCache;
		 
		    /**
		     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
		     * @describe 数据源为空或者为0时，自动切换至默认数据源，即在配置文件中定义的dataSource数据源
		     */
		    @Override
		    protected Object determineCurrentLookupKey() {
//		        String dataSourceName = DBContextHolder.getDBType();
		    	//获得租户编号
		    	String dataSourceName = "fdfd";//(String)HttpNewcapecContext.getContext().getAttribute(Keys.DATA_SOURCE_URL);
		    	String tentantId="1";
		        if (dataSourceName == null) {
		        	dataSourceName = "dataSource";
		        } else {
		            this.selectDataSource(tentantId);
		            if (dataSourceName.equals("0"))
		            	dataSourceName = "dataSource";
		        }
		        log.error("--------> use datasource " + dataSourceName);
//		        return dataSourceName;
		        return HttpNewcapecContext.getContext().getAttribute(Keys.DATA_SOURCE_URL);//MultiDatasourceContextHelper.getDatasource();  
		    }
		 
		    @Override
		    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		        this._dataSourcesCache = targetDataSources;
		        super.setTargetDataSources(this._dataSourcesCache);
		        afterPropertiesSet();
		    }
		 
		    public void addTargetDataSource(String key, ComboPooledDataSource dataSource) {
//		        this._targetDataSources.put(key, dataSource);
		        this.setTargetDataSources(this._dataSourcesCache);
		    }
		 
		
		 
		    /**
		     * 获取数据源
		     * 如缓存中不存在，将新的数据源添加到缓存
		     */
		    public void selectDataSource(String tentantId) {
		    	if(StringUtils.isNotBlank(tentantId)){
		    		ComboPooledDataSource dasicDataSource = (ComboPooledDataSource)_dataSourcesCache.get(tentantId);
		    		if(dasicDataSource==null){
		    			dasicDataSource= this.getDataSource(tentantId);
		    			_dataSourcesCache.put(tentantId, dasicDataSource);
		    		}else{
		    			this.setDataSource(tentantId, dasicDataSource);
		    		}
		    			
		    	}
		    }
		 
		    /**
		     * 通过租户编号获取数据源信息
		     * @param tentantId
		     * @return
		     */
		    public ComboPooledDataSource getDataSource(String tentantId) {
		    	//选择默认的数据源
		        this.selectDataSource(tentantId);
		        this.determineCurrentLookupKey();
		        /*创建dbcp的数据源*/
				        try {

				        	ComboPooledDataSource dataSource =null;
				            Connection conn = null; 
				            conn = this.getConnection();
				            PreparedStatement ps = conn
				                    .prepareStatement("select * from t_data_source  where tenant_id=?"); 
				            ps.setString(1, tentantId);
				            ResultSet rs = ps.executeQuery(); 
				            
//				        	DataSource ds = SpringConext.getApplicationContext().getBean(DataSourceService.class).findDataSourceByTentantId(tentantId);
				        	if(null!=rs){
				        		dataSource = new ComboPooledDataSource();
							dataSource.setDriverClass(rs.getString("driver_class_name"));
							dataSource.setJdbcUrl(rs.getString("url"));
							dataSource.setUser(rs.getString("user_name"));
							dataSource.setPassword(rs.getString("password"));
				        	}
					
				        	return dataSource;
		        }	catch (Exception e) {
		        	log.error(ExceptionUtils.getFullStackTrace(e));
				}
		            return null;
		        }
		 
		    /**
		     * @param tentantId
		     * @param dataSource
		     */
		    public void setDataSource(String tentantId, ComboPooledDataSource dataSource) {
		        this.addTargetDataSource(tentantId , dataSource);
		    } 
}
