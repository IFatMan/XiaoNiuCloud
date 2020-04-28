package com.xiao.niu.bean;

/**
 * author : 郑振楠
 * date   : 2020/4/27
 */
public class LoginBean {

    /**
     * status : 2000
     * msg : SUCCESS
     * data : {"id":743360084185088,"del":0,"createTime":1587622560598,"updateTime":null,"pageNo":null,"pageSize":null,"orderBy":null,"name":"test1","password":"96e79218965eb72c92a549dd5a330112","account":"1234567@163.com","token":"24A2A09D6E7E4344B3EA05B9A9673265"}
     */

    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 743360084185088
         * del : 0
         * createTime : 1587622560598
         * updateTime : null
         * pageNo : null
         * pageSize : null
         * orderBy : null
         * name : test1
         * password : 96e79218965eb72c92a549dd5a330112
         * account : 1234567@163.com
         * token : 24A2A09D6E7E4344B3EA05B9A9673265
         */

        private long id;
        private int del;
        private long createTime;
        private Object updateTime;
        private Object pageNo;
        private Object pageSize;
        private Object orderBy;
        private String name;
        private String password;
        private String account;
        private String token;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getPageNo() {
            return pageNo;
        }

        public void setPageNo(Object pageNo) {
            this.pageNo = pageNo;
        }

        public Object getPageSize() {
            return pageSize;
        }

        public void setPageSize(Object pageSize) {
            this.pageSize = pageSize;
        }

        public Object getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(Object orderBy) {
            this.orderBy = orderBy;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
