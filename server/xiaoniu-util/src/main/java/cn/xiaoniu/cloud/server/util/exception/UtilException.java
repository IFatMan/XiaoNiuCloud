package cn.xiaoniu.cloud.server.util.exception;

/**
 * 工具类异常信息
 *
 * @author 孔明
 * @date 2019/11/22 15:46
 * @description cn.xiaoniu.cloud.server.util.exception.UtilException
 */
public class UtilException extends RuntimeException {

    public UtilException(String msg) {
        super(msg);
    }

    public UtilException(Throwable e) {
        super(e);
    }

    public UtilException(Throwable e, String msg) {
        super(msg, e);
    }

    public static void throwe(String msg) {
        throw new UtilException(msg);
    }

    public static void throwe(Throwable e) {
        throw new UtilException(e);
    }
}
