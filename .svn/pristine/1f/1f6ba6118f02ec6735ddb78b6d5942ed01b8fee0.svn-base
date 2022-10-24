package com.webdb.api.dto;

import java.util.List;

public class PagingDTO<E> {
	private int page = 1;
	private int limit = 50;
	private String sort;
	private String dir;
	private String queryType;
	private String query;
	private String searchType;
    private String keyword;
    private String keyword2;
	private String periodType;
	private String periodFrom;
	private String periodTo;
	private String period;
	private String parent;
	private List<Integer> checkedList;
	private E obj;
	private List<E> list;
	private long totalCount;

	private boolean isNanoomi;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query.trim();
	}

	public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
    	if (keyword != null) {
    		return keyword.trim();
    	}

        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword2() {
    	if (keyword2 != null) {
    		return keyword2.trim();
    	}

        return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}

	public String getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}

	public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<Integer> getCheckedList() {
        return checkedList;
    }

    public void setCheckedList(List<Integer> checkedList) {
        this.checkedList = checkedList;
    }

    public E getObj() {
		return obj;
	}

	public void setObj(E obj) {
		this.obj = obj;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		int totalPage = Integer.parseInt(Long.toString(totalCount / limit));

		if (totalCount % limit > 0) {
			totalPage++;
		}

		return totalPage;
	}

    public int getOffset() {
		return (page - 1) * limit;
	}

	public boolean isPageable() {
        return page > 0;
    }

	public boolean isNanoomi() {
		return isNanoomi;
	}

	public void setNanoomi(boolean isNanoomi) {
		this.isNanoomi = isNanoomi;
	}
}