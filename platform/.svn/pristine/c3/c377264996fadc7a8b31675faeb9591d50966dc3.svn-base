package cn.newcapec.framework.core.model.dbmeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 *  @author andy.li
 */
@SuppressWarnings("all")
public class DBTable implements Comparable {

	private String name;

	private String metaData;

	private Container container;

	private List columns;

	private Map pKeys;

	private Map fKeys;

	private Map columnMap;

	public void removeColumn(String columnName) {
		DBColumn column = getColumn(columnName);
		pKeys.remove(column);
		fKeys.remove(column);
		columnMap.remove(column.getName());
		columns.remove(column);
	}

	public DBTable(Container container) {
		columns = new ArrayList();

		columnMap = new HashMap();
		this.container = container;
	}

	public DBTable(Container container, String name) {
		columns = new ArrayList();

		columnMap = new HashMap();
		setName(name);
		this.container = container;
	}

	public DBTable(String name, Container container) {
		columns = new ArrayList();

		columnMap = new HashMap();
		setName(name);
		this.container = container;
	}

	public void init() {
		if (pKeys == null)
			pKeys = new HashMap();
		if (fKeys == null)
			fKeys = new HashMap();

	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public void notifyPrimaryKey(String columnName) {
		DBColumn column = (DBColumn) columnMap.get(columnName);
		if (column != null) {
			if (pKeys == null)
				pKeys = new HashMap();
			pKeys.put(column.getName(), column);
			column.primaryKey = true;
		}
	}

	public void notifyColumn(DBColumn column) {
		column.setTable(this);
		columns.add(column);
		columnMap.put(column.getName(), column);
	}

	public void notifyForeignKey(String columnName, DBColumn parentKey) {
		DBColumn column = (DBColumn) columnMap.get(columnName);
		if (column != null) {
			column.fkParentKey = parentKey;
			if (fKeys == null)
				fKeys = new HashMap();
			fKeys.put(column.getName(), column);
		}
	}

	protected Properties getProperties() {
		return container.getProperties();
	}

	public boolean hasCompositeKey() {
		return getPkColumns().size() > 1;
	}

	public boolean isCompositeKeyOnly() {
		return getPkColumns().size() > 1
				&& getPkColumns().size() == getColumns().size();
	}

	public boolean isForeignKey(DBColumn column) {
		if (fKeys == null)
			return false;
		return fKeys.get(column.getName()) != null;
	}

	public Collection getFKColumns() {
		if (fKeys == null)
			return new ArrayList(0);
		else
			return fKeys.values();
	}

	public Collection getPkColumns() {
		if (pKeys == null)
			return new ArrayList(0);
		else
			return pKeys.values();
	}

	public String getName() {
		return name;
	}

	public String getClassName() {
		return getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getColumns() {
		return columns;
	}

	public DBColumn getColumn(String columnName) {
		return (DBColumn) columnMap.get(columnName);
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public int compareTo(Object obj) {
		if (obj == null || !(obj instanceof DBTable))
			return -1;
		else
			return getName().compareTo(((DBTable) obj).getName());
	}
}
