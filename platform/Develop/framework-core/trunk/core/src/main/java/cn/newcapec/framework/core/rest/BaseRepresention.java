package cn.newcapec.framework.core.rest;

import cn.newcapec.framework.core.model.variant.Variant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;

import java.io.IOException;
import java.util.Map;

/**
 * @author: andy.li
 * <p>
 * </p>
 * 
 * @version 1.0
 */
public class BaseRepresention extends StringRepresentation {

	/**
	 * 构造方法.格式：{success:true,msg:xx,data:{}}
	 * 
	 * @param message
	 *            消息对象
	 */
	public BaseRepresention(Msg message) {
		super(message.toJSONObject().toString(), MediaType.TEXT_PLAIN, null,
				CharacterSet.UTF_8);
	}

	/**
	 * 构造方法.格式：{success:true/false,msg:xx,data:{}}
	 * 
	 * @param flag
	 * @param msg
	 */
	public BaseRepresention(boolean flag, String msg) {
		super(new Msg(flag, msg).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * 构造方法.格式：{success:true/false,msg:xx,data:{name:value,....}}
	 * 
	 * @param flag
	 * @param msg
	 * @param data
	 */
	public BaseRepresention(boolean flag, String msg, Object data) {
		super(new Msg(flag, msg, data).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * 构造方法，格式：{success:true,msg:xx,data:{name1:value1,...,name:value}}
	 * 
	 * @param msg
	 *            提示信息
	 * @param data
	 *            数据
	 */
	public BaseRepresention(String msg, Object data) {
		super(new Msg(true, msg).setData(data).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * Constructor from a JSON object.
	 * 
	 * @param jsonObject
	 *            The JSON object.
	 */
	public BaseRepresention(JSONObject jsonObject) {
		this(jsonObject.toString());
	}

	/**
	 * Constructor from a map object.
	 * 格式:{success:true,msg:'',data:{name:value,...}}
	 * 
	 * @param map
	 *            The map to convert to JSON.
	 */
	public BaseRepresention(Map<Object, Object> map) {
		super(new Msg().setData(map).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * Constructor from a Variant object.
	 * 
	 *            The map to convert to JSON.
	 */
	public BaseRepresention(Variant variant) {
		super(new Msg().setData(variant).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * .
	 * 
	 * @param data
	 *            The bean to convert to JSON.
	 */
	public BaseRepresention(Object data) {
		super(new Msg().setData(data).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	/**
	 * Constructor.
	 * 
	 * @param jsonRepresentation
	 *            A source JSON representation to parse.
	 */
	public BaseRepresention(Representation jsonRepresentation)
			throws IOException {
		this(jsonRepresentation.getText());
	}

	/**
	 * Converts the representation to a JSON array.
	 * 
	 * @return The converted JSON array.
	 * @throws JSONException
	 */
	public JSONArray toJsonArray() throws Exception {
		return new JSONArray(getText());
	}

	/**
	 * Converts the representation to a JSON object.
	 * 
	 * @return The converted JSON object.
	 * @throws JSONException
	 */
	public JSONObject toJsonObject() throws JSONException {
		return new JSONObject(getText());
	}
}
