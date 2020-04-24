package cn.xiaoniu.cloud.server.web;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * @author 孔明
 * @date 2020/4/24 15:06
 * @description cn.xiaoniu.cloud.server.web.XNAspect
 */
public interface XNAspect extends Ordered {

    /**
     * 以自定义 @PrintLog 注解为切点
     */
    @Pointcut("@annotation(cn.xiaoniu.cloud.server.web.log.PrintLog)")
    default void printLog() {
        // 定义切点不需要方法体
    }

    /**
     * 以自定义 @Login 注解为切点
     */
    @Pointcut("@annotation(cn.xiaoniu.cloud.server.web.authority.Login)")
    default void login() {
        // 定义切点不需要方法体
    }

    /**
     * 以自定义 @Permission 注解为切点
     */
    @Pointcut("@annotation(cn.xiaoniu.cloud.server.web.authority.Permission)")
    default void permission() {
        // 定义切点不需要方法体
    }

    /**
     * 以自定义 @RedisSource 注解为切点
     */
    @Pointcut("@annotation(cn.xiaoniu.cloud.server.web.redis.RedisSource)")
    default void redisSource() {
        // 定义切点不需要方法体
    }

}
