package cn.newcapec.framework.core.model.dbmeta;

import java.util.Map;
import java.util.Properties;

/**
 * 数据库配置
 * @author andy.li
 */
@SuppressWarnings("all")
public class Container {

	private String packageName;

	private Properties properties;


	private Map tables;

	boolean fullyLoaded;

	public Container() {
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Map getTables() {
		return tables;
	}

	public void setTables(Map tables) {
		this.tables = tables;
	}

	public boolean isFullyLoaded() {
		return fullyLoaded;
	}

	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
}
