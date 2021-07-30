package com.chen.sample2.tool.message;

/**
 * @author ChenTian
 * @date 2019/10/24
 */
public class PageInfo {
    private int pageNumber;
    private int pageSize;

    public PageInfo() {
    }

    public PageInfo(Integer pageNumber) {
        if(pageNumber != null) {
            this.pageNumber = pageNumber;
        }
    }

    public PageInfo(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
