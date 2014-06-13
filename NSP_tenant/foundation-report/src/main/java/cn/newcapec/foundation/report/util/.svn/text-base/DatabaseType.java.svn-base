package cn.newcapec.foundation.report.util;

public enum DatabaseType {

	ORACLE{
		public int getTypeIndex(){
			return ORACLE_TYPE;
		}
	},
	MYSQL{
		public int getTypeIndex(){
			return MYSQL_TYPE;
		}
	},
	SQLSERVER{
		public int getTypeIndex(){
			return SQLSERVER_TYPE;
		}
	};
	
	static final int ORACLE_TYPE = 1;
	static final int MYSQL_TYPE = 2;
	static final int SQLSERVER_TYPE = 3;
	
	/**
	 * 返回数据库类型的索引值，系统定义了常用数据库的索引值。
	 * <pre>
	 * 枚举类型如下：
	 * ORACLE, MYSQL, SQLSERVER
	 * </pre>
	 * @return
	 */
	public int getTypeIndex(){
		throw new AbstractMethodError();
	}
}
