package cn.newcapec.foundation.report.rest;

import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.report.biz.impl.DatasetService;
import cn.newcapec.foundation.report.biz.impl.WidgetService;
import cn.newcapec.foundation.report.model.DatasetEntity;
import cn.newcapec.foundation.report.model.DatasetParams;
import cn.newcapec.foundation.report.model.GridEntity;
import cn.newcapec.foundation.report.model.WidgetEntity;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;

/**
 * 控件管理
 * @author shikeying
 *
 */
@Component
public class WidgetManageResource extends NanoReportResource {

	@Autowired
	private WidgetService widgetService;
	
	@Autowired
	private DatasetService datasetService;
	
	public void index(BaseRequest request, BaseResponse response){
		String url = MODULE_PAGE_PREFIX + "widgetmanage.html";
		doIndex(url, response);
	}
	
	//
	public void list(BaseRequest request, BaseResponse response){
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		int pageStartNum = start*limit;
		int pageEndNum = start*limit+limit;
		Object[] args = new Object[]{pageStartNum, pageEndNum};
		GridEntity grid = new GridEntity();
		try {
			List<WidgetEntity> dsList =  widgetService.getWidgetPaginationByParams(args);
			int total = widgetService.getWidgetCountByParams();
			grid.setRoot(dsList);
			grid.setTotal(total);
		
			print2Json(response, JSONObject.fromObject(grid));
		} catch (Exception e) {
			throwBizException(e, "查询控件列表出现异常。");
		}
	}
	
	public void queryDcByType(BaseRequest request, BaseResponse response){
		String type = request.getParameter("type");
		List<DatasetEntity> datasetList =  datasetService.getDatasetByType(type);
		JSONArray datasetArray = JSONArray.fromObject(datasetList);
		
		try{
			print2Json(response, datasetArray);
		} catch(Exception e){
			throwBizException(e, "加载数据集列表出现错误");
		}
	}
	
	public void checkuniquename(BaseRequest request, BaseResponse response){
		String name = request.getParameter("name");
		int res = widgetService.checkUniqueName(name);
		try{
			print2String(response, res);
		} catch(Exception e){
			throwBizException(e, "检查控件名称重复出现错误");
		}
	}
	
	public void queryDcOutParams(BaseRequest request, BaseResponse response){
		String dataset_id = request.getParameter("datasetId");
		String type = request.getParameter("type");
		List<DatasetParams> paramsList = datasetService.queryParmasByDsId(dataset_id, type);
		JSONArray datasetArray = JSONArray.fromObject(paramsList);
		try{
			print2Json(response, datasetArray);
		} catch(Exception e){
			throwBizException(e, "返回数据集对应的输出字段出现错误, dataset = " + dataset_id);
		}
	}
	
	public void delete(BaseRequest request, BaseResponse response){
		String ids = request.getParameter("ids");
		String[] str = ids.split(",");
		try{
			for(String id : str ){
				widgetService.deleteWidgetInfo(id);
			}
		} catch(Exception e){
			throwBizException(e, "删除控件失败: " + ids);
		}
	}
	
	public void add(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);
		WidgetEntity widget = (WidgetEntity) JSONObject.toBean(object,WidgetEntity.class);
		Object[] args = new Object[]{
				UUID.randomUUID().toString(),
				widget.getTextField(),
				widget.getParentField(),widget.getTopDefault(),
				widget.getWidgetType(),widget.getSummary(),
				widget.getName(),widget.getValueField(),
				widget.getUserId(),widget.getDc_id()
		};
		try{
			widgetService.inserWidgetInfo(args);
		} catch(Exception e){
			throwBizException(e, "添加控件数据错误: " + data);
		}
	}
	
	public void update(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);
//		WidgetEntity widget = (WidgetEntity) object.toBean(object,WidgetEntity.class);
		WidgetEntity widget = (WidgetEntity) JSONObject.toBean(object,WidgetEntity.class);
		Object[] args = new Object[]{
				widget.getTextField(),widget.getParentField(),
				widget.getTopDefault(),widget.getWidgetType(),
				widget.getSummary(),widget.getName(),widget.getValueField(),
				widget.getUserId(),widget.getDc_id(),
				widget.getId()
		};
		try{
			widgetService.updateWidgetInfo(args);
		} catch(Exception e){
			throwBizException(e, "更新控件数据错误: " + data);
		}
	}
	
	public void queryByType(BaseRequest request, BaseResponse response){
		String type = request.getParameter("type");
		try{
			List<WidgetEntity> widgetList =  widgetService.queryWidgetByType(type);
			JSONArray widgetArray = JSONArray.fromObject(widgetList);
			print2Json(response, widgetArray);
		} catch(Exception e){
			throwBizException(e, "查询控件类型出现错误: " + type);
		}
	}
}
