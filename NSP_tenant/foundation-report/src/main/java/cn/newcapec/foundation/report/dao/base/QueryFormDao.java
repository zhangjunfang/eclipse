package cn.newcapec.foundation.report.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.report.dao.AbstractJdbcDao;
import cn.newcapec.foundation.report.model.QueryFormEntity;
import cn.newcapec.foundation.report.model.QueryFormMap;

@Repository("queryFormDao")
public class QueryFormDao extends AbstractJdbcDao{
	private static final Log logger = LogFactory.getLog(QueryFormDao.class);
	public List<QueryFormEntity> getQueryFormByParams(Object[] args,String reportId){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from(select t.*,rownum rn from (select f.*,r.name reportname,w.name widget_name from nano_report_form f ");
		sql.append(" left join nano_report_manage r on f.report_id = r.id left join nano_report_widget w on f.widget_id=w.id where 1=1 ");
		if(!"0".equals(reportId)){
			sql.append(" and f.report_id=? ");
		}
		sql.append(" order by f.rowid desc) t where rownum<?) where rn>?");
		List<QueryFormEntity> formList = getJdbcTemplate().query(sql.toString(),args,new QueryFormMap());
		return formList;
	}
	
	public int getQueryFormCount(String reportId){
		String sql = "select count(*) from nano_report_form where 1=1 ";
		if(!"0".equals(reportId)){
			sql += " and report_id=? ";
			return getJdbcTemplate().queryForInt(sql, reportId);
		}else{
			return getJdbcTemplate().queryForInt(sql);
		}
	}
	
//	public void deleteDatasetInfo(String id){
//		String sql = "delete from nano_report_dc where id=?";
//		getJdbcTemplate().update(sql, id);
//	}
//	
	public boolean insertQueryFormInfo(Object[] args){
		try {
			String sql = "insert into nano_report_form (id,name,report_id,widget_id,form_widget_type," +
					"sortcode,form_widget_validator,summary,identifier,form_widget_length,form_widget_id) " +
					"values (?,?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(sql, args);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateQueryFormInfo(Object[] args){
		try {
			String sql = "update nano_report_form set name=?,report_id=?,widget_id=?,form_widget_type=?," +
					"sortcode=?,form_widget_validator=?,summary=?,identifier=?,form_widget_id=?,form_widget_length=? where id=?";
			getJdbcTemplate().update(sql, args);
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	
	public boolean deleteFormById(String report_id){
		try {
			String sql = "delete from nano_report_form where report_id = ?";
			getJdbcTemplate().update(sql,report_id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
			return false;
		}
	}
//	
//	public int checkUniqueName(String name){
//		String sql = "select count(*) from nano_report_dc where name=?";
//		return getJdbcTemplate().queryForInt(sql, name);
//	}
//	
	/**
	 * 根据报表id查询表单信息
	 * @param reportId
	 * @return
	 */
	public List<QueryFormEntity> getQueryFormByReportId(String reportId){
		String sql = "select t.*,w.text_field,w.parent_field,w.top_default,w.value_field  from nano_report_form t " +
				"left join nano_report_widget w on t.widget_id = w.name where t.report_id = ?";
		final List<QueryFormEntity> formList = new ArrayList<QueryFormEntity>();
		getJdbcTemplate().query(sql,new Object[]{reportId}, new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				QueryFormEntity queryForm = new QueryFormEntity();
				queryForm.setId(rs.getString("id"));
				queryForm.setName(rs.getString("name"));
				queryForm.setReport_id(rs.getString("report_id"));
				queryForm.setWidget_id(rs.getString("widget_id"));
				queryForm.setForm_widget_type(rs.getString("form_widget_type"));
				queryForm.setSortCode(rs.getString("sortcode"));
				queryForm.setForm_widget_validator(rs.getString("form_widget_validator"));
				queryForm.setSummary(rs.getString("summary"));
				queryForm.setUserId("identifier");
				queryForm.setForm_widget_length(rs.getString("form_widget_length"));
				queryForm.setForm_widget_id(rs.getString("form_widget_id"));
				queryForm.setParent_field(rs.getString("parent_field"));
				queryForm.setValue_field(rs.getString("value_field"));
				queryForm.setText_field(rs.getString("text_field"));
				queryForm.setTop_default(rs.getString("top_default"));
				formList.add(queryForm);
				
			}
		});
		return formList;
	}
}
