package cn.newcapec.framework.core.model.variant;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.utils.dataUtils.DateUtil;
import cn.newcapec.framework.core.utils.stringUtils.StringUtil;

/**
 * 
 */
public final class VariantUtil implements LogEnabled {
	/**
	 * 短日期formatter
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 时间formatter
	 */
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	/**
	 * 长日期formatter
	 */
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final Object toObject(byte value) {
		return new Byte(value);
	}

	public static final Object toObject(short value) {
		return new Short(value);
	}

	public static final Object toObject(int value) {
		return new Integer(value);
	}

	public static final Object toObject(long value) {
		return new Long(value);
	}

	public static final Object toObject(float value) {
		return new Float(value);
	}

	public static final Object toObject(double value) {
		return new Double(value);
	}

	public static final Object toObject(boolean value) {
		return new Boolean(value);
	}

	public static final Object toObject(Date value) {
		return value;
	}

	public static final Object toObject(BigDecimal value) {
		return value;
	}

	public static final Object toObject(String value) {
		return value;
	}
	
	/**
	 * 转换string
	 *  
	 * @param value
	 * @return
	 */
	public static final String parseString(Object value) {
		if (value == null)
			return null;
		if (value instanceof Date)
			return DateUtil.toDateString((Date) value);
		return value.toString();
	}
	/**
	 * 转换byte
	 *  
	 * @param value
	 * @return
	 */
	public static final byte parseByte(Object value) {
		String str;
		if (value == null)
			return 0;
		if (value instanceof Number)
			return ((Number) value).byteValue();
		if (value instanceof Boolean)
			return (byte) ((((Boolean) value).booleanValue()) ? 1 : 0);
		if ((str = value.toString()).equals(""))
			return 0;
		return Byte.parseByte(str);
	}
	/**
	 * 转换short
	 *  
	 * @param value
	 * @return
	 */
	public static final short parseShort(Object value) {
		String str;
		if (value == null)
			return 0;
		if (value instanceof Number)
			return ((Number) value).shortValue();
		if (value instanceof Boolean)
			return (short) ((((Boolean) value).booleanValue()) ? 1 : 0);
		if ((str = value.toString()).equals(""))
			return 0;
		return Short.parseShort(str);
	}
	
	/**
	 * 转换int
	 *  
	 * @param value
	 * @return
	 */
	public static final int parseInt(Object value) {
		String str;
		if (value == null)
			return 0;
		if (value instanceof Number)
			return ((Number) value).intValue();
		if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				return 1;
			return 0;
		}
		if ((str = value.toString()).equals(""))
			return 0;
		return Integer.parseInt(str);
	}
	
	/**
	 * 转换long
	 *  
	 * @param value
	 * @return
	 */
	public static final long parseLong(Object value) {
		String str;
		if (value == null)
			return 0L;
		if (value instanceof Number)
			return ((Number) value).longValue();
		if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				return 1L;
			return 0L;
		}
		if (value instanceof Date)
			return ((Date) value).getTime();
		if ((str = value.toString()).equals(""))
			return 0L;
		return Long.parseLong(str);
	}
	/**
	 *  转换float
	 * @param value
	 * @return
	 */
	public static final float parseFloat(Object value) {
		String str;
		if (value == null)
			return 0F;
		if (value instanceof Number)
			return ((Number) value).floatValue();
		if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				return 1F;
			return 0F;
		}
		if ((str = value.toString()).equals(""))
			return 0F;
		return Float.parseFloat(str);
	}
	/**
	 * 转换double
	 *  
	 * @param value
	 * @return
	 */
	public static final double parseDouble(Object value) {
		String str;
		if (value == null)
			return 0D;
		if (value instanceof Number)
			return ((Number) value).doubleValue();
		if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue())
				return 1D;
			return 0D;
		}
		if ((str = value.toString()).equals(""))
			return 0D;
		return Double.parseDouble(str);
	}
	
	/**
	 * 转换bigdecimal
	 * @param value
	 * @return
	 */
	public static final BigDecimal parseBigDecimal(Object value) {
		String str;
		if (value == null)
			return BigDecimal.valueOf(0L);
		if (value instanceof BigDecimal)
			return ((BigDecimal) value);
		if (value instanceof Number)
			return BigDecimal.valueOf(((Number) value).longValue());
		if (value instanceof Boolean)
			return BigDecimal.valueOf((((Boolean) value).booleanValue()) ? 1L
					: 0L);
		if ((str = value.toString()).equals(""))
			return BigDecimal.valueOf(0L);
		return new BigDecimal(str);
	}
	/**
	 * 转换逻辑值
	 *  
	 * @param value
	 * @return
	 */
	public static final boolean parseBoolean(String value) {
		if (value == null)
			return false;
		return ((value.equalsIgnoreCase("true")) || (value.equals("1")) || (value.equals("-1")));
	}
	
	/**
	 * 转换布尔值
	 *  
	 * @param value
	 * @return
	 */
	public static final boolean parseBoolean(Object value) {
		if (value == null)
			return false;
		if (value instanceof Boolean)
			return ((Boolean) value).booleanValue();
		return parseBoolean(value.toString());
	}
	/**
	 * 判断是否为数值
	 *  
	 * @param str
	 * @return
	 */
	private static boolean isNumber(String str) {
		int i = str.length();
		for (int j = 0; j < i; ++j) {
			int k;
			if (((((k = str.charAt(j)) < '0') || (k > 57))) && (k != 46)
					&& (((j != 0) || (k != 45))))
				return false;
		}
		return true;
	}
	/**
	 * 转换日期
	 *  
	 * @param value
	 * @return
	 * @throws SQLException 
	 */
	public static final Date parseDate(Object value) {
		String str;
		if (value == null)
			return null;
		if (value instanceof Date)
			return ((Date) value);
		if (value instanceof Number)
			return new Date(((Number) value).longValue());
		if (value instanceof oracle.sql.TIMESTAMP)
			try {
				return ((oracle.sql.TIMESTAMP) value).timestampValue();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		if (StringUtil.isValid(str = String.valueOf(value))) {
			if (isNumber(str)) {
				long l = Long.parseLong(str);
				return new Date(l);
			}
			int i = str.length();
			try {
				if (i < 19) {
					if (str.indexOf(":") > 0)
						return timeFormat.parse(str);
					return dateFormat.parse(str);
				}
				log.info("str:"+str);
				return dateTimeFormat.parse(str);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 将对象转换为对应的数据类型值
	 * @param dataType
	 * @param obj
	 * @return
	 */
	public static final Object translate(int dataType, Object obj) {
		if ((obj == null)
				|| ((obj instanceof String) && (((String) obj)
						.length() == 0))) {
			if (dataType == DataType.STRING)
				return obj;
			return null;
		}
		switch (dataType) {
		case DataType.STRING:
			return parseString(obj);
		case DataType.BOOLEAN:
			return toObject(parseBoolean(obj));
		case DataType.DATE:
		case DataType.TIME:
		case DataType.DATETIME:
			return parseDate(obj);
		case DataType.INT:
			return toObject(parseInt(obj));
		case DataType.DOUBLE:
			return toObject(parseDouble(obj));
		case DataType.LONG:
			return toObject(parseLong(obj));
		case DataType.FLOAT:
			return toObject(parseFloat(obj));
		case DataType.BIGDECIMAL:
			return parseBigDecimal(obj);
		case DataType.BYTE:
			return toObject(parseByte(obj));
		case DataType.SHORT:
			return toObject(parseShort(obj));
		}
		return obj;
	}
}