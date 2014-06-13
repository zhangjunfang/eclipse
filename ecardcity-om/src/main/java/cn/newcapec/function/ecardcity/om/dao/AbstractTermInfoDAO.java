/**
 * 
 */
package cn.newcapec.function.ecardcity.om.dao;

import java.io.Serializable;
import java.util.List;

import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;
import cn.newcapec.function.ecardcity.om.model.TermInfo;

/**
 * @author ocean
 * email: zhangjunfang0505@163.com
 * QQ: 419692181
 * date: 2014-3-14
 */
public abstract class AbstractTermInfoDAO extends HibernateEntityDao implements Serializable {
	
	private static final long serialVersionUID = -994224085804712632L;
	@Override
	protected Class<TermInfo> getReferenceClass() {
		
		return TermInfo.class;
	}
	public TermInfo cast(Object object) {
		return (TermInfo) object;
	}
	public TermInfo load(
			java.io.Serializable key) {
		return (TermInfo) load(
				getReferenceClass(), key);
	}
	public TermInfo get(
			java.io.Serializable key) {
		return (TermInfo) get(
				getReferenceClass(), key);
	}
	@SuppressWarnings("unchecked")
	public List<TermInfo> findAll() {
	return find("from " + getReferenceClass().getName());
	}
	public void save(TermInfo termInfo) {
		super.save(termInfo);
	}
	public void saveOrUpdate(TermInfo termInfo) {
		saveOrUpdate((Object) termInfo);
	}
	public void update(TermInfo termInfo) {
		update((Object) termInfo);
	}
	public void delete(String id) {
		delete((Object) load(id));
	}
	public void delete(TermInfo termInfo) {
		delete((Object) termInfo);
	}
}
