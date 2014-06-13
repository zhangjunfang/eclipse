package cn.newcapec.foundation.report.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.report.biz.SearchReportDataService;
import cn.newcapec.foundation.report.biz.impl.ReportManageService;
import cn.newcapec.foundation.report.model.GridEntity;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.foundation.report.service.ReportContextFactory;
import cn.newcapec.framework.base.exception.BaseException;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;

@Component
public class ReportViewResource extends NanoReportResource {

	@Autowired
	private ReportManageService reportManageService;
	
//	@Autowired
//	private DatasetService datasetService;
	
//	@Autowired
//	private DatasourceService dataSourceService;
	
	@Autowired
	private SearchReportDataService proceduresSearchReportService;
	
	/**
	 * 显示报表预览页面（DEMO页面）
	 * @param request
	 * @param response
	 */
	public void index(BaseRequest request, BaseResponse response){
		String url = MODULE_PAGE_PREFIX + "reportview.html";
		
		String reportId = request.getParameter("reportId");
		String  dc_id = request.getParameter("dc_id");
		 
		/*  拼装业务参数，提供给后续URL使用 */
		 StringBuilder bizParams = null;
		 Map<String, String> filters = ReportContextFactory.getReportBusinessParams(dc_id);
		 if(filters != null){
			 bizParams = new StringBuilder("?t=");
			 String paramValue = null;
			 for(Map.Entry<String, String> entry : filters.entrySet()){
				 paramValue = request.getParameter(entry.getKey());
				 if(paramValue != null && !paramValue.equals("")){
					 bizParams.append("&");
					 bizParams.append(entry.getKey());
					 bizParams.append("=");
					 bizParams.append(paramValue);
				 } else {
					System.out.println("定义了业务参数: " + entry.getKey() + ", 但未找到对应的值。");
				 }
				 
				 // 如果存在映射参数名也加入进来，控件需要使用
				 // 时克英 2013-9-2
				 paramValue = request.getParameter(entry.getValue());
				 if(paramValue != null && !paramValue.equals("")){
					 bizParams.append("&");
					 bizParams.append(entry.getValue());
					 bizParams.append("=");
					 bizParams.append(paramValue);
				 }
			 }
		 }
		 
		 //页面中传入参数
		 context.put("reportId", reportId);
		 context.put("dc_id", dc_id);
		 context.put("bizParams", bizParams.toString());
		 
		doIndex(url, response);
	}
	
	public void queryReportXml(BaseRequest request, BaseResponse response){
		String id = request.getParameter("id");
		
		try{
			List<ReportManageEntity> list = reportManageService.queryPublishReportContent(id);
			if(list == null || list.size() == 0)
				throw new BaseException("no report entities found, reportId = " + id);
			ReportManageEntity report = list.get(0);
			JSONObject object = JSONObject.fromObject(report);
			print2Json(response, object);
		} catch(Exception ex){
			throwBizException(ex, null);
		}
	}
	
	/**
	 * 查询报表执行的结果数据
	 * @param request
	 * @param response
	 */
	public void queryReportData(BaseRequest request, BaseResponse response){
		String dc_id = request.getParameter("dc_id");
		String reportId = request.getParameter("reportId");
		String reportArguments = request.getParameter("data");
		
		try{
			System.out.println("----------- dc_id = " + dc_id);
			List<Map<String, Object>> result = proceduresSearchReportService.getReportResult(reportId, reportArguments);
			List<JSONObject> jsonObject = translateToJson(result);
			
			GridEntity grid = new GridEntity();
			grid.setRoot(jsonObject);
			grid.setTotal(jsonObject.size());
			JSONObject returnObject = JSONObject.fromObject(grid);
			
			print2Json(response, returnObject);
			
		} catch (Exception e) {
			throwBizException(e, "查询报表控件所需要的数据，产生错误: " + e.getMessage());
		}
	}
	
	private List<JSONObject> translateToJson(List<Map<String, Object>> mapList){
		List<JSONObject> listResult = new ArrayList<JSONObject>();
		if(mapList == null)
			return listResult;
		
		JSONObject jsonResult;
		for(Map<String, Object> map : mapList){
			jsonResult = new JSONObject();
			jsonResult.putAll(map);
			listResult.add(jsonResult);
		}
		
		return listResult;
	}
	
//	public void queryReportData(BaseRequest request, BaseResponse response){
//		String dc_id = request.getParameter("dc_id");
//		String reportId = request.getParameter("reportId");
//		String data = request.getParameter("data");
//		
//		try {
//			JSONObject object = JSONObject.fromObject(data);
//			DatasetEntity dataset =  datasetService.getDatasetInfoById(dc_id);
//			
//			DatasourceEntity dsEntity =  dataSourceService.getDsEntityById(dataset.getDs_id());
//			//设置数据库连接
//			DatabaseSource ds = new OraclePooledDatabaseSource();
//			ds.setAddress(dsEntity.getDsAddress());
//			ds.setService(dsEntity.getDsServer());
//			ds.setUsername(dsEntity.getDsUser());
//			ds.setPassword(dsEntity.getDsPass());
//			//获取存储过程名字
//			String procedureName = this.getProcedureName(dataset.getMethod());
//			Connection conn = ds.getConnection();
//			//根据存储过程获取游标、输入
//			List<Map> listMap =  queryParams(conn,procedureName);
//			StringBuffer procedureString = new StringBuffer();
//			procedureString.append("{ call "+dataset.getMethod()+" (");
//			int outMsgPosition = 0;//游标位置,现在假设只支持一个游标返回值
//			for(Map map : listMap){
//				StringBuffer procedureBuffer = new StringBuffer();
//				String type = map.get("data_type").toString().toLowerCase();
//				int position = Integer.parseInt( map.get("position").toString());
//				String in_out = map.get("in_out").toString().toLowerCase();
//				if(in_out.equals("out") && type.equals("ref cursor")){
//					outMsgPosition = position;
//				}
//				String procedure = "";
//				if(position == 1){
//					procedure = parseTypeForParam(type,procedureBuffer,object,map);	
//				}else{
//					procedure =","+ parseTypeForParam(type,procedureBuffer,object,map);
//				}
//				procedureString.append(procedure);
//			}
//			procedureString.append(")}");
//			CallableStatement proc = conn.prepareCall(procedureString.toString());
//			if(outMsgPosition != 0){
//				proc.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
//			}
//			proc.execute();
//			ResultSet rs =  (ResultSet) proc.getObject(1);
//			List<JSONObject> jsonObject =  transferToJSON(rs,dc_id, reportId);
//			GridEntity grid = new GridEntity();
//	grid.setRoot(jsonObject);
//	grid.setTotal(jsonObject.size());
//	JSONObject returnObject = JSONObject.fromObject(grid);
//	
//	print2Json(response, returnObject);
//		} catch (Exception e) {
//			throwBizException(e, "查询报表控件所需要的数据，产生错误: " + e.getMessage());
//		}
//	}
	
	
	
//	/**
//	 * 获取存储过程的名称
//	 * @param express
//	 * @return
//	 */
//	private String getProcedureName(String express){
//		String procedureName = null;
//		if(express.indexOf(".") >= 0){
//			String[] args = express.trim().split("\\.");
//			if(args.length == 2){
//				procedureName = args[1];
//			} else
//				throw new IllegalArgumentException("input error: " + express);
//		} else {
//			procedureName = express.trim();
//		}
//		return procedureName;
//	}
//	
//	/**
//	 * 根据存储过程名称获取输入、输出参数以及类型
//	 * @param conn
//	 * @param procedureName
//	 * @return
//	 */
//	private List<Map> queryParams(Connection conn,String procedureName){
//		try {
//			String sql = "select * from user_arguments where object_name = ? order by position asc";
//			PreparedStatement pst = conn.prepareStatement(sql);
//			pst.setString(1, procedureName.toUpperCase());
//			ResultSet rs = pst.executeQuery();
//			List<Map> list = new ArrayList<Map>(); 
//			while(rs.next()){
//				Map map = new HashMap();
//				map.put("argument_name", rs.getString("argument_name").toLowerCase());
//				map.put("data_type", rs.getString("data_type").toLowerCase());
//				map.put("in_out", rs.getString("in_out").toLowerCase());
//				map.put("position", rs.getString("position").toLowerCase());
//				list.add(map);
//			}
//			pst.close();
//			rs.close();
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	private String parseTypeForParam(String type,StringBuffer procedureString,JSONObject object,Map map){
//		if(type.equals("number")){
//			procedureString.append(object.getInt(map.get("argument_name").toString()));
//		}else if(type.equals("ref cursor")){
//			procedureString.append("?");
//		}else{
//			procedureString.append("'"+object.getString(map.get("argument_name").toString())+"'");
//		}
//		return procedureString.toString();
//	}
	
	 // ResultSet结果封装为 JSONObject列表
//    private List<JSONObject> transferToJSON(ResultSet rs,String dc_id, String reportId) {
//        List<JSONObject> listResult = null;
//        try {
////            List<DatasetParams> parmsList =  datasetservice.queryParmasByDsId(dc_id,"1");
//            List<DatasetParams> parmsList =  datasetService.queryParamsFields(reportId, dc_id);
//            listResult = new ArrayList<JSONObject>();
//            JSONObject jsonResult = null;
//            //封装查询结果为JSONObject列表
//            while (rs.next()) {
//                jsonResult = new JSONObject();
//                String _value = null;
//                for (DatasetParams params : parmsList) {
////                	System.out.println(params.getName());
//                	_value = rs.getString(params.getName());
////                	jsonResult.put(params.getName(), StringUtils.isEmpty(_value) ? "" : _value);
//                	jsonResult.put(params.getFieldTitle(), StringUtils.isEmpty(_value) ? "" : _value);
//                }
//                listResult.add(jsonResult);
//            }
//        } catch (Exception e) {
//        	e.printStackTrace();
//            throw new BaseException("ResultSet转换为Json数据错误: " + listResult);
//        }
//        return listResult;
//    }
}
