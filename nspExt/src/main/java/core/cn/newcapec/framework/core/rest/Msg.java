package cn.newcapec.framework.core.rest;

import cn.newcapec.framework.core.utils.stringUtils.JsonDateValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 用于封装消息,格式{success:true,msg:xx,data:{}}
 * <p>
 * @author: andy.li
 * 
 */
@SuppressWarnings("all")
public class Msg {

	protected Logger log = Logger.getLogger(this.getClass());;
	/**
	 * 成功标志
	 */
	private boolean success = false;
	/**
	 * 提示信息
	 */
	private String msg;
	//code值
	private String code;
	/* 数据*/
	private Object data;

	public Msg() {

	}

	/**
	 * 构造方法。标识成功信息
	 * 
	 * @param msg
	 */
	public Msg(String msg) {
		this(true, msg);
	}

	/**
	 * 构造方法.
	 * 
	 * @param success
	 * @param msg
	 */
	public Msg(boolean success, String msg) {
		this(success, msg, null);
	}

	/**
	 * 构造方法.
	 * 
	 * @param msg
	 *            消息
	 * @param data
	 *            数据
	 */
	public Msg(String msg, Object data) {
		this(true, msg, null);
	}

	/**
	 * 构造方法
	 * 
	 * @param success
	 * @param msg
	 * @param data
	 */
	public Msg(boolean success, String msg, Object data) {
		this.msg = msg;
		this.success = success;
		if(null==data || "null".equals(data)){
			this.data = "";
		}else
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public Msg setData(Object data) {
		this.data = data;
		return this;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public Msg setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @param success
	 *            the success to set
	 */
	public Msg setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	
	
	public JsonConfig getJsonConfig(String dateFormat) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor(dateFormat));
		return config;
	}
	

	/**
	 * 将消息转为JsonObject对象。
	 * 日期格式化
	 * @return
	 */;
	@SuppressWarnings("static-access")
	public JSONObject toJSONObject() {
		 String format = "yyyy-MM-dd HH:mm:ss";
		JSONObject jsonObject = new JSONObject();
		String newMsg = this.msg==null?"":this.msg;
		this.setMsg(newMsg);
		jsonObject = jsonObject.fromObject(this,getJsonConfig(format));
		return jsonObject;
	}
	
	/**
	 * 将消息转为JsonObject对象。
	 * 自定义日期格式化
	 * @return
	 */;
	 @SuppressWarnings("static-access")
	 public JSONObject toJSONObject(String dataFormat) {
		 JSONObject jsonObject = new JSONObject();
		String newMsg = this.msg == null ? "" : this.msg;
		 this.setMsg(newMsg);
		 jsonObject = jsonObject.fromObject(this,getJsonConfig(dataFormat));
		 return jsonObject;
	 }

	

	/**
	 * 将消息转换为JSON
	 * Presention格式:{success:true,msg:xx,data:{xx1:value1,....,xx:value}}
	 * 
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public Representation toJSONObjectPresention() {

		return new StringRepresentation(toJSONObject().toString().replaceAll("null", "\"\""),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * 将消息转换为JSON
	 * Presention格式:{success:true,msg:xx,data:{xx1:value1,....,xx:value}}
	 * 
	 * @param names
	 *            转换为json对象的属性列表
	 * @return
	 */
	public Representation toJSONObjectPresention(String dataFormat) {
		
		return new StringRepresentation(toJSONObject(dataFormat).toString().replaceAll("null", "\"\""),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}
}
