import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.dao.RoleResourceDAO;
import cn.newcapec.foundation.privilege.model.Department;
import cn.newcapec.foundation.privilege.model.Role;
import cn.newcapec.foundation.privilege.model.RoleResource;
import cn.newcapec.framework.base.dao.db.SqlDataset;
import cn.newcapec.framework.junit.BaseTestCaseSpringJunit4;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.SystemContext;


public class DepartmentTest  extends BaseTestCaseSpringJunit4{

	@Autowired
	DepartmentService departmentService;
	@Autowired
	RoleService roleService;
	@Autowired
	ResourceService resourceService;
	
	

	/**
	 *  添加部门数据
	 */
	@Test
	public void addDepartment(){
		
		Department department = new  Department();
		department.setParentId("1");
		department.setCreateDatetime(new Date());
		department.setUpdateTime(new Date());
		department.setName("oceanTest");
//		department.setIsParent("false");
		departmentService.saveOrUpdate(department);
	}
	
	/**
	 *  更新部门数据
	 */
	@Test
	public void updateDepartment(){
		String id ="402881cd412f351f01412f35253d0001";
		Department department = new  Department();
		department.setId(id);
		department.setParentId("1");
		department.setCreateDatetime(new Date());
		department.setUpdateTime(new Date());
		department.setName("oceanTest2");
//		department.setIsParent("false");
		department.setDepartCode("4");
		departmentService.saveOrUpdate(department);
	}

	/**
	 *  查找部门数据
	 */
	@Test
	public void findDepartment(){
		String id ="402881cd412f351f01412f35253d0001";
//		Department department = new  Department();
//		department.setDepartId(id);
//		department.setParentId("1");
//		department.setCreateDatetime(new Date());
//		department.setUpdateTime(new Date());
//		department.setName("oceanTest2");
//		department.setIsParent("false");
//		department.setDepartCode("4");
		departmentService.get(id);
	}
	/**
	 *  查找部门数据
	 */
	@Test
	public void findDepartment2(){
		String id ="402881cd412f351f01412f35253d0001";
//		Department department = new  Department();
//		department.setDepartId(id);
//		department.setParentId("1");
//		department.setCreateDatetime(new Date());
//		department.setUpdateTime(new Date());
//		department.setName("oceanTest2");
//		department.setIsParent("false");
//		department.setDepartCode("4");
		Map<String, Object>  params=new HashMap<String, Object>(4);
		params.put("departId", id);
		departmentService.findDepartmentsbyParams(params, null);
	}
	
	/**
	 *  查找部门数据[模糊查询在mysql,可能存在问题。但是在oracle下不存在问题] 
	 *  
	 */
	@Test
	public void findDepartment3(){
		String id ="402881cd412f351f01412f35253d0001";
//		Department department = new  Department();
//		department.setDepartId(id);
//		department.setParentId("1");
//		department.setCreateDatetime(new Date());
//		department.setUpdateTime(new Date());
//		department.setName("oceanTest2");
//		department.setIsParent("false");
//		department.setDepartCode("4");
		Map<String, Object>  params=new HashMap<String, Object>(4);
		params.put("departId", id);
		params.put("name", " like  '%ocean%'");//可能出现问题的地方
		params.put("status", "1");
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>(10);
		orderby.put("id", "DESC");
		orderby.put("simpName", "asc");
		departmentService.queryDepartments(params, orderby);
	}
	
	/**
	 * 查找一个部门下 所有的子节点
	 * */
	@Test
	public void findDepartment4() {

		Set<String> set = departmentService.findSubIds("0");
		System.out.println("===="+Arrays.deepToString(set.toArray()));
		Page<Map<String, Object>> page = departmentService
				.querySubDepartmentData(set);
		List<Map<String, Object>> list= page.getItems();
		System.out.println("list.size():===="+list.size());
		for(Map<String, Object> map : list){
			System.out.println(map.get("name"));
		}
	}
	
	@Test
	@SuppressWarnings("all")
	public void findRole(){
		String sql="select * from t_role ";
		SqlDataset sqlDataSet = new SqlDataset(sql);
//		sqlDataSet.setClazz(Role.class);
		SqlDataset sqlDataSet2=sqlDataSet.loadData();
		Page page = sqlDataSet2.toPage();
//		Role role = (Role)page.getItems().get(0);
		System.out.println(page.getItems().size());
//		System.out.println(role.getId());
//		Role role = (Role)list.get(0);
//		System.out.println(role.getId());
//		System.out.println(list.size());
//		List list2 = sqlDataSet.loadData().toPage().getItems();
//		Role role2=(Role)list2.get(0);
//		System.out.println("role2"+role2.getId());
//		System.out.println(sqlDataSet.loadData().toPage().getItems().size());
		
////		sqlDataSet.setSql(sql);
//		List list = DBUtil.query(sql, Role.class);
//		Role role = (Role)list.get(0);
//		System.out.println(role.getId());
	}
	
	@Test
	public void addRole(){
//		  for(int i=0;i<100;i++){

			  Role role = new Role();
			  role.setRoleName("多数据源测试75");
			  roleService.saveOrUpdate(role);
			  System.out.println("end");
//		  }
	}
	
	@Test
	public void tet(){
		System.out.println("hello");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void queryRoles(){
		Map params = new HashMap();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		Page list = roleService.queryRoles(params, orderby);
		System.out.println(list.getItems().size());
		
	}
	
	@Test
	public void SystemInit(){
//		roleService.SystemInit();
		
		
		// 接受参数类
		 Map<String, Object> parms = new HashMap<String, Object>();
		// 排序参数
		 LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		SystemContext.setPagesize(Integer.MAX_VALUE);
		List list= resourceService.queryResource(parms, orderby).getItems();
		
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			RoleResource roleResource = new RoleResource();
			roleResource.setResourceId(map.get("ID").toString());
			roleResource.setRoleId("4028808241e3cf0e0141e3cf17210033");
			roleResourceDAO.save(roleResource);
		}
	}
	
	@Autowired
	RoleResourceDAO roleResourceDAO;
	
	@SuppressWarnings("all")
	@Test
	public void testeResource(){
		

		// 接受参数类
		 Map<String, Object> parms = new HashMap<String, Object>();
		// 排序参数
		 LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		SystemContext.setPagesize(Integer.MAX_VALUE);
		List list= resourceService.queryResource(parms, orderby).getItems();
		
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			RoleResource roleResource = new RoleResource();
			roleResource.setResourceId(map.get("ID").toString());
			roleResource.setRoleId("4028808241da0f540141da0f57850033");
			roleResourceDAO.save(roleResource);
		}
		
	}
	
}
