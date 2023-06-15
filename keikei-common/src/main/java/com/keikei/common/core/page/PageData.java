package com.keikei.common.core.page;

import com.keikei.common.utils.StringUtils;

public class PageData {
    private Integer pageSize;
    private Integer pageNum;
    private String orderBy;
    private String isAsc;
    private Boolean reasonable = true;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderBy() {
        if(StringUtils.isEmpty(orderBy)){
            return "";
        }
        return orderBy + " " + this.isAsc;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getIsASC() {
        return isAsc;
    }

    public void setIsASC(String isASC) {
        this.isAsc = isASC;
    }

    public Boolean getReasonable() {
        return reasonable;
    }

    public void setReasonable(Boolean reasonable) {
        this.reasonable = reasonable;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", orderBy='" + orderBy + '\'' +
                ", isASC='" + isAsc + '\'' +
                ", reasonable=" + reasonable +
                '}';
    }
}
