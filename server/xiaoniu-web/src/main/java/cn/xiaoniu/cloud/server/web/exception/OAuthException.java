package cn.xiaoniu.cloud.server.web.exception;

import cn.xiaoniu.cloud.server.web.response.ResultStatus;

/**
 * @author 孔明
 * @date 2019/11/27 14:03
 * @description cn.xiaoniu.cloud.server.web.exception.OAuthException
 */
public class OAuthException extends RuntimeException {

    private ResultStatus status;

    public OAuthException(String msg) {
        this(ResultStatus.ERROR_OAUTH, msg);
    }

    public OAuthException(ResultStatus status, String msg) {
        super(msg);
        this.status = status;
    }

    public ResultStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "OAuthException{" +
                "status=" + status +
                ",msg=" + super.getMessage() +
                '}';
    }
}
