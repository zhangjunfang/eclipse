package cn.newcapec.framework.core.rest.velocity;


import cn.newcapec.framework.core.utils.dataUtils.DateUtil;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.tools.generic.DateTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.Map;

/**
 * 
 *  扩展的velocity view
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public class VelocityView extends org.springframework.web.servlet.view.velocity.VelocityView {
	

	@Override
	protected Context createVelocityContext(Map model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//增加服务器路径
		DateTool dateTool = new DateTool();
		model.put("dateTool", dateTool);
		//日期长短格式
		model.put("DATE_FORMAT_SHORT", DateUtil.DATE_FORMAT);
		model.put("DATE_FORMAT_LONG",DateUtil.DATETIME_FORMAT);
		return super.createVelocityContext(model, request, response);
	}
	
	@Override
	protected void mergeTemplate(Template template, Context context, HttpServletResponse response) throws Exception {
	    StringWriter sw = new StringWriter();
	    try {
	    	//模板合成
			template.merge(context, sw);
				//输出响应
				response.getWriter().write(sw.toString());
		}
		catch (MethodInvocationException ex) {
			throw new Exception(
					"Method invocation failed during rendering of Velocity view with name '" +
					getBeanName() + "': " + ex.getMessage() + "; reference [" + ex.getReferenceName() +
					"], method '" + ex.getMethodName() + "'",
					ex.getWrappedThrowable());
		}
	}
}
