package cn.newcapec.foundation.tenant.dao.base;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTenantDAO extends cn.newcapec.framework.base.dao.hibernate.HibernateEntityDao {

	public BaseTenantDAO () 
	{
	
	}

	public Class getReferenceClass () {
		return cn.newcapec.foundation.tenant.model.Tenant.class;
	}

	public cn.newcapec.foundation.tenant.model.Tenant cast (Object object) {
		return (cn.newcapec.foundation.tenant.model.Tenant) object;
	}


	public cn.newcapec.foundation.tenant.model.Tenant load(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.Tenant) load(getReferenceClass(), key);
	}

	public cn.newcapec.foundation.tenant.model.Tenant get(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.Tenant) get(getReferenceClass(), key);
	}

	public java.util.List findAll()
	{
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.foundation.tenant.model.Tenant tenant)
	{
		super.save(tenant);
	}

	public void saveOrUpdate(cn.newcapec.foundation.tenant.model.Tenant tenant)
	{
		saveOrUpdate((Object) tenant);
	}

	
	public void update(cn.newcapec.foundation.tenant.model.Tenant tenant) 
	{
		update((Object) tenant);
	}

	public void delete(java.io.Serializable id)
	{
		delete((Object) load(id));
	}
	
	
	public void delete(cn.newcapec.foundation.tenant.model.Tenant tenant)
	{
		delete((Object) tenant);
	}


	
	


}