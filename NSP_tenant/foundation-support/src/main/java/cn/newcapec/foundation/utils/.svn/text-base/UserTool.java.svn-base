package cn.newcapec.foundation.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import cn.newcapec.foundation.model.LoginUser;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.framework.utils.context.Keys;
import cn.newcapec.framework.utils.context.NewcapecContext;

/**
 * 用户工具类
 * @author andy.li
 * 
 */
public class UserTool {
	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	public static LoginUser getLoginUser() {
		User user = (User) NewcapecContext.getContext().getAttribute(NewcapecContext.SESSION, Keys.USER);
		return ProUser2LoginUser(user);
	}

	/**
	 * 获取当前登录用户
	 * 
	 * @param request
	 * @return
	 */
	public static LoginUser getLoginUser(ServletRequest request) {
		User user = (User) (((HttpServletRequest) request).getSession()).getAttribute(Keys.USER);
		return ProUser2LoginUser(user);
	}

	public static LoginUser ProUser2LoginUser(User user) {
		LoginUser loginUser =null;
		if(null!=user){
			loginUser=  new LoginUser();
			loginUser.setId(user.getId());
			loginUser.setAccountName(user.getAccountName());
			loginUser.setEmail(user.getEmail());
			loginUser.setMobile(user.getMobile());
			loginUser.setPassword(user.getPassword());
			loginUser.setSex(user.getSex());
			loginUser.setStatus(user.getStatus());
			loginUser.setUserCode(user.getUserCode());
			loginUser.setUserName(user.getUserName());
		}
		return loginUser;
	}

}
