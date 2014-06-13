package cn.newcapec.framework.core.context;
import javax.servlet.http.HttpServletRequest;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * @author andy.li
 */
@SuppressWarnings("all")
public class HttpNewcapecContext extends AbstractNewcapecContext {

	protected Stack reqeustStack;

	HttpNewcapecContext() {
		reqeustStack = new Stack();
	}

	/**
	 * 
	 * 获取request对象
	 * 
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest httpservletrequest = null;
		do {
			if (httpservletrequest != null) {
				break;
			}
			reqeustStack.peek();
			Reference reference = null;
			if ((reference = (Reference) reqeustStack.peek()) == null) {
				break;
			}
			if ((httpservletrequest = (HttpServletRequest) reference.get()) == null) {
				reqeustStack.pop();
			}
		} while (true);
		return httpservletrequest;
	}

	/**
	 * 
	 * 加载当期请求至上下文环境
	 * 
	 */
	public void setRequest(HttpServletRequest httpservletrequest) {
		boolean flag = false;
		int i = reqeustStack.size();
		int j = 0;
		do {
			if (j >= i)
				break;
			Reference reference;
			if ((reference = (Reference) reqeustStack.get(j)) != null
					&& reference.get() == httpservletrequest) {
				flag = true;
				for (int k = i - 1; k > j; k--)
					reqeustStack.remove(k);

				break;
			}
			j++;
		} while (true);
		if (!flag)
			reqeustStack.push(new WeakReference(httpservletrequest));
	}

	/**
	 * 获取请求参数
	 */
	public String getParameter(String s) {
		return getRequest().getParameter(s);
	}

	/**
	 * 获取请求参数
	 */
	public String[] getParameters(String s) {
		return getRequest().getParameterValues(s);
	}

	/**
	 * 获取属性
	 */
	public Object getAttribute(int i, String s) {
		HttpServletRequest httpservletrequest = getRequest();
		switch (i) {
		case 1: // '\001'
			return httpservletrequest.getAttribute(s);

		case 5: // '\005'
			return httpservletrequest.getSession().getAttribute(s);

		case 9: // '\t'
			return httpservletrequest.getSession().getServletContext()
					.getAttribute(s);

		case 3: // '\003'
		case 4: // '\004'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
		default:
			return null;
		}
	}

	public void setAttribute(int i, String s, Object obj) {
		HttpServletRequest httpservletrequest = getRequest();
		switch (i) {
		case 1: // '\001'
			httpservletrequest.setAttribute(s, obj);
			return;

		case 5: //
			httpservletrequest.getSession().setAttribute(s, obj);
			return;

		case 9: // '\t'
			httpservletrequest.getSession().getServletContext()
					.setAttribute(s, obj);
			// fall through

		case 3: // '\003'
		case 4: // '\004'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
		default:
			return;
		}
	}

	/**
	 * 移除属性
	 */

	public void removeAttribute(int i, String s) {
		HttpServletRequest httpservletrequest = getRequest();
		switch (i) {
		case 1: // '\001'
			httpservletrequest.removeAttribute(s);
			return;

		case 5: // '\005'
			httpservletrequest.getSession().removeAttribute(s);
			return;

		case 9: // '\t'
			httpservletrequest.getSession().getServletContext()
					.removeAttribute(s);
			break;
		}
	}
}
