package test.java;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.newcapec.foundation.tenant.biz.DataSourceService;
import cn.newcapec.foundation.tenant.model.DataSource;
import cn.newcapec.framework.junit.BaseTestCaseSpringJunit4;


public class DataSourceTest  extends BaseTestCaseSpringJunit4{

	@Autowired
	DataSourceService dataSourceService;

	@Test
	public void addDataSoruce(){
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/newcapec?characterEncoding=UTF-8");
		ds.setUserName("root");
		ds.setPassword("root");
		dataSourceService.add(ds);
		
	}
}
