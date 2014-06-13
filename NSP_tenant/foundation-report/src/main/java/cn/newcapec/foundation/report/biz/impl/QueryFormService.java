package cn.newcapec.foundation.report.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.report.dao.base.QueryFormDao;
import cn.newcapec.foundation.report.model.QueryFormEntity;

@Service("queryFormService")
@Transactional
public class QueryFormService{
	@Autowired
	private QueryFormDao queryFormDao;
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<QueryFormEntity> queryQueryFormByParams(Object[] args,String reportId){
		return queryFormDao.getQueryFormByParams(args,reportId);
	}
	
	/**
	 * 计算表单控件总数
	 * @param reportId
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int queryDsCountByParams(String reportId){
		return queryFormDao.getQueryFormCount(reportId);
	}
	
	/**
	 * 根据报表id查询表单信息
	 * @param reportId
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<QueryFormEntity> queryFormByReportId(String reportId){
		return queryFormDao.getQueryFormByReportId(reportId);
	}
	
	public boolean insertQueryFormInfo(Object[] args){
		return queryFormDao.insertQueryFormInfo(args);
	}
	
	public boolean deleteFormById(String report_id){
		return queryFormDao.deleteFormById(report_id);
	}
	
	public boolean updateQueryFormInfo(Object[] args){
		return queryFormDao.updateQueryFormInfo(args);
	}
//	public void deleteById(String id){
//		dsDao.deleteDsById(id);
//	}
//	
//	public void addDatasourceInfo(Object[] args){
//		dsDao.addDsInfo(args);
//	}
//	
//	public int checkUniqueName(String name){
//		return dsDao.checkUniqueName(name);
//	}
//	
//	public void updateDsInfo(Object[] args){
//		dsDao.updateDsInfo(args);
//	}
//	
//	public List<DatasourceEntity> queryAllDsEntity(){
//		return dsDao.getAllDsEntity();
//	}
//	
//	public DatasourceEntity getDsEntityById(String id){
//		return dsDao.getDsEntityById(id);
//	}

	public void setQueryFormDao(QueryFormDao queryFormDao) {
		this.queryFormDao = queryFormDao;
	}
	
}
