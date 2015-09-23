package com.alice.common;

/**
 * 分页实体类
 * 
 * @author hyl
 * 
 */
public class Page {

    /**
     * 当前页数
     */
    private int page = 1;

    /**
     * 每页显示数目
     */
    private int pageSize = 2;

    private int start = 0;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (1 > page)
            page = 1;
        this.start = (page - 1) * pageSize;
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
