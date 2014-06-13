package cn.newcapec.foundation.report.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtils {

	private DataBaseConnection connection = new OracleDataBaseConnection();
	
	protected void testResultSetMetaData(){
		String sql = "select * from p_user where rowid = ''";
		Connection conn = null;
		try{
			conn = connection.getConnection();
			Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			System.out.println("---------- nativeSql = " + conn.nativeSQL("select * from p_user t"));
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("execute query, ResultSet = " + rs.toString());
			while(rs.next()){
				System.out.println("data = " + rs.getInt(1));
			}
			
			ResultSetMetaData meta = rs.getMetaData();
			int ncolumns = meta.getColumnCount();
			for(int i=1; i<ncolumns; i++){
				System.out.println(meta.getColumnLabel(i) + " = " + meta.getColumnName(i));
			}
			
//			PreparedStatement pst = conn.prepareStatement(sql);
//			ResultSetMetaData meta2 = pst.getMetaData();
//			System.out.println("meta2 = " + meta2);
			
		} catch(Exception ex){
			System.out.println("--------- error ----------");
			ex.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	protected void testCallProcedure(){
		Connection conn =  connection.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("{call role_test.print_roles(?,?)}");
			cs.setString(1, "aaa");
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cs.executeUpdate();
			
			ResultSet rs = (ResultSet)cs.getObject(2);
			System.out.println("rs = " + rs);
			
			while (rs.next()) {
			    System.out.println(rs.getInt(3) + "\t" + rs.getString(4) + "\t"
			      + rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataBaseUtils main = new DataBaseUtils();
		main.testResultSetMetaData();
//		main.testCallProcedure();
	}

}
