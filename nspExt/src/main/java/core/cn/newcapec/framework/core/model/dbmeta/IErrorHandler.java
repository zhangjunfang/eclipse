package cn.newcapec.framework.core.model.dbmeta;

/**
 * 错误处理接口
 * 
 *  @author andy.li
 */
public interface IErrorHandler {

	public abstract void onError(String s, Throwable throwable);
}
