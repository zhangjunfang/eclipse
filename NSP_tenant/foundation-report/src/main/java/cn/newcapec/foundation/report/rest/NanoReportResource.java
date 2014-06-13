package cn.newcapec.foundation.report.rest;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.DateTool;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.resource.StringRepresentation;
import org.springframework.util.ClassUtils;

import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.framework.base.rest.BaseResource;
import cn.newcapec.framework.base.rest.BaseResourceHandler;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.base.velocity.TemplateEngine;
import cn.newcapec.framework.utils.DateUtil;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PagerFilter;

/**
 * 报表资源
 * @author shikeying
 *
 */
public class NanoReportResource extends BaseResource implements
		BaseResourceHandler {

	protected static transient Log logger = LogFactory.getLog(NanoReportResource.class);
	
	//page分页查询类
	protected Page<List<Map<String, Object>>> page = new Page<List<Map<String, Object>>>();
	//上下文
	protected org.apache.velocity.context.Context context = getVelocityContext();
	
	static final String MODULE_PAGE_PREFIX = "foundation/report/";
	
	protected static VelocityContext getVelocityContext(){
		VelocityContext  result= new VelocityContext();
		//增加服务器路径
		DateTool dateTool = new DateTool();
		result.put("dateTool", dateTool);
		//日期长短格式
		result.put("date_format_short", DateUtil.DATE_FORMAT);
		result.put("date_format_long",DateUtil.DATETIME_FORMAT);
		String ctx = PagerFilter.getRootPath();
		result.put("ctx", ctx);
		return result;
	}
	
	protected void throwBizException(Throwable e, String bizMessage){
		log.error(ExceptionUtils.getFullStackTrace(e));
		if (e instanceof BaseException) {
			throw (BaseException) e;
		} else {
			throw new BaseException(bizMessage, e);
		}
	}
	
	/**
	 * 输出html
	 * @param response
	 * @param url
	 */
	protected void print2html(BaseResponse response, String url) {
		String html = TemplateEngine.parse(url, context);
		response.print(new StringRepresentation(html, MediaType.TEXT_HTML, null, CharacterSet.UTF_8));
	}
	
	/**
	 * 输出JSON数据
	 * @param response
	 * @param object
	 */
	protected void print2Json(BaseResponse response, Object object){
		//JSONObject
		response.print(new StringRepresentation(object.toString()
				, MediaType.APPLICATION_JSON, null, CharacterSet.UTF_8));
	}
	
	protected void print2Xml(BaseResponse response, Object object){
		//JSONObject
		response.print(new StringRepresentation(object.toString()
				, MediaType.APPLICATION_XML, null, CharacterSet.UTF_8));
	}
	
	protected void print2String(BaseResponse response, Object object){
		response.print(new StringRepresentation(object.toString()
				, MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8));
	}
	
	/**
	 * 返回JAVA原始类型对应的字符字面量值
	 * @param val
	 * @return
	 */
	protected String getStringFromPrimitiveObject(Object val){
		Class<?> type = ClassUtils.resolvePrimitiveClassName(val.getClass().getSimpleName());
		if(type == null)
			throw new IllegalArgumentException("type is not primitive: " + val.getClass());
		
		return val.toString();
	}
	
	/**
	 * 渲染功能首页
	 * @param indexURL 页面URL，如：foundation/report/listdata.html
	 * @param response
	 */
	protected void doIndex(String indexURL, BaseResponse response){
		try {
			print2html(response, indexURL);
		} catch (Exception e) {
			throwBizException(e, "未找到功能页面，url = " + indexURL);
		}
	}
}
