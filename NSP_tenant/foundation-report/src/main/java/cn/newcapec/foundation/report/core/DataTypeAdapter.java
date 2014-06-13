package cn.newcapec.foundation.report.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据类型转换适配器，能把数据库类型转换成系统特定的类型
 * @author shikeying
 * @date 2013-7-17
 *
 */
public abstract class DataTypeAdapter {

	private static Log logger = LogFactory.getLog(DataTypeAdapter.class);
	
	/**
	 * 把输入的[数据库对应的字段类型]转换成[系统数据类型]
	 * @param dbType 数据库类型名称，如oracle中的VARCHAR2
	 * @return
	 */
	public abstract DataType getAdapterDataType(String dbType);
	
	/**
	 * 得到一个数据库默认（字符）字段类型
	 * @return
	 */
	public abstract String getDefaultType();
	
	private static final DataTypeAdapter oracleAdapter = new OracleDataTypeAdapterImpl();
	private static final DataTypeAdapter sqlServerAdapter = new SqlServerDataTypeAdapterImpl();
	
	/**
	 * 返回<code>Oracle</code>数据库类型适配器对象
	 * @return
	 */
	public static DataTypeAdapter getOracleDataTypeAdapter(){
		return oracleAdapter;
	}
	
	/**
	 * 返回<code>SqlServer</code>数据库类型适配器对象
	 * @return
	 */
	public static DataTypeAdapter getSqlserverDataTypeAdapter(){
		logger.info("sqlServerAdapter not implement. " + sqlServerAdapter);
		throw new UnsupportedOperationException("系统尚未实现SqlServer数据类型转换适配器");
//		return sqlServerAdapter;
	}
	
	private static class OracleDataTypeAdapterImpl extends DataTypeAdapter{

		private static Map<String, DataType> cachedTypesMap = null;
		
		public OracleDataTypeAdapterImpl(){
			loadDataTypeInfo();
		}
		
		@Override
		public DataType getAdapterDataType(String dbType) {
			// TODO Auto-generated method stub
			assert (dbType != null);
			return cachedTypesMap.get(dbType.toUpperCase());
		}
		
		private synchronized void loadDataTypeInfo(){
			if(cachedTypesMap == null)
				cachedTypesMap = new HashMap<String, DataType>(8);
			cachedTypesMap.put("CHAR", DataType.STRING);
			cachedTypesMap.put("VARCHAR2", DataType.STRING);
			cachedTypesMap.put("NVARCHAR", DataType.STRING);
			cachedTypesMap.put("NUMBER", DataType.NUMBER);
			cachedTypesMap.put("LONG", DataType.NUMBER);
			cachedTypesMap.put("DATE", DataType.DATE);
			cachedTypesMap.put("TIMESTAMP", DataType.DATE_TIME);
			
			cachedTypesMap = Collections.unmodifiableMap(cachedTypesMap);
		}

		@Override
		public String getDefaultType() {
			// TODO Auto-generated method stub
			return "VARCHAR2";
		}
	}
	
	private static class SqlServerDataTypeAdapterImpl extends DataTypeAdapter{
		private static Map<String, DataType> cachedTypesMap = null;
		
		public SqlServerDataTypeAdapterImpl(){
			loadDataTypeInfo();
		}
		
		@Override
		public DataType getAdapterDataType(String dbType) {
			// TODO Auto-generated method stub
			assert (dbType != null);
			return cachedTypesMap.get(dbType.toUpperCase());
		}
		
		private synchronized void loadDataTypeInfo(){
			if(cachedTypesMap == null)
				cachedTypesMap = new HashMap<String, DataType>(8);
//			cachedTypesMap.put("CHAR", DataType.STRING);
			
			cachedTypesMap = Collections.unmodifiableMap(cachedTypesMap);
		}

		@Override
		public String getDefaultType() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args){
		DataTypeAdapter oracleAdapter = DataTypeAdapter.getOracleDataTypeAdapter();
		System.out.println(oracleAdapter.getAdapterDataType("varchar2"));
	}
}
