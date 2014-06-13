package cn.newcapec.foundation.privilege.biz;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.newcapec.foundation.privilege.model.UserRole;
import cn.newcapec.framework.base.biz.BaseService;
import cn.newcapec.framework.utils.Page;

@SuppressWarnings("all")
public interface UserRoleService extends BaseService<UserRole> {
	
	public Page queryUserRoles(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;

}
