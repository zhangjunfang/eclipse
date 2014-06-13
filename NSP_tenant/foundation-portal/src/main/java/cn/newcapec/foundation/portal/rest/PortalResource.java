package cn.newcapec.foundation.portal.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.velocity.VelocityContext;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.resource.StringRepresentation;
import org.springframework.beans.factory.annotation.Autowired;

import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.model.Menu;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.foundation.utils.WebUtils;
import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResource;
import cn.newcapec.framework.base.rest.BaseResourceHandler;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.base.velocity.TemplateEngine;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PagerFilter;
import cn.newcapec.framework.utils.SystemContext;

/**
 * 门户集成基础视图类
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public class PortalResource extends BaseResource implements BaseResourceHandler {
	
	
	//page分页查询类
	protected Page<List<Map<String, Object>>> page = new Page<List<Map<String, Object>>>();
	//上下文
	protected org.apache.velocity.context.Context context  =  this.getVelocityContext();
	
	protected static VelocityContext getVelocityContext(){
		VelocityContext  result= new VelocityContext();
		return result;
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

	
}
