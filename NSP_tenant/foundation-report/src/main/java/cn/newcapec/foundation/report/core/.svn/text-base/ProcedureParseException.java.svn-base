package cn.newcapec.foundation.report.core;

import org.springframework.core.NestedRuntimeException;

public class ProcedureParseException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3490770399579293587L;
	
	private String procedureContent;
	
	public String getProcedureContent() {
		return procedureContent;
	}

	public ProcedureParseException(){
		super("procedure parse error!");
	}
	
	public ProcedureParseException(String msg){
		super(msg);
	}
	
	public ProcedureParseException(String msg, Throwable cause){
		super(msg, cause);
	}
	
	public ProcedureParseException(String msg, Throwable cause, Object procedureContent){
		super(msg, cause);
		this.procedureContent = procedureContent.toString();
	}
}
