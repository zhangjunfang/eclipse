package cn.newcapec.foundation.tenant.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import org.springframework.beans.factory.annotation.Autowired;


import cn.newcapec.framework.utils.collection.JSONTools;
import org.apache.commons.lang.StringUtils;

import cn.newcapec.framework.base.exception.BaseException;
import org.apache.commons.lang.exception.ExceptionUtils;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.base.rest.BaseResource;
import cn.newcapec.framework.base.rest.BaseResourceHandler;
import cn.newcapec.framework.base.rest.Msg;
import cn.newcapec.foundation.tenant.model.DataSource;
import cn.newcapec.foundation.tenant.biz.DataSourceService;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PageView;
import cn.newcapec.framework.utils.SystemContext;



/**
 * DataSourceResource
 * <p>Company: 郑州新开普电子股份有限公司</p>
 */
public class DataSourceResource extends BaseResource implements BaseResourceHandler {

    @Autowired
	private DataSourceService dataSourceService;
	
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	
	/**
	 * 添加DataSourceUI
	 * 
	 * @param request
	 * @param response
	 */
	public void addUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/test/dataSource/pagelet/v1.0/addDataSourceUI.html";
		response.toHtml(url, context);
	}
	
	/**
	 * 添加信息DataSource
	 * 
	 * @param request
	 * @param response
	 */
	public void add(BaseRequest request, BaseResponse response) {
		try {
			Msg msg = new Msg();
			msg.setMsg("添加DataSource失败！");
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd","yyyy-MM-dd HH:mm:ss" }));
				DataSource dataSource = JSONTools.JSONToBean(data,DataSource.class);
					msg.setSuccess(true);
					msg.setMsg("添加DataSource成功！");
					dataSourceService.add(dataSource);
				} 
				 response.print(msg.toJSONObjectPresention());
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("新增DataSource失败！", e);
			}
		}
	}

	/**
	 * 查找DataSourceUI信息
	 * @param request
	 * @param response
	 * @throws JSONException 
	 * @return void
	 */
	public void find(BaseRequest request, BaseResponse response) {
		String url ="/foundation/test/dataSource/pagelet/v1.0/updateDataSourceUI.html";
		try {
			if (null != getJsonObject()) {
				String id = JSONTools.getString(getJsonObject(), "id");
				if (StringUtils.isNotBlank(id)) {
					DataSource dataSource = dataSourceService.findById(id);
					if (null != dataSource) {
						context.put("entity", dataSource);
						response.toHtml(url, context);
					} else {
						throw new BaseException("没有相应的dataSource信息");
					}
				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("查找dataSource失败！", e);
			}
		}
	}
	

	
	/**
	 * 修改DataSource信息
	 * @param request
	 * @param response
	 * @throws JSONException 
	 * @return void
	 */
	public void update(BaseRequest request, BaseResponse response) {
		try {
			Msg msg = new Msg();
			msg.setMsg("更新DataSource失败！");
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd","yyyy-MM-dd HH:mm:ss" }));
				DataSource dataSource = JSONTools.JSONToBean(data,DataSource.class);
					msg.setSuccess(true);
					msg.setMsg("更新DataSource成功！");
					dataSourceService.update(dataSource);
				} 
				   response.print(msg.toJSONObjectPresention());
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("更新DataSource失败！", e);
			}
		}	
		
	}
	
	/**
	 *  删除DataSource信息
	 * @param request
	 * @param response
	 * @return void
	 */
		public void delete(BaseRequest request, BaseResponse response) {
		Msg msg = new Msg();
		msg.setMsg("删去DataSource失败！");
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				if (data != null) {
					List<String> list = new ArrayList<String>();
					String ids = JSONTools.getString(data, "ids");
						String[] idss = ids.split(",");
						for(int i = 0, len = idss.length; i < len; i++) {
							list.add(idss[i]);
						}
						msg.setSuccess(true);
						msg.setMsg("删去DataSource成功！");
						msg.setSuccess(true);
						dataSourceService.delete(list);
				}
			}
			response.print(msg.toJSONObjectPresention());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("删去DataSource失败！", e);
			}
		}
	}
	
	
	/**
	 * 查询DataSource列表UI信息
	 * @param request
	 * @param response
	 * @return void
	 */
	public void ListUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/test/dataSource/pagelet/v1.0/dataSource_list.html";
		listGrid(request, response);
		response.toHtml(url, context);
	}
	
	
	/**
	 * 查询DataSource列表信息
	 * @param request
	 * @param response
	 * @return void
	 */
	public void listGrid(BaseRequest request, BaseResponse response) {
		String url = "/foundation/test/dataSource/pagelet/v1.0/dataSource_list_grid.html";
		/* 查询列表 */
		Page page = dataSourceService.queryDataSources(getJsonObject(), orderby).toPage();;
		// 列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, SystemContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadList");
		context.put("pageView", pageView);
		response.toHtml(url, context);
	}
	
}
