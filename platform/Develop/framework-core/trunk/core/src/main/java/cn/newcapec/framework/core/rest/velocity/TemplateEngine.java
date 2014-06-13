package cn.newcapec.framework.core.rest.velocity;


import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import cn.newcapec.framework.core.utils.springUtils.SpringConext;

/**
 * 模版视图数据合成处理工具类<br />
 * 暂只支持Velocity模版视图
 * @author andy.li
 */
public class TemplateEngine {
	
	private static final Log log = LogFactory.getLog(TemplateEngine.class);
	static {
//		String templatePath = PathUtil.getRootPath();
//		Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath + ConfigUtil.getItem("file.resource.loader.path"));
		Velocity.setProperty(Velocity.SET_NULL_ALLOWED, "true");
//		log.info("templatePath:" + templatePath);
        try {
            Velocity.init();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


	/**
	 * 使用Velocity模版引擎合成视图 [Velocity自身来合成]
	 * 
	 * @param templatePath
	 *            模版路径
	 * @param context
	 *            模版上下文数据
	 * @return 模版合成后的代码片段
	 */
	public static String parse(String templatePath, Context context) throws Exception {
		//获取spring中velocity环境配置
		VelocityConfigurer vc = (VelocityConfigurer)SpringConext.getApplicationContext().getBean("velocityConfig");
		Template t = vc.getVelocityEngine().getTemplate(templatePath,"utf-8");//Velocity.getTemplate(templatePath, "utf-8");
	    context.put("paging", "common/includePagination.vm");
		StringWriter sw = new StringWriter();
		t.merge(context, sw);
		return sw.toString();
	}

}

