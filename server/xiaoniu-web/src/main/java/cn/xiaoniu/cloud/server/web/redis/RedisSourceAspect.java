package cn.xiaoniu.cloud.server.web.redis;

import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.web.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * @author 孔明
 * @date 2020/4/23 21:36
 * @description cn.xiaoniu.cloud.server.web.redis.RedisSourceAspect
 */
@Aspect
@Import(RedisDataSourceHolder.class)
public class RedisSourceAspect implements Ordered {

    @Override
    public int getOrder() {
        return 3;
    }

    /**
     * 以自定义 @RedisSource 注解为切点
     */
    @Pointcut("@annotation(cn.xiaoniu.cloud.server.web.redis.RedisSource)")
    public void redisSource() {
        // 定义切点不需要方法体
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     */
    @Before("redisSource()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            // 目标方法签名
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            // 目标方法
            Method method = methodSignature.getMethod();

            // 获取RedisSource注解接口的描述信息
            RedisSource redisSource = method.getAnnotation(RedisSource.class);

            Log.debug("当前Redis数据源切换至：{}", redisSource.value());

            // 切换数据源
            RedisDataSourceHolder.setLocalThreadUtil(redisSource.value());
        } catch (Exception ex) {
            throw new UtilException(ex, "Redis 数据源切换错误！");
        }
    }

}
