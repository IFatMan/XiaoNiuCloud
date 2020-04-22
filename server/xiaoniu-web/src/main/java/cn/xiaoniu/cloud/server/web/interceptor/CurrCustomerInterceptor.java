package cn.xiaoniu.cloud.server.web.interceptor;

import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.character.StringUtil;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.util.context.ZGMContext;
import cn.xiaoniu.cloud.server.web.authority.Login;
import cn.xiaoniu.cloud.server.web.dao.LoginCacheDao;
import cn.xiaoniu.cloud.server.web.exception.OAuthException;
import cn.xiaoniu.cloud.server.web.util.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 获取当前登录者信息,并放入自定义上下文中
 */
@Slf4j
public class CurrCustomerInterceptor implements HandlerInterceptor {

    /**
     * HttpServletRequest中Token的存储Key
     */
    private String tokenKey;

    /**
     * 获取缓存信息
     */
    private LoginCacheDao loginCacheDao;

    public CurrCustomerInterceptor(String tokenKey, LoginCacheDao loginCacheDao) {
        this.tokenKey = tokenKey;
        this.loginCacheDao = loginCacheDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ZGMContext.Context context = ZGMContext.getContext();
        if (context == null) {
            context = ZGMContext.createContext(request, response);
        }

        String currAccessToken = context.getHeaderIgnoreCase(tokenKey);

        Log.info("请求TOKEN:{}", currAccessToken);

        // 获取缓存中用户信息
        if (Objects.isNull(loginCacheDao)) {
            return true;
        }
        CacheCustomer customer = loginCacheDao.getCacheCustomer(currAccessToken);
        context.setCustomer(customer);
        Log.info("请求用户:{}", JsonUtil.toJson(customer));

        // ================= 权限验证 =================

        HandlerMethod hand = (HandlerMethod) handler;
        if (hand.hasMethodAnnotation(Login.class)) {
            Log.info("登录验证:TRUE");
            if (StringUtil.isBlank(currAccessToken)) {
                throw new OAuthException("请登录 !");
            }
            if (customer == null) {
                throw new OAuthException("登录失效,请重新登录 !");
            }
        } else {
            Log.info("登录验证:FALSE");
        }

        return true;
    }

    /**
     * 渲染后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ZGMContext.clean();
    }
}
