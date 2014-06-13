package cn.newcapec.foundation.tenant.dao.base;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseNetTenantDAO extends cn.newcapec.framework.base.dao.hibernate.HibernateEntityDao {

	public BaseNetTenantDAO () 
	{
	
	}

	public Class getReferenceClass () {
		return cn.newcapec.foundation.tenant.model.NetTenant.class;
	}

	public cn.newcapec.foundation.tenant.model.NetTenant cast (Object object) {
		return (cn.newcapec.foundation.tenant.model.NetTenant) object;
	}


	public cn.newcapec.foundation.tenant.model.NetTenant load(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.NetTenant) load(getReferenceClass(), key);
	}

	public cn.newcapec.foundation.tenant.model.NetTenant get(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.NetTenant) get(getReferenceClass(), key);
	}

	public java.util.List findAll()
	{
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.foundation.tenant.model.NetTenant netTenant)
	{
		super.save(netTenant);
	}

	public void saveOrUpdate(cn.newcapec.foundation.tenant.model.NetTenant netTenant)
	{
		saveOrUpdate((Object) netTenant);
	}

	
	public void update(cn.newcapec.foundation.tenant.model.NetTenant netTenant) 
	{
		update((Object) netTenant);
	}

	public void delete(java.io.Serializable id)
	{
		delete((Object) load(id));
	}
	
	
	public void delete(cn.newcapec.foundation.tenant.model.NetTenant netTenant)
	{
		delete((Object) netTenant);
	}


	
	


}