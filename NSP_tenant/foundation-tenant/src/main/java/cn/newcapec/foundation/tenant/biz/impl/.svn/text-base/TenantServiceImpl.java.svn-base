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
import cn.newcapec.foundation.tenant.model.Tenant;
import cn.newcapec.foundation.tenant.dao.TenantDAO;
import cn.newcapec.foundation.tenant.biz.TenantService;
import cn.newcapec.framework.base.dao.db.SqlDataset;


/**
 *Tenant服务实现类
 *<p>Company: 郑州新开普电子股份有限公司</p>
 *@version 1.0
 */
@Service("tenantService")
@Transactional
@SuppressWarnings("all")
public class TenantServiceImpl implements TenantService {
	
	@Autowired
	private TenantDAO tenantDAO;



	/**
	 *根据ID查找对象
	 *@param id 业务id
	 *
	 */
 	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Tenant findById(java.io.Serializable id) throws BaseException
	{
		return tenantDAO.get(id);
	}

	/**
	 *增加记录
	 *@param tenant 业务对象
	 *
	 */
	public void add(Tenant tenant) throws BaseException {
		
		try
		{
		
			/*
			if(tenantDAO.hasDuplicate(tenant.getBusinessKey()))

				throw new BaseException("xx代码[" + tenant.getBusinessKey() + "]重复！");
			*/
					
			tenantDAO.save(tenant);
			
				
			
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
	public void delete(List tenantList) throws BaseException {
		
		try
		{
			if (tenantList == null || tenantList.isEmpty())	{
				throw new BaseException("待删除的记录为空！");
			}
			for(Iterator iter = tenantList.iterator(); iter.hasNext();){
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
			
			Tenant tenant =  tenantDAO.get(id);
			if (tenant == null) {
				throw new BaseException("未查询到待删除的业务对象");
			}
			//删除
			tenantDAO.delete(tenant);
			
					
			
		} catch (Exception ex) {
			
			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("删除信息失败！",ex); //delete failed
		}
	}
	
	/**
	 *更新记录
	 *@param tenant 业务对象
	 *
	 */
	public void update(Tenant tenant)
			throws BaseException {

		try
		{
			//TODO modify your correct
			/*
			if(tenantDAO.hasDuplicate(tenant.getId())

				throw new BaseException("xx代码[" + tenant.getBusinessKey() + "]重复！");
			*/
			
			tenantDAO.update(tenant);
			
			
			
		} catch (Exception ex) {
			
			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("更新信息失败！",ex); //update failed
			
		}
	}

		/**
	 * 获取 tenant 列表
	 * @param params
	 * @param orderby
	 * @return
	 */
	public SqlDataset queryTenants(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		SqlDataset sqlDataset =  tenantDAO.queryTenants(params, orderby);
		return sqlDataset;
	}
}
