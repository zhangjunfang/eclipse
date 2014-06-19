package cn.newcapec.framework.core.context;
/**
 * 全局关键字注册
 * @author andy.li
 */
public interface Keys {
	/**
	 * 应用路径
	 */
	String CONTEXT_PAHT = "contextPath";


	/**
	 * 年度
	 */
	String YYYY = "yyyy";
	/**
	 * 月份
	 */
	String MM = "mm";
	/**
	 * ID关键字
	 */
	String ID = "ID";

	/**
	 * 系统当前开发模式关键字
	 */
	String DEV_MODE = "devMode";
	/**
	 * 语种关键字
	 */
	String LANG = "lang";

	/**
	 * 用户代码
	 */
	String USER_ID = "userId";
	/**
	 * 机构代码
	 */
	String ORG_ID = "orgId";
	/**
	 * 部门代码
	 */
	String DEPT_ID = "deptId";
	/**
	 * 当前日期19位
	 */
	String CUR_DATE = "curDate";
	/**
	 * 当前日期10位
	 */
	String CUR_DATE_10 = "curDate10";
	/**
	 * 当前年度关键字
	 */
	String CUR_YEAR = "curYear";
	/**
	 * 当前月
	 */
	String CUR_MONTH = "curMonth";

	/**
	 * 当前季
	 */
	String CUR_QUARTER = "curQuarter";

	/**
	 * 金额小数位数
	 */
	String CURRENCY_BITS = "currencyBits";

	/**
	 * 金额单位,万元
	 */
	String CURRENCY_UNIT = "currencyUnit";

	/**
	 * 登陆用户关键字
	 */
	String USER = "user";
	/**
	 * 用户名称关键字
	 */
	String USER_NAME = "userName";
	/**
	 * 部门名称
	 */
	String DEPT_NAME = "deptName";
	/**
	 * 用户对应名称
	 */
	String CORR_NAME = "corrName";
	/**
	 * 用户类型
	 */
	String USER_TYPE = "userType";
	/**
	 * 树根节点值
	 */
	String TREE_ROOT_VALUE = "ROOT";

	/**
	 * 版本号
	 */
	String LOAD_CLAZZ = "loadClass";

	/**
	 * 缓存时间关键字
	 */
	String CACHE_MINUTES = "cacheMinutes";
	/**
	 * 组织名称
	 */
	String ORG_NAME = "orgName";

	/**
	 * 序列编号前缀
	 */
	String SEQ_NO = "_SEQ_NO";
	
	/**
	 * 当前租户
	 */	
  String DATA_SOURCE_URL = "dataSource";		

}
