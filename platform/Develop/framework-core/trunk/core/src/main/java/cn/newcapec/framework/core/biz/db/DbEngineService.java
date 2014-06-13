package cn.newcapec.framework.core.biz.db;

import cn.newcapec.framework.core.dao.db.PagingResultSet;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.model.dbmeta.Container;
import cn.newcapec.framework.core.model.dbmeta.DBTable;

import java.util.List;
import java.util.Set;

/**
 */@SuppressWarnings("all")
public interface DbEngineService extends LogEnabled {

	public abstract List query(final String querySQL, final Class modelClass,
                               boolean convertFieldName) throws BaseException;

	/**
	 * 数据库查询
	 * 
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param params
	 *            查询参数
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public abstract PagingResultSet getPagingResultSet(final String querySQL,
                                                       Object[] params, final Class modelClass, int pageSize, int pageIndex)
			throws BaseException;

	/**
	 * 数据库查询
	 * 
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param params
	 *            查询参数
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public abstract List query(final String querySQL, Object[] params,
                               final Class modelClass, int pageSize, int pageIndex)
			throws BaseException;

	/**
	 * 将SQL中的值设置到Model中，放入List中返回
	 * 
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象类型
	 * @param params
	 *            传递参数
	 * @param convertFieldName
	 *            是否转换field的列名进行对象属性的匹配
	 * @return
	 * @throws Exception
	 */
	public abstract List query(String querySQL, Object[] params,
                               Class modelClass, boolean convertFieldName);

	/**
	 * 
	 * 动态执行SQL
	 * 
	 * @param querySQL
	 * @param params
	 *            传入参数
	 * @return
	 */
	public abstract int execute(final String querySQL, Object[] params);

	/**
	 * 校验记录是否存在
	 * 
	 * @param tableName
	 *            表名
	 * @param field
	 *            数据库表字段名
	 * @param value
	 * @param extraSql
	 *            扩展SQL
	 * @param extraParams
	 *            扩展sql查询参数
	 * @return
	 */
	public abstract boolean checkRecordExist(String tableName, String field,
                                             Object value, String extraSql, Object[] extraParams);

	/**
	 * 
	 * 校验记录是否存在
	 * 
	 * @param entityName
	 *            DO类名
	 * @param property
	 *            属性名
	 * @param value
	 * @param extraHql
	 *            Hibernate sql
	 * @param extraParams
	 *            扩展sql查询参数
	 * @return
	 */
	public abstract boolean checkRecordExist(Class entityName, String property,
                                             Object value, String extraHql, Object[] extraParams);

	/**
	 * 
	 * 获取容器
	 * 
	 * @param schemaPattern
	 * @param tablePattern
	 * @return
	 */
	public abstract Container getDBContainer(String schemaPattern,
                                             String tablePattern);

	/**
	 * 获取表定义
	 * 
	 * @param tableName
	 * @return
	 */
	public abstract DBTable getDBTable(String tableName);

	/**
	 * 获取表名
	 * 
	 * @param schemaPattern
	 * @param tablePattern
	 * @return
	 */
	public abstract Set<String> getTableList(String schemaPattern,
                                             String tablePattern);

	/**
	 * 获取表定义
	 * 
	 * @param schemaPattern
	 * @param tablePattern
	 * @return
	 */
	public abstract List<DBTable> getDBTables(String schemaPattern,
                                              String tablePattern);

}