package cn.xiaoniu.cloud.server.web.util;

import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import cn.xiaoniu.cloud.server.util.context.ZGMContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * LogUtil 日志工具类
 *
 * @author 孔明
 * @date 2019/11/27 10:28
 * @description cn.xiaoniu.cloud.server.web.util.Log
 */
public class Log {

    private Log() {
    }

    // ======================================== INFO 级别日志 ========================================

    /**
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @author 孔明
     * @date 2019-11-27 10:33:08
     */
    public static void info(String format, Object... arguments) {
        logger().info(format(format), arguments);
    }

    // ======================================== DEBUG 级别日志 ========================================

    /**
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @author 孔明
     * @date 2019-11-27 10:33:08
     */
    public static void debug(String format, Object... arguments) {
        Logger logger = logger();
        if (logger.isDebugEnabled()) {
            logger.debug(format(format), arguments);
        }
    }

    // ======================================== ERROR 级别日志 ========================================

    /**
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @author 孔明
     * @date 2019-11-27 10:33:08
     */
    public static void error(String format, Object... arguments) {
        logger().error(format(format), arguments);
    }

    /**
     * @param format the format string
     * @param t      the exception (throwable) to log
     * @author 孔明
     * @date 2019-11-27 10:33:08
     */
    public static void error(String format, Throwable t) {
        logger().error(format(format), t);
    }

    // ======================================== WARN 级别日志 ========================================

    /**
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @author 孔明
     * @date 2019-11-27 10:33:08
     */
    public static void warn(String format, Object... arguments) {
        logger().warn(format(format), arguments);
    }

    // ======================================== TRACE 级别日志 ========================================


    /**
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @author 孔明
     * @date 2019-11-27 10:33:08
     */
    public static void trace(String format, Object... arguments) {
        logger().trace(format(format), arguments);
    }

    // =============================================== 工具 ===========================================

    /**
     * 格式化字符串<br />
     * 如果存在 request_id 则添加 , 没有则不添加
     *
     * @param format 字符串
     * @return java.lang.String
     * @author 孔明
     * @date 2019-11-27 11:11:15
     */
    private static String format(String format) {
        ZGMContext.Context context = ZGMContext.getContext();
        if (Objects.isNull(context) || ZGMContext.getContext().getRequestId() == null) {
            return format;
        }
        return String.format(CommonConstant.REQUEST_ID_LOG, ZGMContext.getContext().getRequestId(), format);
    }


    /**
     * 获取Logger 对象
     *
     * @return org.slf4j.Logger
     * @author 孔明
     * @date 2019-11-27 10:30:57
     */
    private static Logger logger() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        return LoggerFactory.getLogger(stackTraceElements[3].getClassName());
    }
}
