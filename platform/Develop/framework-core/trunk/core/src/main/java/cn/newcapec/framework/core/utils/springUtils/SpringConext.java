/*  
 * @(#) SpringConext.java Create on 2013-5-30 上午11:38:48   
 *   
 * Copyright 2013 by pztx.   
 */

package cn.newcapec.framework.core.utils.springUtils;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

/**
 * spring上下文工具类
 * @author andy.li
 */
@Component
public class SpringConext implements ApplicationContextAware,ServletContextAware {

	private static ApplicationContext context;
	
	private static ServletContext servletContext;


	public void setApplicationContext(ApplicationContext acx) {
		context = acx;
	}

	/**
	 * 获取本地spring环境
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	

	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}
	

}
