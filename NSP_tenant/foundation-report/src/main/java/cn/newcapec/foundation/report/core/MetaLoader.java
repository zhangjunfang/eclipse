package cn.newcapec.foundation.report.core;

/**
 * 元数据加载器，从特定容器中加载元数据信息
 * @author shikeying
 * @date 2013-7-16
 *
 */
public interface MetaLoader extends DataSourceAware {

	/**
	 * 返回初始的元数据名称，例如：存储过程名字、SQL语句等
	 * @return
	 */
	String getMetaName();
	
	/**
	 * 设置初始元数据，如：设置存储过程名字，或者SQL语句等
	 * @param express
	 */
	void setMetaName(String express);
	
	/**
	 * 返回元数据的描述信息，通常是注释或者备注
	 * @return
	 */
	String getDescription();
}
