package cn.newcapec.foundation.tenant.biz.impl;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.foundation.tenant.model.DataSource;
import cn.newcapec.foundation.tenant.dao.DataSourceDAO;
import cn.newcapec.foundation.tenant.biz.DataSourceService;
import cn.newcapec.framework.base.dao.db.SqlDataset;


/**
 *DataSource服务实现类
 *<p>Company: 郑州新开普电子股份有限公司</p>
 *@version 1.0
 */
@Service("dataSourceService")
@Transactional
@SuppressWarnings("all")
public class DataSourceServiceImpl implements DataSourceService {
	
	@Autowired
	private DataSourceDAO dataSourceDAO;



	/**
	 *根据ID查找对象
	 *@param id 业务id
	 *
	 */
 	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public DataSource findById(java.io.Serializable id) throws BaseException
	{
		return dataSourceDAO.get(id);
	}
 	
 	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public DataSource findDataSourceByTentantId(String tentantId){
		return dataSourceDAO.findDataSourceByTentantId(tentantId);
	}

	/**
	 *增加记录
	 *@param dataSource 业务对象
	 *
	 */
	public void add(DataSource dataSource) throws BaseException {
		
		try
		{
		
			/*
			if(dataSourceDAO.hasDuplicate(dataSource.getBusinessKey()))

				throw new BaseException("xx代码[" + dataSource.getBusinessKey() + "]重复！");
			*/
					
			dataSourceDAO.save(dataSource);
			
				
			
		} catch (Exception ex) {

			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("信息保存失败！",ex); //save failed
			
		}

	}

	/**
	 *批量删除记录
	 *@param list 业务对象集合
	 *
	 */
	public void delete(List dataSourceList) throws BaseException {
		
		try
		{
			if (dataSourceList == null || dataSourceList.isEmpty())	{
				throw new BaseException("待删除的记录为空！");
			}
			for(Iterator iter = dataSourceList.iterator(); iter.hasNext();){
				java.io.Serializable id = (java.io.Serializable)iter.next();
				this.delete(id) ;
			}
			
		} catch(Exception ex){

			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("删除信息失败！",ex); //delete failed
		}
	}
	
	/**
	 *删除特定业务对象记录
	 *@param id 业务对象id
	 *
	 */
	public void delete(java.io.Serializable id) throws BaseException {
		
		try {
			
			DataSource dataSource =  dataSourceDAO.get(id);
			if (dataSource == null) {
				throw new BaseException("未查询到待删除的业务对象");
			}
			//删除
			dataSourceDAO.delete(dataSource);
			
					
			
		} catch (Exception ex) {
			
			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("删除信息失败！",ex); //delete failed
		}
	}
	
	/**
	 *更新记录
	 *@param dataSource 业务对象
	 *
	 */
	public void update(DataSource dataSource)
			throws BaseException {

		try
		{
			//TODO modify your correct
			/*
			if(dataSourceDAO.hasDuplicate(dataSource.getId())

				throw new BaseException("xx代码[" + dataSource.getBusinessKey() + "]重复！");
			*/
			
			dataSourceDAO.update(dataSource);
			
			
			
		} catch (Exception ex) {
			
			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("更新信息失败！",ex); //update failed
			
		}
	}

		/**
	 * 获取 dataSource 列表
	 * @param params
	 * @param orderby
	 * @return
	 */
	public SqlDataset queryDataSources(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		SqlDataset sqlDataset =  dataSourceDAO.queryDataSources(params, orderby);
		return sqlDataset;
	}
}
