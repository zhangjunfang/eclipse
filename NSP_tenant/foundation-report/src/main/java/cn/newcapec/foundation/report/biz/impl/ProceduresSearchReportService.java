package cn.newcapec.foundation.report.biz.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.newcapec.foundation.report.biz.SearchReportDataService;
import cn.newcapec.foundation.report.core.DataAccessException;
import cn.newcapec.foundation.report.core.ds.DataSource;
import cn.newcapec.foundation.report.core.ds.DatabaseSource;
import cn.newcapec.foundation.report.core.ds.OraclePooledDatabaseSource;
import cn.newcapec.foundation.report.dao.DatasourceDao;
import cn.newcapec.foundation.report.dao.base.DatasetDao;
import cn.newcapec.foundation.report.dao.base.ReportManageDao;
import cn.newcapec.foundation.report.model.DatasetEntity;
import cn.newcapec.foundation.report.model.DatasetParams;
import cn.newcapec.foundation.report.model.DatasourceEntity;
import cn.newcapec.foundation.report.model.ReportManageEntity;
import cn.newcapec.foundation.report.util.StringUtils;

/**
 * 查询报表数据的 存储过程方式 实现
 * @author shikeying
 * @date 2013-9-23
 *
 */
@Service("proceduresSearchReportService")
public class ProceduresSearchReportService implements SearchReportDataService {

	private Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private DatasetDao datasetDao;
	
	@Autowired
	private DatasourceDao dataSourceDao;
	
	@Autowired
	private ReportManageDao reportManageDao;
	
	private DatabaseSource databaseSource;
	
	@Override
	public List<Map<String, Object>> getReportResult(String reportId,
			Object reportArguments) {
		// TODO Auto-generated method stub
		String dcId = null;
		ReportManageEntity reportEntity = reportManageDao.getReportEntityById(reportId);
		if(reportEntity == null){
			logger.info("not found report entity, reportId = " + reportId);
			return null;
		}
		
		dcId = reportEntity.getDc_id();
		
		// 报表数据集
		DatasetEntity datasetEntity = null;
		try{
			datasetEntity = datasetDao.getDatasetInfoById(dcId);
		} catch(Exception e){
			throw new DataAccessException("查询数据集出现错误", e);
		}
		
		// 报表数据源
		DatasourceEntity dsEntity = null;
		try{
			dsEntity = dataSourceDao.getDsEntityById(datasetEntity.getDs_id());
		} catch(Exception e){
			throw new DataAccessException("查询报表数据源出现错误", e);
		}
		
		if(databaseSource == null){
			DataSource queryDs = getDataSource();
			if(!DatabaseSource.class.isAssignableFrom(queryDs.getClass())){
				throw new DataAccessException("只能配置DatabaseSource对象，而不是: " + DataSource.class.getName());
			} else
				databaseSource = (DatabaseSource)queryDs;
			databaseSource.setAddress(dsEntity.getDsAddress());
			databaseSource.setService(dsEntity.getDsServer());
			databaseSource.setUsername(dsEntity.getDsUser());
			databaseSource.setPassword(dsEntity.getDsPass());
		}
		
		//
		JSONObject arguments = JSONObject.fromObject(reportArguments.toString());
		ResultSet rs = executeProcedure(datasetEntity.getMethod(), databaseSource, arguments);
		return getMapResult(reportId, dcId, rs);
	}
	
	private List<Map<String, Object>> getMapResult(String reportId, String dcId
			, ResultSet rs){
		if(rs == null) return null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			List<DatasetParams> parmsList = datasetDao.getReportParameterFields(reportId, dcId);
			while (rs.next()) {
                Map<String, Object> record = new HashMap<String, Object>(8);
                String _value = null;
                for (DatasetParams params : parmsList) {
                	_value = rs.getString(params.getName());
                	record.put(params.getFieldTitle(), StringUtils.isEmpty(_value) ? "" : _value);
                }
                list.add(record);
            }
			return list;
		} catch(Exception ex){
			throw new DataAccessException("转换ResultSet发生错误", ex);
		}
	}
	
	private ResultSet executeProcedure(String datasetMethod
			, DatabaseSource ds, JSONObject arguments){
		//获取存储过程名字
		String procedureName = this.getProcedureName(datasetMethod);
		Connection conn = ds.getConnection();
		//根据存储过程获取游标、输入
		List<Map> listMap =  queryParams(conn,procedureName);
		StringBuffer procedureString = new StringBuffer();
		procedureString.append("{ call "+ datasetMethod +" (");
		int outMsgPosition = 0;//游标位置,现在假设只支持一个游标返回值
		for(Map map : listMap){
			StringBuffer procedureBuffer = new StringBuffer();
			String type = map.get("data_type").toString().toLowerCase();
			int position = Integer.parseInt( map.get("position").toString());
			String in_out = map.get("in_out").toString().toLowerCase();
			if(in_out.equals("out") && type.equals("ref cursor")){
				outMsgPosition = position;
			}
			String procedure = "";
			if(position == 1){
				procedure = parseTypeForParam(type,procedureBuffer,arguments,map);	
			}else{
				procedure =","+ parseTypeForParam(type,procedureBuffer,arguments,map);
			}
			procedureString.append(procedure);
		}
		procedureString.append(")}");
		try{
			CallableStatement proc = conn.prepareCall(procedureString.toString());
			if(outMsgPosition != 0){
				proc.registerOutParameter(1,oracle.jdbc.OracleTypes.CURSOR);
			}
			proc.execute();
			return (ResultSet) proc.getObject(1);
		} catch(SQLException ex){
			throw new DataAccessException("执行存储过程发生错误", ex);
		}
	}
	
	/**
	 * 获取存储过程的名称
	 * @param express
	 * @return
	 */
	private String getProcedureName(String express){
		String procedureName = null;
		if(express.indexOf(".") >= 0){
			String[] args = express.trim().split("\\.");
			if(args.length == 2){
				procedureName = args[1];
			} else
				throw new IllegalArgumentException("input error: " + express);
		} else {
			procedureName = express.trim();
		}
		return procedureName;
	}
	
	/**
	 * 根据存储过程名称获取输入、输出参数以及类型
	 * @param conn
	 * @param procedureName
	 * @return
	 */
	private List<Map> queryParams(Connection conn,String procedureName){
		try {
			String sql = "select * from user_arguments where object_name = ? order by position asc";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, procedureName.toUpperCase());
			ResultSet rs = pst.executeQuery();
			List<Map> list = new ArrayList<Map>(); 
			while(rs.next()){
				Map map = new HashMap();
				map.put("argument_name", rs.getString("argument_name").toLowerCase());
				map.put("data_type", rs.getString("data_type").toLowerCase());
				map.put("in_out", rs.getString("in_out").toLowerCase());
				map.put("position", rs.getString("position").toLowerCase());
				list.add(map);
			}
			pst.close();
			rs.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("查询存储过程输入参数错误", e);
		}
	}
	
	private String parseTypeForParam(String type,StringBuffer procedureString,JSONObject object,Map map){
		if(type.equals("number")){
			procedureString.append(object.getInt(map.get("argument_name").toString()));
		}else if(type.equals("ref cursor")){
			procedureString.append("?");
		}else{
			procedureString.append("'"+object.getString(map.get("argument_name").toString())+"'");
		}
		return procedureString.toString();
	}

	@Override
	public List<Map<String, Object>> getPagedReportResult(String reportId,
			Object reportArguments, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not implement.");
	}

	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		return new OraclePooledDatabaseSource();
	}

}
