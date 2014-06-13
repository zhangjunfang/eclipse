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
import cn.newcapec.foundation.tenant.model.Net;
import cn.newcapec.foundation.tenant.dao.NetDAO;
import cn.newcapec.foundation.tenant.biz.NetService;
import cn.newcapec.framework.base.dao.db.SqlDataset;


/**
 *Net服务实现类
 *<p>Company: 郑州新开普电子股份有限公司</p>
 *@version 1.0
 */
@Service("netService")
@Transactional
@SuppressWarnings("all")
public class NetServiceImpl implements NetService {
	
	@Autowired
	private NetDAO netDAO;



	/**
	 *根据ID查找对象
	 *@param id 业务id
	 *
	 */
 	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Net findById(java.io.Serializable id) throws BaseException
	{
		return netDAO.get(id);
	}

	/**
	 *增加记录
	 *@param net 业务对象
	 *
	 */
	public void add(Net net) throws BaseException {
		
		try
		{
		
			/*
			if(netDAO.hasDuplicate(net.getBusinessKey()))

				throw new BaseException("xx代码[" + net.getBusinessKey() + "]重复！");
			*/
					
			netDAO.save(net);
			
				
			
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
	public void delete(List netList) throws BaseException {
		
		try
		{
			if (netList == null || netList.isEmpty())	{
				throw new BaseException("待删除的记录为空！");
			}
			for(Iterator iter = netList.iterator(); iter.hasNext();){
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
			
			Net net =  netDAO.get(id);
			if (net == null) {
				throw new BaseException("未查询到待删除的业务对象");
			}
			//删除
			netDAO.delete(net);
			
					
			
		} catch (Exception ex) {
			
			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("删除信息失败！",ex); //delete failed
		}
	}
	
	/**
	 *更新记录
	 *@param net 业务对象
	 *
	 */
	public void update(Net net)
			throws BaseException {

		try
		{
			//TODO modify your correct
			/*
			if(netDAO.hasDuplicate(net.getId())

				throw new BaseException("xx代码[" + net.getBusinessKey() + "]重复！");
			*/
			
			netDAO.update(net);
			
			
			
		} catch (Exception ex) {
			
			if(BaseException.class.isInstance(ex))
				throw (BaseException)ex;
			else
				throw new BaseException("更新信息失败！",ex); //update failed
			
		}
	}

		/**
	 * 获取 net 列表
	 * @param params
	 * @param orderby
	 * @return
	 */
	public SqlDataset queryNets(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		SqlDataset sqlDataset =  netDAO.queryNets(params, orderby);
		return sqlDataset;
	}
}
