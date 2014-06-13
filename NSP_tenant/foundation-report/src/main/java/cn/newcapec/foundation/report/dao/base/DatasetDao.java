package cn.newcapec.foundation.report.dao.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.report.dao.AbstractJdbcDao;
import cn.newcapec.foundation.report.model.DatasetEntity;
import cn.newcapec.foundation.report.model.DatasetMap;
import cn.newcapec.foundation.report.model.DatasetParams;

@Repository("datasetDao")
public class DatasetDao extends AbstractJdbcDao{
	
	public List<DatasetEntity> getDatasetByParams(Object[] args,String name){
		StringBuffer sql = new StringBuffer();
//		sql.append("select * from(select t.*,rownum rn from (select c.*,s.name as dsname from nano_report_dc c " +
//				"left join nano_report_ds s on c.ds_id = s.id  where 1=1 ");
//		if(name != null && !"".equals(name)){
//			sql.append(" and c.name like ? ");
//		}
//		sql.append("order by c.rowid desc) t where rownum<?) where rn>?");
		
		sql.append("select c.*,s.name as dsname from nano_report_dc c " +
				"left join nano_report_ds s on c.ds_id = s.id  where 1=1 ");
		if(name != null && !"".equals(name)){
			sql.append(" and c.name like ? ");
		}
		sql.append("order by c.id asc limit ?,?");
		
		List<DatasetEntity> datasetList = getJdbcTemplate().query(sql.toString(),args,new DatasetMap());
		return datasetList;
	}
	
	public int getDatasetCountByParams(String name){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from nano_report_dc where 1=1 ");
		if(name != null && !"".equals(name)){
			sql.append(" and name like ? ");
			return getJdbcTemplate().queryForInt(sql.toString(), "%'"+name+"'%");
		}else{
			return getJdbcTemplate().queryForInt(sql.toString());
		}
	}
	
	public void deleteDatasetInfo(String id){
		String sql = "delete from nano_report_dc where id=?";
		getJdbcTemplate().update(sql, id);
	}
	
	public boolean insertDatasetInfo(Object[] args){
		boolean b = false;
		try {
			String sql = "insert into nano_report_dc (id,filter_field,name,type,summary," +
					"invoke_method,ds_id,identifier,data_type,content) " +
					"values (?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(sql, args);
			b = true;
		} catch (Exception e) {
			b = false;
		}
		return b;
	}
	
	public void insertFieldsAndParams(Object[] args){
		try {
			String sql = "insert into nano_report_dc_parameter (id,dc_id,name,type,data_type,alias)" +
					" values (?,?,?,?,?,?)";
			getJdbcTemplate().update(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static final String SELECT_REPORT_FIELDS = "select p.*, f.field_title from NANO_REPORT_DC_PARAMETER p"
			+ ", NANO_REPORT_FIELDS f where p.dc_id = ? and f.report_id = ? and p.name = f.field_name";
	
	/**
	 * 返回报表要返回的所有字段数据（游标定义的），同时包含每个列的别名。
	 * @param reportId
	 * @param dcId
	 * @return
	 * @author shikeying
	 */
	public List<DatasetParams> getReportParameterFields(String reportId, String dcId){
		final List<DatasetParams> paramList = new ArrayList<DatasetParams>();
		getJdbcTemplate().query(SELECT_REPORT_FIELDS, new Object[]{dcId, reportId}
				,new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				DatasetParams params = new DatasetParams();
				params.setParamId(rs.getString("id"));
				params.setName(rs.getString("name").toLowerCase());
				params.setAlias(rs.getString("alias"));
				params.setType(rs.getString("data_type").toLowerCase());
				params.setMark(rs.getInt("type"));
				params.setFieldTitle(rs.getString("field_title"));
				paramList.add(params);
			}
		});
		return paramList;
	}
	
	public List<DatasetParams> getParmasByDsId(String dataset_id,String type){
		String sql = "select * from nano_report_dc_parameter where dc_id = ?";
		Object[] object = null;
		if(type!= null && !"".equals(type)){
			sql += " and type =?";
			object = new Object[]{dataset_id,type};
		}else{
			object = new Object[]{dataset_id};
		}
		final List<DatasetParams> paramList = new ArrayList<DatasetParams>();
		getJdbcTemplate().query(sql, object,new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				DatasetParams params = new DatasetParams();
				params.setParamId(rs.getString("id"));
				params.setName(rs.getString("name").toLowerCase());
				params.setAlias(rs.getString("alias"));
				params.setType(rs.getString("data_type").toLowerCase());
				params.setMark(rs.getInt("type"));
				paramList.add(params);
			}
		});
		return paramList;
	}
	public boolean updateDsInfo(Object[] args){
		try {
			String sql = "update nano_report_dc set " +
					"name=?,type=?,summary=?,invoke_method=?,ds_id=?," +
					"identifier=?,content=?,data_type=?,filter_field=? where id=?";
			getJdbcTemplate().update(sql, args);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public void deleteParamsByDcId(String id){
		String sql = "delete from nano_report_dc_parameter where dc_id = ?";
		getJdbcTemplate().update(sql,id);
	}
	
	public int checkUniqueName(String name){
		String sql = "select count(*) from nano_report_dc where name=?";
		return getJdbcTemplate().queryForInt(sql, name);
	}
	
	public List<DatasetEntity> getAllDatasetInfo(){
		String sql = "select * from nano_report_dc";
		final List<DatasetEntity> dsList = new ArrayList<DatasetEntity>();
		getJdbcTemplate().query(sql, new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				DatasetEntity ds = new DatasetEntity();
				ds.setId(rs.getString("id"));
				ds.setName(rs.getString("name"));
				dsList.add(ds);
			}
		});
		return dsList;
	}
	
	public List<DatasetEntity> getDatasetByType(String type){
		String sql = "select * from nano_report_dc where type=?";
		final List<DatasetEntity> dsList = new ArrayList<DatasetEntity>();
		getJdbcTemplate().query(sql,new Object[]{type}, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				DatasetEntity ds = new DatasetEntity();
				ds.setId(rs.getString("id"));
				ds.setName(rs.getString("name"));
				dsList.add(ds);
			}
		});
		return dsList;
	}
	
	public DatasetEntity getDatasetInfoById(String id){
		String sql = "select * from nano_report_dc where id=?";
		DatasetEntity dataset = getJdbcTemplate().queryForObject(sql, new Object[]{id},new RowMapper<DatasetEntity>(){

			@Override
			public DatasetEntity mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				DatasetEntity dataset = new DatasetEntity();
				dataset.setId(rs.getString("id"));
				dataset.setName(rs.getString("name"));
				dataset.setType(rs.getString("type"));
				dataset.setDatatype(rs.getString("data_type"));
				dataset.setMethod(rs.getString("invoke_method"));
				if(rs.getClob("content") != null ){
					BufferedReader in = new BufferedReader(rs.getClob("content").getCharacterStream());
					StringWriter out = new StringWriter();
					int c;
					try {
						while((c = in.read()) != -1){
							out.write(c);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					dataset.setContent(out.toString());
				}
				dataset.setDs_id(rs.getString("ds_id"));
				dataset.setSummary(rs.getString("summary"));
				return dataset;
			}
			
		});
		return dataset;
	}
}
