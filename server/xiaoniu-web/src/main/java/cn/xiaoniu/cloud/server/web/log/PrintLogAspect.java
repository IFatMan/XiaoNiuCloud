package cn.xiaoniu.cloud.server.web.log;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.xiaoniu.cloud.server.util.AssertUtil;
import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.web.util.HttpServletRequestUtil;
import cn.xiaoniu.cloud.server.web.util.Log;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/4/21 17:59
 * @description cn.xiaoniu.cloud.server.web.log.PrintLogAspect
 */
@Aspect
public class PrintLogAspect {

    /**
     * 以自定义 @PrintLog 注解为切点
     */
    @Pointcut("@annotation(cn.xiaoniu.cloud.server.web.log.PrintLog)")
    public void printLog() {
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     */
    @Before("printLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            // 开始打印请求日志
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            Log.info("-------------------- 请求日志 --------------------");
            Log.info("请求IP:{}", HttpServletRequestUtil.getClientIP(request));
            Log.info("请求地址:{}", HttpServletRequestUtil.getRequestURI(request));
            Log.info("请求方式:{}", HttpServletRequestUtil.getMethod(request));

            // 目标Class
            Class targetClass = joinPoint.getTarget().getClass();

            // 目标方法
            String targetMethod = joinPoint.getSignature().getName();

            // 获取@PrintLog注解的接口描述信息
            PrintLog printLog = getPrintLogFromPoint(targetClass, targetMethod);
            if (StringUtils.isNotBlank(printLog.description())) {
                Log.info("请求方法:{}", printLog.description());
            }
            Log.info("请求方法路径:{}.{}()", targetClass.getName(), targetMethod);
            Log.info("请求参数:{}", getParams(joinPoint));

        } catch (Exception ex) {
            throw new UtilException(ex, "使用AOP打印请求参数异常！");
        }
    }

    /**
     * 获取切面注解的描述
     *
     * @param targetClass 目标CLass
     * @param methodName  目标方法
     * @return 描述信息
     * @throws Exception
     */
    private PrintLog getPrintLogFromPoint(Class targetClass, String methodName) {
        AssertUtil.isNotNull(targetClass, "参数 targetClass 不能为null!");
        AssertUtil.isNotNull(methodName, "参数 methodName 不能为null!");

        try {
            Method targetMethod = targetClass.getDeclaredMethod(methodName, targetClass);
            return targetMethod.getAnnotation(PrintLog.class);
        } catch (NoSuchMethodException ex) {
            throw new UtilException(ex, "获取切面注解的描述异常！");
        }
    }

    /**
     * 获取
     *
     * @param joinPoint
     * @return
     */
    private String getParams(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (Objects.isNull(args) || args.length <= 0) {
            return CommonConstant.CHAR_EMPTY;
        }

        StringBuilder builder = new StringBuilder(args.length);
        for (Object arg : args) {
            if ((arg instanceof HttpServletResponse) || (arg instanceof HttpServletRequest)
                    || (arg instanceof MultipartFile) || (arg instanceof MultipartFile[])) {
                continue;
            }
            builder.append(JsonUtil.toJson(arg));
        }
        return builder.toString();
    }

}
