package cn.newcapec.foundation.privilege.dao.base;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.base.AppBaseDAO;
import cn.newcapec.foundation.privilege.dao.UserDepartmentDAO;
import cn.newcapec.foundation.privilege.model.DepartmentUser;
import cn.newcapec.framework.base.dao.hibernate.HibernateEntityDao;

/**
 * 用户部门接口实现类
 * 
 * @author andy.li
 * 
 */

@SuppressWarnings("all")
public abstract class BaseUserDepartmentDAO extends  AppBaseDAO<DepartmentUser> {

}
