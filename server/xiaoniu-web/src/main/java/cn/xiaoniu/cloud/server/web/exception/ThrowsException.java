package cn.xiaoniu.cloud.server.web.exception;

/**
 * @author 孔明
 * @date 2019/11/27 14:10
 * @description cn.xiaoniu.cloud.server.web.exception.ThrowsException
 */
public class ThrowsException {

    private ThrowsException() {
    }

    /**
     * 权限异常
     *
     * @param msg 异常提示信息
     * @author 孔明
     * @date 2019-11-27 14:11:04
     */
    public static void oauthException(String msg) {
        throw new OAuthException(msg);
    }

    /**
     * 抛出请求参数异常
     *
     * @param msg 异常提示信息
     * @return void
     * @author 孔明
     * @date 2019-11-27 14:12:51
     */
    public static void requestParamException(String msg) {
        throw new RequestParamException(msg);
    }

}
