package cn.newcapec.foundation.base;

import java.util.Date;

import cn.newcapec.foundation.model.AppBaseModel;
import cn.newcapec.framework.base.dao.hibernate.HibernateEntityDao;
import cn.newcapec.framework.base.log.LogEnabled;
import cn.newcapec.framework.utils.GenericsUtils;

@SuppressWarnings("all")
public abstract class AppBaseDAO<T extends AppBaseModel> extends HibernateEntityDao implements LogEnabled {

    protected Class<T> entityClass;

	public Class<T> getReferenceClass () {
		return this.entityClass;
	}

	public AppBaseDAO(){
	    this.entityClass = GenericsUtils.getGenericClass(getClass());
	}
	
	public T cast (Object object) {
		return (T)object;
	}

	public T load(java.io.Serializable key){
		return (T) super.load(getReferenceClass(), key);
	}

	public T get(java.io.Serializable key){
		return (T) super.get(getReferenceClass(), key);
	}

	public java.util.List findAll(){
		return super.find("from " + getReferenceClass().getName());
	}

	public void save(T entity){
        entity.setCreateDate(new Date());
		super.save(entity);
	}

	public void saveOrUpdate(T entity){
	    super.saveOrUpdate(entity);
	}
	
	public void update(T entity){
	    entity.setUpdateDate(new Date());
	    super.update(entity);
	}

	public void delete(java.io.Serializable id){
	    super.delete((Object) load(id));
	}
	
	public void delete(T entity){
	    super.delete((Object) entity);
	}

}