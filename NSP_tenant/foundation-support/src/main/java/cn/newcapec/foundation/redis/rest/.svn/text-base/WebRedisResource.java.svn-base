package cn.newcapec.foundation.redis.rest;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.newcapec.framework.base.dao.redis.config.RedisManager;
import cn.newcapec.framework.base.dao.redis.exception.RedisAccessException;
import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.base.rest.Msg;

//@SuppressWarnings("all")
//@Component("webRedisResource")
public class WebRedisResource  extends WebRedisBaseResource{
	 
	@Autowired
	private RedisManager redisManager;

    /**
     * 添加记录,如果记录已存在将覆盖原有的value 
     * @param key
     * @param value
     * @return
     * @throws RedisAccessException
     */
	@SuppressWarnings("unchecked")
	public void  set(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
		Iterator<String>iterator=	getJsonObject().keys();
		String key="";
		while(iterator.hasNext()){
			 key=iterator.next();
			redisManager.set(key, getJsonObject().getString(key));
		}
				  msg.setSuccess(true);
				  msg.setMsg("成功");
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException(" 添加记录失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
    /**
     * 返回hash中指定存储位置的值 
     * @param key
     * @return
     * @throws RedisAccessException
     */
	public void  get(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
			if(	getJsonObject().containsKey("key")){
			msg.setData(redisManager.get(getJsonObject().getString("key")));
			msg.setSuccess(true);
			msg.setMsg("成功");
			}
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("返回hash中指定存储位置的值 失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
    /**
     * 往redis 中增加一个以key为名称 的hash set
     *
     * @param key  redis hash key
     * @param hash redis hash data
     * @return Return OK or Exception if hash is empty
     */
	@SuppressWarnings("unchecked")
	public void  hmset(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
			redisManager.hmset(getJsonObject().getString("key"), getJsonObject().getJSONObject("hash"));
			msg.setSuccess(true);
			msg.setMsg("成功");
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("往redis 中增加一个以key为名称失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
	 /** 
	  * 根据多个name，获取对应的value，返回List,如果指定的key不存在,List对应位置为null 
	  * @param String key 
	  * @param String... names  存储位置 
	  * @return List<String> 
	  * */ 
	public void  hmget(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
			String key=getJsonObject().getString("key");
			String[] names=StringUtils.split(getJsonObject().getString("names"), ",");
			List<String> values=redisManager.hmget(key, names);
			msg.setData(values);
			msg.setSuccess(true);
			msg.setMsg("成功");
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("根据多个name，获取对应的value失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
    /** 
     * 添加一个对应关系 
     * @param String key 
     * @param String name 
     * @param String value 
     * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0 
     * **/ 
	public void  mset(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
			redisManager.hset(getJsonObject().getString("key"), getJsonObject().getString("name"), getJsonObject().getString("value"));
			msg.setSuccess(true);
			msg.setMsg("成功");
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("添加一个对应关系失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
    /**
     * 从redis hash key中获取一个对象
     *
     * @param key   redis hash类型中的key
     * @param field redis hash中的name
     * @return String
     */
	public void  mget(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
			String value=redisManager.hget(getJsonObject().getString("key"), getJsonObject().getString("name"));
			msg.setData(value);
			msg.setSuccess(true);
			msg.setMsg("成功");
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("从redis hash key中获取一个对象失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
    /**
     * 在redis 从list增加一组String
     *
     * @param key
     * @param values(以逗号分隔)
     * @return 数字型 ，返回增加到list的序号
     */
	public void  lpush(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
			String key=getJsonObject().getString("key");
			 String[] values=StringUtils.split(getJsonObject().getString("values"),",");
			 for(String value:values){
				 redisManager.lpush(key, value);
			 }
			msg.setSuccess(true);
			msg.setMsg("成功");
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("在redis 从list增加一组String失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
    /** 
     * 获取List所有记录
     * @param String key 
     * @param long start 
     * @param long end 
     * @return List 
     * */  
	@SuppressWarnings("rawtypes")
	public void  lrange(BaseRequest request, BaseResponse response){
		getjsonParms(request,response);
		Msg msg = new Msg();
		msg.setMsg("失败");
		if (getJsonDataFlag(getJsonObject())) {
		try {
			   List list = redisManager.lrange(getJsonObject().getString("key"), 0, -1); 
			   msg.setData(list);
			msg.setSuccess(true);
			msg.setMsg("成功");
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("获取List所有记录失败！", e);
			}
		}
		}
		response.print(msg.toJSONObjectPresention());
	}
	
	public boolean getJsonDataFlag(JSONObject jsonObject){
		if (jsonObject.containsKey("data") && null != jsonObject.getJSONObject("data")) {
			jsonObject=jsonObject.getJSONObject("data");
			
		return true;
		}
		return false;
	}
}
