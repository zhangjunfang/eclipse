package cn.newcapec.foundation.report.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReportManageMap implements
		RowMapper<ReportManageEntity> {

	@Override
	public ReportManageEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ReportManageEntity report = new ReportManageEntity();
		report.setId(rs.getString("id"));
		report.setName(rs.getString("name"));
		report.setSummary(rs.getString("summary"));
		report.setDcname(rs.getString("dcname"));
		report.setDc_id(rs.getString("dc_id"));
		report.setState(rs.getInt("state"));
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
			report.setContent(out.toString());
		}
		return report;
	}

}
