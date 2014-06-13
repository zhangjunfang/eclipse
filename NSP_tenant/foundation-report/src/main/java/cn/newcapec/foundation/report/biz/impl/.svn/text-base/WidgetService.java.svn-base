package cn.newcapec.foundation.report.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.report.dao.base.WidgetManageDao;
import cn.newcapec.foundation.report.model.WidgetEntity;

@Service("widgetService")
@Transactional
public class WidgetService {
	
	@Autowired
	private WidgetManageDao widgetDao;
	
	/**
	 * 获取控件分页内容
	 * @param args
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<WidgetEntity> getWidgetPaginationByParams(Object[] args){
		return widgetDao.getWidgetByParams(args);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int getWidgetCountByParams(){
		return widgetDao.getWidgetCountByParams();
	}
	/**
	 * 根据主键id删除控件信息
	 * @param id
	 * @return
	 */
	public boolean deleteWidgetInfo(String id){
		return widgetDao.deleteWidgetInfo(id);
	}
	
	public void updateWidgetInfo(Object[] args){
		widgetDao.updateWidgetInfo(args);
	}
	
	public void inserWidgetInfo(Object[] args){
		widgetDao.inserWidgetInfo(args);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<WidgetEntity> queryAllWidgetInfo(){
		return widgetDao.queryAllWidgetInfo();
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<WidgetEntity> queryWidgetByType(String type){
		return widgetDao.queryWidgetByType(type);
	}
	/**
	 * 根据控件名称获取控件信息
	 * @param widget_name
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public WidgetEntity queryWidgetByName(String widget_name){
		return widgetDao.queryWidgetByName(widget_name);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int checkUniqueName(String name){
		return widgetDao.checkUniqueName(name);
	}
	
	public void setWidgetDao(WidgetManageDao widgetDao) {
		this.widgetDao = widgetDao;
	}

}
