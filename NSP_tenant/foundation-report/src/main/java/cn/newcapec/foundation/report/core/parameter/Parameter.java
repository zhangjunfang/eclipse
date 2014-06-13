package cn.newcapec.foundation.report.core.parameter;

import cn.newcapec.foundation.report.core.DataType;

/**
 * 参数定义
 * @author shikeying
 *
 */
public interface Parameter {

	abstract String getName();
	
	abstract Parameter setName(String name);
	
	abstract DataType getType();
	
	abstract Parameter setType(DataType type);
	
	boolean isInput();
	
	boolean isOutput();
	
	/**
	 * 是否游标输出类型
	 * @return
	 */
	boolean isOutCursor();
	
	/**
	 * 返回参数的别名，通常是这个参数的描述信息
	 * @return
	 */
	String getAlias();
	
	Parameter setAlias(String alias);
}
