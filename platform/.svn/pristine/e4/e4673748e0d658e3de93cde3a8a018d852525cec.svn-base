package cn.newcapec.framework.core.model.variant;
/**
 * 数据类型
 */
@SuppressWarnings("all")
public final class DataType {
	/**
	 * 数据类型名称 - 未知型。
	 */
	public static final String UNKNOWN_NAME = "";
	
	/**
	 * 数据类型 - 未知型。
	 */
	public static final int UNKNOWN = 0;
	
	/**
	 * 数据类型名称 - String型。
	 */
	public static final String STRING_NAME = "string";
	
	/**
	 *  数据类型 - String型。
	 */
	public static final int STRING = 1;
	
	/**
	 * 数据类型名称 - byte型。
	 */
	public static final String BYTE_NAME = "byte";
	
	/**
	 * 数据类型 - byte型。
	 */
	public static final int BYTE = 2;
	
	/**
	 * 数据类型名称 - short型
	 */
	public static final String SHORT_NAME = "short";
	
	/**
	 * 数据类型 - short型
	 */
	public static final int SHORT = 3;
	
	/**
	 * 数据类型名称 - int型
	 */
	public static final String INT_NAME = "int";
	
	/**
	 * 数据类型 - int型
	 */
	public static final int INT = 4;
	
	/**
	 * 数据类型名称 - long型
	 */
	public static final String LONG_NAME = "long";
	/**
	 * 数据类型 - long型
	 */
	public static final int LONG = 5;
	
	
	/**
	 * 数据类型名称 - float型
	 */
	public static final String FLOAT_NAME = "float";
	
	
	/**
	 * 数据类型 - float型
	 */
	public static final int FLOAT = 6;
	
	
	/**
	 * 数据类型名称 - double型
	 */
	public static final String DOUBLE_NAME = "double";
	
	
	/**
	 * 数据类型 - double型
	 */
	public static final int DOUBLE = 7;
	
	/**
	 * 数据类型名称 - bigdecimal型
	 */
	public static final String BIGDECIMAL_NAME = "bigdecimal";
	
	/**
	 * 数据类型 - bigdecimal型
	 */
	public static final int BIGDECIMAL = 8;
	
	
	/**
	 * 数据类型名称 - boolean型
	 */
	public static final String BOOLEAN_NAME = "boolean";
	
	/**
	 * 数据类型 - boolean型
	 */
	public static final int BOOLEAN = 9;
	/**
	 * 
	 * 数据类型名称 - date型
	 */
	public static final String DATE_NAME = "date";
	
	/**
	 * 数据类型 - date型
	 */
	public static final int DATE = 10;
	
	/**
	 * 数据类型名称 - time型
	 */
	public static final String TIME_NAME = "time";
	
	/**
	 * 数据类型 - time型
	 */
	public static final int TIME = 11;
	
	/**
	 * 数据类型名称 - datetime型
	 */
	public static final String DATETIME_NAME = "datetime";
	
	/**
	 * 数据类型 - datetime型
	 */
	public static final int DATETIME = 12;
	/**
	 * 数据类型名称 - 二进制型
	 */
	public static final String BINARY_NAME = "binary";
	/**
	 * 数据类型 - 二进制型
	 */
	public static final int BINARY = 20;

	static Class booleanClazz = getClazz("java.lang.Boolean");

	static Class byteClazz = getClazz("java.lang.Byte");

	static Class doubleClazz = getClazz("java.lang.Double");

	static Class floatClazz = getClazz("java.lang.Float");

	static Class integerClazz = getClazz("java.lang.Integer");

	static Class longClazz = getClazz("java.lang.Long");

	static Class numberClazz = getClazz("java.lang.Number");

	static Class shortClazz = getClazz("java.lang.Short");

	static Class stringClazz = getClazz("java.lang.String");

	static Class bigDecimalClazz = getClazz("java.math.BigDecimal");

	static Class sqlDateClazz = getClazz("java.sql.Date");

	static Class timeClazz = getClazz("java.sql.Time");

	static Class timeStampClazz =  getClazz("java.sql.Timestamp");

	static Class dateClazz =  getClazz("java.util.Date");
	/**
	 * 判断类型是否属性数值范围
	 *  
	 * @param dataType
	 * @return
	 */
	public static final boolean isNumberType(int dataType) {
		return ((DataType.BYTE <= dataType) && (dataType <= DataType.BIGDECIMAL));
	}
	/**
	 * 判断数据类型是否属于日期范围
	 *  
	 * @param dataType
	 * @return
	 */
	public static final boolean isDateType(int dataType) {
		return ((DataType.DATE <= dataType) && (dataType <= DataType.DATETIME));
	}
	/**
	 * 判断一个Class类型是否是可以直接处理的简单类型
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final boolean isBaseDataType(Class clazz) {
		if (clazz.isPrimitive()){
			return true;
		}
		if (clazz.equals(stringClazz)){
			return true;
		}
		if (numberClazz.isAssignableFrom(clazz)){
			return true;
		}
		if (booleanClazz.isAssignableFrom(clazz)){
			return true;
		}
		return dateClazz.isAssignableFrom(clazz);
	}
	/**
	 * 将一个Class类型翻译为对应的DataType
	 *  
	 * @param clazz 
	 * @return
	 */
	public static final int parse(Class clazz) {
		if (clazz.equals(stringClazz))
			return DataType.STRING;
		if (clazz.equals(Integer.TYPE))
			return DataType.INT;
		if (clazz.equals(Boolean.TYPE))
			return DataType.BOOLEAN;
		if (clazz.equals(Float.TYPE))
			return DataType.FLOAT;
		if (clazz.equals(dateClazz))
			return DataType.DATE;
		if (clazz.equals(sqlDateClazz))
			return DataType.DATE;
		if (clazz.equals(timeClazz))
			return DataType.TIME;
		if (clazz.equals(timeStampClazz))
			return DataType.DATETIME;
		if (clazz.equals(Long.TYPE))
			return DataType.LONG;
		if (clazz.equals(Double.TYPE))
			return DataType.DOUBLE;
		if (clazz.equals(Byte.TYPE))
			return DataType.BYTE;
		if (clazz.equals(Short.TYPE))
			return DataType.SHORT;
		if (clazz.equals(bigDecimalClazz))
			return DataType.BIGDECIMAL;
		if (clazz.equals(integerClazz))
			return DataType.INT;
		if (clazz.equals(booleanClazz))
			return DataType.BOOLEAN;
		if (clazz.equals(floatClazz))
			return DataType.FLOAT;
		if (clazz.equals(longClazz))
			return DataType.LONG;
		if (clazz.equals(doubleClazz))
			return DataType.DOUBLE;
		if (clazz.equals(byteClazz))
			return DataType.BYTE;
		if (clazz.equals(shortClazz))
			return DataType.SHORT;
		return 1;
	}
	/**
	 * 根据数据类型名称转换为对应的数据类型代码
	 *  
	 * @param dataTypeName
	 * @return
	 */
	public static final int nameToType(String dataTypeName) {
		if (DataType.STRING_NAME.equals(dataTypeName))
			return DataType.STRING;
		if (DataType.BOOLEAN_NAME.equals(dataTypeName))
			return DataType.BOOLEAN;
		if (DataType.INT_NAME.equals(dataTypeName))
			return DataType.INT;
		if (DataType.FLOAT_NAME.equals(dataTypeName))
			return DataType.FLOAT;
		if (DataType.DATE_NAME.equals(dataTypeName))
			return DataType.DATE;
		if (DataType.TIME_NAME.equals(dataTypeName))
			return DataType.TIME;
		if (DataType.DATETIME_NAME.equals(dataTypeName))
			return DataType.DATETIME;
		if (DataType.DOUBLE_NAME.equals(dataTypeName))
			return DataType.DOUBLE;
		if (DataType.LONG_NAME.equals(dataTypeName))
			return DataType.LONG;
		if (DataType.BYTE_NAME.equals(dataTypeName))
			return DataType.BYTE;
		if (DataType.SHORT_NAME.equals(dataTypeName))
			return DataType.SHORT;
		if (DataType.BIGDECIMAL_NAME.equals(dataTypeName))
			return DataType.BIGDECIMAL;
		if (DataType.BINARY_NAME.equals(dataTypeName))
			return DataType.BINARY;
		return DataType.UNKNOWN;
	}
	/**
	 * 根据数据类型代码转换为对应的数据类型名称
	 *  
	 * @param type
	 * @return
	 */
	public static final String typeToName(int type) {
		switch (type) {
		case DataType.STRING:
			return DataType.STRING_NAME;
		case DataType.BOOLEAN:
			return DataType.BOOLEAN_NAME;
		case DataType.INT:
			return DataType.INT_NAME;
		case DataType.FLOAT:
			return DataType.FLOAT_NAME;
		case DataType.DATE:
			return DataType.DATE_NAME;
		case DataType.TIME:
			return DataType.TIME_NAME;
		case DataType.DATETIME:
			return DataType.DATETIME_NAME;
		case DataType.DOUBLE:
			return DataType.DOUBLE_NAME;
		case DataType.LONG:
			return DataType.LONG_NAME;
		case DataType.BYTE:
			return DataType.BYTE_NAME;
		case DataType.SHORT:
			return DataType.SHORT_NAME;
		case DataType.BIGDECIMAL:
			return DataType.BIGDECIMAL_NAME;
		case DataType.BINARY:
			return DataType.BINARY_NAME;
		}
		return "";
	}
	/**
	 * 根据数据类型代码获取对应的数据class类型
	 * @param dataType
	 * @return
	 */
	public static final Class typeToClass(int dataType) {
		switch (dataType) {
		case DataType.STRING:
			return stringClazz;
		case DataType.BOOLEAN:
			return booleanClazz;
		case DataType.INT:
			return integerClazz;
		case DataType.FLOAT:
			return floatClazz;
		case DataType.DATE:
		case DataType.TIME:
		case DataType.DATETIME:
			return dateClazz;
		case DataType.DOUBLE:
			return doubleClazz;
		case DataType.LONG:
			return longClazz;
		case DataType.BYTE:
			return byteClazz;
		case DataType.SHORT:
			return shortClazz;
		case DataType.BIGDECIMAL:
			return bigDecimalClazz;
		case DataType.BINARY:
			return null;
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		}
		return null;
	}

	static final Class getClazz(String clazzName) {
		try {
			return Class.forName(clazzName);
		} catch (ClassNotFoundException ex) {
			throw new NoClassDefFoundError(ex.getMessage());
		}
	}
}