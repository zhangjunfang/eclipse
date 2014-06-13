package cn.newcapec.foundation.report.core;

/**
 * 字段信息定义，在SQL语句中描述一个字段的属性
 * @author shikeying
 *
 */
public class Field {

	private String tableName;
	
	private String fieldName;
	private String fieldType;
	
	private String aliasName;
	
	private String comment;
	
	public Field(){}
	
	public Field(String fieldName){
		assert (fieldName != null && !fieldName.equals(""));
		this.fieldName = fieldName;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFieldType() {
		return fieldType;
	}

	/**
	 * 设置字段类型，数据库对应的类型，如：VARCHAR2
	 * @param fieldType
	 * @return
	 */
	public Field setFieldType(String fieldType) {
		this.fieldType = fieldType;
		return this;
	}

	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置子段所在的表名
	 * @param tableName
	 * @return
	 */
	public Field setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置字段名称
	 * @param fieldName
	 * @return
	 */
	public Field setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	public String getAliasName() {
		return aliasName;
	}

	/**
	 * 设置字段别名
	 * @param aliasName
	 * @return
	 */
	public Field setAliasName(String aliasName) {
		this.aliasName = aliasName;
		return this;
	}
	
	public int hashCode(){
		int result = 31;
		if(this.tableName != null && !this.tableName.equals("")){
			return result + this.tableName.hashCode()*13 + this.fieldName.hashCode()*13;
		} else
			return result + this.fieldName.hashCode()*13;
	}
	
	public boolean equals(Object o){
		if(o == null) return false;
		if(o instanceof Field){
			Field f = (Field)o;
			if(f.getTableName() == null
					&& this.tableName != null){
				return false;
			} else if(f.getTableName() != null
					&& this.tableName == null){
				return false;
			} else if(f.getTableName() != null
					&& this.tableName != null){
				if(f.getTableName().equals(this.tableName)
						&& f.getFieldName().equals(this.fieldName)){
					return true;
				}
			} else if(f.getFieldName().equals(this.fieldName)){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		return new StringBuilder().append("{fieldName:").append(fieldName)
				.append(", tableName:").append(tableName)
				.append(", fieldType:").append(fieldType)
				.append(", aliasName:").append(aliasName)
				.append(", comment:").append(comment).append("}").toString();
	}
}
