package cn.newcapec.framework.core.dao.hibernate;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ddlutils.PlatformUtils;
import org.hibernate.dialect.Oracle10gDialect;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import cn.newcapec.framework.core.logs.LogEnabled;

/**
 * @author andy.li
 *
 */

public class SessionFactoryAutoDialectBean extends AnnotationSessionFactoryBean implements LogEnabled {
	 private static final String PROPERTY_NAME_DIALECT = "hibernate.dialect";
	 	 
	 	    private String dialect = null;
	 	 
	 	    @Override
	 	    public void setDataSource(DataSource dataSource) {
	 	        PlatformUtils platformUtils = new PlatformUtils();
	 	        String dbType = platformUtils.determineDatabaseType(dataSource);
	             log.info("Database type is \"" + dbType + "\"");
	             if ("Oracle".equals(dbType)) {
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
