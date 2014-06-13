package cn.newcapec.framework.core.exception;

import cn.newcapec.framework.core.context.NewcapecContext;
import cn.newcapec.framework.core.i18n.LangUtil;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.utils.stringUtils.StringUtil;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.sql.SQLException;
/**
 * 
 * 异常工具类
 * 用于对异常信息进行转换、国际化翻译.
 * @author andy.li
 */
public class ExceptionUtil implements LogEnabled {
	/**
	 * 异常信息前缀
	 */
	private static final String  ERROR_CODE="err_code";

	private ExceptionUtil() {

	}
	/**
	 * 
	 * 抽取底层错误信息，并进行国际化，转换成AppException/SysException抛出.
	 *
	 * @param t
	 * @param lang
	 * @param messageArgs
	 * @throws RuntimeException
	 */
	public static void extractException(Throwable t, String lang,Object[] messageArgs) throws RuntimeException {
	    
	    extractException(t,lang,messageArgs,null);
	}
	/**
	 * 抽取底层错误信息，并进行国际化，转换成AppException/SysException抛出.
	 * <ul>
	 * <li><p>使用实例sys_err_code_0003（删除失败）:系统将根据错误代码翻译成对应的语言提示。</p></li>
	 * <code>
	 * 	if(AppException.class.isInstance(ex))
				throw (AppException)ex;
			else
				ExceptionUtil.extractException(new AppException("sys_err_code_0003")); //删除失败
	
	 * 
	 * </code>
	 * <li><p>sys_err_code_0004（xx代码重复！）:系统将根据错误代码，及传递参数翻译成对应的语言提示。示例代码如下：</p></li>
	 * <code>
	 * 
	 * if(AppException.class.isInstance(ex))
	 *			throw (AppException)ex;
	 *		else
	 *			ExceptionUtil.extractException(new AppException("sys_err_code_0003")); //删除失败
	 * </code>
	 * <ul>
	 * 
	 * @param t 异常
	 * @param lang 语种
	 * @param messageArgs 传递参数
	 */
	public static void extractException(Throwable t, String lang,Object[] messageArgs,Object entityObject) throws RuntimeException {
		Throwable t1 = null;//若未获得
		
		if ( t instanceof SysException){
			t1 = t;
		}else{
			//获取AppException 
			t1 = getTargetAppException(t);
		}
		if(t1 == null){
			t1 = ExceptionUtils.getRootCause(t);
		}
		if (t1 == null){
			t1 = t;
		}
		
//		获取错误代码
		String messageCode = StringUtil.trim(t1.getMessage());
		String errorMessage = null; //国际化信息
		if (t1 instanceof SysException) {
			//若存在error_code的代码
			if(messageCode.indexOf(ERROR_CODE) != -1){
				errorMessage = Message.getInfo(messageCode, lang, messageArgs,null,entityObject);
				if (StringUtil.isValid(errorMessage)){
					throw new SysException(errorMessage,t1);//转换异常信息并抛出
				}
			}
			throw (SysException) t1;

		} else if (t1 instanceof SysException) {
			if(messageCode.indexOf(ERROR_CODE) != -1){
				errorMessage = Message.getInfo(messageCode, lang, messageArgs,null,entityObject);
				if (StringUtil.isValid(errorMessage)){
					throw new SysException(errorMessage,t1);//转换异常信息并抛出
				}	
			}
			throw (SysException) t;

		} else if (t1 instanceof SQLException) {

			//对SQL错误专门处理
			SQLExceptionUtil.translateException(t);

		} else {
			throw new SysException(t1.getMessage(), t1);
		}

	}
	
	
	/**
	 * 截获应用异常信息
	 *  
	 * @param t
	 * @return
	 */
	private static Throwable getTargetAppException(Throwable t) {
		Throwable t1=null;
		do{
			t1 = ExceptionUtils.getCause(t);
			if ( t1 instanceof SysException  ){
				return t1;
			}
			t = t1;
		}while(t1 != null);
		
		return null;
		
	}
	/**
	 * 抽取底层错误信息，转换成AppException/SysException抛出.
	 * @param t
	 * @param lang 语种
	 */
	public static void extractException(Throwable t,String lang) throws RuntimeException  {
		extractException(t, lang,null,null);
	}
	
	
	/**
	 * 抽取底层错误信息，转换成AppException/SysException抛出.
	 */
	public static void extractException(Throwable t) throws RuntimeException  {
		String lang = LangUtil.getLang(NewcapecContext.getContext());
		extractException(t, lang);
	}
	
	/**
	 * 抽取底层错误信息，转换成AppException/SysException抛出.
	 */
	public static void extractException(Throwable t,Object[] messageArgs) throws RuntimeException  {
		String lang = LangUtil.getLang(NewcapecContext.getContext());
		extractException(t, lang,messageArgs,null);
	}
	
	public static void extractException(Throwable t, Object[] args, Object entityObject) {
	    	String lang = LangUtil.getLang(NewcapecContext.getContext());
		extractException(t, lang,args,entityObject);
	    
	    
	}

}