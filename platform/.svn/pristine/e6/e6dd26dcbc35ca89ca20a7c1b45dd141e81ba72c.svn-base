package cn.newcapec.framework.core.handler;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import cn.newcapec.framework.core.logs.LogEnabled;

/**
 * 动态读取配置文件属性
 * @author andy.li
 *
 */
@Component
public class ConfigurationRead implements LogEnabled {


	/**
	 * 属性文件所对应的属性对象变量
	 */
	private Properties m_props = null;
	
	private ConfigurationRead(){
		
	}

	/**
	 * 私有构造函数
	 * 
	 * @throws URISyntaxException
	 */
	public ConfigurationRead(List<Resource> pFile) {
		try {
			
			if(pFile!=null && pFile.size()>0){
				for(int i=0;i<pFile.size();i++){
					m_props = new Properties();
//					InputStream is = getClass().getClassLoader().getResourceAsStream(pFile.get(i));
					m_props.load(pFile.get(i).getInputStream());
			        // 返回Properties中包含的key-value的Set视图  
			        Set<Entry<Object, Object>> set = m_props.entrySet();  
			        // 返回在此Set中的元素上进行迭代的迭代器  
			        Iterator<Map.Entry<Object, Object>> it = set.iterator();  
			        String key = null, value = null;  
			        // 循环取出key-value  
			        while (it.hasNext()) {  
			            Entry<Object, Object> entry = it.next();  
			            key = String.valueOf(entry.getKey());  
			            value = String.valueOf(entry.getValue());  
			            key = key == null ? key : key.trim();  
			            value = value == null ? value : value.trim();  
			            // 将key-value放入map中  
			            UrlMapping.loadUrlMap.put(key, value);  
			        }  
				}
				
			}

		} catch (Exception e) {
			log.error("文件读取异常");
		}
	}
}