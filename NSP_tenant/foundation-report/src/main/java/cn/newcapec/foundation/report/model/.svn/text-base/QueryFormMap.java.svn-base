package cn.newcapec.foundation.report.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QueryFormMap implements RowMapper<QueryFormEntity> {

	@Override
	public QueryFormEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		QueryFormEntity form = new QueryFormEntity();
		form.setId(rs.getString("id"));
		form.setName(rs.getString("name"));
		form.setReport_id(rs.getString("report_id"));
		form.setReport_name(rs.getString("reportname"));
		form.setForm_widget_length(rs.getString("form_widget_length"));
		form.setForm_widget_type(rs.getString("form_widget_type"));
		form.setForm_widget_validator(rs.getString("form_widget_validator"));
		form.setSortCode(rs.getString("sortcode"));
		form.setWidget_id(rs.getString("widget_id"));
		form.setSummary(rs.getString("summary"));
		form.setForm_widget_id(rs.getString("form_widget_id"));
		form.setWidget_name(rs.getString("widget_name"));
		return form;
	}

}
