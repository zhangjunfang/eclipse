package cn.newcapec.framework.core.rest.velocity;


/**
 * 
 * @author andy.li
 *
 */
public class PathUtil {
	private static String rootPath;
	
	static {
		String classPath = PathUtil.class.getResource("/").getPath();
		//linux 下多了个"./"
		if (classPath.indexOf("/./") != -1) {
			classPath = classPath.replaceAll("/./", "/");
		}
		rootPath = classPath.substring(0, classPath.indexOf("WEB-INF"));
	}
	
	/**
	 * 取系统发布的物理目录
	 * @return
	 */
	public static String getRootPath() {
		return rootPath;
	}
}
