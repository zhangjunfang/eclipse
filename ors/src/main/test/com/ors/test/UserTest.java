package com.ors.test;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ors.dao.UserDAO;
import com.ors.dao.UserDAOImpl;
import com.ors.db.DBConnection;
import com.ors.model.User;
import com.ors.util.OptTemplate;
import com.ors.util.PageModel;

public class UserTest {
	DBConnection dbConn = null;

	@Before
	public void setUp() {
		dbConn = new DBConnection();
	}

	@After
	public void tearDown() {
		dbConn.closeConn();

	}

	/************ 测试插入记录 ***************/

	@Test
	public void testinsert() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		for (int i = 0; i < 20; i++) {
			User u = new User();
			u.setName("郑六");
			u.setAddress("2" + i);
			u.setDate(new Date());
			u.setSex("男");
			boolean b = UserDAO.insert(u);
			if (b == false) {
				System.out.println("插入失败");
			} else {
				System.out.println("插入成功");
			}
		}

	}

	/************ 测试修改记录 ***************/

	@Test
	public void testupdate() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		User u = new User();
		u.setId(5);
		u.setName("666666666");
		u.setAddress("21");
		boolean b = UserDAO.update(u);
		if (b == false) {
			System.out.println("更新失败");
		} else {
			System.out.println("更新成功");
		}

	}

	/************ 测试删除单条记录 ***************/

	@Test
	public void testdeleteById() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		boolean b = UserDAO.delete(80);
		if (b == false) {
			System.out.println("删除失败");
		} else {
			System.out.println("删除成功");
		}

	}

	/************ 测试批量删除记录 ***************/

	@Test
	public void testdeleteByArray() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		Integer[] s = { 73, 74, 75 };
		boolean b = UserDAO.delete(s);
		if (b == false) {
			System.out.println("删除失败");
		} else {
			System.out.println("删除成功");
		}

	}

	/********* 查询全部记录结果集为泛型 ************/
	@Test
	public void testqueryAll3() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		List<User> list = UserDAO.query();
		for (User u : list) {
			System.out.println(u.getId());
		}
	}

	/********* 查询单条记录结果集为对象 ************/
	@Test
	public void testqueryAll2() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		User u = UserDAO.query(71);
		System.out.println(u.getName());

	}

	/********* 分页查询全部记录结果集为pagemodel ************/
	@Test
	public void testqueryAll1() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		PageModel<User> pml = UserDAO.query(2, 2);
		List<User> list = pml.getList();
		for (User u : list) {
			System.out.println(u.getId());
		}
	}

	/********* 分页模糊查询全部记录结果集为pagemodel ************/
	@Test
	public void testqueryAll() {
		UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
		PageModel<User> pml = UserDAO.query(1, 2, "2");
		List<User> list = pml.getList();
		for (User u : list) {
			System.out.println(u.getId());
		}
	}

}
