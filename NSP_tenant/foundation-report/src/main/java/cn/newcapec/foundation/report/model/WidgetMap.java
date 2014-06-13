package cn.newcapec.foundation.report.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WidgetMap implements RowMapper<WidgetEntity> {

	@Override
	public WidgetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		WidgetEntity widget  = new WidgetEntity();
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
		widget.setDcname(rs.getString("dcname"));
		return widget;
	}
}
