package cn.newcapec.framework.core.context;

/**
 * 系统上下文线程变量
 * 
 * @author andy.li
 */
@SuppressWarnings("all")
public abstract class NewcapecContext {

	/**
	 * 作用域类型 - Reqeust
	 */
	public static final int REQUEST = 1;

	/**
	 * 作用域类型 - Session
	 */
	public static final int SESSION = 5;

	/**
	 * 作用域类型 - Application.即整个应用中的所有Client都可访问。
	 */
	public static final int APPLICATION = 9;

	private static ThreadLocal cache = new ThreadLocal();

	public NewcapecContext() {
	}

	public static void registerContext(NewcapecContext context) {
		cache.set(context);
	}

	public static synchronized NewcapecContext getContext() {
		Object obj;
		if ((obj = (NewcapecContext) cache.get()) == null) {
			registerContext(((NewcapecContext) (obj = new DynaNewcapecContext())));
		}
		return ((NewcapecContext) (obj));
	}

	/**
	 * 从当前的线程中注销一个context对象
	 */
	public static void unregisterContext() {
		cache.set(null);
	}

	/**
	 * 从上下文中获取一个参数的值.
	 */
	public abstract String getParameter(String s);

	/**
	 * 从上下文中获取一个参数的值数组.
	 */
	public abstract String[] getParameters(String s);

	/**
	 * 从上下文的Request作用域中获取一个属性的值.
	 */
	public abstract Object getAttribute(String s);

	/**
	 * 根据指定的作用域从上下文中获取一个属性的值.
	 */
	public abstract Object getAttribute(int i, String s);

	/**
	 * 为指定的作用域中的某个属性设定一个值.
	 */
	public abstract void setAttribute(int i, String s, Object obj);

	/**
	 * 删除指定的作用域中的某个属性.
	 */
	public abstract void removeAttribute(int i, String s);
}
