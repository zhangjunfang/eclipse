package cn.newcapec.foundation.report.dao.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.report.dao.AbstractJdbcDao;
import cn.newcapec.foundation.report.model.ReportFieldsEntity;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.foundation.report.model.ReportManageMap;
import cn.newcapec.foundation.report.service.ReportObject;

@Repository("reportManageDao")
public class ReportManageDao extends AbstractJdbcDao{
	private static final Log logger = LogFactory.getLog(ReportManageDao.class);
	
	private static final String SELECT_REPORT_LIST = new StringBuilder()
		.append("select r.*, dc.filter_field")
		.append(" from NANO_REPORT_MANAGE r, NANO_REPORT_DC dc")
		.append(" where r.dc_id = dc.id and r.state = 1").toString();
	
	public ReportManageEntity getReportEntityById(String id){
		String sql = "select m.*,c.name as dcname from nano_report_manage m " +
				"left join nano_report_dc c on m.dc_id = c.id where m.id=?";
		ReportManageEntity report =getJdbcTemplate().queryForObject(sql,new Object[]{id},new ReportManageMap());
		return report;
	}
	
	public List<ReportManageEntity> getReportByParams(Object[] args,String name){
		StringBuffer sql = new StringBuffer();
		sql.append("select m.*,c.name as dcname from nano_report_manage m " +
				"left join nano_report_dc c on m.dc_id = c.id where 1=1 ");
		if(name != null && !"".equals(name)){
			sql.append(" and m.name like ? ");
		}
		sql.append("order by m.id asc limit ?,?");
		
		List<ReportManageEntity> reportList = getJdbcTemplate().query(sql.toString(),args,new ReportManageMap());
		return reportList;
	}
	
	public int getReportCountByParams(String name){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from nano_report_manage where 1=1 ");
		if(name != null && !"".equals(name)){
			sql.append(" and name like ? ");
			return getJdbcTemplate().queryForInt(sql.toString(), "%'"+name+"'%");
		}else{
			return getJdbcTemplate().queryForInt(sql.toString());
		}
	}
	
	public boolean inserReportInfo(Object[] args){
		try {
			String sql = "insert into nano_report_manage (id,name,dc_id,summary,state) values (?,?,?,?,?)";
			getJdbcTemplate().update(sql,args);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateReportInfo(Object[] args){
		try {
			String sql = "update nano_report_manage set name=?,dc_id=?,summary=? where id=?";
			getJdbcTemplate().update(sql,args);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void deleteReportInfo(String id){
		String sql = "delete from nano_report_manage where id=?";
		getJdbcTemplate().update(sql,id);
	}
	
	public boolean updateReportTemplate(String content,String id){
		try {
			String sql = "update nano_report_manage set content=? where id=?";
			getJdbcTemplate().update(sql, new Object[]{content,id});
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	/**
	 * 修改报表发布状态
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateReportState(String id,int state){
		try {
			String sql = "update nano_report_manage set state=? where id=?";
			getJdbcTemplate().update(sql, new Object[]{state,id});
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	public int checkUniqueName(String name){
		String sql = "select count(*) from nano_report_manage where name=?";
		return getJdbcTemplate().queryForInt(sql, name);
	}
	
	public List<ReportManageEntity> queryAllReportInfo(){
		String sql = "select * from nano_report_manage";
		final List<ReportManageEntity> reportList = new ArrayList<ReportManageEntity>();
		getJdbcTemplate().query(sql,new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ReportManageEntity report = new ReportManageEntity();
				report.setId(rs.getString("id"));
				report.setName(rs.getString("name"));
				report.setDc_id(rs.getString("dc_id"));
				reportList.add(report);
			}
		});
		return reportList;
	}
	
	/**
	 * 根据报表id获取报表内容
	 * @return
	 */
	public List<ReportManageEntity> queryPublishReportContent(String id){
		String sql = "select * from nano_report_manage where id = ?";
		final List<ReportManageEntity> reportList = new ArrayList<ReportManageEntity>();
		getJdbcTemplate().query(sql,new Object[]{id},new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ReportManageEntity report = new ReportManageEntity();
				report.setId(rs.getString("id"));
				report.setName(rs.getString("name"));
				report.setDc_id(rs.getString("dc_id"));
				if(rs.getClob("content") != null ){
					BufferedReader in = new BufferedReader(rs.getClob("content").getCharacterStream());
					StringWriter out = new StringWriter();
					int c;
					try {
						while((c = in.read()) != -1){
							out.write(c);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					report.setContent(out.toString());
				}
				reportList.add(report);
			}
		});
		return reportList;
	}
	
	/**
	 * 查询已经发布的报表
	 * @return
	 */
	public List<ReportManageEntity> queryPublishReportInfo(){
		String sql = "select * from nano_report_manage where state = 1";
		final List<ReportManageEntity> reportList = new ArrayList<ReportManageEntity>();
		getJdbcTemplate().query(sql,new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ReportManageEntity report = new ReportManageEntity();
				report.setId(rs.getString("id"));
				report.setName(rs.getString("name"));
				report.setDc_id(rs.getString("dc_id"));
				reportList.add(report);
			}
		});
		return reportList;
	}
	
	/**
	 * 添加报表字段信息
	 * @param args
	 * @return
	 */
	public boolean insertReportFieldsInfo(Object[] args){
		try {
			String sql = "insert into nano_report_fields " +
					"(id,field_name,field_title,sort,summary,identifier,field_type,dc_id,report_id) " +
					"values (?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(sql,args);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 根据报表id查询报表字段信息
	 * @param reportId
	 * @return
	 */
	public List<ReportFieldsEntity> getReportFieldsList(String reportId){
		String sql = "select * from nano_report_fields where report_id = ? order by sort asc";
		final List<ReportFieldsEntity> fieldsList = new ArrayList<ReportFieldsEntity>();
		getJdbcTemplate().query(sql, new Object[]{reportId}, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ReportFieldsEntity reportFields = new ReportFieldsEntity();
				reportFields.setField_name(rs.getString("field_name"));
				reportFields.setField_title(rs.getString("field_title"));
				reportFields.setField_type(rs.getString("field_type"));
				reportFields.setDc_id(rs.getString("dc_id"));
				reportFields.setReport_id(rs.getString("report_id"));
				reportFields.setId(rs.getString("id"));
				reportFields.setSort(rs.getString("sort"));
				reportFields.setSummary(rs.getString("summary"));
				fieldsList.add(reportFields);
			}
		});
		return fieldsList;
	}
	/**
	 * 根据报表id查询报表展示字段信息
	 * @param reportId
	 * @return
	 */
	public boolean deleteReportFields(String reportId){
		try {
			String sql = "delete from nano_report_fields where report_id=?";
			getJdbcTemplate().update(sql,reportId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ReportObject> getReportObjectList(){
		final List<ReportObject> reportList = new ArrayList<ReportObject>();
		getJdbcTemplate().query(SELECT_REPORT_LIST, new Object[0]
				, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						// TODO Auto-generated method stub
						ReportObject report = new ReportObject();
						report.setName(rs.getString("name"))
							.setReportId(rs.getString("id"))
							.setDatasetId(rs.getString("dc_id"))
							.setParameter(rs.getString("filter_field"));
						reportList.add(report);
					}
				});
		return reportList;
	}

	public static final String SELECT_REPORT_FILTER = "select t.filter_field from NANO_REPORT_DC t where t.id = ?";
	
	/**
	 * 返回数据集对应的过滤字段（业务参数信息）
	 * @param dcId
	 * @return
	 */
	public String getReportFilterField(String dcId){
		Map<String, Object> filter = getJdbcTemplate().queryForMap(SELECT_REPORT_FILTER, new Object[]{dcId});
		if(filter != null){
			return filter.get("filter_field").toString();
		}
		return null;
	}
}
