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
import cn.newcapec.foundation.tenant.model.NetTenant;
import cn.newcapec.foundation.tenant.biz.NetTenantService;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PageView;
import cn.newcapec.framework.utils.SystemContext;



/**
 * NetTenantResource
 * <p>Company: 郑州新开普电子股份有限公司</p>
 */
public class NetTenantResource extends BaseResource implements BaseResourceHandler {

    @Autowired
	private NetTenantService netTenantService;
	
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	
	/**
	 * 添加NetTenantUI
	 * 
	 * @param request
	 * @param response
	 */
	public void addUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/test/netTenant/pagelet/v1.0/addNetTenantUI.html";
		response.toHtml(url, context);
	}
	
	/**
	 * 添加信息NetTenant
	 * 
	 * @param request
	 * @param response
	 */
	public void add(BaseRequest request, BaseResponse response) {
		try {
			Msg msg = new Msg();
			msg.setMsg("添加NetTenant失败！");
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd","yyyy-MM-dd HH:mm:ss" }));
				NetTenant netTenant = JSONTools.JSONToBean(data,NetTenant.class);
					msg.setSuccess(true);
					msg.setMsg("添加NetTenant成功！");
					netTenantService.add(netTenant);
				} 
				 response.print(msg.toJSONObjectPresention());
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("新增NetTenant失败！", e);
			}
		}
	}

	/**
	 * 查找NetTenantUI信息
	 * @param request
	 * @param response
	 * @throws JSONException 
	 * @return void
	 */
	public void find(BaseRequest request, BaseResponse response) {
		String url ="/foundation/test/netTenant/pagelet/v1.0/updateNetTenantUI.html";
		try {
			if (null != getJsonObject()) {
				String id = JSONTools.getString(getJsonObject(), "id");
				if (StringUtils.isNotBlank(id)) {
					NetTenant netTenant = netTenantService.findById(id);
					if (null != netTenant) {
						context.put("entity", netTenant);
						response.toHtml(url, context);
					} else {
						throw new BaseException("没有相应的netTenant信息");
					}
				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("查找netTenant失败！", e);
			}
		}
	}
	

	
	/**
	 * 修改NetTenant信息
	 * @param request
	 * @param response
	 * @throws JSONException 
	 * @return void
	 */
	public void update(BaseRequest request, BaseResponse response) {
		try {
			Msg msg = new Msg();
			msg.setMsg("更新NetTenant失败！");
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd","yyyy-MM-dd HH:mm:ss" }));
				NetTenant netTenant = JSONTools.JSONToBean(data,NetTenant.class);
					msg.setSuccess(true);
					msg.setMsg("更新NetTenant成功！");
					netTenantService.update(netTenant);
				} 
				   response.print(msg.toJSONObjectPresention());
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("更新NetTenant失败！", e);
			}
		}	
		
	}
	
	/**
	 *  删除NetTenant信息
	 * @param request
	 * @param response
	 * @return void
	 */
		public void delete(BaseRequest request, BaseResponse response) {
		Msg msg = new Msg();
		msg.setMsg("删去NetTenant失败！");
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
						msg.setMsg("删去NetTenant成功！");
						msg.setSuccess(true);
						netTenantService.delete(list);
				}
			}
			response.print(msg.toJSONObjectPresention());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("删去NetTenant失败！", e);
			}
		}
	}
	
	
	/**
	 * 查询NetTenant列表UI信息
	 * @param request
	 * @param response
	 * @return void
	 */
	public void ListUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/test/netTenant/pagelet/v1.0/netTenant_list.html";
		listGrid(request, response);
		response.toHtml(url, context);
	}
	
	
	/**
	 * 查询NetTenant列表信息
	 * @param request
	 * @param response
	 * @return void
	 */
	public void listGrid(BaseRequest request, BaseResponse response) {
		String url = "/foundation/test/netTenant/pagelet/v1.0/netTenant_list_grid.html";
		/* 查询列表 */
		Page page = netTenantService.queryNetTenants(getJsonObject(), orderby).toPage();;
		// 列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, SystemContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadList");
		context.put("pageView", pageView);
		response.toHtml(url, context);
	}
	
}
