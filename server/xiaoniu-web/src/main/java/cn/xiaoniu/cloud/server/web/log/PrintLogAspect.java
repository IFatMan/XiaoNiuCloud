package cn.xiaoniu.cloud.server.web.log;

import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.web.XNAspect;
import cn.xiaoniu.cloud.server.web.util.HttpServletRequestUtil;
import cn.xiaoniu.cloud.server.web.util.Log;
import cn.xiaoniu.cloud.server.web.util.MethodUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/4/21 17:59
 * @description cn.xiaoniu.cloud.server.web.log.PrintLogAspect
 */
@Aspect
public class PrintLogAspect implements XNAspect {

    /**
     * 执行顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     */
    @Before("permission() || login() || printLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            // 目标Class
            Class targetClass = joinPoint.getTarget().getClass();

            // 目标方法签名
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            // 目标方法
            Method method = methodSignature.getMethod();

            // 开始打印请求日志
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.isNull(attributes)) {
                throw new UtilException("获取HttpServletRequest异常！");
            }
            HttpServletRequest request = attributes.getRequest();

            Log.info("-------------------- 请求日志 --------------------");
            Log.info("请求IP:{}", HttpServletRequestUtil.getClientIP(request));
            Log.info("请求地址:{}", HttpServletRequestUtil.getRequestURI(request));
            Log.info("请求方式:{}", HttpServletRequestUtil.getMethod(request));

            // 获取@PrintLog注解的接口描述信息
            String methodName;
            PrintLog printLog = method.getAnnotation(PrintLog.class);
            if (Objects.nonNull(printLog)) {
                methodName = printLog.value();
            } else {
                // 没有PrintLog时，使用io.swagger.annotations.ApiOperation
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                if (Objects.nonNull(apiOperation)) {
                    methodName = apiOperation.value();
                } else {
                    methodName = method.getName();
                }
            }
            Log.info("请求方法:{}", methodName);
            Log.info("请求方法路径:{}.{}()", targetClass.getName(), method.getName());
            Log.info("请求参数:{}", MethodUtil.getInstance().getJSONParams(methodSignature, joinPoint.getArgs()));

        } catch (Exception ex) {
            throw new UtilException(ex, "使用AOP打印请求参数异常！");
        }
    }

    /**
     * 切点环绕织入
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("permission() || login() || printLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        Log.info("请求返回:{}", JsonUtil.toJson(result));
        Log.info("请求耗时:{}\n", System.currentTimeMillis() - startTime);
        return result;
    }

}
