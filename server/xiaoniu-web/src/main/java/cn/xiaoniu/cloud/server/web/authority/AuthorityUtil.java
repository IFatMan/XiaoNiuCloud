package cn.xiaoniu.cloud.server.web.authority;

import cn.xiaoniu.cloud.server.util.character.StringUtil;
import cn.xiaoniu.cloud.server.util.context.CacheCustomer;
import cn.xiaoniu.cloud.server.web.exception.OAuthException;
import cn.xiaoniu.cloud.server.web.response.ResultStatus;
import cn.xiaoniu.cloud.server.web.util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


/**
 * @author 孔明
 * @date 2020/4/24 11:17
 * @description cn.xiaoniu.cloud.server.web.authority.AuthorityUtil
 */
public final class AuthorityUtil {

    private static final ThreadLocal<CacheCustomer> CUSTOMER_THREAD_LOCAL = new ThreadLocal<>();

    private AuthorityUtil() {

    }

    /**
     * 存储当前用户信息<br />
     * 用户记得清理哦
     *
     * @param currCustomer
     */
    public static <T extends CacheCustomer> void setCurrCustomer(T currCustomer) {
        CUSTOMER_THREAD_LOCAL.set(currCustomer);
    }

    /**
     * 取得当前用户信息
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends CacheCustomer> T getCurrCustomer() {
        return (T) CUSTOMER_THREAD_LOCAL.get();
    }

    /**
     * 用完了清理一下
     */
    public static void clear() {
        CUSTOMER_THREAD_LOCAL.remove();
    }

    public static CacheCustomer getCustomerFromRequest(HttpServletRequest request, String loginTokenName, LoginCacheDao loginCacheDao) {
        String token = HttpServletRequestUtil.getHeaderIgnoreCase(request, loginTokenName);
        if (StringUtil.isBlank(token)) {
            throw new OAuthException(ResultStatus.ERROR_OAUTH_NOT_LOGIN, "系统检测到您还未登录，请登录！");
        }

        CacheCustomer cacheCustomer = loginCacheDao.getCacheCustomer(token);
        if (Objects.isNull(cacheCustomer)) {
            throw new OAuthException(ResultStatus.ERROR_OAUTH_ACCESS_TOKEN_OVERDUE, "系统检测到您此次登录失效，请重新登录！");
        }
        return cacheCustomer;
    }

}
