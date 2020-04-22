package cn.xiaoniu.cloud.server.web.authority;

import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.web.dao.LoginCacheDao;
import cn.xiaoniu.cloud.server.web.log.PrintLog;
import cn.xiaoniu.cloud.server.web.util.HttpServletRequestUtil;
import cn.xiaoniu.cloud.server.web.util.Log;
import cn.xiaoniu.cloud.server.web.util.MethodUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 权限验证Aspect
 *
 * @author 孔明
 * @date 2020/4/22 10:18
 * @description cn.xiaoniu.cloud.server.web.authority.LoginAspect
 */
@Aspect
@Component
//@ConditionalOnBean(LoginCacheDao.class)
public class LoginAspect implements Ordered {

    @Autowired(required = false)
    private LoginCacheDao loginCacheDao;

    /**
     * 执行顺序
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 2;
    }

    /**
     * 以自定义 @Login 注解为切点
     */
    @Pointcut("@annotation(cn.xiaoniu.cloud.server.web.authority.Login)")
    public void login() {
        // 定义切点不需要方法体
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     */
    @Before("login()")
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
            HttpServletRequest request = attributes.getRequest();

            // 没有打印日志
            if (Objects.isNull(method.getAnnotation(PrintLog.class))) {
                Log.info("-------------------- 请求日志 --------------------");
                Log.info("请求IP:{}", HttpServletRequestUtil.getClientIP(request));
                Log.info("请求地址:{}", HttpServletRequestUtil.getRequestURI(request));
                Log.info("请求方式:{}", HttpServletRequestUtil.getMethod(request));
                Log.info("请求方法路径:{}.{}()", targetClass.getName(), method.getName());
                Log.info("请求参数:{}", MethodUtil.getInstance().getJSONParams(methodSignature, joinPoint.getArgs()));
            }

            Login login = method.getAnnotation(Login.class);
            if (Objects.isNull(login)) {
                throw new UtilException("权限验证Aspect未获取到@Login注解！");
            }

            String token = HttpServletRequestUtil.getHeaderIgnoreCase(request, "");


        } catch (Exception ex) {
            throw new UtilException(ex, "使用AOP权限验证异常！");
        }
    }

    @Around("login()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Log.info(" Login ------- Around");
        return proceedingJoinPoint.proceed();
    }


}
