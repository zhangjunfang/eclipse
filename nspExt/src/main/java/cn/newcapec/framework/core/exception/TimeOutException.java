package cn.newcapec.framework.core.exception;

/**
 * 登陆用户超时异常,用于用户未登陆系统进行非法访问、或session超时或无效使用.


 * @author andy.li
 */

public class TimeOutException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3957340248111739698L;

	/**
	 * 
	 *
	 */
	public TimeOutException() {
		super();
	}

	/**
	 * @param message
	 */
	public TimeOutException(String message) {
		super(message);
	}
}
