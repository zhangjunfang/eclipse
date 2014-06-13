package cn.newcapec.foundation.report.biz;

import java.util.List;
import java.util.Map;

import cn.newcapec.foundation.report.core.ds.DataSource;

/**
 * 返回报表查询结果的接口定义</p>
 * 不同数据源检索报表数据的方式不同，如：SQL语句、存储过程、文件、RPC调用等，</br>
 * 但是返回的最终数据是一样的，<code>Map</code>集合。
 * @author shikeying
 *
 */
public interface SearchReportDataService {

	/**
	 * 根据报表ID，返回报表查询的所有数据
	 * @param reportId 报表ID
	 * @param reportArguments 报表的输入参数，由业务来决定参数格式
	 * @return
	 * @throws DataAccessException 报表数据访问异常
	 * @see {@link cn.newcapec.foundation.report.core.DataAccessException}}
	 */
	List<Map<String, Object>> getReportResult(String reportId
			, Object reportArguments);
	
	/**
	 * 返回支持分页的报表数据
	 * @param reportId 报表ID
	 * @param reportArguments 报表的输入参数，由业务来决定参数格式
	 * @param currentPage 当前页
	 * @param pageSize 每页行数
	 * @return
	 */
	List<Map<String, Object>> getPagedReportResult(String reportId
			, Object reportArguments
			, int currentPage, int pageSize);
	
	/**
	 * 返回报表统计需要的数据源对象
	 * @return
	 */
	DataSource getDataSource();
}
