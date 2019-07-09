package com.atguigu.gmall.entity;

import java.util.List;

public class Page<T> {
	
	public static final int PAGE_SIZE=4;
	
	private Integer pageno;// 当前页码
	private Integer pageTotal;// 总页码
	private Long pageTotalCount;// 总记录数
	/*private List<T> items;// 当前页数据*/
	private Integer pageSize;// 每页显示几条数据
	private String url;//请求资源路径


	public Page() {
	}

	public Page(Integer pageno, Integer pageTotal, Long pageTotalCount, Integer pageSize, String url) {
		this.pageno = pageno;
		this.pageTotal = pageTotal;
		this.pageTotalCount = pageTotalCount;
		this.pageSize = pageSize;
		this.url = url;
	}

	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		if(pageno<1){
			pageno=1;
		}
		if(pageno>this.pageTotal){
			pageno=this.pageTotal;
		}

		this.pageno = pageno;
	}

	public static int getPageSize() {
		return PAGE_SIZE;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public Long getPageTotalCount() {
		return pageTotalCount;
	}

	public void setPageTotalCount(Long pageTotalCount) {
		this.pageTotalCount = pageTotalCount;
	}

	@Override
	public String toString() {
		return "Page{" +
				"pageno=" + pageno +
				", pageTotal=" + pageTotal +
				", pageTotalCount=" + pageTotalCount +
				", pageSize=" + pageSize +
				", url='" + url + '\'' +
				'}';
	}
}
