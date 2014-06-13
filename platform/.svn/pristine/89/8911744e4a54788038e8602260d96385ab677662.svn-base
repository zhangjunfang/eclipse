package cn.newcapec.framework.core.model.variant;

import org.apache.commons.lang.ObjectUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 */
@SuppressWarnings("all")
public class Variant implements Serializable, Cloneable {
	/**
	 * 存储变量的数据类型
	 */
	private int dataType;
	/**
	 * 存储变量名称
	 */
	private String name;
	/**
	 * 存储实际值
	 */
	private Object value;

	public Variant() {
		this.dataType = DataType.UNKNOWN;
	}

	/**
	 * @param dataType 数据类型
	 */
	public Variant(int dataType) {
		this.dataType = dataType;
	}
	public Variant(String name) {
		this.name = name;
	}

	public Variant(String name, int dataType, Object value) {
		this.name = name;
		this.dataType = dataType;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取数据类型
	 * 
	 * @return
	 */
	public int getDataType() {
		return this.dataType;
	}

	public void setDataType(int dataType) {
		if (this.dataType == dataType)
			return;
		this.dataType = dataType;
	}

	public void setDataType(String dataTypeName) {
		setDataType(DataType.nameToType(dataTypeName));
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 按String类型读取数据
	 * 
	 * @return
	 */
	public String getString() {
		return VariantUtil.parseString(this.value);
	}

	/**
	 * 设置String类型的数据
	 * 
	 * @param value
	 */
	public void setString(String value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.STRING;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.STRING) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按Byte类型读取数据
	 * 
	 * @return
	 */
	public byte getByte() {
		return VariantUtil.parseByte(this.value);
	}

	/**
	 * 设置Byte类型数据
	 * 
	 * @param value
	 */
	public void setByte(byte value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.BYTE;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.BYTE) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按Short类型读取数据
	 * 
	 * @return
	 */
	public short getShort() {
		return VariantUtil.parseShort(this.value);
	}

	/**
	 * 设置Short类型数据
	 * 
	 * @param value
	 */
	public void setShort(short value) {
		if (this.dataType == 0)
			this.dataType = DataType.SHORT;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.SHORT) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按int类型读取数据
	 * 
	 * @return
	 */
	public int getInt() {
		return VariantUtil.parseInt(this.value);
	}

	/**
	 * 设置int类型的数据
	 * 
	 * @param value
	 */
	public void setInt(int value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.INT;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.INT) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按long型读取数据
	 * 
	 * @return
	 */
	public long getLong() {
		return VariantUtil.parseLong(this.value);
	}

	/**
	 * 设置long型数据
	 * 
	 * @param value
	 */
	public void setLong(long value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.LONG;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.LONG) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按float读取数据
	 * 
	 * @return
	 */
	public float getFloat() {
		return VariantUtil.parseFloat(this.value);
	}

	/**
	 * 设置float类型数据
	 * 
	 * @param value
	 */
	public void setFloat(float value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.FLOAT;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.FLOAT) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按double读取数据
	 * 
	 * @return
	 */
	public double getDouble() {
		return VariantUtil.parseDouble(this.value);
	}

	/**
	 * 设置double类型数据
	 * 
	 * @param value
	 */
	public void setDouble(double value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.DOUBLE;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.DOUBLE) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按bigdecimal读取数据
	 * 
	 * @return
	 */
	public BigDecimal getBigDecimal() {
		return VariantUtil.parseBigDecimal(this.value);
	}

	/**
	 * 设置bigdecimal类型数据
	 * 
	 * @param value
	 */
	public void setBigDecimal(BigDecimal value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.BIGDECIMAL;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.BIGDECIMAL) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按boolean类型读取数据
	 * 
	 * @return
	 */
	public boolean getBoolean() {
		return VariantUtil.parseBoolean(this.value);
	}

	/**
	 * 设置boolean类型数据
	 * 
	 * @param value
	 */
	public void setBoolean(boolean value) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.BOOLEAN;
		Object obj = VariantUtil.toObject(value);
		if (this.dataType == DataType.BOOLEAN) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 按date类型读取数据
	 * 
	 * @return
	 */
	public Date getDate() {
		return VariantUtil.parseDate(this.value);
	}

	/**
	 * 设置date类型数据
	 * 
	 * @param paramDate
	 */
	public void setDate(Date paramDate) {
		if (this.dataType == DataType.UNKNOWN)
			this.dataType = DataType.DATE;
		Object obj = VariantUtil.toObject(paramDate);
		if ((this.dataType == DataType.DATE) || (this.dataType == DataType.TIME)
				|| (this.dataType == DataType.DATETIME)) {
			this.value = obj;
			return;
		}
		this.value = VariantUtil.translate(this.dataType, obj);
	}

	/**
	 * 判断值是否为空
	 * 
	 * @return
	 */
	public boolean isNull() {
		return (this.value == null);
	}

	/**
	 * 设置值为空
	 * 
	 */
	public void setNull() {
		this.value = null;
	}

	/**
	 * 重写equals
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Variant)
			return ObjectUtils.equals(this.value, ((Variant) obj).getValue());
		return false;
	}

	public int hashCode() {
		if (this.value != null)
			return this.value.hashCode();
		return 0;
	}

	/**
	 * 克隆
	 */
	protected Object clone() throws CloneNotSupportedException {
		Variant variant = (Variant) super.clone();
		variant.setValue(getValue());
		return variant;
	}

	public String toString() {
		if (this.value == null)
			return "Variant {null}";
		return this.value.toString();
	}
}