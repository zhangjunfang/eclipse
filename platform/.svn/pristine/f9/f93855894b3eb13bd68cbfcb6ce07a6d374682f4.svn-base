package cn.newcapec.framework.core.model.variant;

import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.utils.listUtils.ObjectCollection;
import cn.newcapec.framework.core.utils.listUtils.ObjectList;
import org.apache.commons.lang.ClassUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */
public class VariantSet implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6329100785977056907L;
	private ObjectCollection data = new ObjectList();

	/**
	 * 根据键值读取对应的数据类型
	 * 
	 * @param key
	 * @return
	 */
	private Variant doGetVariant(String key) {
		Variant variant;
		if ((variant = getVariant(key)) == null) {
			variant = new Variant(key);
			setVariant(key, variant);
		}
		return variant;
	}

	/**
	 * 校验index的合法性
	 * 
	 * @param index
	 */
	private void checkIndexValid(int index) {
		if ((index < 0) || (index >= this.data.size()))
			throw new ArrayIndexOutOfBoundsException(index);
	}

	private void checkKeyValid(String name) {
		if (!(exists(name)))
			throw new BaseException("Variant '" + name + "' does not exist!");
	}

	/**
	 * 判断给定键值下是否存有数值.
	 * 
	 * @param name
	 * @return
	 */
	public boolean exists(String name) {
		return (getVariant(name) != null);
	}

	/**
	 * 将本集合中的所有数据全部指派到给定的另一个集合中.
	 * 
	 * @param variantSet
	 */
	public void assign(VariantSet variantSet) {
		for (int j = 0, len = variantSet.count(); j < len; ++j) {
			String str = variantSet.indexToName(j);
			if (!(exists(str)))
				setDataType(str, variantSet.getDataType(j));
			setValue(str, variantSet.getValue(j));
		}
	}

	public void clear() {
		this.data.removeAll();
	}

	/**
	 * 根据索引号并按Variant类型读取数据
	 * 
	 * @param index
	 * @return
	 */

	public Variant getVariant(int index) {
		return ((Variant) this.data.get(index));
	}

	/**
	 * 根据键值并按Object类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public Variant getVariant(String name) {
		return ((Variant) this.data.get(name));
	}

	/**
	 * 根据键值返回默认数据类型
	 * 
	 * @param index
	 * @return
	 */
	public int getDataType(int index) {
		checkIndexValid(index);
		return getVariant(index).getDataType();
	}

	/**
	 * 根据索引号设置默认数据类型
	 * 
	 * @param index
	 * @param dataType
	 */
	public void setDataType(int index, int dataType) {
		checkIndexValid(index);
		getVariant(index).setDataType(dataType);
	}

	/**
	 * 据索引号设置默认数据类型
	 * 
	 * @param index
	 * @param dataTypeName
	 */
	public void setDataType(int index, String dataTypeName) {
		checkIndexValid(index);
		getVariant(index).setDataType(dataTypeName);
	}

	/**
	 * 根据键值返回默认数据类型
	 * 
	 * @param name
	 * @return
	 */
	public int getDataType(String name) {
		checkKeyValid(name);
		return getVariant(name).getDataType();
	}

	/**
	 * 根据键值设置默认数据类型
	 * 
	 * @param name
	 * @param dataType
	 */
	public void setDataType(String name, int dataType) {
		doGetVariant(name).setDataType(dataType);
	}

	/**
	 * 根据键值设置默认数据类型
	 * 
	 * @param name
	 * @param dataTypeName
	 */
	public void setDataType(String name, String dataTypeName) {
		doGetVariant(name).setDataType(dataTypeName);
	}

	/**
	 * 根据索引号并按Object类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public Object getValue(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getValue();
		return null;
	}

	/**
	 * 
	 * 根据索引号并按BigDecimel类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public BigDecimal getBigDecimal(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getBigDecimal();
		return null;
	}

	/**
	 * 
	 * 根据索引号并按Boolean类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public boolean getBoolean(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getBoolean();
		return false;
	}

	/**
	 * 
	 * 根据索引号并按Byte类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public byte getByte(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getByte();
		return 0;
	}

	/**
	 * 根据索引号并按Date类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public Date getDate(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getDate();
		return null;
	}

	/**
	 * 
	 * 根据索引号并按double类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public double getDouble(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getDouble();
		return 0D;
	}

	/**
	 * 根据索引号并按float类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public float getFloat(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getFloat();
		return 0F;
	}

	/**
	 * 
	 * 根据索引号并按int类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public int getInt(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getInt();
		return 0;
	}

	/**
	 * 根据索引号并按long类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public long getLong(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getLong();
		return 0L;
	}

	/**
	 * 
	 * 根据索引号并按short类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public short getShort(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getShort();
		return 0;
	}

	/**
	 * 根据索引号并按String类型读取数据
	 * 
	 * @param index
	 * @return
	 */
	public String getString(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.getString();
		return null;
	}

	/**
	 * 
	 * 根据索引号设置Object类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setValue(int index, Object value) {
		checkIndexValid(index);
		getVariant(index).setValue(value);
	}

	/**
	 * 
	 * 根据索引号设置bigdecimal类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setBigDecimal(int index, BigDecimal value) {
		checkIndexValid(index);
		getVariant(index).setBigDecimal(value);
	}

	/**
	 * 根据索引号设置boolean类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setBoolean(int index, boolean value) {
		checkIndexValid(index);
		getVariant(index).setBoolean(value);
	}

	/**
	 * 根据索引号设置byte类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setByte(int index, byte value) {
		checkIndexValid(index);
		getVariant(index).setByte(value);
	}

	/**
	 * 根据索引号设置date类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setDate(int index, Date value) {
		checkIndexValid(index);
		getVariant(index).setDate(value);
	}

	/**
	 * 根据索引号设置double类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setDouble(int index, double value) {
		checkIndexValid(index);
		getVariant(index).setDouble(value);
	}

	/**
	 * 根据索引号设置float类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setFloat(int index, float value) {
		checkIndexValid(index);
		getVariant(index).setFloat(value);
	}

	/**
	 * 根据索引号设置int类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setInt(int index, int value) {
		checkIndexValid(index);
		getVariant(index).setInt(value);
	}

	/**
	 * 根据索引号设置long类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setLong(int index, long value) {
		checkIndexValid(index);
		getVariant(index).setLong(value);
	}

	/**
	 * 根据索引号设置short类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setShort(int index, short value) {
		checkIndexValid(index);
		getVariant(index).setShort(value);
	}

	/**
	 * 根据索引号设置string类型的数据
	 * 
	 * @param index
	 * @param value
	 */
	public void setString(int index, String value) {
		checkIndexValid(index);
		getVariant(index).setString(value);
	}

	/**
	 * 
	 * 根据键值并按Object类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public Object getValue(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getValue();
		return null;
	}

	/**
	 * 根据键值并按BigDecimal类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public BigDecimal getBigDecimal(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getBigDecimal();
		return null;
	}

	/**
	 * 根据键值并按Boolean类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public boolean getBoolean(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getBoolean();
		return false;
	}

	/**
	 * 根据键值并按Byte类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public byte getByte(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getByte();
		return 0;
	}

	/**
	 * 根据键值并按Date类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public Date getDate(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getDate();
		return null;
	}

	/**
	 * 根据键值并按double类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public double getDouble(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getDouble();
		return 0D;
	}

	/**
	 * 根据键值并按float类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public float getFloat(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getFloat();
		return 0F;
	}

	/**
	 * 根据键值并按int类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public int getInt(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getInt();
		return 0;
	}

	/**
	 * 根据键值并按long类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public long getLong(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getLong();
		return 0L;
	}

	/**
	 * 根据键值并按Short类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public short getShort(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getShort();
		return 0;
	}

	/**
	 * 根据键值并按String类型读取数据
	 * 
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.getString();
		return null;
	}

	/**
	 * 根据键值设置Variant类型的数据
	 * 
	 * @param name
	 * @param variant
	 */
	public void setVariant(String name, Variant variant) {
		this.data.forceAdd(name, variant);
	}

	/**
	 * 
	 * 根据键值设置Object类型的数据
	 * 
	 * @param name
	 * @param object
	 */
	public void setValue(String name, Object object) {
		doGetVariant(name).setValue(object);
	}

	/**
	 * 根据键值设置BigDecimal类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setBigDecimal(String name, BigDecimal value) {
		doGetVariant(name).setBigDecimal(value);
	}

	/**
	 * 根据键值设置boolean类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setBoolean(String name, boolean value) {
		doGetVariant(name).setBoolean(value);
	}

	/**
	 * 根据键值设置byte类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setByte(String name, byte value) {
		doGetVariant(name).setByte(value);
	}

	/**
	 * 根据键值设置date类型的数据
	 * 
	 * @param name
	 * @param date
	 */
	public void setDate(String name, Date date) {
		doGetVariant(name).setDate(date);
	}

	/**
	 * 根据键值设置double类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setDouble(String name, double value) {
		doGetVariant(name).setDouble(value);
	}

	/**
	 * 根据键值设置float类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setFloat(String name, float value) {
		doGetVariant(name).setFloat(value);
	}

	/**
	 * 
	 * 根据键值设置int类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setInt(String name, int value) {
		doGetVariant(name).setInt(value);
	}

	/**
	 * 根据键值设置long类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setLong(String name, long value) {
		doGetVariant(name).setLong(value);
	}

	/**
	 * 根据键值设置short类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setShort(String name, short value) {
		doGetVariant(name).setShort(value);
	}

	/**
	 * 根据键值设置String类型的数据
	 * 
	 * @param name
	 * @param value
	 */
	public void setString(String name, String value) {
		doGetVariant(name).setString(value);
	}

	/**
	 * 根据索引号判断数据是否为空
	 * 
	 * @param index
	 * @return
	 */
	public boolean isNull(int index) {
		Variant variant;
		if ((variant = getVariant(index)) != null)
			return variant.isNull();
		return true;
	}

	/**
	 * 根据索引号设置数据为NULL
	 * 
	 * @param index
	 */
	public void setNull(int index) {
		checkIndexValid(index);
		getVariant(index).setNull();
	}

	/**
	 * 根据键值判断数据是否为空
	 * 
	 * @param name
	 * @return
	 */
	public boolean isNull(String name) {
		Variant variant;
		if ((variant = getVariant(name)) != null)
			return variant.isNull();
		return true;
	}

	/**
	 * 设置给定键值下的数值为空(null)
	 * 
	 * @param name
	 */
	public void setNull(String name) {
		doGetVariant(name).setNull();
	}

	/**
	 * 删除记录
	 * 
	 * @param index
	 */
	public void remove(int index) {
		this.data.remove(index);
	}

	/**
	 * 删除记录
	 * 
	 * @param name
	 */
	public void remove(String name) {
		this.data.remove(name);
	}

	/**
	 * 获取记录数
	 * 
	 * @return
	 */
	public int count() {
		return this.data.size();
	}

	/**
	 * 根据位置获取主键名称
	 * 
	 * @param index
	 * @return
	 */
	public String indexToName(int index) {
		return ((String) this.data.getKey(index));
	}

	/**
	 * 克隆
	 */
	public Object clone() throws CloneNotSupportedException {
		VariantSet variantSet = (VariantSet) super.clone();
		ObjectList objectList = new ObjectList();
		for (int i = 0, len = this.data.size(); i < len; ++i)
			objectList.add(this.data.getKey(i), ((Variant) this.data.get(i)).clone());
		variantSet.data = objectList;
		return variantSet;
	}

	/**
	 * 重写equals
	 */
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object instanceof VariantSet)
			return this.data.equals(((VariantSet) object).data);
		return false;
	}

	public int hashCode() {
		return this.data.hashCode();
	}

	public String toString() {
		StringBuffer localStringBuffer;
		localStringBuffer = new StringBuffer(ClassUtils.getShortClassName(super.getClass())).append(":").append(
				this.data.toString());
		return localStringBuffer.toString();
	}

	/**
	 * 获取键列表
	 * 
	 * @return
	 */
	public Object[] keyList() {
		return data.keyList().toArray();
	}
}