package cn.newcapec.foundation.tenant.dao.base;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseDataSourceDAO extends cn.newcapec.framework.base.dao.hibernate.HibernateEntityDao {

	public BaseDataSourceDAO () 
	{
	
	}

	public Class getReferenceClass () {
		return cn.newcapec.foundation.tenant.model.DataSource.class;
	}

	public cn.newcapec.foundation.tenant.model.DataSource cast (Object object) {
		return (cn.newcapec.foundation.tenant.model.DataSource) object;
	}


	public cn.newcapec.foundation.tenant.model.DataSource load(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.DataSource) load(getReferenceClass(), key);
	}

	public cn.newcapec.foundation.tenant.model.DataSource get(java.io.Serializable key)
	{
		return (cn.newcapec.foundation.tenant.model.DataSource) get(getReferenceClass(), key);
	}

	public java.util.List findAll()
	{
		return find("from " + getReferenceClass().getName());
	}

	public void save(cn.newcapec.foundation.tenant.model.DataSource dataSource)
	{
		super.save(dataSource);
	}

	public void saveOrUpdate(cn.newcapec.foundation.tenant.model.DataSource dataSource)
	{
		saveOrUpdate((Object) dataSource);
	}

	
	public void update(cn.newcapec.foundation.tenant.model.DataSource dataSource) 
	{
		update((Object) dataSource);
	}

	public void delete(java.io.Serializable id)
	{
		delete((Object) load(id));
	}
	
	
	public void delete(cn.newcapec.foundation.tenant.model.DataSource dataSource)
	{
		delete((Object) dataSource);
	}


	
	


}