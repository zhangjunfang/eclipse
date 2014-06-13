package cn.newcapec.foundation.report.model;

public class WidgetEntity {
	
	private String id;
	private String textField;
	private String valueField;
	private String parentField;
	private String topDefault;
	private String widgetType;
	private String name;
	private String summary;
	private String userId;
	private String dc_id;//数据集id
	private String dcname;//数据集名称
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTextField() {
		return textField;
	}
	public void setTextField(String textField) {
		this.textField = textField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public String getParentField() {
		return parentField;
	}
	public void setParentField(String parentField) {
		this.parentField = parentField;
	}
	public String getTopDefault() {
		return topDefault;
	}
	public void setTopDefault(String topDefault) {
		this.topDefault = topDefault;
	}
	public String getWidgetType() {
		return widgetType;
	}
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDc_id() {
		return dc_id;
	}
	public void setDc_id(String dc_id) {
		this.dc_id = dc_id;
	}
	public String getDcname() {
		return dcname;
	}
	public void setDcname(String dcname) {
		this.dcname = dcname;
	}
	
}
