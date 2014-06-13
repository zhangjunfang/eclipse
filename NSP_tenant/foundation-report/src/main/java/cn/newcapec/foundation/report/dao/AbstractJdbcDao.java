package cn.newcapec.foundation.report.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.newcapec.foundation.report.util.NanoReportService;

public abstract class AbstractJdbcDao {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		if(jdbcTemplate == null)
			jdbcTemplate = new JdbcTemplate(NanoReportService.getInjectedDataSource());
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated method stub
		assert (jdbcTemplate != null);
		this.jdbcTemplate = jdbcTemplate;
	}

}
