package com.ors.dao;

import java.sql.ResultSet;
import java.util.List;

import com.ors.model.User;
import com.ors.util.ObjectMapper;
import com.ors.util.OptTemplate;
import com.ors.util.PageModel;

public class UserDAOImpl implements UserDAO {

	/**
	 *
	 */
	private static final long serialVersionUID = -6749648830599830637L;
	private OptTemplate optTemplate = null;

	public UserDAOImpl(OptTemplate optTemplate) {
		super();
		this.optTemplate = optTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean Login(String name, String ids) {
		String sql = "select * from user  where name=? and  ids =?";
		Object[] obj = { name, ids };
		List<User> users = (List<User>) optTemplate.query(sql, obj,
				new UserDAOObjectMapper());
		if (null == users || users.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean delete(Integer id) {
		String sql = "delete from user where id=?";
		Object[] obj = { id };
		return optTemplate.update(sql, obj, false);
	}

	@Override
	public boolean delete(Integer[] userIds) {
		StringBuffer sbStr = new StringBuffer();
		Integer[] obj = userIds;
		for (int i = 0; i < userIds.length; i++) {
			sbStr.append("?,");
		}
		String sql = "delete from user where id in("
				+ sbStr.substring(0, sbStr.length() - 1) + ")";
		return optTemplate.update(sql, obj, false);
	}

	@Override
	public boolean insert(User user) {
		String sql = "insert into user(name,sex,ids,politicsStatus,nation,address,graduation,date,education,specialty,unit,department,workoccupation,job,professional,telephone,contactaddress) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = { user.getName(), user.getSex(), user.getIds(),
				user.getPoliticsStatus(), user.getNation(), user.getAddress(),
				user.getGraduation(), user.getDate(), user.getEducation(),
				user.getSpecialty(), user.getUnit(), user.getDepartment(),
				user.getWorkoccupation(), user.getJob(),
				user.getProfessional(), user.getTelephone(),
				user.getContactaddress() };
		return optTemplate.update(sql, obj, false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> query() {
		String sql = "select * from user";
		Object[] obj = {};
		return (List<User>) optTemplate.query(sql, obj,
				new UserDAOObjectMapper());

	}

	@Override
	public User query(Integer id) {
		String sql = "select * from user";
		Object[] obj = {};
		return (User) optTemplate.query(sql, obj, new UserDAOObjectMapper())
				.get(0);
	}

	@Override
	@SuppressWarnings("all")
	public PageModel<User> query(int pageNo, int pageSize) {
		String sql1 = "select * from user";
		Object[] obj1 = {};
		List<User> list1 = (List<User>) optTemplate.query(sql1, obj1,
				new UserDAOObjectMapper());
		int i = list1.size();
		// String sql =
		// "select * from (select j.*,rownum rn from (select * from user) j where rownum<=?) where rn>?";
		String sql = " select * from user  limit  ?, ?";
		Object[] obj = { pageNo * pageSize, (pageNo - 1) * pageSize };
		List<User> list = (List<User>) optTemplate.query(sql, obj,
				new UserDAOObjectMapper());
		PageModel<User> pagemodel = new PageModel<User>();
		pagemodel.setPageNo(pageNo);
		pagemodel.setPageSize(pageSize);
		pagemodel.setList(list);
		pagemodel.setTotalRecords(i);
		return pagemodel;
	}

	@Override
	@SuppressWarnings("all")
	public PageModel<User> query(int pageNo, int pageSize, String condition) {
		String sql1 = "select * from user";
		Object[] obj1 = {};
		List<User> list1 = (List<User>) optTemplate.query(sql1, obj1,
				new UserDAOObjectMapper());
		int i = list1.size();
		// String sql =
		// "select * from (select j.*,rownum rn from (select * from user where id like '"
		// + condition
		// + "%' or name like '"
		// + condition
		// + "%') j where rownum<=?) where rn>?";
		String sql = "select u.* from ( select * from user where id like '"
				+ condition + "%' or name like '" + condition
				+ "%') u limit ?, ?";
		// System.err.println("==================="+sql);
		Object[] obj = { pageNo * pageSize, (pageNo - 1) * pageSize };
		List<User> list = (List<User>) optTemplate.query(sql, obj,
				new UserDAOObjectMapper());
		PageModel<User> pagemodel = new PageModel<User>();
		pagemodel.setPageNo(pageNo);
		pagemodel.setPageSize(pageSize);
		pagemodel.setList(list);
		pagemodel.setTotalRecords(i);
		return pagemodel;
	}

	@Override
	public boolean update(User user) {
		String sql = "update user set name=?,address=? where id=?";
		Object[] obj = { user.getName(), user.getAddress(), user.getId() };
		return optTemplate.update(sql, obj, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User queryUser(String name, String ids) {
		String sql = "select * from user  where name=? and  ids =?";
		Object[] obj = { name, ids };
		List<User> users = (List<User>) optTemplate.query(sql, obj,
				new UserDAOObjectMapper());
		if (null == users || users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

}

class UserDAOObjectMapper implements ObjectMapper {
	public Object mapping(ResultSet rs) {
		User u = new User();
		try {
			u.setId(rs.getInt("id"));
			u.setName(rs.getString("name"));
			u.setSex(rs.getString("sex"));
			u.setIds(rs.getString("ids"));
			u.setPoliticsStatus(rs.getString("politicsStatus"));
			u.setNation(rs.getString("nation"));
			u.setAddress(rs.getString("address"));
			u.setGraduation(rs.getString("graduation"));
			u.setDate(rs.getDate("date"));
			u.setEducation(rs.getString("education"));
			u.setSpecialty(rs.getString("specialty"));
			u.setUnit(rs.getString("unit"));
			u.setDepartment(rs.getString("department"));
			u.setWorkoccupation(rs.getString("workoccupation"));
			u.setJob(rs.getString("job"));
			u.setProfessional(rs.getString("professional"));
			u.setTelephone(rs.getString("telephone"));
			u.setContactaddress(rs.getString("contactaddress"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return u;
	}

}
