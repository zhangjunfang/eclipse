package cn.newcapec.foundation.report.biz.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.report.dao.base.ReportManageDao;
import cn.newcapec.foundation.report.model.ReportFieldsEntity;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.foundation.report.service.ReportObject;
import cn.newcapec.foundation.report.util.StringTools;
import cn.newcapec.framework.utils.Page;

@Service("reportManageService")
@Transactional
public class ReportManageService{
	
	@Autowired
	private ReportManageDao reportManageDao;
	
	@Autowired
	private cn.newcapec.foundation.report.dao.ReportManageDao reportManageDaoNew;
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<ReportManageEntity> queryReportByParam(Object[] args ,String name){
		return reportManageDao.getReportByParams(args, name);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int queryCountByName(String name){
		return reportManageDao.getReportCountByParams(name);
	}
	/**
	 * 增加报表基本信息
	 * @param args
	 */
	public boolean inserReportInfo(Object[] args){
		return reportManageDao.inserReportInfo(args);
	}
	/**
	 * 更新报表基本信息
	 * @param args
	 */
	public boolean updateReportInfo(Object[] args){
		return reportManageDao.updateReportInfo(args);
	}
	/**
	 * 修改报表发布状态
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateReportState(String id,int state){
		return reportManageDao.updateReportState(id, state);
	}
	/**
	 * 删除报表信息
	 * @param args
	 */
	public void deleteReportInfo(String id){
		reportManageDao.deleteReportInfo(id);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public ReportManageEntity getReportEntityById(String id){
		return reportManageDao.getReportEntityById(id);
	}
	/**
	 * 根据主键id更新报表模版
	 * @param args
	 */
	public boolean updateReportTemplate(String content,String id){
		return reportManageDao.updateReportTemplate(content, id);
	}
	/**
	 * 根据报表名称检查唯一性
	 * @param args
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int checkUniqueName(String name){
		return reportManageDao.checkUniqueName(name);
	}
	/**
	 * 添加报表字段信息
	 * @param args
	 * @return
	 */
	public boolean insertReportFieldsInfo(Object[] args){
		return reportManageDao.insertReportFieldsInfo(args);
	}
	/**
	 * 查询所有报表信息
	 * @param args
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<ReportManageEntity> queryAllReportInfo(){
		return reportManageDao.queryAllReportInfo();
	}
	/**
	 * 根据报表id查询报表字段信息
	 * @param reportId
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<ReportFieldsEntity> queryReportFieldsList(String reportId){
		return reportManageDao.getReportFieldsList(reportId);
	}
	
	/**
	 * 根据报表id查询报表展示字段信息
	 * @param reportId
	 * @return
	 */
	public boolean deleteReportFields(String reportId){
		return reportManageDao.deleteReportFields(reportId);
	}
	/**
	 * 查询已经发布的报表
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<ReportManageEntity> queryPublishReportInfo(){
		return reportManageDao.queryPublishReportInfo();
	}
	/**
	 * 根据报表id获取报表内容
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<ReportManageEntity> queryPublishReportContent(String id){
		return reportManageDao.queryPublishReportContent(id);
	}
	
	/**
	 * 返回用户对应的所有报表对象集合
	 * @param identifier
	 * @return
	 * @author shikeying
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<ReportObject> queryReportObjectList(String identifier){
		return reportManageDao.getReportObjectList();
	}
	
	/**
	 * 返回数据集对应的过滤字段信息，并组织成Map对象，</br>
	 * map中key=数据库字段名, value=业务映射名称
	 * @param dcId
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Map<String, String> queryReportFilters(String dcId){
		String filters = reportManageDao.getReportFilterField(dcId);
		if(filters == null) return null;
		return StringTools.transStringToMap(filters, ",", "/");
	}
	
	public void setReportManageDao(ReportManageDao reportManageDao) {
		this.reportManageDao = reportManageDao;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 使用系统框架，编写符合规范的方法。
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public Page queryReports(Map<String, Object> params,LinkedHashMap<String, String> orderby){
		return reportManageDaoNew.queryReports(params, orderby);
	}
}
