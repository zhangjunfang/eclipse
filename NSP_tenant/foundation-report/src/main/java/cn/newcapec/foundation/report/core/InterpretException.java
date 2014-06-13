package cn.newcapec.foundation.report.core;

/**
 * 解释异常定义
 * @author shikeying
 *
 */
public class InterpretException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8707624722000824783L;

	public InterpretException(){
		super("interpret failed!");
	}
	
	public InterpretException(String msg){
		super(msg);
	}
	
	public InterpretException(String msg, Throwable cause){
		super(msg, cause);
	}
}
