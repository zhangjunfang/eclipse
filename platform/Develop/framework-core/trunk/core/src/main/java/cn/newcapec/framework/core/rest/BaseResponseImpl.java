package cn.newcapec.framework.core.rest;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;

import cn.newcapec.framework.core.filter.PagerFilter;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.rest.velocity.TemplateEngine;
import cn.newcapec.framework.core.utils.httpUtils.WebUtils;

/**
 * @author: andy.li
 * </p>
 * 
 */
public class BaseResponseImpl implements BaseResponse,LogEnabled {
	private Response response = null;
	public static final String rootPath = PagerFilter.getRootPath(); 

	private static final CharacterSet DEFAULT_CHARSET = CharacterSet.UTF_8;

	public BaseResponseImpl(Response response) {
		this.response = response;
	}

	public void print(Representation representation) {
		representation.setCharacterSet(DEFAULT_CHARSET);
		this.getOrignResponse().setEntity(representation);
	}
	
	public void toView(String url,org.apache.velocity.context.Context context) {
		String html = "";
        try {
        	
            html = TemplateEngine.parse(url, context);
            Representation representation =  new StringRepresentation(html, MediaType.TEXT_HTML,
            		null, CharacterSet.UTF_8);
            this.getOrignResponse().setEntity(representation);
        } catch (Exception e) {
        	log.error(ExceptionUtils.getFullStackTrace(e));
        }
	}
	
	public void print(Representation representation, CharacterSet characterSet) {
		if (!DEFAULT_CHARSET.equals(characterSet)) {
			representation.setCharacterSet(characterSet);
		}

		this.getOrignResponse().setEntity(representation);
	}

	public void print(String str) {
		print(new StringRepresentation(str));
	}

	public void print(JSONObject jsonObj) {
		print(jsonObj.toString());
	}

	public void print(Representation representation, String characterSet) {
		CharacterSet newCharacterSet = new CharacterSet("UTF-8", "");
		if (!DEFAULT_CHARSET.equals(newCharacterSet)) {
			representation.setCharacterSet(newCharacterSet);
		}
		this.getOrignResponse().setEntity(representation);
	}

	public Response getOrignResponse() {
		return this.response;
	}

	@Override
	public void toMultiView(String url, Map model) {
		HttpServletResponse result = null;
		result =  WebUtils.getResponse(response.getRequest());
		
	}
}