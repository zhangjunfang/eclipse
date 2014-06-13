package com.ors.serlvet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ors.dao.UserDAO;
import com.ors.dao.UserDAOImpl;
import com.ors.db.DBConnection;
import com.ors.util.OptTemplate;

/**
 *
 * @Description : 登录信息
 * @author : ocean
 * @date : 2014-6-10 下午04:05:57
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DBConnection dbConn = new DBConnection();
		UserDAO userDAO = new UserDAOImpl(new OptTemplate());
		boolean b = userDAO.Login(request.getParameter("xm"),
				request.getParameter("sfzh"));
		dbConn.closeConn();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
		request.getSession().setAttribute("land", b);
		if (!b) {
			response.sendRedirect(basePath+"page/login.jsp");
		} else {
			response.sendRedirect(basePath+"page/queryuser.jsp");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

}
