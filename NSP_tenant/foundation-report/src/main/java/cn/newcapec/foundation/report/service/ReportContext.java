package cn.newcapec.foundation.report.service;

import java.util.List;
import java.util.Map;

/**
 * 报表服务上下文对象
 * @author shikeying
 *
 */
public interface ReportContext {

	/**
	 * 返回该标识下所有报表对象数据，</br>
	 * 这仅仅返回原始报表数据，不带任何业务参数。</p>
	 * 如果用户定制的报表本身没有业务参数，那这些就是可用的最终报表。
	 * @param identifier 报表平台中对应的用户标识，可为空
	 * @return 所有可用的报表对象集合
	 */
	List<ReportObject> getReportList();
	
	/**
	 * 设置报表需要的业务参数</br>
	 * 对于存在业务参数的报表必须调用该方法，否则报表预览就会因缺少输入参数而发生错误。</p>
	 * 
	 * @param param Map中存放参数名和参数值
	 * @param identifier 用户标识，如果报表系统供多系统使用，就必须提供此标识</br>
	 * 来区别是哪个系统的，如果只有一个用户则可为空
	 */
	void setReportParameters(Map<String, String> param);
	
	/**
	 * 返回最终可以预览的报表连接地址集合</br>
	 * 通常这些可用的报表连接会嵌入到用户的业务系统当中，这些地址已经被加入了必要的</br>
	 * 业务参数；如果用户定制的报表本身存在业务参数，而调用方又没有调用</br>
	 * <code> setReportParameters(Map<String, String> param, String identifier)</code>方法，</br>
	 * 那么报表就不会正确显示。
	 * @param identifier
	 * @return
	 * 
	 * @see #setReportParameters(Map, String)
	 */
	List<ReportObject> getPreviewRportList();
	
	String getIdentifer();
	
	/**
	 * 设置报表系统访问的url链接地址，这是用户业务配置的地址，如：</br>
	 * http://localhost:8080/myapp/NanoReportServlet
	 * @param baseUrl 要设置的报表系统的url链接地址
	 */
	void setReportContextPath(String baseUrl);
}
