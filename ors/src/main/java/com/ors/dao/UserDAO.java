package com.ors.dao;

import java.io.Serializable;
import java.util.List;

import com.ors.model.User;
import com.ors.util.PageModel;

public interface UserDAO extends Serializable {
	public boolean insert(User user); // 增

	public boolean delete(Integer id); // 单条删除

	public boolean delete(Integer[] userIds); // 批量删除

	public boolean update(User user); // 修改

	public List<User> query(); // 全部查询

	public User query(Integer id); // 单记录查询

	public PageModel<User> query(int pageNo, int pageSize); // 分页查询

	public PageModel<User> query(int pageNo, int pageSize, String condition); // 分页模糊查询

	public boolean Login(String name, String ids); // 登录

	public User queryUser(String name, String ids);// 查询登录用户信息

}