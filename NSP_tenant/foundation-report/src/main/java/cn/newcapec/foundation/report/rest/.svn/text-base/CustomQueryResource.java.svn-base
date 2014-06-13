package cn.newcapec.foundation.report.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.report.biz.impl.DatasetService;
import cn.newcapec.foundation.report.biz.impl.QueryFormService;
import cn.newcapec.foundation.report.biz.impl.ReportManageService;
import cn.newcapec.foundation.report.model.DatasetParams;
import cn.newcapec.foundation.report.model.QueryFormEntity;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.foundation.report.util.StringUtils;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;

/**
 * 报表的自定义查询
 * @author shikeying
 *
 */
@Component
public class CustomQueryResource extends NanoReportResource {

	@Autowired
	private DatasetService datasetService;
	
	@Autowired
	private QueryFormService queryFormService;
	
	@Autowired
	private ReportManageService reportManageService;
	
	public void queryparams(BaseRequest request, BaseResponse response){
		String dc_id = request.getParameter("dc_id");
		try{
			String reportId = request.getParameter("reportId");
			List<DatasetParams> paramsList =  datasetService.queryParmasByDsId(dc_id, "0");
			List<QueryFormEntity> formList =  queryFormService.queryFormByReportId(reportId);
			Map<String,List<?>> map = new HashMap<String, List<?>>();
			if(formList.size()>0){
				// 遍历所有表单组件，把传递过来的值设置好
				String pValue = null;
				for(QueryFormEntity f : formList){
					pValue = request.getParameter(f.getForm_widget_id());
					logger.debug("查找参数: " + f.getForm_widget_id());
					if(StringUtils.isNotEmpty(pValue)){
	//					f.setValue_field(pValue);
						f.setDefault_value(pValue);
						logger.debug("找到参数值: " + pValue);
					}
				}
				paramsList = new ArrayList<DatasetParams>();
			}
			map.put("params", paramsList);
			map.put("form", formList);
			JSONObject object = JSONObject.fromObject(map);
			logger.debug("--------------- form object -----------------");
			logger.debug(object.toString());
			logger.debug("--------------- end -----------------");
			
			print2Json(response, object);
		} catch(Exception e){
			throwBizException(e, "查询数据集参数错误：" + dc_id);
		}
	}
	
	@SuppressWarnings("static-access")
	public void add(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		String reportId = request.getParameter("reportId");
		try{
			boolean delb = queryFormService.deleteFormById(reportId);
			if(delb){
				JSONArray arry = JSONArray.fromObject("["+data+"]");
				for(int i=0;i<arry.size();i++){
					JSONObject object = arry.getJSONObject(i);
					QueryFormEntity form = (QueryFormEntity) object.toBean(object,QueryFormEntity.class);
					String datasourInfoId = UUID.randomUUID().toString();
					Object[] args = new Object[]{
							datasourInfoId,form.getName(),reportId,
							form.getWidget_id(),form.getForm_widget_type(),
							form.getSortCode(),form.getForm_widget_validator(),
							form.getSummary(),form.getUserId(),form.getForm_widget_length(),
							form.getForm_widget_id()
					};
					delb = queryFormService.insertQueryFormInfo(args);
				}
				print2String(response, Boolean.toString(delb));
			} else
				throw new RuntimeException("删除查询表单数据错误：" + reportId);
		} catch(Exception e){
			throwBizException(e, "添加报表表单数据错误: " + reportId);
		}
	}
	
	public void querytree(BaseRequest request, BaseResponse response){
		List<ReportManageEntity> reportList = reportManageService.queryAllReportInfo();
		StringBuffer json = new StringBuffer();
		json.append("{name:'报表列表',open:true,children:[");
		int i = 0;
		for(ReportManageEntity report : reportList){
			if(i == 0)
				json.append("{name:'"+report.getName()+"',id:'"+report.getId()+"',dc_id:'"+report.getDc_id()+"',isParent:false}");
			else 
				json.append(",{name:'"+report.getName()+"',id:'"+report.getId()+"',dc_id:'"+report.getDc_id()+"',isParent:false}");
			i++;
		}
		json.append("]}");
		try{
			print2Json(response, json);
		} catch(Exception e){
			throwBizException(e, null);
		}
	}
}
