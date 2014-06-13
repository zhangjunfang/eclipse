package cn.newcapec.foundation.privilege.rest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.biz.UserService;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PageView;
import cn.newcapec.framework.utils.SystemContext;

@Component
@Scope("prototype")
@SuppressWarnings("all")
public class UserRoleResource extends PrivilegeResource{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	// 接受参数类
	private Map<String, Object> parms = new HashMap<String, Object>();
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	
	/**
	 * 获取角色列表信息
	 * 
	 * @param request
	 * @param response
	 */
	public void userRoleListUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/userRole/pagelet/v1.0/user_role_list.html";
		userRoleListGrid(request, response);
		response.toHtml(url, context);
	}

	/**
	 * 获取角色列表信息
	 * 
	 * @param request
	 * @param response
	 */
	public void userRoleListGrid(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/userRole/pagelet/v1.0/user_role_list_grid.html";
		/* 查询列表 */
		Page page = roleService.queryRoles(getJsonObject(), orderby);
		// 角色列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, SystemContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadRoleList");
		context.put("pageView", pageView);
		response.toHtml(url, context);
	}

}
