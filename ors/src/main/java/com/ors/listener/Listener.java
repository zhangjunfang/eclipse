package com.ors.listener;

//import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent event) {
		// 销毁记录
	}

	public void contextInitialized(ServletContextEvent event) {
		// 记录登录信息
		// Date date = new Date();
		// event.getServletContext().log(date.toString());
	}
}
