package cn.newcapec.foundation.report.core;

import java.util.List;

import cn.newcapec.foundation.report.core.parameter.Parameter;
import cn.newcapec.foundation.report.core.sql.SqlFieldExtractor;

/**
 * 存储过程解析器定义
 * @author shikeying
 * @date 2013-7-16
 *
 */
public interface ProceduresParser extends Parser, DataTypeAdapterAware {

	/**
	 * 返回输入参数列表
	 * @return
	 */
	List<Parameter> getInputParameters();
	
	/**
	 * 返回输出参数列表，</br>
	 * 注意：这里只包含普通输出，不包含游标类型
	 * @return
	 */
	List<Parameter> getOutputParameters();
	
	/**
	 * 判断输出中是否包含游标，如果包含返回<code>true</code>
	 * @return
	 */
	boolean hasCursorOutput();
	
	/**
	 * 返回游标类型参数包含的所有字段列表
	 * @return
	 */
	List<Parameter> getCursorParameters();
	
	/**
	 * 设置游标类型定义名字
	 * @param cursorTypeName
	 */
	void setCursorTypeName(String cursorTypeName);
	
	/**
	 * 设置SQL字段提取器对象
	 * @param sqlFieldExtractor
	 */
	void setSqlFieldExtractor(SqlFieldExtractor sqlFieldExtractor);
	
	void setProceduresMetaLoader(ProceduresMetaLoader metaLoader);
}
