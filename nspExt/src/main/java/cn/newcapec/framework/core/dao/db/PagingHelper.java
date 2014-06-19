package cn.newcapec.framework.core.dao.db;

public class PagingHelper {
	private int pageSize = 0;

	private int pageIndex = 1;

	private int totalCount = -1;

	private int fromIndex = 0;

	private int toIndex = 0;

	private int pageCount = 0;

	private static int DEFAULT_MAX_PAGING_LINK = 10;// 返回固定的链接数值

	/**
	 * 根据当前页，总共页数显示客户端的分页序号
	 * 
	 *
	 */
	public int[] getPaginLinks() {

		if (this.pageCount == 0) {
			return new int[] { 1 };
		}

		if (this.pageIndex > this.pageCount) {
			this.pageIndex = this.pageCount;
		}

		// 计算开始位置
		int firstNo = this.pageIndex - DEFAULT_MAX_PAGING_LINK / 2;
		int offset = firstNo - 1;
		// 计算结束位置
		int endNo = this.pageIndex + DEFAULT_MAX_PAGING_LINK / 2 - 1;
		int endoffset = endNo - this.pageCount;// 结束的偏移量
		if (offset < 0) {// 计算开始是否越界
			if (endoffset > 0) { // 结束也越界
				firstNo = 1;
			} else {
				endNo = endNo - offset;
				endoffset = endNo - this.pageCount;
			}
		}
		if (endoffset > 0) {
			if (offset < 0) {
				endNo = this.pageCount;
			} else {
				firstNo = firstNo - endoffset;
			}
		}
		if (firstNo < 1) {
			firstNo = 1;
		}
		if (endNo > this.pageCount) {
			endNo = this.pageCount;
		}
		if (endNo == 0) {
			endNo = 1;
		}

		int[] nums = new int[endNo - firstNo + 1];
		int i = 0;
		while (firstNo <= endNo) {
			nums[i++] = firstNo;
			firstNo++;
		}
		return nums;
	}

	/**
	 * 构造方法.
	 * 
	 * @param pageSize
	 * @param pageIndex
	 */
	public PagingHelper(int pageSize, int pageIndex) {
		doSetPageSize(pageSize);
		doSetPageIndex(pageIndex);
		calc();
	}

	/**
	 * 构造方法.
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @param totalCount
	 */
	public PagingHelper(int pageSize, int pageIndex, int totalCount) {
		doSetPageSize(pageSize);
		doSetPageIndex(pageIndex);
		this.totalCount = totalCount;
		calc();
	}

	/**
	 * 计算记录开始序号，结束序号
	 * 
	 */
	private void calc() {
		if ((this.totalCount > -1)
				&& (this.pageSize * (this.pageIndex - 1) > this.totalCount))
			this.pageIndex = ((this.totalCount - 1) / this.pageSize + 1);
		this.fromIndex = (this.pageSize * (this.pageIndex - 1));
		this.toIndex = (this.fromIndex + this.pageSize);
		if ((this.totalCount > -1) && (this.toIndex > this.totalCount))
			this.toIndex = this.totalCount;
		if (this.totalCount > -1) {
			if (this.totalCount > 0) {
				this.pageCount = ((this.totalCount - 1) / this.pageSize + 1);
				return;
			}
			this.pageCount = 0;
			return;
		}
		this.pageCount = this.pageIndex;
	}

	/**
	 * 校正并设置页号.
	 * 
	 * @param pageIndex
	 */
	private void doSetPageIndex(int pageIndex) {
		if (pageIndex < 1)
			pageIndex = 1;
		this.pageIndex = pageIndex;
	}

	/**
	 * 
	 * 
	 * @param pageSize
	 */
	private void doSetPageSize(int pageSize) {
		if (pageSize < 0)
			pageSize = 0;
		this.pageSize = pageSize;
	}

	/**
	 * 设置页号。 注意：此处的页号是从1开始计算。
	 * 
	 * @param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		doSetPageIndex(pageIndex);
		calc();
	}

	/**
	 * 设置每页的记录数.
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		doSetPageSize(pageSize);
		calc();
	}

	/**
	 * 设置结果集的总记录数.
	 * 
	 * @param totalCount
	 */
	public void setTotalRow(int totalCount) {
		this.totalCount = totalCount;
		calc();
	}

	/**
	 * 返回当前页号.
	 * 
	 * @return
	 */
	public int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * 返回每页的记录数.
	 * 
	 * @return
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * 返回总记录数.
	 * 
	 * @return
	 */
	public int getTotalRow() {
		return this.totalCount;
	}

	/**
	 * 返回当前页记录的起始序号。
	 * 
	 * @return
	 */
	public int getFromIndex() {
		return this.fromIndex;
	}

	/**
	 * 返回当前页记录的结束序号。 注意：此处的结束序号的返回值等于当前页最后一条记录的序号加1。 结束序号代表的记录并不属于当前页。
	 * 
	 * @return
	 */
	public int getToIndex() {
		return this.toIndex;
	}

	/**
	 * 返回总页数。
	 * 
	 * @return
	 */
	public int getPageCount() {
		return this.pageCount;
	}

}