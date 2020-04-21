package cn.xiaoniu.cloud.server.web.exception;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.util.RegexUtil;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.web.response.Result;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import cn.xiaoniu.cloud.server.web.util.Log;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 全局异常捕获
 *
 * @author 孔明
 * @date 2019/11/27 13:39
 * @description cn.xiaoniu.cloud.server.web.exception.GlobalExceptionResolver
 */
@RestControllerAdvice
public class GlobalExceptionResolver {

    /**
     * 捕获 UtilException
     *
     * @param e cn.xiaoniu.cloud.server.util.exception.UtilException
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2019-11-27 13:45:39
     */
    @ExceptionHandler(UtilException.class)
    public Result handlerUtilException(UtilException e) {
        Log.error("系统工具类异常:", e);
        return Result.fail(ResultStatus.ERROR_SYSTEM, "服务器开小差啦,请稍后重试 !");
    }

    /**
     * 捕获路径错误异常<br/>
     * spring boot 需配置 spring.mvc.throw-exception-if-no-handler-found=true
     *
     * @param e 路径错误异常
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2019-12-19 10:40:46
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNoHandlerFoundException(NoHandlerFoundException e) {
        Log.error("路径错误:", e.getRequestURL());
        return Result.fail(ResultStatus.ERROR_REQUEST, String.format("路径错误:%s", e.getRequestURL()));
    }

    /**
     * 捕获请求方法错误
     *
     * @param e 请求方法错误异常
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2019-12-19 10:46:48
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Log.error("请求方法错误:", e.getMethod());
        return Result.fail(ResultStatus.ERROR_REQUEST, String.format("请求方法错误:%s", e.getMethod()));
    }

    /**
     * 捕获参数缺少异常
     *
     * @param e 参数缺少异常
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2019-12-19 09:58:55
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        List<String> params = RegexUtil.getMatcherText(e.getMessage(), "'\\w*'");
        if (CollUtil.isNotEmpty(params)) {
            String param = params.get(0).replaceAll("'", "");
            Log.error("缺少请求参数:", param);
            return Result.fail(ResultStatus.ERROR_REQUEST, String.format("缺少请求参数:%s", param));
        }
        Log.error("缺少请求参数:", e);
        return Result.fail(ResultStatus.ERROR_REQUEST, e.getMessage());
    }

    /**
     * 请求参数异常
     *
     * @param e cn.xiaoniu.cloud.server.web.exception.RequestParamException
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2019-11-27 13:54:27
     */
    @ExceptionHandler(RequestParamException.class)
    public Result handlerRequestParamException(RequestParamException e) {
        Log.error("用户请求参数错误:{}", e.getMessage());
        return Result.fail(ResultStatus.ERROR_REQUEST, e.getMessage());
    }

    /**
     * 捕获用户权限异常
     *
     * @param e cn.xiaoniu.cloud.server.web.exception.OAuthException
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2019-11-27 14:06:21
     */
    @ExceptionHandler(OAuthException.class)
    public Result handlerOAuthException(OAuthException e) {
        Log.error("用户权限异常:", e);
        return Result.fail(ResultStatus.ERROR_OAUTH, e.getMessage());
    }

    /**
     * 捕获未知异常
     *
     * @param e java.lang.Exception
     * @return cn.xiaoniu.cloud.server.web.response.Result
     * @author 孔明
     * @date 2019-11-27 13:49:35
     */
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        Log.error("系统未知异常:", e);
        return Result.fail(ResultStatus.ERROR_UNKNOW, "服务器开小差啦,请稍后重试");
    }

}
