package cn.newcapec.foundation.report.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newcapec.foundation.report.core.ds.DataSource;
import cn.newcapec.foundation.report.core.ds.DefaultDatabaseSource;
import cn.newcapec.foundation.report.core.sql.SQLStringTools;
import cn.newcapec.foundation.report.core.sql.SqlFieldExtractor;
import cn.newcapec.foundation.report.core.sql.SqlMetaLoader;
import cn.newcapec.foundation.report.core.sql.SqlReader;
import cn.newcapec.foundation.report.core.sql.SqlReader.SqlObject;
import cn.newcapec.foundation.report.core.sql.simp.OracleSqlFieldExtractor;
import cn.newcapec.foundation.report.core.sql.simp.OracleSqlMetaLoader;
import cn.newcapec.foundation.report.util.StringUtils;

/**
 * 嵌套的TABLE定义</br>
 * 在FROM子句中存在嵌套的SELECT子句来作为表使用，如下：
 * <pre>
 * select ... from (select * from tb1 where ...) as t1, table2 t2
 * 在这种情况下，嵌套的表相当于一个用户定义的集合，是一个虚拟的表，
 * 它里面定义了自己的字段属性，所以这种表的元数据在数据库中是不存在的。
 * </pre>
 * @author shikeying
 *
 */
public class NestedTable extends Table {

	protected Log logger = LogFactory.getLog(this.getClass());
	
//	private String nestedSql;
	
	private List<Field> fields;
	
	/* 嵌套的表对象，如果存在该对象说明里面嵌套有select子句 */
	private NestedTable nestedTable;
	
	private SqlFieldExtractor singleTableExtractor;
	
	public NestedTable(String nestedFromSql, SqlFieldExtractor singleTableExtractor){
		assert (nestedFromSql != null && !nestedFromSql.equals(""));
		assert (singleTableExtractor != null);
		nestedFromSql = nestedFromSql.toLowerCase().trim();
		setSingleTableExtractor(singleTableExtractor);
		parse(nestedFromSql);
	}
	
	public NestedTable setSingleTableExtractor(SqlFieldExtractor singleTableExtractor) {
		this.singleTableExtractor = singleTableExtractor;
		return this;
	}

	/**
	 * 返回嵌套表对象中的所有字段信息
	 * @return
	 */
	public List<Field> getFieldList(){
		if(fields != null)
			return fields;
		
		if(nestedTable == null)
			throw new NullPointerException("nestedTable is required!");
		
		List<Field> list = nestedTable.getFieldList();
		if(list != null){
			logger.debug("找到嵌套的字段列表：" + list);
			return obtainCurrentFields(list, nestedTable.getAlias());
		} else if(nestedTable.nestedTable != null){
			return nestedTable.getFieldList();
		} else
			throw new UnsupportedOperationException();
	}
	
	/**
	 * 根据输入的字段名称，返回查找到的字段对象，系统优先按照别名查找，如果无法找到，直接按照字段名。
	 * @param fieldName 子段名称
	 * @param fieldAlias 字段别名
	 * @return
	 * @throws NullPointerException 如果无法找到，抛出异常
	 */
	public Field getFieldDetail(String fieldName, String fieldAlias){
		List<Field> list = getFieldList();
		if(!StringUtils.isEmpty(fieldAlias)){
			for(Field f : list){
				if(f.getAliasName() != null && f.getAliasName().equals(fieldAlias))
					return f;
			}
		} else {
			for(Field f : list){
				if(f.getFieldName() != null && f.getFieldName().equals(fieldName))
					return f;
			}
		}
//		throw new NullPointerException("field not found: " + fieldName);
		return null;
	}
	
	protected List<Field> getFields() {
		return fields;
	}

	private List<Field> obtainCurrentFields(List<Field> nestedList, String tableAlias){
		List<Field> result = new ArrayList<Field>(8);
		Field f = null;
		for(Field field : nestedList){
			f = new Field();
			if(!StringUtils.isEmpty(field.getAliasName())){
				f.setAliasName(field.getAliasName())
					.setFieldName(field.getAliasName());
			} else if(!StringUtils.isEmpty(field.getFieldName())){
				f.setAliasName(field.getFieldName())
				.setFieldName(field.getFieldName());
			} else
				throw new RuntimeException("嵌套查询中的字段信息，既没有字段名又没有别名，field = " + field);
			f.setComment(field.getComment());
			f.setTableName(tableAlias);
			result.add(f);
		}
		logger.debug("返回组装好的嵌套字段信息: " + result);
		return result;
	}
	
	private void parse(String nestedFromSql){
		String nestedSql = null;
		if(nestedFromSql.startsWith("(") && nestedFromSql.endsWith(")")){
			nestedSql = nestedFromSql.substring(1, nestedFromSql.length()-1);
			setAlias(null);
		} else {
			int lastBracketInx = nestedFromSql.lastIndexOf(")");
			int lastSpaceInx = nestedFromSql.lastIndexOf(" ");
			if(lastSpaceInx > lastBracketInx && lastBracketInx > 0){
				nestedSql = nestedFromSql.substring(1, lastBracketInx);
				setAlias(nestedFromSql.substring(lastSpaceInx+1, nestedFromSql.length()));
			} else 
				throw new IllegalArgumentException("parse nestedFromSql failed: " + nestedFromSql);
		}
		
		logger.debug("分析得到的select语句: " + nestedSql);
		SqlObject sqlObj = getSqlObject(nestedSql);
		String _fromSql = sqlObj.getFrom();
		if(!SQLStringTools.containNestedFrom(_fromSql)){
			logger.debug("没有嵌套表了，返回对象。");
			singleTableExtractor.setSql(sqlObj.getSelect(), _fromSql);
			// 所有字段信息
			fields = singleTableExtractor.getFieldList();
			// 表信息
			if(singleTableExtractor.isSingleTable()){
				Table tbl = singleTableExtractor.getTable();
				setName(tbl.getName());
			}
//			setAlias(alias);
			
		} else {
			logger.debug("包含有嵌套对象，继续递归查找。");
			this.nestedTable = new NestedTable(_fromSql, singleTableExtractor);
		}
	}
	
	private SqlObject getSqlObject(String sql){
		SqlReader reader = new SqlReader();
		reader.setSql(sql);
		SqlObject result = reader.getResult();
		if(result == null)
			throw new NullPointerException("从SQL语句中无法解析SqlObject对象: " + sql);
		return result;
	}
	
	public static void main(String[] args){
		DataSource ds = new DefaultDatabaseSource();
		SqlMetaLoader sqlMetaloader = new OracleSqlMetaLoader();
		sqlMetaloader.setDataSource(ds);
		
		SqlFieldExtractor sfe = new OracleSqlFieldExtractor();
		sfe.setSqlMetaLoader(sqlMetaloader);
		
		StringBuilder input = new StringBuilder();
		input.append("(");
		input.append("select t2.detailname as cardtype,");
		input.append("nvl (sum (t1.operno), 0) operno,");
		input.append("nvl (sum (t1.opermn), 0) opermoney ");
//		input.append("0 as viceoperno,");
		input.append("from st_payment_day t1 ");
		input.append("left join base_cardtype_detail t2 ");
		input.append("on t1.cardtype = t2.detailid ");
		input.append(" left join base_emp ");
		input.append("on t1.empid = base_emp.empid ");
		input.append("left join base_acc_type a ");
		input.append("on t1.acccode = a.acccode ");
		input.append("where t1.balancedate >= begindate ");
		input.append("group by t1.cardtype, t2.detailname");
		input.append(")");
		
		NestedTable table = new NestedTable(input.toString(), sfe);
		table.setSingleTableExtractor(sfe);
		System.out.println(table.getFieldList());
	}
}
