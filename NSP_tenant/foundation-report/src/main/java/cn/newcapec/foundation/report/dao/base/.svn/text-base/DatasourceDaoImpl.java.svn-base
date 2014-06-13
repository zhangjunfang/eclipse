package cn.newcapec.foundation.report.dao.base;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.report.dao.AbstractJdbcDao;
import cn.newcapec.foundation.report.dao.DatasourceDao;
import cn.newcapec.foundation.report.model.DatasourceEntity;
import cn.newcapec.foundation.report.model.DatasourceMap;

@Repository("dataSourceDao")
public class DatasourceDaoImpl extends AbstractJdbcDao implements DatasourceDao {
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#addDsInfo(java.lang.Object[])
	 */
	@Override
	public void addDsInfo(Object[] args){
		String sql = "insert into nano_report_ds (id,name,type,ds_service,ds_user,ds_pass,ds_address,summary)" +
				" values (?,?,?,?,?,?,?,?) ";
		getJdbcTemplate().update(sql, args);
	}
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#getDsByParams(java.lang.Object[], java.lang.String)
	 */
	@Override
	public List<DatasourceEntity> getDsByParams(Object[] args,String name){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from nano_report_ds where 1=1 ");
		if(name != null && !"".equals(name)){
			sql.append(" and name like ? ");
		}
		sql.append("order by id desc limit ?,?");
		
		List<DatasourceEntity> userList = getJdbcTemplate().query(sql.toString(),args,new DatasourceMap());
		return userList;
	}
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#getDsCountByParams(java.lang.Object[], java.lang.String)
	 */
	@Override
	public int getDsCountByParams(Object[] args,String name){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from nano_report_ds where 1=1 ");
		if(name != null && !"".equals(name)){
			sql.append(" and name like ? ");
			return getJdbcTemplate().queryForList(sql.toString(),args).size();
		}else{
			return getJdbcTemplate().queryForList(sql.toString()).size();
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#deleteDsById(java.lang.String)
	 */
	@Override
	public void deleteDsById(String id){
		String sql = "delete from  nano_report_ds where id = ?";
		getJdbcTemplate().update(sql,id);
	}
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#checkUniqueName(java.lang.String)
	 */
	@Override
	public int checkUniqueName(String name){
		String sql = "select count(*) from nano_report_ds where name=?";
		return getJdbcTemplate().queryForInt(sql, name);
	}
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#updateDsInfo(java.lang.Object[])
	 */
	@Override
	public void updateDsInfo(Object[] args){
		String sql = "update nano_report_ds set " +
				"name=?,type=?,ds_service=?,ds_user=?,ds_pass=?," +
				"ds_address=?,summary=? where id=?";
		getJdbcTemplate().update(sql,args);
	}
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#getAllDsEntity()
	 */
	@Override
	public List<DatasourceEntity> getAllDsEntity(){
		String sql = "select * from nano_report_ds";
		return getJdbcTemplate().query(sql,new DatasourceMap());
	}
	
	/* (non-Javadoc)
	 * @see cn.newcapec.foundation.report.dao.impl.DatasourceDao#getDsEntityById(java.lang.String)
	 */
	@Override
	public DatasourceEntity getDsEntityById(String id){
		String sql = "select * from nano_report_ds where id=?";
		return getJdbcTemplate().queryForObject(sql,new Object[]{id},new DatasourceMap());
	}
	
}
