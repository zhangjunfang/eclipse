package cn.newcapec.framework.core.i18n;

import cn.newcapec.framework.core.context.Keys;
import cn.newcapec.framework.core.context.NewcapecContext;
import cn.newcapec.framework.core.utils.stringUtils.GlobalVariant;
import cn.newcapec.framework.core.utils.stringUtils.StringUtil;

/**
 * 
 * @author andy.li
 */
public class LangUtil {
	private LangUtil() {
	}

	public static String getLang(NewcapecContext context) {
		String lang = null;

		// get lang from request.parameter()
		lang = context.getParameter(Keys.LANG);
		lang = StringUtil.trim(lang);
		if (lang.length() > 0) {
			return lang;
		}

		// get lang from request.attribute()
		lang = (String) context.getAttribute(Keys.LANG);
		lang = StringUtil.trim(lang);
		if (lang.length() > 0) {
			return lang;
		}

		// get lang from session.user
//		LoginUser user = (LoginUser) (context.getAttribute(Keys.USER));
//		if (user != null) {
//			lang = user.getLang();
//			lang = StringUtil.trim(lang);
//			if (lang.length() > 0) {
//				return lang;
//			}
//		}

		// get lang from context
		lang = GlobalVariant.getVariant(Keys.LANG);
		lang = StringUtil.trim(lang);
		if (lang.length() > 0) {
			return Lang.ZH;
		}

		return Lang.ZH;
	}
}
