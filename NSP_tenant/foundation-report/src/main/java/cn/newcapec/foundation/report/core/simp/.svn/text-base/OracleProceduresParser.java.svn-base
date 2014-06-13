package cn.newcapec.foundation.report.core.simp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.newcapec.foundation.report.core.DataTypeAdapter;
import cn.newcapec.foundation.report.core.ProceduresMetaLoader;
import cn.newcapec.foundation.report.core.ProceduresParser;
import cn.newcapec.foundation.report.core.ds.DataSource;
import cn.newcapec.foundation.report.core.ds.DefaultDatabaseSource;
import cn.newcapec.foundation.report.core.parameter.Parameter;
import cn.newcapec.foundation.report.core.sql.SqlMetaLoader;
import cn.newcapec.foundation.report.core.sql.simp.OracleSqlMetaLoader;
import cn.newcapec.foundation.report.util.StringUtils;

/**
 * 基于ORACLE的存储过程参数列表解析器实现
 * @author shikeying
 * @date 2013-7-17
 *
 */
public class OracleProceduresParser extends AbstractProceduresParser {

	private static final String ORACLE_PARAMETER_IN = "IN";
	private static final String ORACLE_PARAMETER_OUT = "OUT";
	private static final String ORACLE_PARAMETER_INOUT = "IN/OUT";
	private static final String QUERY_PARAMETERS_BYNAME1="select * from user_arguments where object_name=?";
	private static final String QUERY_PARAMETERS_BYNAME2="select * from user_arguments where object_name=? and package_name=?";
	@Override
	protected List<String[]> getParameterList(String content, Connection conn,
			String packageName, String procedureName, String cursorTypeName) {
		// TODO Auto-generated method stub
		logger.debug("start search parameter in procedure '" + procedureName + "'.");
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// test data
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println(content);
		List<String[]> datas = this.procedureParams(conn, packageName, procedureName, cursorTypeName);
		
//		datas.add(new String[]{"prmbegindate","IN","VARCHAR2"});
//		datas.add(new String[]{"prmenddate","IN","VARCHAR2"});
//		datas.add(new String[]{"prmcode","IN","VARCHAR2"});
//		datas.add(new String[]{"prmcodetype","IN","NUMBER"});
//		datas.add(new String[]{"out_cursor","OUT","REF CURSOR"});
		
		return datas;
	}
	
	/**
	 * 根据存储过程名称查询当前存储过程的输入输出参数、类型等信息
	 * @param conn
	 * @param procedureName
	 * @return
	 */
	private List<String[]> procedureParams(Connection conn
			,String packageName, String procedureName,String cursorTypeName){
		
		try {
			List<String[]> datas = new ArrayList<String[]>();
			PreparedStatement ps = null;
			if(packageName == null){
				ps = conn.prepareStatement(QUERY_PARAMETERS_BYNAME1);
				ps.setString(1, procedureName.toUpperCase());
			} else {
				ps = conn.prepareStatement(QUERY_PARAMETERS_BYNAME2);
				ps.setString(1, procedureName.toUpperCase());
				ps.setString(2, packageName.toUpperCase());
			}
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String parameterName = rs .getString("argument_name");
				String in_out = rs.getString("in_out");
				String dataType = rs.getString("data_type");
				if(StringUtils.isNotEmpty(dataType)){
					if(dataType.toUpperCase().equals("REF CURSOR")) dataType = cursorTypeName.toUpperCase();
					datas.add(new String[]{parameterName,in_out,dataType});
					
				} else {
					logger.debug("xxx 未找到数据类型: " + parameterName);
					continue;
				}
			}
			rs.close();
			ps.close();
			return datas;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	@Override
	protected String getParameterInKey() {
		// TODO Auto-generated method stub
		return ORACLE_PARAMETER_IN;
	}

	@Override
	protected String getParameterOutKey() {
		// TODO Auto-generated method stub
		return ORACLE_PARAMETER_OUT;
	}
	
	@Override
	protected String getParameterInOutKey(){
		return ORACLE_PARAMETER_INOUT;
	}

	public static void main(String[] args){
		DataSource ds = new DefaultDatabaseSource();
		ProceduresMetaLoader procedureLoader = new OracleProceduresMetaLoader();
		procedureLoader.setDataSource(ds);
		procedureLoader.setMetaName("pkg_rpt_balance.balance_system");
		Object procedureContent = procedureLoader.getProcedure();
//		System.out.println("=============== procedure ================");
//		System.out.println(procedureContent);
		System.out.println("############## " + procedureLoader.getCursorTypeName());
		
		SqlMetaLoader sqlMetaloader = new OracleSqlMetaLoader();
		sqlMetaloader.setDataSource(ds);
		
//		SqlFieldExtractor sfe = new OracleSqlFieldExtractor();
//		sfe.setSqlMetaLoader(sqlMetaloader);
		
		ProceduresParser parser = new OracleProceduresParser();
		ProceduresMetaLoader loader = (ProceduresMetaLoader)parser;
//		loader.setMetaName(procedureLoader.getMetaName());
		loader.setProcedureName(procedureLoader.getDefinedName(), procedureLoader.getProcedureName());
		loader.setDataSource(ds);
		parser.setCursorTypeName(procedureLoader.getCursorTypeName());
		parser.setDataTypeAdapter(DataTypeAdapter.getOracleDataTypeAdapter());
//		parser.setSqlFieldExtractor(sfe);
		parser.setContent(procedureContent);
		
		List<Parameter> in      = parser.getInputParameters();
		List<Parameter> out     = parser.getOutputParameters();
		List<Parameter> cursors = parser.getCursorParameters();
		
		System.out.println("parameters of in:\t");
		System.out.println(in);
		System.out.println("parameters of out:\t");
		System.out.println(out);
		System.out.println("parameters of cursor:\t");
		System.out.println(cursors);
	}

	@Override
	public boolean hasCursorType() {
		// TODO Auto-generated method stub
		return false;
	}

}
