package org.jmu.multiinfo.core.dto;

public class BaseQueryCondition {
    /**
     * 当前页数
     */
    protected Integer page;
    /**
     * 每页显示的行数
     */
    protected Integer rows;
    /**
     * 排序字段
     */
    protected String  sort;
    /**
     * 排序方式
     */
    protected String  order;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
    
    
}
