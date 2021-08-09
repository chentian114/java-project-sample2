package com.chen.sample2.tool.message;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 分页请求消息对象
 * @author ChenTian
 */
public class RequestMsg implements Serializable {
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 10;
    public static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "createTime");

    /**
     *  查询页数，从0开始
     */
    private int pageNum = DEFAULT_PAGE;

    /**
     * 每页最大数量
     */
    private int pageSize = DEFAULT_SIZE;


    /** 查询请求参数{"key1":"value1","key2":"value2"} */
     private JSONObject params;

    public RequestMsg() {
    }

    public RequestMsg(Integer pageNum, Integer pageSize) {
        if(pageNum != null && pageNum >= 0) {
            this.pageNum = pageNum;
        }
        if(pageSize != null && pageSize >= 0) {
            this.pageSize = pageSize;
        }
    }

    public RequestMsg(Object params, Integer pageNum, Integer pageSize) {
        this(pageNum,pageSize);
        if(params==null){
            return;
        }
        this.params = JSONUtil.parseObj(params);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        if(pageSize==0){
            return DEFAULT_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }
}
