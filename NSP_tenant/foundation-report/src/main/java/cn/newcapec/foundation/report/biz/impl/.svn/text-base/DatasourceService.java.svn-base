package cn.newcapec.foundation.report.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.report.dao.DatasourceDao;
import cn.newcapec.foundation.report.model.DatasourceEntity;

@Service("dataSourceService")
@Transactional
public class DatasourceService{
	
	@Autowired
	private DatasourceDao dataSourceDao;
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<DatasourceEntity> queryDsByParams(Object[] args,String name){
		return dataSourceDao.getDsByParams(args,name);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int queryDsCountByParams(Object[] args,String name){
		return dataSourceDao.getDsCountByParams(args, name);
	}
	
	
	public void deleteById(String id){
		dataSourceDao.deleteDsById(id);
	}
	
	public void addDatasourceInfo(Object[] args){
		dataSourceDao.addDsInfo(args);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int checkUniqueName(String name){
		return dataSourceDao.checkUniqueName(name);
	}
	
	public void updateDsInfo(Object[] args){
		dataSourceDao.updateDsInfo(args);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<DatasourceEntity> queryAllDsEntity(){
		return dataSourceDao.getAllDsEntity();
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public DatasourceEntity getDsEntityById(String id){
		return dataSourceDao.getDsEntityById(id);
	}
	
	public void setDsDao(DatasourceDao dsDao) {
		this.dataSourceDao = dsDao;
	}
}
