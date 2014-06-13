package cn.newcapec.foundation.report.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class StringTools {

	/**
	 * 把字符串转换成Map对象，此方法用于把业务过滤字段转换到Map中
	 * <pre>
	 * 格式如：prmdpid/DpID,prmdcode/pcode,...
	 * 其中多个以逗号分隔，前面是数据库中对应的字段名，后面是用户业务定义的参数名（映射名）
	 * </pre>
	 * @param val
	 * @param outSeparator
	 * @param innerSeparator
	 * @return
	 */
	public static final Map<String, String> transStringToMap(String val
			, String outSeparator, String innerSeparator){
		Assert.notNull(val);
		Assert.notNull(outSeparator);
		Assert.notNull(innerSeparator);
		
		Map<String, String> result = new HashMap<String, String>(2);
		
		val = val.replace("，", ",").trim();
		String[] pArray = val.split(outSeparator);
		if(pArray != null){
			String _dbname, _mapname;
			String[] _names;
			for(String s : pArray){
				_names = s.split(innerSeparator);
				if(_names != null){
					_dbname = _names[0];
					_mapname= _names[1];
					result.put(_dbname, _mapname);
				} else
					throw new IllegalArgumentException("wrong map names: " + s);
			}
			return result;
		} else
			throw new IllegalArgumentException("wrong parameter: " + val
					+ ", e.g. prmdpid/DpID,prmdcode/pcode,...");
	}
}
