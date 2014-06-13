package cn.newcapec.foundation.privilege.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import cn.newcapec.foundation.model.LoginUser;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.foundation.utils.SpringBusinessConext;
import cn.newcapec.foundation.utils.UserTool;
import cn.newcapec.foundation.utils.WebUtils;
import cn.newcapec.framework.base.dao.redis.cache.DefaultLocalCache;
import cn.newcapec.framework.base.dao.redis.cache.LocalCache;
import cn.newcapec.framework.base.log.LogEnabled;
import cn.newcapec.framework.utils.context.HttpNewcapecContext;
import cn.newcapec.framework.utils.context.HttpNewcapecContextFactory;
import cn.newcapec.framework.utils.context.Keys;

/**
 * 用户登录过滤器
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public class LoginFilter implements Filter,LogEnabled {

//	@Autowired
//	ResourceService resourceService ;
////	/* 资源业务接口类 */
	static ResourceService resourceService = SpringBusinessConext
			.getApplicationContext().getBean(ResourceService.class);
	
	//本地缓存
	private  static LocalCache<String, List<Map<String, Object>>> authCache= DefaultLocalCache.instance();

	/**
	 * 普通用户允许访问的url
	 */
	private List<String> allowUrl = new ArrayList<String>();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String url = WebUtils.getRequestURI(httpRequest);
		//5代表session级别
		User user = (User) HttpNewcapecContextFactory.getContext(request).getAttribute(5, Keys.USER);
		//用户转换为登录账号
		LoginUser loginUser = UserTool.ProUser2LoginUser(user);
//		//获得数据源信息
//		String dataSoruce= (String)HttpNewcapecContext.getContext().getAttribute(Keys.DATA_SOURCE_URL);
//		log.info("dataSoruce"+dataSoruce);
//		if(StringUtils.isBlank(dataSoruce)){
//			PrintWriter printWriter;
//			printWriter = ((HttpServletResponse) response).getWriter();
//			printWriter.write("<script type='text/javascript'>window.parent.location='/restful/privilegeProxyService/user/loginUI'</script>");
//			printWriter.close();
//		}
		// 判断用户
		if (loginUser != null) {
			if (!allowUrl.contains(url)) {
					if (validateUrl(loginUser.getId(), url)) {
						chain.doFilter(request, response);
						
					} else {
						response.setContentType("text/html;charset=utf-8");
						response.getWriter().write("您没有权限访问该资源。");
						response.getWriter().flush();
//					}
				}
			}else{
				chain.doFilter(request, response);
			}
		} else {
			if (!allowUrl.contains(url)) {
				PrintWriter printWriter;
				printWriter = ((HttpServletResponse) response).getWriter();
				printWriter.write("<script type='text/javascript'>window.parent.location='/restful/privilegeProxyService/user/loginUI'</script>");
				printWriter.close();
				return;
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		splitUrl(config.getInitParameter("allowUrl"));
	}

	private void splitUrl(String urlStr) {
		if (urlStr != null && urlStr.length() > 0) {
			allowUrl = Arrays.asList(StringUtils.stripAll(urlStr.split(";")));
		}
	}

	public boolean validateUrl(String userid, String url) {
		 List<Map<String, Object>> priviles = new CopyOnWriteArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(userid)) {
				//提高性能，增加了本地缓存,用户id唯一的，所以不会重复
				if(null==authCache.get(userid) || authCache.size()<1){
					priviles = resourceService.queryResorucesByUserid(userid);
					authCache.put(userid, priviles);
				}else{
					priviles=authCache.get(userid);
				}
				
			if (null != priviles && priviles.size() > 0) {
				for (Map<String, Object> map : priviles) {
					for (Iterator<String> keys = map.keySet().iterator(); keys
							.hasNext();) {
						String key = (String) keys.next();
						if (key.equals("URL")) {
							Object value = map.get(key);
							if (null != value) {
								if (value.toString().indexOf(url) != -1) {
									return true;
									// break;
								}

							}

						}
					}

				}

			}
		}
		return false;
	}
}