package com.cernet.util;

import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * Created by IntelliJ IDEA. User: pengxj Date: 2007-11-22 Time: 10:50:41 To
 * change this template use File | Settings | File Templates.
 */
public class PaginatedListImpl implements PaginatedList {
	// 当前页显示的数据
	private List list;
	// 当前所在页数
	private int pageNumber;
	// 每页显示数量
	private int objectsPerPage;
	// 数据总条目数
	private int fullListSize;
	// 排序集合
	private String sortCriterion;
	// 排序方向
	private SortOrderEnum sortDirection;
	// 缓存对象的id，当你缓存一个list时，用作list的缓存id
	private String searchId;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	public int getFullListSize() {
		return fullListSize;
	}

	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	public String getSortCriterion() {
		return sortCriterion;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public SortOrderEnum getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
}
