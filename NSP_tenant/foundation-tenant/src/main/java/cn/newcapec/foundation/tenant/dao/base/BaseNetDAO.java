package cn.newcapec.foundation.tenant.dao.base;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseNetDAO extends cn.newcapec.framework.base.dao.hibernate.HibernateEntityDao {

	public BaseNetDAO () 
	{
	
	}

	public Class getReferenceClass () {
		return cn.newcapec.foundation.tenant.model.Net.class;
	}

	public cn.newcapec.foundation.tenant.model.Net cast (Object object) {
		return (cn.newcapec.foundation.tenant.model.Net) object;
	}


	public cn.newcapec.foundation.tenant.model.Net load(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.Net) load(getReferenceClass(), key);
	}

	public cn.newcapec.foundation.tenant.model.Net get(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.Net) get(getReferenceClass(), key);
	}

	public java.util.List findAll()
	{
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.foundation.tenant.model.Net net)
	{
		super.save(net);
	}

	public void saveOrUpdate(cn.newcapec.foundation.tenant.model.Net net)
	{
		saveOrUpdate((Object) net);
	}

	
	public void update(cn.newcapec.foundation.tenant.model.Net net) 
	{
		update((Object) net);
	}

	public void delete(java.io.Serializable id)
	{
		delete((Object) load(id));
	}
	
	
	public void delete(cn.newcapec.foundation.tenant.model.Net net)
	{
		delete((Object) net);
	}


	
	


}