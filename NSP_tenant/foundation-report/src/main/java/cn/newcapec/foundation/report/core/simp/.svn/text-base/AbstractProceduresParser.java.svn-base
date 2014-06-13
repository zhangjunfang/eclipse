package cn.newcapec.foundation.report.core.simp;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import cn.newcapec.foundation.report.core.DataType;
import cn.newcapec.foundation.report.core.DataTypeAdapter;
import cn.newcapec.foundation.report.core.Field;
import cn.newcapec.foundation.report.core.ProcedureParseException;
import cn.newcapec.foundation.report.core.ProceduresMetaLoader;
import cn.newcapec.foundation.report.core.ProceduresParser;
import cn.newcapec.foundation.report.core.ds.DataSource;
import cn.newcapec.foundation.report.core.ds.DatabaseSource;
import cn.newcapec.foundation.report.core.parameter.CursorOutParameter;
import cn.newcapec.foundation.report.core.parameter.InParameter;
import cn.newcapec.foundation.report.core.parameter.OutParameter;
import cn.newcapec.foundation.report.core.parameter.Parameter;
import cn.newcapec.foundation.report.core.sql.SQLStringTools;
import cn.newcapec.foundation.report.core.sql.SqlFieldExtractor;
import cn.newcapec.foundation.report.core.sql.SqlFieldExtractorAdapter;
import cn.newcapec.foundation.report.core.sql.SqlReader;
import cn.newcapec.foundation.report.core.sql.SqlReader.SqlObject;
import cn.newcapec.foundation.report.util.SegmentReader;

/**
 * 抽象的存储过程解析器，处理输入与输出参数。</br>
 * 子类中会有针对不同数据库的具体实现。
 * @author shikeying
 *
 */
public abstract class AbstractProceduresParser 
implements ProceduresParser, ProceduresMetaLoader {

	protected Log logger = LogFactory.getLog(getClass());
	
	private DatabaseSource dataSource = null;
	
	private boolean existCursorOutput = false;
	
	private List<Parameter> allParameters = new ArrayList<Parameter>(8);
	
	private DataTypeAdapter dataTypeAdapter;
	
	private String cursorTypeName;
	
	private SqlFieldExtractor sqlFieldExtractor;
	
	private Object procedureContent;
	
	private List<Field> fieldsResult;
	
	protected ProceduresMetaLoader metaLoader;
	
	private String cursorVariableName; //当前存储过程中定义的游标变量名称
	
	@Override
	public void setSqlFieldExtractor(SqlFieldExtractor sqlFieldExtractor){
		assert (sqlFieldExtractor != null);
		this.sqlFieldExtractor = sqlFieldExtractor;
	}
	
	@Override
	public void setDataTypeAdapter(DataTypeAdapter dta){
		assert (dta != null);
		this.dataTypeAdapter = dta;
	}
	
	@Override
	public void setDataSource(DataSource ds){
		assert (ds != null);
		if(ds instanceof DatabaseSource){
			dataSource = (DatabaseSource)ds;
		} else 
			throw new IllegalArgumentException("DataSource must be type of " + DatabaseSource.class.getName());
	}
	
	@Override
	public void setContent(Object content) {
		// TODO Auto-generated method stub
		assert (content != null);
		Assert.notNull(this.procedureName, "please invoke setProcedureName() method firstly!");
		Assert.notNull(this.dataSource, "please invoke setDataSource() method firstly!");
		
		/* 准备DataTypeAdapter对象 */
		Assert.notNull(dataTypeAdapter, "please invoke setDataTypeAdapter() method firstly!");
		
		this.procedureContent = content;
		
		/* 获取IN/out关键字 */
		String keyIn = this.getParameterInKey().toLowerCase();
		String keyOut = this.getParameterOutKey().toLowerCase();
		String keyInout = this.getParameterInOutKey().toLowerCase();
		Assert.notNull(keyIn, "the method of getParameterInKey() must be implements.");
		Assert.notNull(keyOut, "the method of getParameterOutKey() must be implements.");
		Assert.notNull(keyInout, "the method of getParameterInOutKey() must be implements.");
		
//		Assert.notNull(cursorTypeName, "cursorTypeName is required.");
		
		Connection conn = dataSource.getConnection();
		List<String[]> paramsList = null;
		try{
			paramsList = this.getParameterList(content.toString()
					, conn, packageName, procedureName, cursorTypeName);
		} catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
			throw new ProcedureParseException(ex.getMessage());
		} finally {
			dataSource.releaseConnection(conn);
		}
		
		if(paramsList == null || paramsList.size() == 0){
			logger.debug("no input or output parameter found.");
			return;
		}
		
		/* 游标变量名，目前只支持定义一个， 不支持存在多个 */
//		String cursorName = null;
		
		/* 开始拆分参数，当前只考虑了oracle和sqlserver，其他数据库暂不支持 */
		String pname, dbType, inout;
		for(String[] parameterArray : paramsList){
			if(parameterArray == null) continue;
			if(parameterArray.length == 3){
				logger.debug("这是一个标准的参数定义");
				pname = parameterArray[0];
				inout = parameterArray[1];
				dbType = parameterArray[2];
				
			} else if(parameterArray.length == 2){
				logger.debug("参数定义第二个(in/out)省略了");
				pname = parameterArray[0];
				dbType = parameterArray[1];
				inout = keyIn;
				
			} else {
				logger.error("参数定义不正确，缺少关键字");
				throw new ProcedureParseException("parameter defined error!");
			}
			
			if(cursorTypeName != null 
					&& dbType.toLowerCase().equals(cursorTypeName)){
				logger.debug("cursor type parameter: " + pname);
				allParameters.add(obtainCursorParameter(pname));
				this.cursorVariableName = pname;
			} else {
				if(inout.toLowerCase().equals(keyIn)){
					allParameters.add(obtainInParameter(pname, dbType));
				} else if(inout.toLowerCase().equals(keyOut)){
					allParameters.add(obtainOutParameter(pname, dbType));
				} else if(inout.toLowerCase().equals(keyInout)){
					logger.debug("存在一个IN/OUT参数：" + pname);
					allParameters.add(obtainInParameter(pname, dbType));
					allParameters.add(obtainOutParameter(pname, dbType));
				} else
					throw new ProcedureParseException("unknown in/out key: " + inout);
			}
		}
		
		/* 因为存在重新定义游标的情况，所以需要在存储过程内容中查找是否存在新的定义类型名字 */
		String redeclaredCursorName = this.metaLoader.getRedeclaredCursorName();
		if(redeclaredCursorName != null){
			logger.debug("重新定义的游标变量: " + redeclaredCursorName);
			this.cursorVariableName = redeclaredCursorName;
		}
		
		if(cursorVariableName != null){
			logger.debug("存在游标类型定义");
			this.existCursorOutput = true;
			String sqlForCursorOpened = extractFieldsFromCursor(content.toString(), cursorVariableName);
			SqlReader sr = new SqlReader();
			sr.setSql(sqlForCursorOpened);
			
			SqlObject sqlObj = sr.getResult();
			if(sqlObj == null)
				throw new ProcedureParseException("未读取到正确的游标SQL定义语句! sqlForCursorOpened = " + sqlForCursorOpened);
			
			/* 在此需要进一步分析FROM子句中是否存在嵌套SELECT子句 */
			if(SQLStringTools.containNestedFrom(sqlObj.getFrom())){
				sqlFieldExtractor = SqlFieldExtractorAdapter.getNestedFieldExtractor(dataSource);
				logger.debug("发现嵌套select子句，创建: " + sqlFieldExtractor.getClass().getName());
			} else {
				sqlFieldExtractor = SqlFieldExtractorAdapter.getFieldExtractor(dataSource);
			}
			sqlFieldExtractor.setSql(sqlObj.getSelect(), sqlObj.getFrom());
			fieldsResult = sqlFieldExtractor.getFieldList();
			
			if(logger.isDebugEnabled()){
				logger.debug("----------- parsed all fields ---------");
				if(fieldsResult == null)
					logger.debug("not found fields.");
				else {
					for(Field f : fieldsResult){
						logger.debug("# " + f);
					}
				}
			}
		}
	}

	/**
	 * 从存储过程内容中提取游标变量对应的SQL语句，系统在获得这些SQL后</br>
	 * 就会对其中的字段进行分析，进而找出包含的字段名称。
	 * @param content 存储过程内容
	 * @param cursorName 游标定义的变量名
	 */
	private String extractFieldsFromCursor(String content, String cursorName){
		if(content == null) return null;
		content = content.toLowerCase();
		SegmentReader segmentReader = new SegmentReader();
		segmentReader.addRemoveKey("--", "\n");
		segmentReader.addRemoveKey("/*", "*/");
		
		String cursorStartKey = new StringBuilder().append("open ")
				.append(cursorName.toLowerCase()).append(" for").toString();
		String cursorEndKey = ";";
		segmentReader.addKey(cursorStartKey, cursorEndKey);
		segmentReader.read(content);
		List<StringBuilder> resultList = segmentReader.getSolvedList(cursorStartKey, cursorEndKey);
		
		String sqlForCursorOpened = null;
		if(resultList != null){
			logger.debug("找到游标定义sql语句，共有 " + resultList.size() + " 个。");
			for(StringBuilder sb : resultList){
				if(isSql(sb)){
					if(validateSql(sb.toString())){
						sqlForCursorOpened = sb.toString();
						break;
					} else
						continue;
				}
				else {
					logger.debug("找到的游标打开是一个变量定义: " + sb.toString());
					// findSql from variable
					throw new UnsupportedOperationException("此逻辑还未实现，需要现在写么？");
				}
			}
		}
		
		logger.debug("found SQL for cursor is below =======");
		logger.debug(sqlForCursorOpened);
		if(sqlForCursorOpened == null)
			throw new ProcedureParseException("在存储过程中，未找到定义的游标打开语句，请确定存储过程定义正确。");
		return sqlForCursorOpened.replaceAll("\n", " ");
	}
	
	private boolean isSql(StringBuilder findDefinition){
		assert (findDefinition != null);
		if(findDefinition.toString().indexOf("select") >= 0)
			return true;
		else
			return false;
	}
	
	private static List<String> invalidSampleSql = new ArrayList<String>();
	static {
		invalidSampleSql.add("select null");
	}
	
	/**
	 * 验证一些无效的SQL语句
	 * @param sql
	 * @return
	 */
	protected boolean validateSql(String sql){
		for(String s : invalidSampleSql){
			if(sql.indexOf(s) >= 0){
				logger.debug("发现无效SQL语句: " + sql);
				return false;
			}
		}
		return true;
	}
	
	private Parameter obtainInParameter(String name, String type){
		DataType dt = this.dataTypeAdapter.getAdapterDataType(type);
		if(dt == null)
			throw new ProcedureParseException("not found DataType by '" + type + "'.");
		return new InParameter().setName(name).setType(dt);
	}
	
	private Parameter obtainOutParameter(String name, String type){
		DataType dt = this.dataTypeAdapter.getAdapterDataType(type);
		if(dt == null)
			throw new ProcedureParseException("not found DataType by '" + type + "'.");
		return new OutParameter().setName(name).setType(dt);
	}
	
	private Parameter obtainCursorParameter(String name){
		return new CursorOutParameter().setName(name);
	} 
	
	@Override
	public List<Parameter> getInputParameters() {
		// TODO Auto-generated method stub
		if(allParameters.size() == 0) return null;
		List<Parameter> result = new ArrayList<Parameter>(2);
		for(Parameter p : allParameters){
			if(p.isInput())
				result.add(p);
		}
		return result;
	}

	@Override
	public List<Parameter> getOutputParameters() {
		// TODO Auto-generated method stub
		if(allParameters.size() == 0) return null;
		List<Parameter> result = new ArrayList<Parameter>(2);
		for(Parameter p : allParameters){
			if(p.isOutput() && !p.isOutCursor())
				result.add(p);
		}
		return result;
	}

	@Override
	public boolean hasCursorOutput() {
		// TODO Auto-generated method stub
		if(allParameters.size() == 0) return false;
		if(!this.existCursorOutput){
			for(Parameter p : allParameters){
				if(p.isOutCursor()){
					this.existCursorOutput = true;
					logger.debug("found cursor output: " + p.getName());
					break;
				}
			}
		}
		return this.existCursorOutput;
	}

	@Override
	public List<Parameter> getCursorParameters() {
		// TODO Auto-generated method stub
		if(fieldsResult == null) return null;
		List<Parameter> result = new ArrayList<Parameter>(8);
		CursorOutParameter parameter = null;
		for(Field f : fieldsResult){
			parameter = new CursorOutParameter();
			parameter.setName(f.getAliasName() != null ? 
					f.getAliasName() : f.getFieldName());
			parameter.setAlias(f.getComment());
			parameter.setType(dataTypeAdapter.getAdapterDataType(f.getFieldType().toUpperCase()));
			result.add(parameter);
		}
		return result;
	}

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// MetaLoader implements
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/* 存储过程的名字 */
//	private String express = null;
	
	/* 包名和存储过程名字 */
	private String packageName, procedureName;
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getMetaName(){
		if(packageName == null)
			return procedureName;
		else
			return packageName + "." + procedureName;
	}
	
	@Override
	public void setMetaName(String express){
		
	}
	
	@Override
	public void setCursorTypeName(String cursorTypeName){
//		assert (cursorTypeName != null && !cursorTypeName.equals(""));
		if(cursorTypeName != null && !cursorTypeName.equals(""))
			this.cursorTypeName = cursorTypeName.toLowerCase();
	}
	
	@Override
	public void setProcedureName(String definedName, String procedureName){
		assert (procedureName != null && !procedureName.equals(""));
		this.packageName = definedName;
		this.procedureName = procedureName;
	}
	
	@Override
	public void setProceduresMetaLoader(ProceduresMetaLoader metaLoader){
		this.metaLoader = metaLoader;
	}
	
	@Override
	public String getDefinedName() {
		// TODO Auto-generated method stub
		return this.packageName;
	}

	@Override
	public String getProcedureName() {
		// TODO Auto-generated method stub
		return this.procedureName;
	}
	
	@Override
	public String getCursorTypeName() {
		// TODO Auto-generated method stub
		return this.cursorTypeName;
	}
	
	@Override
	public Object getProcedure() {
		// TODO Auto-generated method stub
		return this.procedureContent;
	}
	
	@Override
	public String getRedeclaredCursorName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获得输入输出参数列表，每个参数是个数组对象，格式如：</br>
	 * ["var1","in/out/inout", "number"],数组中第二个(in/out)可以省略
	 * @param content 存储过程语句
	 * @param conn 数据库连接对象
	 * @param packageName 存储过程定义接口名（ORACLE为包名）
	 * @param procedureName 存储过程名字
	 * @param cursorTypeName 游标类型名称
	 * @return
	 */
	protected abstract List<String[]> getParameterList(String content
			, Connection conn, String packageName, String procedureName, String cursorTypeName);
	
	/**
	 * 返回存储过程参数定义中IN关键字</br>
	 * 每种数据库可能对于此关键字定义不同。
	 * @return
	 */
	protected abstract String getParameterInKey();
	
	/**
	 * 返回存储过程参数定义中OUT关键字</br>
	 * 每种数据库可能对于此关键字定义不同。
	 * @return
	 */
	protected abstract String getParameterOutKey();
	
	/**
	 * 存储过程中可以定义参数同时为输入和输出，返回其关键字，如oracle中：IN/OUT
	 * @return
	 */
	protected abstract String getParameterInOutKey();
	
	static class CursorFieldsCollection implements Iterator<Field>{

		public CursorFieldsCollection(String sqlForCursorOpened){
			
		}
		
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public Field next() {
			// TODO Auto-generated method stub
			return null;
		}

		public void remove() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("you can't remove the element.");
		}
	}
	
}
