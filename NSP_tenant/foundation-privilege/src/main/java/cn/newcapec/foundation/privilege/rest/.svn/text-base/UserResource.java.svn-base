package cn.newcapec.foundation.privilege.rest;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.model.LoginUser;
import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.biz.UserRoleService;
import cn.newcapec.foundation.privilege.biz.UserService;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.foundation.utils.UserTool;
import cn.newcapec.foundation.utils.WebUtils;
import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.base.rest.Msg;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PageView;
import cn.newcapec.framework.utils.SystemContext;
import cn.newcapec.framework.utils.collection.JSONTools;
import cn.newcapec.framework.utils.context.HttpNewcapecContextFactory;
import cn.newcapec.framework.utils.context.Keys;
import cn.newcapec.framework.utils.context.NewcapecContext;

/**
 * 用户视图资源类
 * 
 * @author andy.li
 * 
 */
@Component
@Scope("prototype")
@SuppressWarnings("all")
public class UserResource extends PrivilegeResource {

	/* 角色业务类 */
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartmentService departmentService;

	// 接受参数类
	private Map<String, Object> parms = new HashMap<String, Object>();
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();

	/**
	 * 用户登录UI
	 * 
	 * @param request
	 * @param response
	 */
	public void loginUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/user/pagelet/v1.0/loginUI.html";
		response.toHtml(url, context);
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 */
	public void login(BaseRequest request, BaseResponse response) {
		try {
			Msg msg = new Msg();
			// 名称
			String name = JSONTools.getString(getJsonObject(), "name");
			// 密码
			String password = JSONTools.getString(getJsonObject(), "password");
			HttpSession session = WebUtils.getSession(request.getOrginRequest());
			//获得验证码
	        String strEnsure = (String)session.getAttribute("validateCode");
//	        if(!strEnsure.equals( JSONTools.getString(getJsonObject(), "validateCode"))){
//	        	throw new BaseException("验证码不正确！");
//	        }

			if (StringUtils.isNotBlank(name)) {
				User user = userService.findUserByName(name);
				if (StringUtils.isNotBlank(password)) {
					if (user != null) {
						if (user.getPassword().equals(password)) {
							//将登陆用户放到上下文里面
							session.setAttribute(Keys.USER, user);
							NewcapecContext context = HttpNewcapecContextFactory.getContext(WebUtils.getRequests(request.getOrginRequest()));
							NewcapecContext.registerContext(context);
							WebUtils.getResponse(request.getOrginRequest()).sendRedirect("/restful/portalProxyService/tradition/index");
							return ;
							
						}else{
							throw new BaseException("用户密码不正确！");
						}
					}else{
						throw new BaseException("用户名称不存在！");
					}

				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("用户登录失败！", e);
			}
		}

	}
	
	

	/**
	 * 获取角色列表信息
	 * 
	 * @param request
	 * @param response
	 */
	public void userListUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/user/pagelet/v1.0/user_list.html";
		userListGrid(request, response);
		response.toHtml(url, context);
	}

	/**
	 * 获取用户列表信息
	 * 
	 * @param request
	 * @param response
	 */
	public void userListGrid(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/user/pagelet/v1.0/user_list_grid.html";
		/* 查询列表 */
		Page page = userService.queryUsers(getJsonObject(), orderby);
		// 角色列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, SystemContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadUserList");
		context.put("pageView", pageView);
		response.toHtml(url, context);
	}

	/**
	 * 添加用户UI
	 * 
	 * @param request
	 * @param response
	 */
	public void addUserUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/user/pagelet/v1.0/addUserUI.html";
		SystemContext.setPagesize(Integer.MAX_VALUE);
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> subparams = new HashMap<String, Object>();
		params.put("parent_id", "-1");
		// 获取顶层部门
		context.put("department", departmentService.queryDepartments(params,
				orderby));
		// 获取所有部门
		context.put("departments", departmentService.queryDepartments(
				subparams, orderby));
		response.toHtml(url, context);
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param response
	 */
	public void addUser(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				String departmentids = JSONTools.getString(getJsonObject(),
						"departmentids");
				if (StringUtils.isNotBlank(departmentids)) {
					String[] departments = departmentids.split(",");
					if (null != departments && departments.length > 0) {

						JSONObject data = JSONTools.getJSONObject(getJsonObject(),
								"data");
						JSONUtils.getMorpherRegistry().registerMorpher(
								new DateMorpher(new String[] { "yyyy-MM-dd",
										"yyyy-MM-dd HH:mm:ss" }));
						User user = (User) JSONObject.toBean(data, User.class);
						if (null != user) {
							// 判断用户账号是否存在
							if (null != userService.findUserByName(user
									.getAccountName())) {
								throw new BaseException("用户账号已经存在，请重新输入！");
							} else {
								Msg msg = new Msg();
								msg.setSuccess(true);
								user.setCreateDate(new Date());
								userService.saveCascadeUser(user, departments);
								response.print(msg.toJSONObjectPresention());
							}
						}

					}

				} else {
					throw new BaseException("请选择部门！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("新增用户失败！", e);
			}
		}
	}

	/**
	 * 删去人员信息
	 * 
	 * @param request
	 * @param response
	 */
	public void delete(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(), "data");
				if (data != null) {
					String ids = JSONTools.getString(data, "userids");
					if (StringUtils.isNotBlank(ids)) {
						String[] idss = ids.split(",");
						Msg msg = new Msg();
						msg.setMsg("删去人员成功！");
						msg.setSuccess(true);
						userService.deleteCascadeUser(idss);
						response.print(msg.toJSONObjectPresention());
					}

				}
			}
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("删去人员失败！", e);
			}
		}
	}

	/**
	 * 更新用户UI
	 * @param request
	 * @param response
	 */
	public void updateUserUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/user/pagelet/v1.0/updateUserUI.html";
		try {
			if (null != getJsonObject()) {
				String userId = JSONTools.getString(getJsonObject(), "userid");
				if (StringUtils.isNotBlank(userId)) {
					User user = userService.get(userId);
					if (null != user) {
						context.put("user", user);
						SystemContext.setPagesize(Integer.MAX_VALUE);
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, Object> subparams = new HashMap<String, Object>();
						params.put("parent_id", "-1");
						// 获取顶层部门
						context.put("department", departmentService
								.queryDepartments(params, orderby));
						// 获取所有部门
						context.put("departments", departmentService
								.queryDepartments(subparams, orderby));
						// 获取人员的全部部门
						context.put("departmented", departmentService
								.queryDepartmentByUserId(userId));
						response.toHtml(url, context);
					} else {
						throw new BaseException("没有相应的人员信息");
					}
				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("查找人员失败！", e);
			}
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param request
	 * @param response
	 */
	public void updateUser(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(), "data");
				String id = JSONTools.getString(data, "id");
//				User user = userService.get(id);
				JSONUtils.getMorpherRegistry().registerMorpher(
						new DateMorpher(new String[] { "yyyy-MM-dd",
								"yyyy-MM-dd HH:mm:ss" }));
				User user = (User) JSONObject.toBean(data, User.class);
				if (null != user) {
					// 判断用户账号是否存在
					Msg msg = new Msg();
					msg.setSuccess(true);
					// user.setCreateDate(new Date());
					userService.saveOrUpdate(user);
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("新增用户失败！", e);
			}
		}
	}

	/**
	 * 打开分配角色界面
	 * 
	 * @param request
	 * @param response
	 */
	public void openDistributionUI(BaseRequest request, BaseResponse response) {
		/* 参数类 */
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> userParams = new HashMap<String, Object>();
		String url = "/foundation/privilege/user/pagelet/v1.0/distributionUserRoleUI.html";
		// 获取界面的reloid
		String userid = JSONTools.getString(getJsonObject(), "userid");
		context.put("userid", userid);
		SystemContext.setPagesize(Integer.MAX_VALUE); // 不分页
		context.put("roles", roleService.queryRoles(params, orderby));
		userParams.put("userid", userid);
		context.put("hasRoles", userRoleService.queryUserRoles(userParams,
				orderby));
		response.toHtml(url, context);
	}

	/**
	 * 分配角色
	 * 
	 * @param request
	 * @param response
	 */
	public void distributionResource(BaseRequest request, BaseResponse response) {
		Msg msg = new Msg();
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(), "data");
				if (null != data) {
					// 获取角色标记
					String userid = JSONTools.getString(data, "userid");
					if (StringUtils.isNotBlank(userid)) {
						String str = JSONTools.getString(data, "roleids");
						if (StringUtils.isNotBlank(str)) {
							String[] roleids = str.split(",");
							userService.setUsersAuthorize(roleids, userid);
							clearCache();
							msg.setSuccess(true);
							response.print(msg.toJSONObjectPresention());
						}
					}

				} else {
					msg.setMsg("分配角色失败！");
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("分配角色失败！", e);
			}
		}
	}
	
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 */
	public void logonOut(BaseRequest request, BaseResponse response) {
		NewcapecContext context = HttpNewcapecContextFactory.getContext(WebUtils.getRequests(request.getOrginRequest()));
//		User user =(User)context.getAttribute(5, Keys.USER);
//		clearCache();		
		context.removeAttribute(5, Keys.USER);
		NewcapecContext.unregisterContext();
		PrintWriter printWriter;
		try {
			printWriter = WebUtils.getResponse(request.getOrginRequest()).getWriter();
			printWriter.write("<script type='text/javascript'>window.parent.location='/restful/privilegeProxyService/user/loginUI'</script>");
			printWriter.close();
		} catch (Exception e) {
			ThrowsException(e, "退出失败！");
		}			
	}
	

}
