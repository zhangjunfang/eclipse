package cn.newcapec.framework.core.rest;

import org.restlet.Application;
import org.restlet.Context;

/**
 * 平台级Rest封装,映射到虚拟主机
 * @author: andy.li
 * 
 */
public class BaseApplication extends Application {

	private boolean tunnelServiceExtensionsTunnerl = false;

	/**
	 * <p>
	 * Title: BaseApplication.java
	 * </p>
	 * <p>
	 * Description: 初始化Rest上下文环境
	 * </p>
	 * 
	 * @param context
	 */
	public BaseApplication(Context context) {
		super(context.createChildContext());
		this.getTunnelService().setExtensionsTunnel(
				this.tunnelServiceExtensionsTunnerl);
	}

	/**
	 * <p>
	 * Title: isTunnelServiceExtensionsTunnerl
	 * </p>
	 * <p>
	 * Description: TODO
	 * </p>
	 * 
	 * @return boolean
	 */
	public boolean isTunnelServiceExtensionsTunnerl() {
		return tunnelServiceExtensionsTunnerl;
	}

	/**
	 * <p>
	 * Title: setTunnelServiceExtensionsTunnerl
	 * </p>
	 * <p>
	 * Description: TODO
	 * </p>
	 * 
	 * @param tunnelServiceExtensionsTunnerl
	 * @return void
	 */
	public void setTunnelServiceExtensionsTunnerl(
			boolean tunnelServiceExtensionsTunnerl) {
		this.tunnelServiceExtensionsTunnerl = tunnelServiceExtensionsTunnerl;
	}

}
