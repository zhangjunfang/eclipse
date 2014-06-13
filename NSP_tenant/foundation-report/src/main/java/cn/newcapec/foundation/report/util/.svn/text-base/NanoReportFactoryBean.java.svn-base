package cn.newcapec.foundation.report.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

public class NanoReportFactoryBean implements FactoryBean<Object> {

	public void setDataSource(DataSource dataSource) {
		Assert.notNull(dataSource, "dataSource is required.");
		NanoReportService.setInjectedDataSource(dataSource);
		NanoReportService.getSystemConfig();
	}

	@Override
	public Object getObject() throws Exception {
		// TODO Auto-generated method stub
		return new Object();
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Object.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

}
