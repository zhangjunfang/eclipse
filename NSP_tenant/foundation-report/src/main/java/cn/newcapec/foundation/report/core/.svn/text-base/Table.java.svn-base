package cn.newcapec.foundation.report.core;

public class Table {

	private String name;
	private String alias;
	
	public String getName() {
		return name;
	}
	public Table setName(String name) {
		assert (name != null && !name.equals(""));
		this.name = name;
		return this;
	}
	public String getAlias() {
		if(alias == null || alias.equals(""))
			return name;
		return alias;
	}
	public Table setAlias(String alias) {
		this.alias = alias;
		return this;
	}
	
	public int hashCode(){
		return 31 + name.hashCode()*13;
	}
	
	public boolean equals(Object o){
		if(o == null) return false;
		if(o instanceof Table){
			Table t = (Table)o;
			if(t.name.equals(this.name))
				return true;
		}
		return false;
	}
	
	public String toString(){
		return new StringBuilder().append("{tableName:").append(name)
				.append(", aliasName:").append(alias).append("}").toString();
	}
}
