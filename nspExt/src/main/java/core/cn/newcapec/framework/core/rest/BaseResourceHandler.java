package cn.newcapec.framework.core.rest;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;

/**
 * <p>
 * Title: BaseResourceHandler
 * </p>
 * <p>
 * @author: andy.li
 * <p>
 * </p>
 * 
 */
public interface BaseResourceHandler {

	/**
	 * <p>
	 * Title: beforeHandle
	 * </p>
	 * <p>
	 * Description: 前置处理接口
	 * </p>
	 * 
	 * @param context
	 * @param request
	 * @param response
	 * @return void
	 */
	public void beforeHandle(Context context, Request request, Response response);

	/**
	 * <p>
	 * Title: afterHandle
	 * </p>
	 * <p>
	 * Description: 后置处理接口
	 * </p>
	 * 
	 * @param context
	 * @param request
	 * @param response
	 * @return void
	 */
	public void afterHandle(Context context, Request request, Response response);
}
