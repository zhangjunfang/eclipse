package cn.newcapec.framework.core.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * 
 * SQL 异常类
 * @author andy.li
 */
public class SQLExceptionUtil {

    /**
     * 
     */
    private SQLExceptionUtil() {
        
    }
    
    /**
     * 抽取SQL错误信息，转换成AppException/SysException抛出.
     * */
    public static void translateException(Throwable t){
        Throwable t1 = null;
        Throwable t2 = null;
        
        
        while (t!=null){
            t2 = t1;
            t1 = t;
            t = ExceptionUtils.getCause(t);
        }
        
        throw new SysException(t2.getMessage(),t2);     
    }
}