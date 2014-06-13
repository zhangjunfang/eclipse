/*  
 * @(#) MyTestInterceptor.java Create on 2013-10-8 下午3:28:55   
 *   
 * Copyright 2013 by pztx.   
 */

package cn.newcapec.framework.core.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.newcapec.framework.core.filter.PagerFilter;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.utils.fileUtils.SysConfigUtil;

public class NspApplicationContextInterceptor extends HandlerInterceptorAdapter implements LogEnabled {

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			/*获取mvc的上下文路径*/
			//上下文加 web.xml 的spring 配置路径
			String envRoot=PagerFilter.getRootPath()+ "/" +SysConfigUtil.get("framework.springContext");  
			log.info("viewName : "+viewName);
			ctxMap.put("ctx",PagerFilter.getRootPath());
			ctxMap.put("viewName", viewName);
			ctxMap.put("mvcRoot", SysConfigUtil.get("framework.springContext"));
			ctxMap.put("envRoot",envRoot);
			modelAndView.addObject("_project", ctxMap);
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
