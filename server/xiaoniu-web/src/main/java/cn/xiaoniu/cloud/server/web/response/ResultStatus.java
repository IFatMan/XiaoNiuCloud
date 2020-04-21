package cn.xiaoniu.cloud.server.web.response;

public enum ResultStatus {
    /**
     * 成功
     */
    SUCCESS(2000),

    /**
     * 请求错误 例如:参数不能为空,类型转换异常,未根据参数查询到信息等
     */
    ERROR_REQUEST(3000),

    /**
     * 系统异常 比如:空指针,数组越界
     */
    ERROR_SYSTEM(4000),

    /**
     * 微服务内部通信异常
     */
    ERROR_FEIGN(5000),

    /**
     * 权限异常
     */
    ERROR_OAUTH(6000),

    /**
     * 权限异常-未登录
     */
    ERROR_OAUTH_NOT_LOGIN(6001),

    /**
     * 权限异常-AccessToken过期
     */
    ERROR_OAUTH_ACCESS_TOKEN_OVERDUE(6002),

    /**
     * 权限异常-RefreshToken过期
     */
    ERROR_OAUTH_REFRESH_TOKEN_OVERDUW(6003),

    /**
     * 特殊状态值 1
     */
    ERROR_SPECIAL_ONE(7000),

    /**
     * 特殊状态值 2
     */
    ERROR_SPECIAL_TWO(800),

    /**
     * 未知异常
     */
    ERROR_UNKNOW(9000);

    private Integer code;

    private ResultStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
