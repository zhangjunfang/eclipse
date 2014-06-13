package cn.newcapec.framework.core.context;

/**
 * 平台上下文环境抽象类
 * @author andy.li
 */
public abstract class AbstractNewcapecContext extends NewcapecContext {

	public AbstractNewcapecContext() {

	}

	/**
	 * 获取属性值，顺序：request,session,application
	 */
	public Object getAttribute(String s) {
		Object obj;
		if ((obj = getAttribute(1, s)) == null
				&& (obj = getAttribute(2, s)) == null
				&& (obj = getAttribute(5, s)) == null)
			obj = getAttribute(9, s);
		return obj;
	}
}
