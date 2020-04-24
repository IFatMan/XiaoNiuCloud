package cn.xiaoniu.cloud.server.web.authority;

import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.util.exception.UtilException;
import cn.xiaoniu.cloud.server.web.XNAspect;
import cn.xiaoniu.cloud.server.web.log.PrintLogAspect;
import cn.xiaoniu.cloud.server.web.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 权限验证Aspect
 *
 * @author 孔明
 * @date 2020/4/22 10:18
 * @description cn.xiaoniu.cloud.server.web.authority.LoginAspect
 */
@Aspect
@Import(PrintLogAspect.class)
@EnableConfigurationProperties(AuthorityProperties.class)
public class LoginAspect implements XNAspect {

    @Autowired(required = false)
    private LoginCacheDao loginCacheDao;

    @Autowired
    private AuthorityProperties properties;

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
     * 在切点之前织入
     *
     * @param joinPoint
     */
    @Before("login() || permission()")
    public void doBefore(JoinPoint joinPoint) {
        if (Objects.isNull(loginCacheDao)) {
            throw new UtilException("请实现LoginCacheDao接口！");
        }

        // 获取 HttpServletRequest
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            throw new UtilException("获取HttpServletRequest异常！");
        }
            HttpServletRequest request = attributes.getRequest();

        // 登录权限验证开始，获取当前登录者信息
        String loginTokenName = (Objects.isNull(properties.getLoginTokenName()) ? AuthorityProperties.Constatnt.LOGIN_TOKEN_NAME : properties.getLoginTokenName());
        CacheCustomer cacheCustomer = AuthorityUtil.getCustomerFromRequest(request, loginTokenName, loginCacheDao);
        Log.info("当前用户:{}", JsonUtil.toJson(cacheCustomer));

        // 缓存当前用户对象
        AuthorityUtil.setCurrCustomer(cacheCustomer);
    }

    /**
     * 我用完了，清理一下
     */
    @After("login() || permission()")
    public void doAfter() {
        AuthorityUtil.clear();
    }

}
