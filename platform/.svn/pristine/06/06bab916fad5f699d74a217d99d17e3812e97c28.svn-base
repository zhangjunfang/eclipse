package cn.newcapec.framework.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 系统异常.这是一个运行时异常，代表系统原因产生的异常，比如SQLException产生的异常，
 * 网络访问失败产生的异常，等。这种异常，程序中无法编程处理，用户也无法恢复，因此作为运行时异常抛出.
 * 
 * @author andy.li
 */

public class SysException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5542169322764723764L;
	/**
	 * 根异常，代表产生本异常的根本原因.
	 */
	protected Throwable rootCause = null;
	private String messageKey = null;
	private Object[] messageArgs = null;

	/**
	 * 构造函数.
	 */
	public SysException() {
		super();
	}

	/**
	 * 构造函数.
	 * 
	 * @param msg
	 *            异常信息.
	 */
	public SysException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数.
	 * 
	 * @param cause
	 *            产生本异常的根异常实例.
	 */
	public SysException(Throwable cause) {
		this.rootCause = cause;
	}

	/**
	 * 构造函数.
	 * 
	 * @param msg
	 *            异常信息.
	 * @param cause
	 *            产生本异常的根异常实例.
	 */
	public SysException(String msg, Throwable cause) {
		super(msg);
		this.rootCause = cause;
	}

	/**
	 * 设置异常消息的keyCode，主要为支持国际化.
	 * 
	 * @param key
	 *            异常消息的keyCode.
	 */
	public void setMessageKey(String key) {
		this.messageKey = key;
	}

	/**
	 * 获得异常的keyCode，主要为了支持国际化.
	 * 
	 * @return 异常消息的keyCode.
	 */
	public String getMessageKey() {
		return messageKey;
	}

	/**
	 * 设置异常消息的参数，主要为了支持国际化. 异常消息使用如下格式定义带参数的消息: {0} is larger than {1}.
	 * 
	 * @param args
	 *            异常消息的参数数组.
	 */
	public void setMessageArgs(Object[] args) {
		this.messageArgs = args;
	}

	/**
	 * 获得异常消息的参数，主要为了支持国际化.
	 * 
	 * @return 异常消息的参数数组.
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * 设置根异常.
	 * 
	 * @param anException
	 *            根异常.
	 */
	public void setRootCause(Throwable anException) {
		rootCause = anException;
	}

	/**
	 * 获得根异常.
	 * 
	 * @return 根异常.
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * 将异常的栈信息打印到System.err，先打印本异常的栈信息，然后打印根异常的栈信息.
	 */
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * 将异常的栈信息打印到输出流，先打印本异常的栈信息，然后打印根异常的栈信息.
	 * 
	 * @param outStream
	 *            输出流.
	 */
	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	/**
	 * 将异常的栈信息打印到输出流，先打印本异常的栈信息，然后打印根异常的栈信息.
	 * 
	 * @param writer
	 *            输出流
	 */
	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);

		if (getRootCause() != null) {
			getRootCause().printStackTrace(writer);
		}
		writer.flush();
	}
}
