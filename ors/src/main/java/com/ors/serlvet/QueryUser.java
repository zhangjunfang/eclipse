package com.ors.serlvet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ors.dao.UserDAO;
import com.ors.dao.UserDAOImpl;
import com.ors.db.DBConnection;
import com.ors.model.User;
import com.ors.util.OptTemplate;

/**
 *
 * @Description : 查询当前注册信息
 * @author : ocean
 * @date : 2014-6-10 下午03:48:57
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class QueryUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public QueryUser() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DBConnection dbConn = new DBConnection();
		UserDAO userDAO = new UserDAOImpl(new OptTemplate());
		User user= userDAO.queryUser(request.getParameter("name"),
				request.getParameter("ids"));
		dbConn.closeConn();
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control","no-cache");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		if (null==user) {
			out.write(new User().toString());
		} else {
			out.write(user.toString());
		}
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
