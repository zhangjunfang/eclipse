package cn.newcapec.foundation.report.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.report.dao.AbstractJdbcDao;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.foundation.report.model.ReportManageMap;
import cn.newcapec.foundation.report.model.WidgetEntity;
import cn.newcapec.foundation.report.model.WidgetMap;

@Repository("widgetManageDao")
public class WidgetManageDao extends AbstractJdbcDao{
	private static final Log logger = LogFactory.getLog(WidgetManageDao.class);
//	private static BaseDao baseDao = new BaseDao();
	public ReportManageEntity getReportEntityById(String id){
		String sql = "select m.*,c.name as dcname from nano_report_manage m " +
				"left join nano_report_dc c on m.dc_id = c.id where m.id=?";
		ReportManageEntity report =getJdbcTemplate().queryForObject(sql,new Object[]{id},new ReportManageMap());
		return report;
	}
	
	public List<WidgetEntity> getWidgetByParams(Object[] args){
		String sql = "select w.*,c.name dcname from nano_report_widget w " +
				"left join nano_report_dc c on w.dc_id = c.id where 1=1 "+
		"order by w.id asc limit ?,?";
		try {
			List<WidgetEntity> widgetList = getJdbcTemplate().query(sql.toString(),args,new WidgetMap());
			return widgetList;
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return null;
	}
	
	public int getWidgetCountByParams(){
		String sql = "select count(*) from nano_report_widget";
		return getJdbcTemplate().queryForInt(sql);
	}
	
	public void inserWidgetInfo(Object[] args){
		try {
			String sql = "insert into nano_report_widget (id,text_field,parent_field," +
					"top_default,widget_type,summary,name,value_field,identifier,dc_id) " +
					"values (?,?,?,?,?,?,?,?,?,?)";
			getJdbcTemplate().update(sql,args);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
		}
	}
	
	public void updateWidgetInfo(Object[] args){
		try {
			String sql = "update nano_report_widget set text_field=?,parent_field=?," +
					"top_default=?,widget_type=?,summary=?,name=?,value_field=?," +
					"identifier=?,dc_id=? where id=?";
			getJdbcTemplate().update(sql,args);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
		}
		
	}
	
	public boolean deleteWidgetInfo(String id){
		try {
			String sql = "delete from nano_report_widget where id=?";
			getJdbcTemplate().update(sql,id);
			return true;
		} catch (Exception e) {
			logger.info(e.toString());
			return false;
		}
	}
	
	
	public int checkUniqueName(String name){
		String sql = "select count(*) from nano_report_widget where name=?";
		return getJdbcTemplate().queryForInt(sql, name);
	}
	
	public List<WidgetEntity> queryAllWidgetInfo(){
		String sql = "select * from nano_report_widget";
		final List<WidgetEntity> widgetList = new ArrayList<WidgetEntity>();
		getJdbcTemplate().query(sql,new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				WidgetEntity widget = new WidgetEntity();
				widget.setId(rs.getString("id"));
				widget.setName(rs.getString("name"));
				widgetList.add(widget);
			}
		});
		return widgetList;
	}
	public List<WidgetEntity> queryWidgetByType(String type){
		String sql = "select * from nano_report_widget where widget_type=?";
		final List<WidgetEntity> widgetList = new ArrayList<WidgetEntity>();
		getJdbcTemplate().query(sql,new Object[]{type},new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				WidgetEntity widget = new WidgetEntity();
				widget.setId(rs.getString("id"));
				widget.setName(rs.getString("name"));
				widgetList.add(widget);
			}
		});
		return widgetList;
	}
	
	public WidgetEntity queryWidgetByName(String widget_name){
		String sql = "select * from nano_report_widget where name=?";
		WidgetEntity widget = getJdbcTemplate().queryForObject(sql,new Object[]{widget_name},new RowMapper<WidgetEntity>(){

			@Override
			public WidgetEntity mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				WidgetEntity widget = new WidgetEntity();
				widget.setId(rs.getString("id"));
				widget.setName(rs.getString("name"));
				widget.setParentField(rs.getString("parent_field"));
				widget.setTextField(rs.getString("text_field"));
				widget.setValueField(rs.getString("value_field"));
				widget.setSummary(rs.getString("summary"));
				widget.setTopDefault(rs.getString("top_default"));
				widget.setWidgetType(rs.getString("widget_type"));
				widget.setUserId(rs.getString("identifier"));
				widget.setDc_id(rs.getString("dc_id"));
				return widget;
			}
			
		});
		return widget;
	}
}
