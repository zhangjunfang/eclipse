package cn.newcapec.foundation.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.newcapec.foundation.report.biz.impl.ReportManageService;

public abstract class ReportContextFactory {

	private static transient Log logger = LogFactory.getLog(ReportContextFactory.class);
	
	@Autowired
	private static ReportManageService reportManageService;
	
	public static void setReportManageService(ReportManageService rms){
		assert (rms != null);
		if(reportManageService == null)
			reportManageService = rms;
	}
	
	/**
	 * 创建一个报表上下文实例
	 * @param identifier
	 * @param reportContextPath
	 * @return
	 */
	public static final ReportContext getInstance(String identifier
			, String reportContextPath){
		return new ReportContextImpl(identifier, reportContextPath);
	}
	
	/**
	 * 返回数据集对应的过滤字段信息，并组织成Map对象，</br>
	 * map中key=数据库字段名, value=业务映射名称
	 * @param dcId
	 * @return
	 */
	public static final Map<String, String> getReportBusinessParams(String dcId){
		Map<String, String> filters = reportManageService.queryReportFilters(dcId);
//		if(filters == null)
//			throw new NullPointerException("数据集中未查询到定义的过滤字段, dc_id = " + dcId);
		return filters;
	}
	
	private static class ReportContextImpl implements ReportContext{

		private String identifier;
		private String reportContextPath;
		
		@SuppressWarnings("unused")
		private Map<String, String> businessParams = new HashMap<String, String>(2);
		
		private List<ReportObject> cachedReportList = null;
		
		public ReportContextImpl(String identifier
				, String reportContextPath){
			this.identifier = identifier;
			setReportContextPath(reportContextPath);
		}
		
		@Override
		public List<ReportObject> getReportList() {
			// TODO Auto-generated method stub
			if(cachedReportList == null){
				cachedReportList = reportManageService.queryReportObjectList(identifier);
			}
			return cachedReportList;
		}

		@Override
		public void setReportParameters(Map<String, String> param) {
			// TODO Auto-generated method stub
			Assert.notNull(param, "parameter map is required!");
			this.businessParams = param;
			if(cachedReportList == null)
				getReportList();
			if(cachedReportList == null)
				throw new RuntimeException("没有找到任何报表数据");
			for(ReportObject ro : cachedReportList){
				for(Map.Entry<String, String> entry : param.entrySet()){
					if(ro.containsMapName(entry.getKey()))
						ro.addParameter(entry.getKey(), entry.getValue());
				}
				ro.setReportUrl(reportContextPath);
			}
		}

		@Override
		public List<ReportObject> getPreviewRportList() {
			// TODO Auto-generated method stub
			if(cachedReportList != null){
//				List<String> urls = new ArrayList<String>();
//				for(ReportObject ro : cachedReportList){
//					urls.add(ro.getReportUrl());
//				}
				return cachedReportList;
			}
			return null;
		}

		@Override
		public String getIdentifer() {
			// TODO Auto-generated method stub
			return this.identifier;
		}

		@Override
		public void setReportContextPath(String baseUrl) {
			// TODO Auto-generated method stub
			assert (baseUrl != null && !baseUrl.equals(""));
			if(!baseUrl.trim().toLowerCase().startsWith("http://"))
				throw new IllegalArgumentException("reportContextPath must be a available url! e.g. "
						+ "http://localhost:8080/myapp/NanoReportServlet.");
			this.reportContextPath = baseUrl.trim();
//			if(!this.reportContextPath.endsWith("/")){
//				this.reportContextPath += "/";
//			}
			logger.info("print report url: " + this.reportContextPath);
		}
	}
}
