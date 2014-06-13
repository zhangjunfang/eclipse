package com.ors.serlvet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
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
 * @Description : 报名信息入库
 * @author : ocean
 * @date : 2014-6-10 上午11:06:32
 * @email : zhangjunfang0505@163.com
 * @Copyright : xinanli zhengzhou
 */
public class SignUP extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignUP() {
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids=request.getParameter("sfzh");
		if(null!=ids&&ids.trim().length()>0){
			User u = new User();
			// u.setId(request.getParameter("id"));
			u.setIds(request.getParameter("sfzh"));
			u.setName(request.getParameter("xm"));
			u.setSex(request.getParameter("xb"));
			u.setPoliticsStatus(request.getParameter("zzmm"));
			u.setNation(request.getParameter("mz"));
			u.setAddress(request.getParameter("jg"));
			u.setGraduation(request.getParameter("byyx"));
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(request.getParameter("bysj"));
				u.setDate(date);
			} catch (ParseException e) {

			}
			u.setEducation(request.getParameter("xl"));
			u.setSpecialty(request.getParameter("zy"));
			u.setUnit(request.getParameter("gzdw"));
			u.setDepartment(request.getParameter("szbm"));
			u.setWorkoccupation(request.getParameter("cszy"));
			u.setJob(request.getParameter("zw"));
			u.setProfessional(request.getParameter("zc"));
			u.setTelephone(request.getParameter("lxdh"));
			u.setContactaddress(request.getParameter("lxdz"));
			DBConnection dbConn = new DBConnection();
			UserDAO UserDAO = new UserDAOImpl(new OptTemplate());
			boolean b = UserDAO.insert(u);
			dbConn.closeConn();
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			request.getSession().setAttribute("sign", b);
			response.sendRedirect(basePath+"page/signup.jsp");
		}

		//request.getRequestDispatcher(basePath+"page/signup.jsp").forward(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
