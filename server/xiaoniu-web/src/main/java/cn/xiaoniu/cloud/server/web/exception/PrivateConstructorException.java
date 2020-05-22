package cn.xiaoniu.cloud.server.web.exception;

/**
 * 私有构造器异常
 *
 * @author 孔明
 * @date 2020/5/13 15:53
 * @description cn.xiaoniu.cloud.server.web.exception.PrivateConstructorException
 */
public class PrivateConstructorException extends RuntimeException {

    private static final String MSG = "当前类不可使用构造器创建";

    public PrivateConstructorException() {
        super(MSG);
    }
}
