package cn.newcapec.foundation.report.core;

public class DataAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1800778115562671733L;

	public DataAccessException(){
		super("data access failed.");
	}
	
	public DataAccessException(String msg){
		super(msg);
	}
	
	public DataAccessException(String msg, Throwable cause){
		super(msg, cause);
	}
}
