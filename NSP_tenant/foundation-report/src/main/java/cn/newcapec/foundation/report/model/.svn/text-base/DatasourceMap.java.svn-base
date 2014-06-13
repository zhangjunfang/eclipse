package cn.newcapec.foundation.report.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DatasourceMap implements RowMapper<DatasourceEntity> {

	@Override
	public DatasourceEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		DatasourceEntity ds = new DatasourceEntity();
		ds.setId(rs.getString("id"));
		ds.setName(rs.getString("name"));
		ds.setType(rs.getString("type"));
		ds.setDsAddress(rs.getString("ds_address"));
		ds.setDsPass(rs.getString("ds_pass"));
		ds.setDsUser(rs.getString("ds_user"));
		ds.setSummary(rs.getString("summary"));
		ds.setDsServer(rs.getString("ds_service"));
		ds.setUserId(rs.getString("identifier"));
		return ds;
	}
}
