package cn.newcapec.framework.core.rest;

import java.util.Map;

import org.json.JSONObject;
import org.restlet.data.Response;
import org.restlet.resource.Representation;

/**
 * @author: andy.li
 * <p>
 * </p>
 * 
 */
public interface BaseResponse {
	
	
	
	
	/**
	 * 打印输出展现结果
	 * 
	 * @param representation
	 */
	public void toMultiView(String url,Map model);
	
	
	/**
	 * 打印输出展现结果
	 * 
	 * @param representation
	 */
	public void print(Representation representation);
	
	
	/**
	 * 输出结果到velocity html
	 * @param url
	 * @param context
	 */
	public void toView(String url, org.apache.velocity.context.Context context);
	

	/**
	 * 输出字符类型
	 * 
	 * @param str
	 */
	public void print(String str);

	/**
	 * 输出json类型
	 * 
	 * @param jsonObj
	 */
	public void print(JSONObject jsonObj);

	/**
	 * 获取restlet原始封装后的response
	 * 
	 * @return
	 */
	public Response getOrignResponse();



}
