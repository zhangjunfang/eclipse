package cn.newcapec.framework.core.logs;


import org.apache.log4j.Logger;

/**
 * 日志接口
 */
public interface LogEnabled {
	/**
	 * 日志打印
	 */
	public Logger log = Logger.getRootLogger();

	


}
