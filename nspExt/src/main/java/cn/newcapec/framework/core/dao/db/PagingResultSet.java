package cn.newcapec.framework.core.dao.db;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.utils.clazzUtils.BeanUtils;

@SuppressWarnings("all")
public class PagingResultSet implements Serializable,LogEnabled {

	private int currRow;

	private long rowCount;

	private int pageCount;

	private int pageIndex;

	private int pageSize;

	private ResultSet resultSet;

	private Class modelClass;

	private List list;

	private PagingResultSet() {

	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public PagingResultSet(ResultSet rst, Class modelClass) {
		currRow = 0;// 当前行
		rowCount = 0;
		resultSet = rst;
		this.modelClass = modelClass;
		list = new ArrayList();
	}

	private void calcEndRow() throws SQLException {
		if (resultSet.getType() != 1003) {
			resultSet.last();
			currRow = resultSet.getRow();
		} else {
			while (isLast())
				;
		}
		if (currRow < 0)
			currRow = 0;
		rowCount = currRow;
	}

	/**
	 * 
	 * @throws SQLException
	 */
	private void calPageCount() throws SQLException {
		if (pageSize > 0)
			pageCount = currRow % pageSize != 0 ? currRow / pageSize + 1
					: currRow / pageSize;
		else
			pageCount = 1;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	private void setPageIndex(int pageIndex) {
		if (pageIndex < 1)
			this.pageIndex = 1;
		else
			this.pageIndex = pageIndex;
	}

	private void setPageSize(int pageSize) {
		if (pageSize < 1)
			this.pageSize = 0;
		else
			this.pageSize = pageSize;
	}

	private void updateList() throws Exception {
		try {

			caculFirstRow();
			for (int i = 0; i < pageSize && isLast(); i++) {
				list.add(BeanUtils.resultSetToDO(resultSet, modelClass, true));
			}

		} finally {

		}

	}

	public List getData() {
		return list;
	}

	/**
	 * 计算开始行
	 * 
	 * @throws SQLException
	 */
	private void caculFirstRow() throws SQLException {
		log.info("resultSet.getType:"+resultSet.getType());
		if (resultSet.getType() != 1003) {
			int startRow = 1 * (pageIndex - 1);
			if (startRow == 0) {
				resultSet.beforeFirst();
			} else {
				resultSet.absolute(startRow);
			}
			currRow = resultSet.getRow();
			if (currRow < 0) {
				currRow = 0;
			}
		} else {
			int startRow = pageSize * (pageIndex - 1);
			for (int i = 0; i < startRow && isLast(); i++)
				;
		}
	}

	private boolean isLast() throws SQLException {
		if (resultSet.next()) {
			currRow++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO 目前基于resultset实现，后续可基于各版本的数据库分页机制实现
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	public int tranlateData(int pageSize, int pageIndex) throws Exception {

		setPageSize(pageSize);
		setPageIndex(pageIndex);
		if (list != null)
			updateList();
		calcEndRow();
		calPageCount();
		return pageCount;
	}

}
