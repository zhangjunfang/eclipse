package cn.newcapec.foundation.privilege.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jodd.exception.ExceptionUtil;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.DateTool;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.resource.StringRepresentation;

import cn.newcapec.framework.base.dao.redis.cache.DefaultLocalCache;
import cn.newcapec.framework.base.dao.redis.cache.LocalCache;
import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.framework.base.rest.BaseResource;
import cn.newcapec.framework.base.rest.BaseResourceHandler;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.base.velocity.TemplateEngine;
import cn.newcapec.framework.utils.DateUtil;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PagerFilter;

/**
 * 用户授权模块集成资源类
 * 
 * @author andy.li
 * 
 */
@SuppressWarnings("all")
public abstract class PrivilegeResource extends BaseResource implements
		BaseResourceHandler {

	// page分页查询类
	protected Page<List<Map<String, Object>>> page = new Page<List<Map<String, Object>>>();

	/**
	 * 清除权限缓存
	 */
	protected void clearCache() {
	    DefaultLocalCache.instance().clear();
	}
	

	/**
	 * 抛出异常
	 * 
	 * @param e
	 * @param msg
	 */
	protected void ThrowsException(Exception e, String msg) {
		log.error(ExceptionUtil.getCurrentStackTrace());
		if (e instanceof BaseException) {
			throw (BaseException) e;
		} else {
			throw new BaseException(msg, e);
		}
	}


}
