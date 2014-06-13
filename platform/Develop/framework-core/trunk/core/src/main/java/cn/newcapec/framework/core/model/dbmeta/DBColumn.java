package cn.newcapec.framework.core.model.dbmeta;

import java.util.Properties;


/**
 * 
  * @author andy.li
 */
@SuppressWarnings("all")
public class DBColumn {

	private DBTable table;

	private String name;

	private String dataType;

	private int size;

	private int digits;

	private int nullable;

	private String metaData;

	boolean primaryKey;

	DBColumn fkParentKey;

	private String fkPropName;

	private String propName;

	private String javaType;

	private Integer hashCode;

	public DBColumn() {
	}

	public DBColumn(DBTable table, String name, String dataType, int size,
			int digits, int nullable) {
		setName(name);
		setDataType(dataType);
		if (size <= 4096)
			setSize(size);
		setDigits(digits);
		setNullable(nullable);
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public boolean isForeignKey() {
		return fkParentKey != null;
	}

	public DBTable getParentTable() {
		if (fkParentKey == null)
			return null;
		else
			return fkParentKey.getTable();
	}

	protected Properties getProperties() {
		return table.getProperties();
	}

	public void setPropName(String propName) {
		this.propName = propName;
		fkPropName = propName;
	}

	public boolean isNull() {
		return nullable == 1;
	}

	/**
	 * Return true if the type given represents some kind of a numeric value and false if not
	 * @param type
	 */
	private boolean isInteger (String type) {
	    if (null == type) return false;
	    else if (type.equals("int")) return true;
	    else if (type.equals("short")) return true;
	    else if (type.equals("long")) return true;
	    else return false;
	}
	
	/**
	 * Return true if this column's type can be resolved and false if not
	 */
	public boolean isTypeResolved () {
		return (null != TypeResolver.resolveType(getDataType(), false));
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public DBTable getTable() {
		return table;
	}

	public void setTable(DBTable table) {
		this.table = table;
	}

	public String toString() {
		return getName() + " (" + getDataType() + ")";
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DBColumn))
			return false;
		if (getTable().getName() == null || getName() == null)
			return false;
		DBColumn col = (DBColumn) obj;
		if (col.getTable().getName() == null || col.getName() == null)
			return false;
		return col.getTable().getName().equals(getTable().getName())
				&& col.getName().equals(getName());
	}

	public int hashCode() {
		if (hashCode == null) {
			if (getTable().getName() == null || getName() == null)
				return super.hashCode();
			hashCode = new Integer((new String(getTable().getName() + ":"
					+ getName())).hashCode());
		}
		return hashCode.intValue();
	}

	/**
	 * @return the javaType
	 */
	public String getJavaType() {
		if (null == javaType) {
			javaType = TypeResolver.resolveType(getDataType(), true);
			if (null != javaType) {
			    if (isInteger(javaType)) {
			        if (digits > 0) javaType = "bigdecimal";
			    }
			}
			if (null == javaType) javaType = getDataType();
		}
		return javaType;
	}
	
	/**
	 * @return the propName
	 */
	public String getPropName() {
		return propName;
	}
	
	
}
