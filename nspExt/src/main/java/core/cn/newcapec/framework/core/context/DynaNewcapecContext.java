package cn.newcapec.framework.core.context;
import java.util.Hashtable;
import java.util.Map;

/**
 * 动态上下文环境
 * @author andy.li
 */
@SuppressWarnings("all")
public class DynaNewcapecContext extends AbstractNewcapecContext {

	private Map cache;

	public DynaNewcapecContext() {
		cache = new Hashtable();
	}

	private String a(int i, String s) {
		return "$" + i + "." + s;
	}

	public Object getAttribute(int i, String s) {
		return cache.get(a(i, s));
	}

	public String getParameter(String s) {
		return null;
	}

	public String[] getParameters(String s) {
		return null;
	}

	public void removeAttribute(int i, String s) {
		cache.remove(a(i, s));
	}

	public void setAttribute(int i, String s, Object obj) {
		cache.put(a(i, s), obj);
	}
}
