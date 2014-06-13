
package cn.newcapec.foundation.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @author andy.li
 */
@Component
public class SpringBusinessConext implements ApplicationContextAware {

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext acx) {
		context = acx;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}
}
