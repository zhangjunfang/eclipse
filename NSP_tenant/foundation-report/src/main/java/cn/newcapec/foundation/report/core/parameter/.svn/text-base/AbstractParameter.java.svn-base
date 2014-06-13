package cn.newcapec.foundation.report.core.parameter;

import cn.newcapec.foundation.report.core.DataType;

public abstract class AbstractParameter implements Parameter {

	private String name;
	private DataType dt;
	private String alias;
	
	public AbstractParameter(){}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public Parameter setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		return this;
	}

	@Override
	public DataType getType() {
		// TODO Auto-generated method stub
		return this.dt;
	}

	@Override
	public Parameter setType(DataType type) {
		// TODO Auto-generated method stub
		this.dt = type;
		return this;
	}

	@Override
	public boolean isInput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOutput() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isOutCursor(){
		return false;
	}

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return this.alias;
	}

	@Override
	public Parameter setAlias(String alias) {
		// TODO Auto-generated method stub
		this.alias = alias;
		return this;
	}
	
	public String toString(){
		return new StringBuilder().append("{name=").append(name)
				.append(", dataType=").append(dt)
				.append(", alias=").append(alias).append("}").toString();
	}
}
