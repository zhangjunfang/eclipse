package cn.newcapec.foundation.report.rest;

import java.sql.DriverManager;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.report.biz.impl.DatasourceService;
import cn.newcapec.foundation.report.model.DatasourceEntity;
import cn.newcapec.foundation.report.model.GridEntity;
import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;

@Component
@Scope("prototype")
public class DsManageResource extends NanoReportResource {

	private transient Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private DatasourceService dsservice;
	
//	/**
//	 * 首页
//	 * @param request
//	 * @param response
//	 */
//	public void index(BaseRequest request, BaseResponse response) {
//		String url = "foundation/portal/tradition/pagelet/index.html";
//		try {
//			response.toHtml(url, context);
//		} catch (Exception e) {
//			log.error(ExceptionUtils.getFullStackTrace(e));
//			if (e instanceof BaseException) {
//				throw (BaseException) e;
//			} else {
//				throw new BaseException("传统模式视图资源类不存在！", e);
//			}
//		}
//	}
	
	public void index(BaseRequest request, BaseResponse response){
		String url = "foundation/report/datasource.html";
		
		try {
			response.toHtml(url, context);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("未找到数据源管理页面，url = " + url, e);
			}
		}
	}
	
	public void list(BaseRequest request, BaseResponse response){
		String ds_name = request.getParameter("ds_name");
		Object[] args = null;
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		int pageStartNum = start*limit;
		int pageEndNum = start*limit+limit;
		if(ds_name != null && !"".equals(ds_name))
			args = new Object[]{"%"+ds_name+"%",pageEndNum,pageStartNum};
		else{
//			args = new Object[]{pageEndNum,pageStartNum};
			args = new Object[]{pageStartNum, pageEndNum};
		}
		GridEntity grid = new GridEntity();
		List<DatasourceEntity> dsList =  dsservice.queryDsByParams(args,ds_name);
		int total = dsservice.queryDsCountByParams(new Object[]{ds_name}, ds_name);
		grid.setRoot(dsList);
		grid.setTotal(total);
		JSONObject object = JSONObject.fromObject(grid);
		
		try {
			print2Json(response, object);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("查询数据源列表出现异常。", e);
			}
		}
	}
	
	public void checkconn(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		boolean result = false;
		try {
			JSONObject object = JSONObject.fromObject(data);
			@SuppressWarnings("static-access")
			DatasourceEntity dsEntity = (DatasourceEntity) object.toBean(object,DatasourceEntity.class);
			String url = "jdbc:oracle:thin:@"+dsEntity.getDsAddress()+":1521:"+dsEntity.getDsServer();
//			ClassUtils.forName("oracle.jdbc.driver.OracleDriver", this.getClass().getClassLoader());
			Class.forName("oracle.jdbc.driver.OracleDriver");
			logger.info(url);
			String user = dsEntity.getDsUser();
			String password = dsEntity.getDsPass();
			DriverManager.getConnection(url, user, password);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("can't connected at: " + e.getMessage());
		}
		response.print(Boolean.toString(result));
	}
	
	public void add(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);
		DatasourceEntity dsEntity = (DatasourceEntity) JSONObject.toBean(object,DatasourceEntity.class);
		Object[] args = new Object[]{
				UUID.randomUUID().toString(),
				dsEntity.getName(),
				dsEntity.getType(),
				dsEntity.getDsServer(),
				dsEntity.getDsUser(),
				dsEntity.getDsPass(),
				dsEntity.getDsAddress(),
				dsEntity.getSummary()
		};
		dsservice.addDatasourceInfo(args);
	}
	
	public void update(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);
		DatasourceEntity dsEntity = (DatasourceEntity) JSONObject.toBean(object,DatasourceEntity.class);
		Object[] args = new Object[]{
				dsEntity.getName(),
				dsEntity.getType(),
				dsEntity.getDsServer(),
				dsEntity.getDsUser(),
				dsEntity.getDsPass(),
				dsEntity.getDsAddress(),
				dsEntity.getSummary(),
				dsEntity.getId()
		};
		dsservice.updateDsInfo(args);
	}
	
	public void delete(BaseRequest request, BaseResponse response){
		String ids = request.getParameter("ids");
		String[] str = ids.split(",");
		for(String id : str ){
			dsservice.deleteById(id);
		}
	}
	
	public void checkuniquename(BaseRequest request, BaseResponse response){
		String name = request.getParameter("name");
		int res = dsservice.checkUniqueName(name);
		response.print(String.valueOf(res));
	}
	
	public void queryall(BaseRequest request, BaseResponse response){
		List<DatasourceEntity> dsEntityList =  dsservice.queryAllDsEntity();
//		Map<String,DatasourceEntity> map = new HashMap<String,DatasourceEntity>();
		JSONArray object = JSONArray.fromObject(dsEntityList);
		try {
			print2Json(response, object);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("查询数据源列表出现异常。", e);
			}
		}
	}
}
