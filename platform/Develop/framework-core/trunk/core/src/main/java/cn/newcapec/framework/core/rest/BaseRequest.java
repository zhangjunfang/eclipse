package cn.newcapec.framework.core.rest;

import org.apache.commons.fileupload.FileItem;

import cn.newcapec.framework.core.model.FileAttach;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public interface BaseRequest {
	
	/**
	 * 
	 * @return
	 */
	public org.json.JSONObject getJSONObject();

	/**
	 * 获取参数
	 * 
	 * @param name
	 * @return
	 */
	public String getParameter(String name);

	/**
	 * 获取参数
	 * 
	 * @param name
	 * @return
	 */
	public String[] getParameters(String name);

	/**
	 * 获取客户端提交的文件流
	 * 
	 * @return
	 */
	public List<FileItem> getUploadFileItems();
	
	/**
	 * 获取上传附件
	 * @return
	 */
	public  List<FileAttach> getfiles();

	/**
	 * 获取参数名称
	 */
	public String[] getParamNames();

	/**
	 * 获取客户端请求参数
	 * 
	 * @return
	 */

	public Map<String, String> getParamValueMap();

	/**
	 * 获取参数，此方法总是返回一个有效地String, 不会返回null.
	 * 
	 * @param name
	 * @return
	 */
	public String getString(String name);

	/**
	 * 获取参数，此方法总是返回一个有效地String, 不会返回null
	 * 
	 * @param name
	 *            参数名称
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public String getString(String name, String defaultValue);

	/**
	 * 按int型获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public int getInt(String name);

	/**
	 * 按int型获取参数值
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String name, int defaultValue);

	/**
	 * 按long型获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public long getLong(String name);

	/**
	 * 按long型获取参数值
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public long getLong(String name, long defaultValue);

	/**
	 * 按float型获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public float getFloat(String name);

	/**
	 * 按float型获取参数值
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(String name, float defaultValue);

	/**
	 * 按double型获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public double getDouble(String name);

	/**
	 * 按double型获取参数值
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String name, double defaultValue);

	/**
	 * 按boolean型获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public boolean getBoolean(String name);

	/**
	 * 按boolean型获取参数值
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String name, boolean defaultValue);

	/**
	 * 按date型获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public Date getDate(String name);

	/**
	 * 按date型获取参数值
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Date getDate(String name, Date defaultValue);

	/**
	 * 返回参数值数组.该方法返回的每个参数值始终不为NULL
	 * 
	 * @param name
	 * @return
	 */
	public String[] getStringValues(String name);

	/**
	 * 将当前Request中包含的参数(Parameter)设置到给定的对象当中.
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void parameterToDO(Object object);

	/**
	 * 将当前Request中包含的参数(Parameter)设置到Map当中.
	 * 
	 * @return
	 */
	public Map parametersToMap();

	/**
	 * 将当前Request中包含的属性(Attribute)设置到给定的对象当中.
	 * 
	 * @param dest
	 * @throws Exception
	 */
	public void attributiesToDO(Object dest);

	/**
	 * 当前Request中包含的属性(Attribute)设置到Map对象当中
	 * 
	 * @return
	 */
	public Map attributiesToMap();

	/**
	 * 获取原始request
	 */
	public org.restlet.data.Request getOrginRequest();

	/**
	 * bigdecimal型获取参数值
	 * 
	 * @param name
	 * @return
	 */
	public BigDecimal getBigdecimal(String name);

	/**
	 * 按bigdecimal型获取参数值
	 * 
	 * @param name
	 *            参数名称
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public BigDecimal getBigdecimal(String name, BigDecimal defaultValue);
}
