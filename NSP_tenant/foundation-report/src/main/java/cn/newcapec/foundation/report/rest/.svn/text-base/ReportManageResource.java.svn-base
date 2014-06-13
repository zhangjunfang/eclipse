package cn.newcapec.foundation.report.rest;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.report.biz.impl.DatasetService;
import cn.newcapec.foundation.report.biz.impl.DatasourceService;
import cn.newcapec.foundation.report.biz.impl.ReportManageService;
import cn.newcapec.foundation.report.biz.impl.WidgetService;
import cn.newcapec.foundation.report.core.ds.DatabaseSource;
import cn.newcapec.foundation.report.core.ds.OraclePooledDatabaseSource;
import cn.newcapec.foundation.report.core.sql.SqlInOutParser;
import cn.newcapec.foundation.report.model.DatasetEntity;
import cn.newcapec.foundation.report.model.DatasetParams;
import cn.newcapec.foundation.report.model.DatasourceEntity;
import cn.newcapec.foundation.report.model.GridEntity;
import cn.newcapec.foundation.report.model.ReportFieldsEntity;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.foundation.report.model.WidgetEntity;
import cn.newcapec.foundation.report.service.ReportContextFactory;
import cn.newcapec.foundation.report.util.StringUtils;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;
import cn.newcapec.framework.utils.Page;
import cn.newcapec.framework.utils.PageView;
import cn.newcapec.framework.utils.SystemContext;

@Component
public class ReportManageResource extends NanoReportResource {

	@Autowired
	private ReportManageService reportManageService;
	
	@Autowired
	private DatasetService datasetService;
	
	@Autowired
	private WidgetService widgetService;
	
	@Autowired
	private DatasourceService dataSourceService;
	
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	
	public void index(BaseRequest request, BaseResponse response){
		String url = MODULE_PAGE_PREFIX + "reportmanage.html";
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		Page page = reportManageService.queryReports(jsonObject, orderby);
//		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
//				5, SystemContext.getOffset());
//		pageView.setQueryResult(page);
//		pageView.setJsMethod("reload");
//		context.put("pageView", pageView);
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		context.put("menuName", "设计报表");
		showPageList(request, response);
		doIndex(url, response);
	}
	
	public void showPageList(BaseRequest request, BaseResponse response){
		String url = MODULE_PAGE_PREFIX + "reportmanage_list.html";
		
		SystemContext.setPagesize(5);
		Page page = reportManageService.queryReports(getJsonObject(), orderby);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				5, SystemContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reload");
		context.put("menuName", "设计报表");
		context.put("pageView", pageView);
		response.toHtml(url, context);
	}
	
	public void list(BaseRequest request, BaseResponse response){
		String report_name = request.getParameter("name");
		Object[] args = null;
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		int pageStartNum = start*limit;
		int pageEndNum = start*limit+limit;
		if(report_name != null && !"".equals(report_name))
			args = new Object[]{"%"+report_name+"%", pageStartNum, pageEndNum};
		else
			args = new Object[]{pageStartNum, pageEndNum};
		GridEntity grid = new GridEntity();
		List<ReportManageEntity> dsList =  reportManageService.queryReportByParam(args, report_name);
		int total = reportManageService.queryCountByName(report_name);
		grid.setRoot(dsList);
		grid.setTotal(total);
		JSONObject object = JSONObject.fromObject(grid);
		try{
			print2Json(response, object);
		} catch(Exception e){
			throwBizException(e, "查询报表设计列表出错");
		}
	}
	
	public void checkuniquename(BaseRequest request, BaseResponse response){
		String name = request.getParameter("name");
		int res = reportManageService.checkUniqueName(name);
		try{
			print2String(response, res);
		} catch(Exception e){
			throwBizException(e, "检查报表名称唯一出现错误: " + name);
		}
	}
	
	public void queryDsParamAndReportFields(BaseRequest request, BaseResponse response){
		String report_id = request.getParameter("reportId");
		String dc_id = request.getParameter("dcId");
		List<DatasetParams> outParamsList =  datasetService.queryParmasByDsId(dc_id,"1");
		List<ReportFieldsEntity> reportFieldsList = reportManageService.queryReportFieldsList(report_id);
		
		List<DatasetParams> noCheckedList = new ArrayList<DatasetParams>();
		if(reportFieldsList.size()>0){
			for(DatasetParams dsParams : outParamsList){
				String fieldName = dsParams.getName().toLowerCase();
				boolean b = true;
				for(ReportFieldsEntity reportFields : reportFieldsList ){
					String outFieldName = reportFields.getField_name().toLowerCase();
					if(fieldName.equals(outFieldName)){
						b = false;
					}
				}
				if(b){
					noCheckedList.add(dsParams);
				}
			}
		}else{
			noCheckedList = outParamsList;
		}
		
		Map<String,List<?>> map = new HashMap<String,List<?>>();
		map.put("noChecked",noCheckedList );
		map.put("checked", reportFieldsList);
		JSONObject object = JSONObject.fromObject(map);
		
		try{
			print2Json(response, object);
		} catch(Exception e){
			throwBizException(e, "渲染报表字段数据错误: " + object);
		}
	}
	
	/**
	 * 添加一个报表设计基本信息
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("static-access")
	public void add(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);
		ReportManageEntity report = (ReportManageEntity) object.toBean(object,ReportManageEntity.class);
		String id = UUID.randomUUID().toString(); 
		Object[] args = new Object[]{
			id,report.getName(),report.getDc_id(),report.getSummary(),0
		};
		try{
			boolean b = reportManageService.inserReportInfo(args);
			if(b){
				print2String(response, id);
			}else{
				print2String(response, "");
			}
		} catch(Exception e){
			throwBizException(e, "保存报表设计信息出现异常: " + data);
		}
	}
	
	@SuppressWarnings("static-access")
	public void update(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		JSONObject object = JSONObject.fromObject(data);
		ReportManageEntity report = (ReportManageEntity) object.toBean(object,ReportManageEntity.class);
		Object[] args = new Object[]{
				report.getName(),
				report.getDc_id(),
				report.getSummary(),
				report.getId()
		};
		try{
			boolean b = reportManageService.updateReportInfo(args);
			if(b){
				print2String(response, report.getId());
			}else{
				print2String(response, "");
			}
		} catch(Exception e){
			throwBizException(e, "更新报表设计信息出现异常: " + data);
		}
	}
	
	public void delete(BaseRequest request, BaseResponse response){
		String ids = request.getParameter("ids");
		String[] str = ids.split(",");
		try{
			for(String id : str ){
				reportManageService.deleteReportInfo(id);
			}
		} catch(Exception e){
			throwBizException(e, "删除报表设计记录错误: " + ids);
		}
	}
	
	@SuppressWarnings("static-access")
	public void addreportfields(BaseRequest request, BaseResponse response){
		String data = request.getParameter("data");
		String reportId = request.getParameter("reportId");
		
		try{
			boolean delb = reportManageService.deleteReportFields(reportId);
			if(delb == false)
				throw new RuntimeException("删除报表字段异常: " + reportId);
			JSONArray array =  JSONArray.fromObject("["+data+"]");
			boolean b = false;
			for(int i=0;i<array.size();i++){
				JSONObject object = (JSONObject) array.get(i);
				ReportFieldsEntity reportFields = (ReportFieldsEntity) object.toBean(object,ReportFieldsEntity.class);
				Object[] args = new Object[]{
						UUID.randomUUID().toString(),
						reportFields.getField_name(),reportFields.getField_title(),
						reportFields.getSort(),reportFields.getSummary(),
						reportFields.getUserId(),reportFields.getField_type(),
						reportFields.getDc_id(),reportFields.getReport_id()
				};
				b = reportManageService.insertReportFieldsInfo(args);
			}
			print2String(response, Boolean.toString(b));
		
		} catch(Exception e){
			throwBizException(e, "添加报表字段数据错误: " + reportId);
		}
	}
	
	//
	public void updateReportState(BaseRequest request, BaseResponse response){
		String ids = request.getParameter("ids");
		String state = request.getParameter("state");
		String[] str = ids.split(",");
		boolean b = false;
		
		try{
			for(String id : str){
				b = reportManageService.updateReportState(id, Integer.parseInt(state));
				if(!b) break;
			}
			print2String(response, Boolean.toString(b));
		} catch(Exception e){
			throwBizException(e, null);
		}
	}
	
	public void showDesignor(BaseRequest request, BaseResponse response){
		String url = MODULE_PAGE_PREFIX + "designreport.html";
		try{
			response.toHtml(url, context);
		} catch(Exception e){
			throwBizException(e, null);
		}
	}
	
	public void reporttemplate(BaseRequest request, BaseResponse response){
		String id = request.getParameter("id");
//		String dc_id = request.getParameter("dc_id");
		ReportManageEntity report = reportManageService.getReportEntityById(id);
		String content = report.getContent();
		try{
			if(content ==  null || content == ""){
				List<ReportFieldsEntity> filedsList = reportManageService.queryReportFieldsList(id);
	//			String filePath = request.getSession().getServletContext().getRealPath("/")+"/nanoreport/page/template/report.xml";
	//			File file = new File(filePath);
				
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cn/newcapec/foundation/report/rest/report.xml");
				SAXReader reader = new SAXReader();
				
				
				Document doc = reader.read(inputStream);
				Element root = doc.getRootElement();
				Element element = (Element) doc.selectSingleNode("/Report/DataSources");
				if(element == null){
					Element datasourcesElement = root.addElement("DataSources");
					Element datasourceElement = datasourcesElement.addElement("DataSource");
					datasourceElement.addAttribute("type", "4");
					Element dataElement = datasourceElement.addElement("Data");
					dataElement.addElement("ID").setText("ds1");
					dataElement.addElement("Version").setText("2");
					dataElement.addElement("Type").setText("4");
					dataElement.addElement("TypeMeaning").setText("JSON");
					Element xml_RecordAble_NodesElement = dataElement.addElement("XML_RecordAble_Nodes");
					xml_RecordAble_NodesElement.addElement("node").addElement("name");
					Element columnsElement = dataElement.addElement("Columns");
					int i=1;
					for(ReportFieldsEntity field : filedsList){
						Element columnElement = columnsElement.addElement("Column");
						
						// 现在只能使用列标题，而不能用列索引名
	//					columnElement.addElement("name").setText(field.getField_name());
						if(cn.newcapec.foundation.report.util.StringUtils.isEmpty(field.getField_title()))
							throw new NullPointerException("报表字段列别名未定义，无法生成报表。");
						columnElement.addElement("name").setText(field.getField_title());
						
						columnElement.addElement("text").setText(field.getField_title() == null ? field.getField_name():field.getField_title());
						columnElement.addElement("type").setText(field.getField_type());
						columnElement.addElement("visible").setText("true");
						columnElement.addElement("sequence").setText(i+"");
						i++;
					}
				}
		
				boolean b = reportManageService.updateReportTemplate(content, id);
				if(b){
					content = doc.asXML();
					System.out.println(content);
//					print2Xml(response, content);
					// 使用纯文本返回即可，如果用xml格式返回客户端报错
					print2String(response, content);
				}else{
					throw new RuntimeException("更新报表模板失败: " + content);
				}
			} else
				print2String(response, content);
		} catch(DocumentException de){
			throwBizException(de, "读取模板配置文件失败: report.xml");
		} catch(Exception e){
			throwBizException(e, e.getMessage());
		}
	}
	
	public void updatetemplate(BaseRequest request, BaseResponse response){
		String template = request.getParameter("content");
		String id = request.getParameter("id");
		try{
			boolean res = reportManageService.updateReportTemplate(template, id);
			print2String(response, Boolean.toString(res));
		} catch(Exception e){
			throwBizException(e, "更新报表模板失败: " + template);
		}
	}
	
	public void queryWidgetData(BaseRequest request, BaseResponse response){
		String widget_name = request.getParameter("widget_name");
		try {
			WidgetEntity widget =  widgetService.queryWidgetByName(widget_name);
			DatasetEntity dataset =  datasetService.getDatasetInfoById(widget.getDc_id());
			DatasourceEntity dsEntity = dataSourceService.getDsEntityById(dataset.getDs_id());
			DatabaseSource ds = new OraclePooledDatabaseSource();
			ds.setAddress(dsEntity.getDsAddress());
			ds.setService(dsEntity.getDsServer());
			ds.setUsername(dsEntity.getDsUser());
			ds.setPassword(dsEntity.getDsPass());
			Connection conn = ds.getConnection();
			System.out.println("sql-------------------------"+dataset.getContent());
			
			/* 找到SQL中的变量，如果存在就使用传递过来的参数替换它 */
			Map<String, String> bizParams = ReportContextFactory.getReportBusinessParams(widget.getDc_id());
			
			String sql = dataset.getContent();
			if(StringUtils.isEmpty(sql))
				throw new NullPointerException("控件中未定义任何SQL语句：" + dataset);
			
			sql = replaceSqlParameters(sql, ds, request, bizParams);
			logger.debug(sql);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			List<JSONObject> objectList = new ArrayList<JSONObject>();
			JSONObject object = null;
			if("下拉树".equals(widget.getWidgetType())){
				while(rs.next()){
					object = new JSONObject();
					String text = rs.getString(widget.getTextField());
					String value = rs.getString(widget.getValueField());
					String parentId = rs.getString(widget.getParentField());
					object.put("id", value);
					object.put("pId", parentId);
//					object.put("pId", parentId.equals("ROOT") ? "0" : parentId);
					object.put("name", text);
					objectList.add(object);
				}
			}else if("下拉框".equals(widget.getWidgetType())){
				while(rs.next()){
					object = new JSONObject();
					String text = rs.getString(widget.getTextField());
					String value = rs.getString(widget.getValueField());
					object.put("value", value);
					object.put("text", text);
					objectList.add(object);
				}
			}
			String result = objectList.toString();
//			print2String(response, result);
			
//			result = "[{\"id\":\"ZZZ\", \"pid\":\"0\", \"name\":\"4\"},{\"id\":\"012\", \"pid\":\"ZZZ\", \"name\":\"测试\"}]";
			print2Json(response, result);
//			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
			throwBizException(e, "获取查询组件列表数据错误。");
		}
	}
	
	/**
	 * 把SQL语句中的变量替换成值</p>
	 * 即：@dpid --> 'value'
	 * @param sql
	 * @param ds
	 * @param request
	 * @param bizParams
	 * @return
	 */
	private String replaceSqlParameters(String sql
			, DatabaseSource ds, BaseRequest request
			, Map<String, String> bizParams){
		SqlInOutParser parser = new SqlInOutParser(sql, ds);
		List<String> inputNames = parser.getInputParameters();
		if(inputNames != null && inputNames.size() > 0){
			if(bizParams == null || bizParams.size() == 0)
				throw new IllegalStateException("在控件SQL语句中发现定义有业务参数，但在数据集中并没有定义，请确定数据集定义正确。"
						 + "\ninputNames in sql: " + inputNames);
			
			String _value = null;
			String mapName = null;
			for(String _parameter : inputNames){
				mapName = containsMapNameInDbnames(bizParams, _parameter);
				if(StringUtils.isNotEmpty(mapName)){
					_value = request.getParameter(mapName);
					if(StringUtils.isNotEmpty(_value)){
						sql = sql.replaceAll("@" + _parameter, "'" + _value + "'");
					} else
						throw new NullPointerException("指定的参数名没有提供参数值：" + mapName);
				} else
					throw new IllegalArgumentException("通过数据字段参数名，没有找到映射参数名称: " + _parameter);
			}
		}
		return sql;
	}
	
	/**
	 * 从参数集合中找出映射的参数名
	 * @param bizParams 参数集合<数据字段名，映射名>
	 * @param dbName 数据字段名
	 * @return
	 */
	private String containsMapNameInDbnames(Map<String, String> bizParams, String dbName){
		if(bizParams == null) return null;
		for(Map.Entry<String, String> entry : bizParams.entrySet()){
			if(entry.getKey().equals(dbName))
				return entry.getValue();
		}
		return null;
	}
}
