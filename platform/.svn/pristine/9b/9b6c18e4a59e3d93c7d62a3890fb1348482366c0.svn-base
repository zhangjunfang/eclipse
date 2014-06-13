/**
 * 
 */
package cn.newcapec.framework.core.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.exception.SysException;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.stringUtils.StringUtil;

/**
 * @author 景明超
 * @version SystemExceptionHandler.java 2013-12-20 上午10:57:17
 */

public class SystemExceptionHandler implements HandlerExceptionResolver ,LogEnabled{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		Msg msg = new Msg();
		try {
			if (ex instanceof BaseException) {
				msg.setMsg(ex.getMessage());
				response.getWriter().write(msg.toJSONObject().toString());
				response.getWriter().flush();
				log.error(ExceptionUtils.getFullStackTrace(ex));

			} else if (ex instanceof SysException) {
				msg.setMsg(ex.getMessage());
				response.getWriter().write(msg.toJSONObject().toString());
				response.getWriter().flush();
				log.error(ExceptionUtils.getFullStackTrace(ex));
			} else {
				response.getWriter().write(msg.toJSONObject().toString());
				response.getWriter().flush();
				log.error(ExceptionUtils.getFullStackTrace(ex));
			}

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(ex));
			String errMsg = e.getMessage();
			if (StringUtil.isValid(errMsg)) {
				try {
					msg.setMsg(errMsg);
					response.getWriter().write(msg.toJSONObject().toString());
					response.getWriter().flush();
				} catch (IOException e1) {
				}
			} else {
				try {
					msg.setMsg("服务端处理发生错误，请尝试重新操作！");
					response.getWriter().write(msg.toJSONObject().toString());
					response.getWriter().flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
}
		

