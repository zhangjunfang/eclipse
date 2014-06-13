package cn.newcapec.foundation.report.core.simp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.newcapec.foundation.report.core.ProceduresMetaLoader;
import cn.newcapec.foundation.report.core.ds.DefaultDatabaseSource;
import cn.newcapec.foundation.report.util.SegmentReader;
import cn.newcapec.foundation.report.util.StringUtils;

public class OracleProceduresMetaLoader extends AbstractProceduresMetaLoader {
	private static final String QUERY_PROCEDURETEXT_BYNAME = "select text from user_source where type = 'PROCEDURE' and name =?";
	private static final String QUERY_PACKAGEBODY_BYNAME = "select text from user_source where type = 'PACKAGE BODY' and name =?";
//	private static final String QUERY_PACKAGE_BYPROCEDURE = "select package_name , argument_name , data_type , " +
//			"in_out from user_arguments where object_name=?";
	private static final String QUERY_PACKAGECURSOR = "select text from user_source where type = 'PACKAGE' and name =?";
	
	private static final String PROCEDURE_KEY = "procedure";
	
	/**
	 * oracle注释开头、结尾
	 */
	private static final String NOTE_START = "/*";
	private static final String NOTE_END = "*/";
	
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}

	public String getCursorTypeName() {
		// TODO Auto-generated method stub
		if(this.cursorTypeName == null){
			// load from db
			// this.cursorTypeName = getFromDb();
		}
		return this.cursorTypeName;
	}

	@Override
	protected String querySql(Connection conn
			, String packageName
			, String procedureName) {

//		String packageName = this.procedurePackageName(conn, procedureName);
		if(packageName == null || packageName == "") //当该存储过程没有包体时，根绝存储过程名称直接获取该内容
			return this.getProcedureText(conn, procedureName);
		this.cursorTypeName = this.getPackageCursor(conn, packageName);//获取包中的游标变量
		System.out.println(this.cursorTypeName);
		
		String packageBody = this.getPackageBody(conn, packageName, procedureName);
//		if(packageBody != null && packageBody.length() >0 ){
//			String[] str = packageBody.split("procedure");
//			for(String text : str){
//				if(text.startsWith(" "+procedureName.toLowerCase())){
//					packageBody = "procedure"+text;
//				}
//			}
//		}
		return packageBody;
	}
	
	private String getPackageCursor(Connection conn , String packageName){
		try {
			StringBuilder procedureText = new StringBuilder();
			PreparedStatement ps = conn.prepareStatement(QUERY_PACKAGECURSOR);
			ps.setString(1, packageName.toUpperCase());
			ResultSet rs = ps.executeQuery();
			
			String cursorType = null;
			String text = null;
			
			while(rs.next()){
				text = rs.getString("text").toLowerCase();
				if(text.indexOf("is ref cursor") >= 0){
					SegmentReader reader = new SegmentReader();
					reader.addKey("type ", " is ref cursor");
					Object res = reader.read(text);
					logger.debug("分析游标定义类型，结果 = " + res);
					cursorType = reader.getSolvedContent("type ", " is ref cursor");
					
				} else
					procedureText.append(text);
			}
			rs.close();
			ps.close();
			
			/* 顺便把注释也提取出来 */
			SegmentReader descriptionReader = new SegmentReader();
			descriptionReader.addKey("/*", "*/");
			descriptionReader.read(procedureText.toString());
			this.description = descriptionReader.getSolvedContent("/*", "*/");
			
			if(cursorType != null && !cursorType.equals(""))
				return cursorType.trim();
//			else
//				throw new RuntimeException("找到了游标类型定义，但是分析器没有得到结果，cursorType = " + cursorType);
			
			return null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 *  根据存储过程名称直接获取该内容
	 * @param conn
	 * @param procedureName
	 * @return
	 */
	private String getProcedureText(Connection conn, String procedureName){
		try {
			StringBuilder procedureText = new StringBuilder();
			PreparedStatement ps = conn.prepareStatement(QUERY_PROCEDURETEXT_BYNAME);
			ps.setString(1, procedureName.toUpperCase());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				procedureText.append(rs.getString("text").toLowerCase());
			}
			rs.close();
			ps.close();
			return procedureText.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取存储过程内容异常");
		}
	}
	
	// 当前是否存在注释的开头
	private boolean hasNoteStart = false;
	
	/**
	 * 根据packageName 查询 packageBody的值
	 * @param conn
	 * @param packageName
	 * @return
	 */
	private String getPackageBody(Connection conn
			, String packageName, String procedureName){
		procedureName = procedureName.toLowerCase().trim();
		
		try {
			StringBuilder packageBody = new StringBuilder();
			PreparedStatement ps = conn.prepareStatement(QUERY_PACKAGEBODY_BYNAME);
			ps.setString(1, packageName.toUpperCase());
			ResultSet rs = ps.executeQuery();
			
			String line = null;
			boolean start = false;
			
			while(rs.next()){
				line = rs.getString("text").toLowerCase().trim();
				logger.debug(line);
				if(line.equals(PROCEDURE_KEY)){
					logger.debug("存储过程定义第一行只有关键字: " + PROCEDURE_KEY);
					throw new RuntimeException("暂未实现该逻辑代码，需要时再加入。");
					
				} else if(line.indexOf(PROCEDURE_KEY) >= 0
						&& (line.contains(" " + procedureName))
							|| line.contains(" " + procedureName + "(")) {
					logger.debug("此行完整定义了存储过程名字,而且是所要的: " + procedureName);
					start = true;
					packageBody.append(line);
				} else {
					if(start){
						if(checkNote(line)) continue;
						packageBody.append(line);
						/* 查找重新定义的游标变量 */
						checkRedeclaredCursorName(line);
						if(line.equals("end;")){
							break;
						}
					}
				}
				packageBody.append("\n");
			}
			rs.close();
			ps.close();
			return packageBody.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取包体内容异常");
		}
	}

	private boolean checkNote(String line){
		if(line.startsWith(NOTE_START)){
			this.hasNoteStart = true;
			return true;
		} else if(line.endsWith(NOTE_END) && hasNoteStart){
			hasNoteStart = false;
			return true;
		} else if(hasNoteStart){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查是否存在有游标的重新定义
	 * @param line
	 */
	private void checkRedeclaredCursorName(String line){
		if(this.redeclaredCursorName != null) return;
		if(cursorTypeName != null
				&& line.indexOf(cursorTypeName) >= 0
				// 排除参数中定义的游标
				&& line.indexOf("out ") < 0){
			logger.debug("用户重新定义了游标类型。line: " + line);
			line = line.trim().replaceFirst(cursorTypeName, "");
			line = line.trim().replace(';', ' ').trim();
			if(StringUtils.isNotEmpty(line)){
				this.redeclaredCursorName = line;
			}
			else
				throw new NullPointerException("未找到游标定义变量名，line = " + line);
			logger.debug("redeclaredCursorName = " + redeclaredCursorName);
		}
	}
	
	public static void main(String[] args){
		ProceduresMetaLoader loader = new OracleProceduresMetaLoader();
		loader.setDataSource(new DefaultDatabaseSource());
		//loader.setMetaName("procedures.test");
		//zpf
//		loader.setMetaName("PRINT_ROLES");//存储过程名称
		loader.setMetaName("rpt_cardtypecollect");//存储过程名称
		
		loader.getProcedure();
		System.out.println("============ 游标类型定义：" + loader.getCursorTypeName());
		System.out.println(loader.getDescription());
		
//		System.out.println("存储过程内容是：" + procedureSql);
//		System.out.println("存储过程注释为：" + description);
		
	}
}
