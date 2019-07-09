package com.atguigu.gmall.entity;

import java.io.Serializable;

public class SkuParam implements Serializable {
    private String catalog3Id;
    private String keyword;
    String[] valueId;
    int pageNo=1;
    int pageSize=2;
    Integer pageTotal;//总页码
    static Integer pageTotalCount=0;//总记录数
    public SkuParam() {
    }

    public SkuParam(String catalog3Id, String keyword, String[] valueId, int pageNo, int pageSize) {
        this.catalog3Id = catalog3Id;
        this.keyword = keyword;
        this.valueId = valueId;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String[] getValueId() {
        return valueId;
    }

    public void setValueId(String[] valueId) {
        this.valueId = valueId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public static Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public static void setPageTotalCount(Integer pageTotalCount) {
        SkuParam.pageTotalCount = pageTotalCount;
    }
}
