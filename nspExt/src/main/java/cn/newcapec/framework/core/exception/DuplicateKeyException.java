package cn.newcapec.framework.core.exception;




/**
 *  重复键值异常类
 * @author andy.li
 */
@SuppressWarnings("all")
public class DuplicateKeyException extends BaseException {
	/**
	 *  
	 * @param key
	 */
	public DuplicateKeyException(Object key) {
		super("Can not add a object with the duplicate key \"" + key
				+ "\"!");
	}
}