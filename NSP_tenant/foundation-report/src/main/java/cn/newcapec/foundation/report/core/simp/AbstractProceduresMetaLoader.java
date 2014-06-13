package cn.newcapec.foundation.report.core.simp;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newcapec.foundation.report.core.ProceduresMetaLoader;
import cn.newcapec.foundation.report.core.ds.DataSource;
import cn.newcapec.foundation.report.core.ds.DatabaseSource;
import cn.newcapec.foundation.report.util.StringUtils;

public abstract class AbstractProceduresMetaLoader implements
		ProceduresMetaLoader {

	protected Log logger = LogFactory.getLog(getClass());
	
	protected boolean existCursorOutput = false;
	
	protected String description = null;
	protected String cursorTypeName = null;
	
	private DatabaseSource ds = null;
	
	private String content = null;
	
//	/* 用户输入的调用名称 */
//	private String express = null;
	
	/* 包名和存储过程名字 */
	private String packageName, procedureName;
	
	protected String redeclaredCursorName;
	
	public String getMetaName(){
		if(packageName == null)
			return procedureName;
		else
			return packageName + "." + procedureName;
	}
	
	public void setMetaName(String express){
		assert (express != null && !express.equals(""));
		if(express.indexOf(".") >= 0){
			String[] args = express.trim().split("\\.");
			if(args.length == 2){
				packageName = args[0];
				procedureName = args[1];
			} else
				throw new IllegalArgumentException("input error: " + express);
		} else {
			packageName = null;
			procedureName = express.trim();
		}
	}
	
	@Override
	public void setProcedureName(String definedName, String procedureName){
		
	}
	
	@Override
	public Object getProcedure() {
		// TODO Auto-generated method stub
		if(ds == null)
			throw new RuntimeException("DatabaseSource is required!");
		
		if(content == null){
			Connection conn = ds.getConnection();
			try{
				logger.debug("获取数据库连接对象 = " + conn);
				content = querySql(conn, packageName, procedureName);
			} catch(Exception ex){
				throw new RuntimeException(ex.getMessage());
			} finally {
				ds.releaseConnection(conn);
				logger.debug("数据库连接释放");
			}
		}
		
		logger.debug(content);
		return content;
	}

	@Override
	public String getDefinedName(){
		return this.packageName;
	}
	@Override
	public String getProcedureName(){
		return this.procedureName;
	}
	
//	@Override
//	public List<Parameter> getParameters(String name) {
//		// TODO Auto-generated method stub
//		assert (name != null && !name.equals(""));
//		if(content == null)
//			getProcedure(name);
//		
//		
//		return null;
//	}
//
//	@Override
//	public boolean hasCursor() {
//		// TODO Auto-generated method stub
//		return existCursorOutput;
//	}

	public void setDataSource(DataSource ds){
		assert (ds != null);
		if(!(ds instanceof DatabaseSource)){
			logger.error("设置数据源必须是'数据库数据源'，" + this.getClass().getName());
			throw new RuntimeException("error: dataSource is not type of " + DatabaseSource.class.getName());
		}
		this.ds = (DatabaseSource)ds;
	}
	
	public boolean hasCursorType(){
		if(!StringUtils.isEmpty(this.cursorTypeName)){
			this.existCursorOutput = true;
		}
		return this.existCursorOutput;
	}
	
	@Override
	public String getRedeclaredCursorName(){
		return redeclaredCursorName;
	}
	
	/**
	 * 从数据库中查询存储过程的具体内容
	 * @param conn 数据库连接对象
	 * @param definedName 存储过程定义的接口名称
	 * @param procedureName 存储过程名称
	 * @return
	 */
	protected abstract String querySql(Connection conn
			, String definedName
			, String procedureName);
	
//	protected abstract List<Parameter> combinParameters(List<String[]> parameters);
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("packageName=");
		s.append(packageName);
		s.append(", procedureName=");
		s.append(procedureName);
		s.append(", content=");
		s.append(content);
		s.append(", existCursor=");
		s.append(existCursorOutput);
		return s.toString();
	}
}
