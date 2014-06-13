package cn.newcapec.foundation.base;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ddlutils.PlatformUtils;
import org.hibernate.dialect.Oracle10gDialect;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import cn.newcapec.foundation.base.dialect.NewcapecMySQLDialect;
import cn.newcapec.framework.base.log.LogEnabled;

/**
 * @author Administrator
 *
 */
public class SessionFactoryAutoDialectBean extends AnnotationSessionFactoryBean implements LogEnabled {
	 private static final String PROPERTY_NAME_DIALECT = "hibernate.dialect";
	 	 
	 	    private static final String ORACLE_TYPE = "Oracle";
	 	 
	 	    private static final String MYSQL_TYPE = "MySQL";
		 
	 	    
	 	    private String dialect = null;
	 	 
	 	    @Override
	 	    public void setDataSource(DataSource dataSource) {
	 	 
	 	        PlatformUtils platformUtils = new PlatformUtils();
	 	        String dbType = platformUtils.determineDatabaseType(dataSource);
	         log.info("Database type is \"" + dbType + "\"");
	 	 
	 	        if (MYSQL_TYPE.equals(dbType)) {
	 	            dialect = NewcapecMySQLDialect.class.getName();

	 	        } else if (ORACLE_TYPE.equals(dbType)) {
	 	            dialect = Oracle10gDialect.class.getName();

	 	        } else {
	 	        	log.error("unknown database :" + dbType);
	 	        }
	 	 
	 	        super.setDataSource(dataSource);
	 	    }
	 	 
	 	    @Override
	 	    public void setHibernateProperties(Properties hibernateProperties) {
	 	 
	 	        if (hibernateProperties.containsKey(PROPERTY_NAME_DIALECT)) {
	 	            hibernateProperties.remove(hibernateProperties);
	 	        }
	 	 
	 	        hibernateProperties.setProperty(PROPERTY_NAME_DIALECT, dialect);
	 	 
	 	        super.setHibernateProperties(hibernateProperties);
	 	    }
}
