package cn.newcapec.foundation.report.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.report.biz.impl.DatasetService;
import cn.newcapec.foundation.report.biz.impl.DatasourceService;
import cn.newcapec.foundation.report.core.DataAccessException;
import cn.newcapec.foundation.report.core.InterpretException;
import cn.newcapec.foundation.report.core.ds.DatabaseSource;
import cn.newcapec.foundation.report.core.ds.OraclePooledDatabaseSource;
import cn.newcapec.foundation.report.core.parameter.Parameter;
import cn.newcapec.foundation.report.core.simp.OracleProceduresInterpretation;
import cn.newcapec.foundation.report.core.simp.ProceduresInterpretation;
import cn.newcapec.foundation.report.model.DatasetEntity;
import cn.newcapec.foundation.report.model.DatasetParams;
import cn.newcapec.foundation.report.model.DatasourceEntity;
import cn.newcapec.foundation.report.model.GridEntity;
import cn.newcapec.foundation.report.util.StringUtils;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;

@Component
@Scope("prototype")
public class DatasetManageResource extends NanoReportResource {

	@Autowired
	private DatasetService datasetService;
	
	@Autowired
	private DatasourceService dsservice;
	
	public void index(BaseRequest request, BaseResponse response){
		String url = MODULE_PAGE_PREFIX + "listdata.html";
		
//		try {
//			response.toHtml(url, context);
//		} catch (Exception e) {
//			throwBizException(e, "未找到数据集管理页面，url = " + url);
//		}
		doIndex(url, response);
	}
	
	public void list(BaseRequest request, BaseResponse response){
		String listds_name = request.getParameter("name");
		Object[] args = null;
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		int pageStartNum = start*limit;
		int pageEndNum = start*limit+limit;
		if(listds_name != null && !"".equals(listds_name))
			args = new Object[]{"%"+listds_name+"%",pageEndNum,pageStartNum};
		else
			args = new Object[]{pageStartNum, pageEndNum};
		GridEntity grid = new GridEntity();
		List<DatasetEntity> dsList =  datasetService.queryDatasetByParam(args,listds_name);
		int total = datasetService.queryCountByName(listds_name);
		grid.setRoot(dsList);
		grid.setTotal(total);
//		JSONObject object = JSONObject.fromObject(grid);
		
		try {
			print2Json(response, JSONObject.fromObject(grid));
		} catch (Exception e) {
			throwBizException(e, "查询数据集列表出现异常。");
		}
	}
	
	public void checkuniquename(BaseRequest request, BaseResponse response){
		String name = request.getParameter("name");
		int res = datasetService.checkUniqueName(name);
		
		try {
			print2String(response, String.valueOf(res));
		} catch (Exception e) {
			throwBizException(e, "检查数据集名称唯一性出现错误，name = " + name);
		}
	}
	
	public void queryprocedure(BaseRequest request, BaseResponse response){
		String id = request.getParameter("id");
		DatasourceEntity dsEntity = dsservice.getDsEntityById(id);
		DatabaseSource ds = new OraclePooledDatabaseSource();
		ds.setAddress(dsEntity.getDsAddress());
		ds.setService(dsEntity.getDsServer());
		ds.setUsername(dsEntity.getDsUser());
		ds.setPassword(dsEntity.getDsPass());
		ProceduresInterpretation interpret = new OracleProceduresInterpretation();
		interpret.setDataSource(ds);
		List<String> list = interpret.getProcedureNameList();
		
		try {
			response.print(list.toString());
		} catch (Exception e) {
			throwBizException(e, "检查存储过程列表出现错误，name = " + id);
		}
	}
	
	//
	public void getprofiledsparams(BaseRequest request, BaseResponse response){
		String message = null;
		try {
			String procedure = request.getParameter("data");
			String dsId = request.getParameter("dsId");//数据源id
			String dcId = request.getParameter("dcId");//数据集id
			
			DatasourceEntity dsEntity = dsservice.getDsEntityById(dsId);
			DatabaseSource ds = new OraclePooledDatabaseSource();
			ds.setAddress(dsEntity.getDsAddress());
			ds.setService(dsEntity.getDsServer());
			ds.setUsername(dsEntity.getDsUser());
			ds.setPassword(dsEntity.getDsPass());
			ProceduresInterpretation interpret = new OracleProceduresInterpretation();
			interpret.setDataSource(ds);
			interpret.interpret(procedure);
			List<Parameter> params = interpret.getInputParameters();
			List<Parameter> fields  = interpret.getFinalOutputParameters();
			List<Parameter> oputFields = new ArrayList<Parameter>();
			List<DatasetParams> outParamsList = datasetService.queryParmasByDsId(dcId,"1");
			if(outParamsList.size()>0){
				for(Parameter paramField : fields){
					String fieldName = paramField.getName().toLowerCase();
					for(DatasetParams outparams : outParamsList ){
						String outFieldName = outparams.getName().toLowerCase();
						if(fieldName.equals(outFieldName)){
							paramField.setAlias(outparams.getAlias());
						}
					}
					oputFields.add(paramField);
				}
			}else{
				oputFields = fields;
			}
			Map<String,List<Parameter>> map = new HashMap<String,List<Parameter>>();
			map.put("params", params);
			map.put("fields", oputFields);
			JSONObject object = JSONObject.fromObject(map);
			print2Json(response, object);
			
		}catch (InterpretException e){
			message = "{\"state\":\"error\",\"msg\":\"解析存储过程错误,请点击参数设置\"}";
			print2Json(response, message);
		} 
		catch (Exception e) {
			message = "{\"state\":\"error\",\"msg\":\"解析存储过程错误,请点击参数设置\"}";
			print2Json(response, message);
		}
	}
	
	public void add(BaseRequest request, BaseResponse response){
		try {
			datasetService.execInsertDatasetInfo(request,dsservice);
		} catch (DataAccessException e) {
			throwBizException(e, "{\"state\":\"error\",\"msg\":\"获取查询字段列表信息错误,保存失败\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throwBizException(e, null);
		}
	}
	
	public void update(BaseRequest request, BaseResponse response){
		try{
			datasetService.execUpdateDsInfo( request,dsservice);
		} catch (DataAccessException e) {
			throwBizException(e, "{\"state\":\"error\",\"msg\":\"获取查询字段列表信息错误,保存失败\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throwBizException(e, null);
		}
	}

	public void delete(BaseRequest request, BaseResponse response){
		String ids = request.getParameter("ids");
		if(StringUtils.isEmpty(ids))
			throw new NullPointerException("not found parameter ids, please put (?ids=value) in your url.");
		String[] str = ids.split(",");
		
		try{
			for(String id : str ){
				datasetService.execDeleteDatasetById(id);
			}
		} catch (DataAccessException e) {
			throwBizException(e, "{\"state\":\"error\",\"msg\":\"删除数据集出现错误。\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throwBizException(e, null);
		}
	}
}
