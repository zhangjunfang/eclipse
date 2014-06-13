package cn.newcapec.foundation.report.biz.impl;

import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.report.core.DataAccessException;
import cn.newcapec.foundation.report.core.Field;
import cn.newcapec.foundation.report.core.ds.DatabaseSource;
import cn.newcapec.foundation.report.core.ds.OraclePooledDatabaseSource;
import cn.newcapec.foundation.report.core.sql.SqlInOutParser;
import cn.newcapec.foundation.report.dao.base.DatasetDao;
import cn.newcapec.foundation.report.model.DatasetEntity;
import cn.newcapec.foundation.report.model.DatasetParams;
import cn.newcapec.foundation.report.model.DatasourceEntity;
import cn.newcapec.foundation.report.util.StringUtils;
import cn.newcapec.framework.base.rest.BaseRequest;

@Service("datasetService")
@Transactional
public class DatasetService{
	
	@Autowired
	private DatasetDao datasetDao;
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<DatasetEntity> queryDatasetByParam(Object[] args ,String name){
		return datasetDao.getDatasetByParams(args, name);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int queryCountByName(String name){
		return datasetDao.getDatasetCountByParams(name);
	}
	public void execDeleteDatasetById(String id){
		datasetDao.deleteDatasetInfo(id);
	}
	public void execInsertDatasetInfo(BaseRequest request,DatasourceService dsservice) throws Exception{
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String filter_field = request.getParameter("filter_field");
		String sql_content = request.getParameter("sql_content");
		String datatype = request.getParameter("datatype");
		String summary = request.getParameter("summary");
		String ds_id = request.getParameter("ds_id");
		String method = request.getParameter("method");
		String fields = request.getParameter("fields");
		String params = request.getParameter("params");
		JSONArray fieldsArray = JSONArray.fromObject("["+fields+"]");
		JSONArray paramsArray = JSONArray.fromObject("["+params+"]");
		String datasourInfoId = UUID.randomUUID().toString();
		String id = request.getParameter("id");
		try {
//			datasetDao.deleteDatasetInfo(id);
			Object[] args = null;
			if("sql".equals(datatype.toLowerCase())){
				args = new Object[]{
						datasourInfoId,filter_field,name,type,summary,
						"",ds_id,"",datatype,sql_content
				};
				datasetDao.insertDatasetInfo(args);
				this.saveSqlParams(ds_id,sql_content,datasourInfoId,dsservice);
			}else{
				args = new Object[]{
						datasourInfoId,filter_field,name,type,summary,
						method,ds_id,"",datatype,""
				};
				boolean b = datasetDao.insertDatasetInfo(args);
				saveProcedureParams(b,fieldsArray,paramsArray,datasourInfoId);
			}
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/**
	 * 保存解析sql语句的字段信息
	 * @param ds_id
	 * @param sql
	 * @param datasourInfoId
	 * @param dsservice
	 */
	private void saveSqlParams(String ds_id,String sql,String datasourInfoId,DatasourceService dsservice){
		try {
			DatasourceEntity dsEntity = dsservice.getDsEntityById(ds_id);
			DatabaseSource ds = new OraclePooledDatabaseSource();
			ds.setAddress(dsEntity.getDsAddress());
			ds.setService(dsEntity.getDsServer());
			ds.setUsername(dsEntity.getDsUser());
			ds.setPassword(dsEntity.getDsPass());
			SqlInOutParser parser = new SqlInOutParser(sql, (DatabaseSource)ds);
			List<Field> fieldList = parser.getOutputParameters();
			if(fieldList != null){
				for(Field field : fieldList){
					String fieldName = field.getFieldName();
					String aliasName = field.getAliasName();
					String type = field.getFieldType().toLowerCase();
					String alias = field.getComment();
					Object[] fieldargs = new Object[]{
							UUID.randomUUID().toString(),datasourInfoId
							,StringUtils.isNotEmpty(aliasName) ? aliasName : fieldName,1,type,alias
					};
					datasetDao.insertFieldsAndParams(fieldargs);
				}
			}
		} catch (DataAccessException e) {
			throw e;
		}
		
	};
	/**
	 * 保存解析存储过程语句的输入、输出参数
	 * @param ds_id
	 * @param sql
	 * @param datasourInfoId
	 * @param dsservice
	 */
	private void saveProcedureParams(boolean b,JSONArray fieldsArray, JSONArray paramsArray,String datasourInfoId){
		if(b && fieldsArray.size()>0){
			for(int i=0;i<fieldsArray.size();i++){
				JSONObject object = fieldsArray.getJSONObject(i);
				String fieldsname = object.getString("name");
				String fieldstype = object.getString("type");
				String fieldsalias = object.getString("alias");
				Object[] fieldargs = new Object[]{
						UUID.randomUUID().toString(),datasourInfoId,fieldsname,1,fieldstype,fieldsalias
				};
				datasetDao.insertFieldsAndParams(fieldargs);
			}
		}
		if(b && paramsArray.size()>0){
			for(int i=0;i<paramsArray.size();i++){
				JSONObject object = paramsArray.getJSONObject(i);
				String fieldsname = object.getString("name");
				String fieldstype = object.getString("type");
				Object[] fieldargs = new Object[]{
						UUID.randomUUID().toString(),datasourInfoId,fieldsname,0,fieldstype,""
				};
				datasetDao.insertFieldsAndParams(fieldargs);
			}
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<DatasetParams> queryParmasByDsId(String dataset_id,String type){
		return datasetDao.getParmasByDsId(dataset_id,type);
	}
	
	/**
	 * 返回报表要返回的所有字段数据（游标定义的），同时包含每个列的别名。
	 * @param reportId
	 * @param dcId
	 * @return
	 * @author shikeying
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<DatasetParams> queryParamsFields(String reportId, String dcId){
		assert (StringUtils.isNotEmpty(reportId));
		assert (StringUtils.isNotEmpty(dcId));
		return datasetDao.getReportParameterFields(reportId, dcId);
	}
	
	public void execUpdateDsInfo(BaseRequest request,DatasourceService dsservice) throws Exception{
		String id = request.getParameter("id");
		String filter_field = request.getParameter("filter_field");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String datatype = request.getParameter("datatype");
		String summary = request.getParameter("summary");
		String ds_id = request.getParameter("ds_id");
		String method = request.getParameter("method");
		String fields = request.getParameter("fields");
		String params = request.getParameter("params");
		String sql_content = request.getParameter("sql_content");
		JSONArray fieldsArray = JSONArray.fromObject("["+fields+"]");
		Object[] args = new Object[]{
			name,type,summary,method,
			ds_id,"",sql_content,datatype,filter_field,id
		};
		try {
			boolean b = datasetDao.updateDsInfo(args);
			datasetDao.deleteParamsByDcId(id);
			if("sql".equals(datatype.toLowerCase())){
				this.saveSqlParams(ds_id, sql_content, id,dsservice);
			}else{
				if(b && fieldsArray.size()>0){
					for(int i=0;i<fieldsArray.size();i++){
						JSONObject object = fieldsArray.getJSONObject(i);
						String fieldsname = object.getString("name");
						String fieldstype = object.getString("type");
						String fieldsalias = object.getString("alias");
						Object[] fieldargs = new Object[]{
								UUID.randomUUID().toString(),id,
								fieldsname,1,fieldstype,fieldsalias
						};
						datasetDao.insertFieldsAndParams(fieldargs);
					}
				}
				JSONArray paramsArray = JSONArray.fromObject("["+params+"]");
				if(b && paramsArray.size()>0){
					for(int i=0;i<paramsArray.size();i++){
						JSONObject object = paramsArray.getJSONObject(i);
						String fieldsname = object.getString("name");
						String fieldstype = object.getString("type");
						Object[] fieldargs = new Object[]{
								UUID.randomUUID().toString(),id,fieldsname,
								0,fieldstype,""
						};
						datasetDao.insertFieldsAndParams(fieldargs);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	public void execDeleteParamsByDcId(String id){
		datasetDao.deleteParamsByDcId(id);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int checkUniqueName(String name){
		return datasetDao.checkUniqueName(name);
	}
	/**
	 * 获取所有数据集信息
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<DatasetEntity> getAllDatasetInfo(){
		return datasetDao.getAllDatasetInfo();
	}
	/**
	 * 根据数据集类型查询对应的数据，如：报表、控件
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<DatasetEntity> getDatasetByType(String type){
		return datasetDao.getDatasetByType(type);
	}
	/**
	 * 根据id获取数据集实体信息
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public DatasetEntity getDatasetInfoById(String id){
		return datasetDao.getDatasetInfoById(id);
	}
	public void setDatasetDao(DatasetDao datasetDao) {
		this.datasetDao = datasetDao;
	}
	
}
