package cn.newcapec.foundation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.newcapec.framework.base.log.LogEnabled;
import cn.newcapec.framework.utils.context.HttpNewcapecContext;
import cn.newcapec.framework.utils.context.HttpNewcapecContextFactory;
import cn.newcapec.framework.utils.context.Keys;
import cn.newcapec.framework.utils.context.NewcapecContext;
import cn.newcapec.framework.utils.tools.ServerAdressConfig;
import cn.newcapec.framework.utils.tools.ServerDateSourceConfig;

/**
 * 
 * 通过域名找到租户名
 * 
 * 
 */

/**
 * web.xml的第一个filter，通过域名找到租户名，设置线程变量
 * 
 * 
 */
public class TenantContextFilter implements Filter, LogEnabled {

	public void destroy() {

	}
	
	/**
	 * 
	 * 过滤请求
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		String serverName = request.getServerName();
		String dns = ServerAdressConfig.getItem(serverName);
		
		if (StringUtils.isNotBlank(dns)) {
			String dataSource = ServerDateSourceConfig.getItem(dns);
			if (StringUtils.isNotBlank(dataSource)) {
				request.setAttribute(Keys.DATA_SOURCE_URL, dataSource);
				NewcapecContext context = HttpNewcapecContextFactory.getContext(request);
				NewcapecContext.registerContext(context);
				log.info("DynamicDataSource:"+HttpNewcapecContext.getContext().getAttribute(Keys.DATA_SOURCE_URL));
				filterChain.doFilter(request, response);
			}else{
				printError(response);
				return;
			}
		}else{
			printError(response);
			return;
		}
	}
	/**
	 * 输出
	 * @param response
	 * @throws IOException
	 */
	private void printError(ServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("租户未开通，请联系管理员！");
		response.getWriter().flush();
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
