package com.chen.sample2.tool.message;

import java.util.List;

/**
 * 分页查询返回结果对象
 * @author ChenTian
 * @date 2019/11/4
 */
public class PageResult<T> {

    /** 当前页码 */
    private int pageNumber;
    /** 每页数据数 */
    private int pageSize;

    /** 元素总数量 */
    private long totalElements;
    /** 总页数*/
    private long totalPages;

    /** 分页查询结果列表 */
    private List<T> contentList;

    public PageResult() {
    }


    public PageResult(int pageNumber, int pageSize, long totalElements, long totalPages, List<T> contentList) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.contentList = contentList;
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

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContentList() {
        return contentList;
    }

    public void setContentList(List<T> contentList) {
        this.contentList = contentList;
    }
}
