package cn.newcapec.foundation.report.core.simp;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newcapec.foundation.report.core.DataTypeAdapter;
import cn.newcapec.foundation.report.core.InterpretException;
import cn.newcapec.foundation.report.core.Interpretation;
import cn.newcapec.foundation.report.core.ProcedureParseException;
import cn.newcapec.foundation.report.core.ProceduresMetaLoader;
import cn.newcapec.foundation.report.core.ProceduresParser;
import cn.newcapec.foundation.report.core.ds.DataSource;
import cn.newcapec.foundation.report.core.ds.DatabaseSource;
import cn.newcapec.foundation.report.core.parameter.Parameter;
import cn.newcapec.foundation.report.core.sql.SqlMetaLoader;

public abstract class ProceduresInterpretation implements Interpretation {

	protected Log logger = LogFactory.getLog(getClass());
	
	private DatabaseSource databaseSource;
	
	private ProceduresMetaLoader procedureLoader;
	
	private SqlMetaLoader sqlMetaloader;
	
	private ProceduresParser procedureParser;
	
	private DataTypeAdapter dataTypeAdapter;
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private List<Parameter> inList     = new ArrayList<Parameter>(8);
	private List<Parameter> outList    = new ArrayList<Parameter>(2);
	private List<Parameter> cursorList = new ArrayList<Parameter>(8);
	
	private List<String> procedureNames = new ArrayList<String>();
	
	public ProceduresInterpretation(){}
	
	@Override
	public void setDataSource(DataSource ds){
		assert (ds != null);
		if(!(ds instanceof DatabaseSource))
			throw new IllegalArgumentException("DatabaseSource is required.");
		this.databaseSource = (DatabaseSource)ds;
		
		procedureLoader = getProceduresMetaLoader();
		sqlMetaloader   = getSqlMetaLoader();
		procedureParser = getProceduresParser();
		dataTypeAdapter = getDatatypeAdapter();
		
		loadProcedures();
	}
	
	@Override
	public List<Parameter> interpret(String express) throws InterpretException {
		// TODO Auto-generated method stub
		assert (procedureLoader != null);
		assert (sqlMetaloader != null);
		assert (procedureParser != null);
		assert (dataTypeAdapter != null);
		
		procedureLoader.setDataSource(databaseSource);
		procedureLoader.setMetaName(express);
		
		Object procedureContent = procedureLoader.getProcedure();
		
		sqlMetaloader.setDataSource(databaseSource);
		sqlMetaloader.setDataTypeAdapter(dataTypeAdapter);
		
		ProceduresMetaLoader loader = (ProceduresMetaLoader)procedureParser;
		loader.setProcedureName(procedureLoader.getDefinedName(), procedureLoader.getProcedureName());
		loader.setDataSource(databaseSource);
		procedureParser.setCursorTypeName(procedureLoader.getCursorTypeName());
		procedureParser.setDataTypeAdapter(dataTypeAdapter);
		procedureParser.setProceduresMetaLoader(procedureLoader);
		
		try{
			procedureParser.setContent(procedureContent);
		} catch(ProcedureParseException ppe){
			logger.error(ppe.getMessage());
			throw new InterpretException(ppe.getMessage(), ppe);
		} catch(UnsupportedOperationException usoe){
			logger.error("未实现的代码逻辑");
			throw new InterpretException(usoe.getMessage(), usoe);
		} catch(RuntimeException re){
			logger.error("解析过程出现了错误，解析过程被终止,这些错误没有被找到。");
			re.printStackTrace();
			throw new InterpretException(re.getMessage(), re);
		} catch(Throwable e){
			logger.debug("未知异常出现");
			throw new InterpretException(e.getMessage(), e);
		}
		
		List<Parameter> result = new ArrayList<Parameter>(8);
		
		inList     = procedureParser.getInputParameters();
		outList    = procedureParser.getOutputParameters();
		cursorList = procedureParser.getCursorParameters();
		
		result.addAll(inList == null ? new ArrayList<Parameter>(1) : inList);
		result.addAll(outList == null ? new ArrayList<Parameter>(1) : outList);
		result.addAll(cursorList == null ? new ArrayList<Parameter>(1) : cursorList);
		
//		Connection conn = databaseSource.getConnection();
//		try{
//			procedureNames.addAll(getProcedureNames(conn));
//		} catch(Exception ex){
//			logger.error(ex.getMessage());
//			throw new RuntimeException(ex.getMessage());
//		} finally {
//			databaseSource.releaseConnection(conn);
//		}
		loadProcedures();
		
		return result;
	}
	
	/**
	 * 获得存储过程名字集合
	 */
	private synchronized void loadProcedures(){
		if(procedureNames.size() == 0){
			Connection conn = databaseSource.getConnection();
			try{
				procedureNames.addAll(getProcedureNames(conn));
			} catch(Exception ex){
				logger.error(ex.getMessage());
				throw new RuntimeException(ex.getMessage());
			} finally {
				databaseSource.releaseConnection(conn);
			}
		}
	}

	protected abstract ProceduresMetaLoader getProceduresMetaLoader();
	
	protected abstract SqlMetaLoader getSqlMetaLoader();
	
	protected abstract ProceduresParser getProceduresParser();
	
	protected abstract DataTypeAdapter getDatatypeAdapter();
	
	/**
	 * 得到数据库中所有存储过程的名称。如：ORACLE中会得到这样结果：
	 * <pre>
	 * pkg_rpt_balance.balance_system
	 * pkg_rpt_balance.getaccdatabygroupid
	 * proc_elecconsum_validateterm
	 * ...
	 * </pre>
	 * @param conn
	 * @return
	 */
	protected abstract List<String> getProcedureNames(Connection conn);
	
	public List<Parameter> getInputParameters(){
		return this.inList;
	}
	
	public List<Parameter> getOutputParameters(){
		return this.outList;
	}
	
	public List<Parameter> getCursorParameters(){
		return this.cursorList;
	}
	
	public List<String> getProcedureNameList(){
		return this.procedureNames;
	}
	
	public List<Parameter> getFinalOutputParameters(){
		List<Parameter> result = new ArrayList<Parameter>(8);
		if(procedureParser.hasCursorOutput()){
			result = this.getCursorParameters();
			if(result == null || result.size() == 0)
				throw new RuntimeException("定义了游标输出，但没有找到任何输出字段，系统调用失败。");
		}
		List<Parameter> outList = this.getOutputParameters();
		if(outList != null)
			result.addAll(outList);
		return result;
	}
	
}
