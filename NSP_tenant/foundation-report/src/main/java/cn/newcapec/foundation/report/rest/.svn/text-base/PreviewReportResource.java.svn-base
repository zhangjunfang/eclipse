package cn.newcapec.foundation.report.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.report.biz.impl.ReportManageService;
import cn.newcapec.foundation.report.service.ReportContext;
import cn.newcapec.foundation.report.service.ReportContextFactory;
import cn.newcapec.foundation.report.service.ReportObject;
import cn.newcapec.framework.base.rest.BaseRequest;
import cn.newcapec.framework.base.rest.BaseResponse;

@Component
public class PreviewReportResource extends NanoReportResource {

	public static final String REPORT_BASE_SERVICE = "/reportProxyService/view/index";
	
	@Autowired
	private ReportManageService reportManageService;
	
	public void index(BaseRequest request, BaseResponse response){
		String url = MODULE_PAGE_PREFIX + "preview_report.html";
		String path = request.getOrginRequest().getRootRef().toString();
		
		String reportURL = path + REPORT_BASE_SERVICE;
		System.out.println(">>>>>>>>>>>> reportURL = " + reportURL);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("DpID", "5");
		
		ReportContextFactory.setReportManageService(reportManageService);
		ReportContext reportContext = ReportContextFactory.getInstance("user1", reportURL);
		reportContext.setReportParameters(params);
		List<ReportObject> urls = reportContext.getPreviewRportList();
		
		context.put("reportUrls", urls);
		doIndex(url, response);
	}
}
