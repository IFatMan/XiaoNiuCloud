package cn.xiaoniu.cloud.server.web.entity;

import java.io.Serializable;

/**
 * Entity基类
 *
 * @author 孔明
 * @date 2019/11/22 15:30
 * @description cn.xiaoniu.cloud.server.common.web.base.BaseEntity
 */
public class BaseEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 是否删除 0:未删除 ; 1:已删除
     */
    private Integer del;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 当前页码
     */
    private Integer pageNo;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 初始化分页
     *
     * @param pageNo   页码
     * @param pageSize 每页数据量
     */
    public void initPage(int pageNo, int pageSize) {
        initPage(pageNo, pageSize, null);
    }

    /**
     * 初始化分页
     *
     * @param pageNo   页码
     * @param pageSize 每页数据量
     */
    public void initPage(int pageNo, int pageSize, String orderBy) {
        if (pageNo < 1) {
            pageNo = 1;
        }

        if (pageSize < 1) {
            pageSize = 0;
        }

        this.pageNo = (pageNo - 1) * pageSize;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
