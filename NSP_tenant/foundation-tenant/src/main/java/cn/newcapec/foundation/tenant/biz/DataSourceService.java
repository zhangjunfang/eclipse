package cn.newcapec.foundation.tenant.biz;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.newcapec.foundation.tenant.model.DataSource;
import cn.newcapec.framework.base.dao.db.SqlDataset;
import cn.newcapec.framework.base.exception.BaseException;

/**
 *DataSource服务
 *<p>Company: 郑州新开普电子股份有限公司</p>
 */
public interface DataSourceService {
	
	/**
	 *根据ID查找对象
	 *@param id 业务id
	 *
	 */
	public DataSource findById(java.io.Serializable id) throws BaseException;
	

	/**
	 * 通过租户编号获取数据源信息
	 * @param tentantId
	 * @return
	 */
	public DataSource findDataSourceByTentantId(String tentantId);

	
	/**
	 *批量删除记录
	 *@param list 业务对象集合
	 *
	 */
	@SuppressWarnings("unchecked")
	public void delete(List dataSourceList) throws BaseException;
	
	
	/**
	 *删除特定业务对象记录
	 *@param id 业务对象id
	 *
	 */
		
	public void delete(java.io.Serializable id) throws BaseException;
	
	/**
	 *增加记录
	 *@param dataSource 业务对象
	 *
	 */
	public void add(DataSource dataSource) throws BaseException;
	
	/**
	 *更新记录
	 *@param dataSource 业务对象
	 *
	 */
	public void update(DataSource dataSource) throws BaseException;
	
	/**
	 * 获取 dataSource 列表
	 * @param params
	 * @param orderby
	 * @return
	 */
	public SqlDataset queryDataSources(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
	
}
