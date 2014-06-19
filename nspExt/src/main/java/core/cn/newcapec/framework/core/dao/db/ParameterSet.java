package cn.newcapec.framework.core.dao.db;

import cn.newcapec.framework.core.model.variant.Variant;
import cn.newcapec.framework.core.model.variant.VariantSet;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class ParameterSet extends VariantSet {

	private Map b = new HashMap();

	/**
	 * 根据键值并按Variant类型读取数据
	 */
	public Variant getVariant(String key) {
		String str = key.toLowerCase();
		return super.getVariant(str);
	}

	/**
	 * 根据键值设置Variant类型的数据
	 */
	public void setVariant(String name, Variant variant) {
		String str = name.toLowerCase();
		this.b.put(str, name);
		super.setVariant(str, variant);
	}

	/**
	 * 删除给定键值及其下的数值.
	 */
	public void remove(String key) {
		String str = key.toLowerCase();
		this.b.remove(str);
		super.remove(str);
	}

	/**
	 * 返回给定的索引号对应的键值.
	 */
	public String indexToName(int index) {
		String key;
		String name = super.indexToName(index);
		if ((key = (String) this.b.get(name)) == null)
			key = name;
		return key;
	}
}