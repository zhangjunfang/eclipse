package cn.newcapec.foundation.tenant.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Repository;
import cn.newcapec.foundation.tenant.model.NetTenant;
import cn.newcapec.foundation.tenant.dao.base.BaseNetTenantDAO;
import cn.newcapec.framework.base.dao.db.SqlDataset;

/**
 *NetTenantDAO类
 *<p>Company: 郑州新开普电子股份有限公司</p>
 *
 */
@SuppressWarnings("all")
@Repository("netTenantDAO")
public class NetTenantDAO extends BaseNetTenantDAO  {

	public NetTenantDAO () {}
	
	/**
	 *用于记录新增时，根据对象标识代码判断是否已存在
	 */
	public boolean hasDuplicate(String businessKey) {

		int count = 0;
		StringBuffer hql = new StringBuffer("select count(*) from ");
		hql.append(NetTenant.class.getName());
		hql.append(" where businessKey = ?");

		String[] params = new String[] {businessKey};

		List list = find(hql.toString(), params);
		if (list != null && list.size() > 0)
			count = (Integer) list.get(0);

		return count > 0;
	}

	 /**
	 *用于记录修改时，根据对象标识代码判断是否已存在
	 */
	public boolean hasDuplicate(String id, String businessKey) {
		int count = 0;
		StringBuffer hql = new StringBuffer("select count(*) from ");

		hql.append(NetTenant.class.getName());
		hql.append(" where id != ? and businessKey = ?");

		String[] params = new String[] { id, businessKey };

		List list = find(hql.toString(), params);
		if (list != null && list.size() > 0)
			count = (Integer) list.get(0);

		return count > 0;
	}	
	
	/**
	 *批量删除记录
	 */
	@SuppressWarnings("unchecked")
	public int deleteById(List netTenantList)
	{
	
		int count = 0;
		
		if(netTenantList != null && !netTenantList.isEmpty())
		{
			List params = new ArrayList();
			
			final StringBuffer hql = new StringBuffer("delete ");
			hql.append(NetTenant.class.getName());
			for(int i=0; i < netTenantList.size(); i++)
			{
				if(i == 0)
					hql.append(" where ");
				else
					hql.append(" or ");
				
				hql.append(" id = ?");
				NetTenant netTenant = (NetTenant) netTenantList.get(i);
				params.add(netTenant.getId());
			}
	
			count = delete(hql.toString(), params.toArray());
		}
		
		return count;
	}
	
	
	/**
	 * 获取NetTenant列表
	 * @param params
	 * @param orderby
	 * @return
	 */
	public SqlDataset queryNetTenants(Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		StringBuffer sb = new StringBuffer("select * from  t_netTenant t where 1=1");	
		if(params.containsKey("name") &&StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and t.name like :name ");
		}
		SqlDataset sqlDataset = new SqlDataset(sb.toString());
		if(params.containsKey("name") &&StringUtils.isNotBlank(params.get("name").toString())) {
			sqlDataset.parameters().setValue("name",params.get("name"));
		}
		return sqlDataset.loadData();
		
	}

}