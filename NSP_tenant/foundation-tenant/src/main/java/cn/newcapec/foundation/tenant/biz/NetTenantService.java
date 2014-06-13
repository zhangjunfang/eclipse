package cn.newcapec.foundation.tenant.biz;

import java.util.List;
import java.util.LinkedHashMap;
import org.apache.commons.lang.StringUtils;
import java.util.Map;

import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.foundation.tenant.model.NetTenant;
import cn.newcapec.framework.base.dao.db.SqlDataset;

/**
 *NetTenant服务
 *<p>Company: 郑州新开普电子股份有限公司</p>
 */
public interface NetTenantService {
	
	/**
	 *根据ID查找对象
	 *@param id 业务id
	 *
	 */
	public NetTenant findById(java.io.Serializable id) throws BaseException;
	
	/**
	 *批量删除记录
	 *@param list 业务对象集合
	 *
	 */
	public void delete(List netTenantList) throws BaseException;
	
	
	/**
	 *删除特定业务对象记录
	 *@param id 业务对象id
	 *
	 */
		
	public void delete(java.io.Serializable id) throws BaseException;
	
	/**
	 *增加记录
	 *@param netTenant 业务对象
	 *
	 */
	public void add(NetTenant netTenant) throws BaseException;
	
	/**
	 *更新记录
	 *@param netTenant 业务对象
	 *
	 */
	public void update(NetTenant netTenant) throws BaseException;
	
	/**
	 * 获取 netTenant 列表
	 * @param params
	 * @param orderby
	 * @return
	 */
	public SqlDataset queryNetTenants(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
	
}
