package cn.xiaoniu.cloud.server.web.authority;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.web.XNAspect;
import cn.xiaoniu.cloud.server.web.exception.OAuthException;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import cn.xiaoniu.cloud.server.web.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Import;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/4/24 11:36
 * @description cn.xiaoniu.cloud.server.web.authority.PermissionAspect
 */
@Aspect
@Import(LoginAspect.class)
public class PermissionAspect implements XNAspect {

    @Override
    public int getOrder() {
        return 3;
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     */
    @Before("permission()")
    public void doBefore(JoinPoint joinPoint) {
        CacheCustomer customer = AuthorityUtil.getCurrCustomer();
        if (Objects.isNull(customer)) {
            throw new OAuthException(ResultStatus.ERROR_OAUTH_NOT_LOGIN, "系统检测到您还未登录，请登录！");
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        Log.info("当前接口代码:{}", permission.value());

        if (CollUtil.isEmpty(customer.getPermissions()) || !customer.getPermissions().contains(permission.value())) {
            Log.info("没有当前接口权限");
            throw new OAuthException(ResultStatus.ERROR_OAUTH_NO_PERMISSION, "无此接口权限，请联系管理员！");
        }
        Log.info("当前接口权限验证已通过");
    }

}
