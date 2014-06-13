package cn.newcapec.foundation.report.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import cn.newcapec.foundation.report.util.StringUtils;

public class ReportObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6072184682199160758L;
	
	private String name;
	private String reportUrl; // 报表浏览的URL，不带参数
	
	private String reportId;
	private String datasetId;
	
	/* 业务参数映射名和具体值 */
	private Map<String, String> businessParams = new HashMap<String, String>();
	
	/* 业务参数映射名和对应的数据库字段名 */
	private Map<String, String> businessMaps = new HashMap<String, String>();

	public String getName() {
		return name;
	}

	public ReportObject setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 返回报表可访问的绝对URL地址
	 * @return
	 */
	public String getReportUrl() {
		return reportUrl;
	}

	/**
	 * 设置报表URL地址，同时会计算报表的访问绝对地址
	 * @param reportUrl 报表系统的根路径，如：http://localhost:8080/webapp/
	 * @return
	 */
	public ReportObject setReportUrl(String reportUrl) {
		StringBuilder _url = new StringBuilder(reportUrl);
//		_url.append("nanoreport/page/reportview.jsp?");
		_url.append("?");
		_url.append("reportId=").append(reportId);
		_url.append("&dc_id=").append(datasetId);
		
		if(businessMaps.size() > 0){
			if(businessParams.size() == 0)
				throw new NullPointerException("businessParams is required.");
			for(Map.Entry<String, String> entry : businessParams.entrySet()){
				_url.append("&");
				_url.append(businessMaps.get(entry.getKey())); // 数据库字段名
				_url.append("=");
				_url.append(entry.getValue());
				
				// 暂时先把映射名和参数也拼接上，供后面使用，有时间再重构
				// 正常来说，应该只传入映射参数名的，因为后面会对应不同的数据集，
				// 例如：报表对应数据集和控件对应的不同数据集，他们定义的业务参数名一样，
				// 但对应的数据字段却不一致
				_url.append("&");
				_url.append(entry.getKey());
				_url.append("=");
				_url.append(entry.getValue());
			}
		}
		this.reportUrl = _url.toString();
		return this;
	}

	public String getReportId() {
		return reportId;
	}

	public ReportObject setReportId(String reportId) {
		this.reportId = reportId;
		return this;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public ReportObject setDatasetId(String datasetId) {
		this.datasetId = datasetId;
		return this;
	}

	/**
	 * 报表中是否包含该名字的参数
	 * @param pname
	 * @return 如果包含返回<code> true </code>
	 */
	public boolean containsMapName(String pname){
		if(businessParams == null || businessParams.size() == 0)
			return false;
		if(businessParams.containsKey(pname))
			return true;
		return false;
	}
	
	/**
	 * 添加一个业务参数：
	 * @param pname 业务的映射参数名
	 * @param pvalue 业务的参数值
	 */
	public void addParameter(String pname, String pvalue){
		if(businessParams.containsKey(pname)){
			businessParams.remove(pname);
			businessParams.put(pname, pvalue);
		} else
			throw new IllegalArgumentException("该参数名未在报表中定义: " + pname);
	}
	
	public void removeParameter(String pname){
		if(businessParams == null)
			throw new IllegalStateException("no parameter found.");
		businessParams.remove(pname);
	}
	
	/**
	 * 从数据库中读出过滤字段值，然后设置到报表对象中，并把字符串解析成多个参数
	 * <pre>
	 * 格式如：prmdpid/DpID,prmdcode/pcode,...
	 * 其中多个以逗号分隔，前面是数据库中对应的字段名，后面是用户业务定义的参数名（映射名）
	 * </pre>
	 * @param val
	 */
	public ReportObject setParameter(String val){
		if(StringUtils.isEmpty(val))
			return this;
		val = val.replace("，", ",").trim();
		String[] pArray = val.split(",");
		if(pArray != null){
			String _dbname, _mapname;
			String[] _names;
			for(String s : pArray){
				_names = s.split("/");
				if(_names != null){
					_dbname = _names[0];
					_mapname= _names[1];
					businessParams.put(_mapname, null);
					businessMaps.put(_mapname, _dbname);
				} else
					throw new IllegalArgumentException("wrong map names: " + s);
			}
			return this;
		} else
			throw new IllegalArgumentException("wrong parameter: " + val
					+ ", e.g. prmdpid/DpID,prmdcode/pcode,...");
	}
	
	public void print(){
		System.out.println(this.businessMaps);
		System.out.println(this.businessParams);
	}
	
	public static void main(String[] args){
		String test = "prmdpid/DpID,prmdcode/pcode";
		ReportObject ro = new ReportObject();
		ro.setParameter(test);
		ro.print();
	}
}
