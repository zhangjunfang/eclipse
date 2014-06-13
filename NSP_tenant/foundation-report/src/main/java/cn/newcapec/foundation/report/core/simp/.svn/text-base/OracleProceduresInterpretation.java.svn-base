package cn.newcapec.foundation.report.core.simp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.newcapec.foundation.report.core.DataAccessException;
import cn.newcapec.foundation.report.core.DataTypeAdapter;
import cn.newcapec.foundation.report.core.ProceduresMetaLoader;
import cn.newcapec.foundation.report.core.ProceduresParser;
import cn.newcapec.foundation.report.core.sql.SqlMetaLoader;
import cn.newcapec.foundation.report.core.sql.simp.OracleSqlMetaLoader;

public class OracleProceduresInterpretation extends ProceduresInterpretation {

	private static final String SELECT_OBJECT_NAME  = "select distinct(object_name), package_name from user_arguments order by package_name";
	private static final String SELECT_FUNCION_NAME = "select object_name from user_objects where object_type = 'FUNCTION'";
	
	@Override
	protected ProceduresMetaLoader getProceduresMetaLoader() {
		// TODO Auto-generated method stub
		return new OracleProceduresMetaLoader();
	}

	@Override
	protected SqlMetaLoader getSqlMetaLoader() {
		// TODO Auto-generated method stub
		return new OracleSqlMetaLoader();
	}

	@Override
	protected ProceduresParser getProceduresParser() {
		// TODO Auto-generated method stub
		return new OracleProceduresParser();
	}

	@Override
	protected DataTypeAdapter getDatatypeAdapter() {
		// TODO Auto-generated method stub
		return DataTypeAdapter.getOracleDataTypeAdapter();
	}

	@Override
	protected List<String> getProcedureNames(Connection conn) {
		// TODO Auto-generated method stub
		List<String[]> procedures = loadProcedureNames(conn);
		if(procedures != null && procedures.size() > 0){
			List<String> result = new ArrayList<String>();
			for(String[] ss : procedures){
				if(ss[1] != null){
					result.add(ss[1] + "." + ss[0]);
				} else
					result.add(ss[0]);
			}
			return result;
		}
		return null;
	}
	
	private List<String[]> loadProcedureNames(Connection conn){
		List<String[]> result = new ArrayList<String[]>(64);
		try {
			PreparedStatement statement = conn.prepareStatement(SELECT_OBJECT_NAME);
			ResultSet rs1 = statement.executeQuery();
			String[] s = null;
			String temp = null;
			while(rs1.next()){
				s = new String[2];
				s[0] = rs1.getString("OBJECT_NAME").toLowerCase();
				temp = rs1.getString("PACKAGE_NAME");
				if(temp == null || temp.equals("") || temp.equalsIgnoreCase("null"))
					s[1] = null;
				else
					s[1] = temp.toLowerCase();
				result.add(s);
			}
			
			List<String> functionNames = new ArrayList<String>(4);
			PreparedStatement stmt = conn.prepareStatement(SELECT_FUNCION_NAME);
			ResultSet rs2 = stmt.executeQuery();
			while (rs2.next()){
				functionNames.add(rs2.getString("OBJECT_NAME").toLowerCase());
			}
			
			// 过滤掉所有函数名
			if(functionNames.size() > 0){
				String[] st = null;
				for(Iterator<String[]> i = result.iterator(); i.hasNext();){
					st = i.next();
					if(st[1] == null && functionNames.contains(st[0])){
						i.remove();
					}
				}
			}
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException("查询存储过程名称出现错误，msg = " + e.getMessage());
		}
	}

}
