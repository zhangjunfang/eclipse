package cn.newcapec.foundation.report.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DatasetMap implements RowMapper<DatasetEntity>{

	@Override
	public DatasetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			DatasetEntity dataset = new DatasetEntity();
			dataset.setId(rs.getString("id"));
			dataset.setFilter_field(rs.getString("filter_field"));
			dataset.setName(rs.getString("name"));
			dataset.setType(rs.getString("type"));
			dataset.setDatatype(rs.getString("data_type"));
			dataset.setMethod(rs.getString("invoke_method"));
			if(rs.getClob("content") != null ){
				BufferedReader in = new BufferedReader(rs.getClob("content").getCharacterStream());
				StringWriter out = new StringWriter();
				int c;
				while((c = in.read()) != -1){
					out.write(c);
				}
				dataset.setContent(out.toString());
			}
			dataset.setDs_id(rs.getString("ds_id"));
			dataset.setDsname(rs.getString("dsname"));
			dataset.setSummary(rs.getString("summary"));
			return dataset;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
