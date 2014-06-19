package cn.newcapec.framework.core.context;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 平台上下文环境工厂类
 * @author andy.li
 */
public final class HttpNewcapecContextFactory {

	public static final String ATTRIBUTE_KEY = "cn.newcapec.framework.utils.context.NewcapecContext";

	private HttpNewcapecContextFactory() {
	}

	/**
	 * 获取当请上下文环境
	 * 
	 * @param servletrequest
	 * @return
	 */
	public static final NewcapecContext getContext(ServletRequest servletrequest) {
		return getContext(servletrequest, true);
	}

	/**
	 * 获取上下文环境
	 * 
	 * @param servletrequest
	 * @param flag
	 *            是否创建标识
	 * @return
	 */

	public static final NewcapecContext getContext(
			ServletRequest servletrequest, boolean flag) {
		HttpNewcapecContext httpNewcapecContext;
		if (servletrequest != null) {
			if ((httpNewcapecContext = (HttpNewcapecContext) servletrequest
					.getAttribute(ATTRIBUTE_KEY)) == null) {
				if (!flag) {
					return null;
				}
				httpNewcapecContext = new HttpNewcapecContext();
				servletrequest.setAttribute(ATTRIBUTE_KEY, httpNewcapecContext);
			}
			httpNewcapecContext.setRequest((HttpServletRequest) servletrequest);
		} else {
			httpNewcapecContext = new HttpNewcapecContext();
		}
		return httpNewcapecContext;
	}
}
