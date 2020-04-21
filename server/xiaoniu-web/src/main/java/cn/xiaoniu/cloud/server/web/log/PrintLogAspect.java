package cn.xiaoniu.cloud.server.web.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
     * @param joinPoint
     * @throws Throwable
     */
    // https://blog.csdn.net/HuHao_CSDN/article/details/105048588?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-20&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-20
    @Before("printLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取 @WebLog 注解的描述信息
//        String methodDescription = getAspectLogDescription(joinPoint);
//
//        // 打印请求相关参数
//        log.info("========================================== Start ==========================================");
//        // 打印请求 url
//        log.info("URL            : {}", request.getRequestURL().toString());
//        // 打印描述信息
//        log.info("Description    : {}", methodDescription);
//        // 打印 Http method
//        log.info("HTTP Method    : {}", request.getMethod());
//        // 打印调用 controller 的全路径以及执行方法
//        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
//        // 打印请求的 IP
//        log.info("IP             : {}", request.getRemoteAddr());
//        // 打印请求入参
//        log.info("Request Args   : {}", getParams(joinPoint));
    }

}
