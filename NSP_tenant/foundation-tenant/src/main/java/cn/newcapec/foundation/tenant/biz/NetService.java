package cn.newcapec.foundation.tenant.biz;

import java.util.List;
import java.util.LinkedHashMap;
import org.apache.commons.lang.StringUtils;
import java.util.Map;

import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.foundation.tenant.model.Net;
import cn.newcapec.framework.base.dao.db.SqlDataset;

/**
 *Net服务
 *<p>Company: 郑州新开普电子股份有限公司</p>
 */
public interface NetService {
	
	/**
	 *根据ID查找对象
	 *@param id 业务id
	 *
	 */
	public Net findById(java.io.Serializable id) throws BaseException;
	
	/**
	 *批量删除记录
	 *@param list 业务对象集合
	 *
	 */
	public void delete(List netList) throws BaseException;
	
	
	/**
	 *删除特定业务对象记录
	 *@param id 业务对象id
	 *
	 */
		
	public void delete(java.io.Serializable id) throws BaseException;
	
	/**
	 *增加记录
	 *@param net 业务对象
	 *
	 */
	public void add(Net net) throws BaseException;
	
	/**
	 *更新记录
	 *@param net 业务对象
	 *
	 */
	public void update(Net net) throws BaseException;
	
	/**
	 * 获取 net 列表
	 * @param params
	 * @param orderby
	 * @return
	 */
	public SqlDataset queryNets(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
	
}
