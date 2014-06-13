package cn.newcapec.foundation.report.core;


/**
 * 存储过程加载器定义
 * @author shikeying
 * @date 2013-7-16
 *
 */
public interface ProceduresMetaLoader extends MetaLoader {

	/**
	 * 返回存储过程内容
	 * @param name 存储过程名称
	 * @return
	 */
	Object getProcedure();
	
//	/**
//	 * 返回存储过程中的输入和输出参数
//	 * @param name 存储过程名称
//	 * @return
//	 */
//	List<Parameter> getParameters(String name);
//	
//	/**
//	 * 输出中是否包含游标，如果包含返回<code>true</code>
//	 * @return
//	 */
//	boolean hasCursor();
//	
	/**
	 * 返回存储过程中定义的游标类型名称</br>
	 * 如：在Oracle中会在包中定义游标返回类型
	 * <pre>
	 * TYPE ref_cursor IS REF CURSOR;
	 * 找出定义的游标类型名字为"ref_cursor"
	 * </pre>
	 * @return
	 */
	String getCursorTypeName();
	
	/**
	 * 返回存储过程定义的名称，在ORACLE中为包名
	 * @return
	 */
	String getDefinedName();
	
	/**
	 * 返回存储过程名字
	 * @return
	 */
	String getProcedureName();
	
	/**
	 * 设置存储过程名称
	 * @param definedName 存储过程定义名字（ORACLE中为包名）
	 * @param procedureName 存储过程名称
	 */
	void setProcedureName(String definedName, String procedureName);
	
	boolean hasCursorType();
	
	/**
	 * 返回用户重新定义的游标变量名称
	 * @return
	 */
	String getRedeclaredCursorName();
}
