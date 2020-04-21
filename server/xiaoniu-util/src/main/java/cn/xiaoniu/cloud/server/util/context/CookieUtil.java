package cn.xiaoniu.cloud.server.util.context;

import cn.xiaoniu.cloud.server.util.character.StringUtil;
import cn.xiaoniu.cloud.server.util.collection.MapUtil;
import cn.xiaoniu.cloud.server.util.constant.CommonConstant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Cookie 工具类
 *
 * @author 孔明
 * @date 2019/11/18 11:22
 * @description cn.xiaoniu.cloud.server.util.context.CookieUtil
 */
public class CookieUtil {

    private CookieUtil() {
    }

    /**
     * 得到Cookie的值,
     *
     * @param request    HttpServletRequest
     * @param cookieName Cookie值
     * @return java.lang.String
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        return cookie == null ? null : cookie.getValue();
    }


    /**
     * 获取Cookie 以Cookie名称为Key
     *
     * @param request HttpServletRequest
     * @return java.util.Map<java.lang.String, javax.servlet.http.Cookie>
     * @author 孔明
     * @date 2019-11-18 14:55:33
     */
    public static Map<String, Cookie> getAllCookie(HttpServletRequest request) {
        Map<String, Cookie> result = MapUtil.createEmptyHashMap();
        if (Objects.isNull(request)) {
            return result;
        }
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieList.length <= 0) {
            return result;
        }
        for (Cookie cookie : cookieList) {
            result.put(cookie.getName(), cookie);
        }
        return result;
    }

    /**
     * 获取所有Cookie
     *
     * @return java.util.Set<javax.servlet.http.Cookie>
     * @author 孔明
     * @date 2019-11-18 16:23:58
     */
    public static Set<Cookie> getCookieSet(HttpServletRequest request) {
        Map<String, Cookie> cookies = CookieUtil.getAllCookie(request);
        return MapUtil.getValues(cookies);
    }

    /**
     * 获取特定Cookie
     *
     * @param request    HttpServletRequest
     * @param cookieName Cookie值
     * @return javax.servlet.http.Cookie
     * @author 孔明
     * @date 2019-11-18 14:58:45
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (hasCookie(request)) {
            return null;
        }
        Cookie[] cookieList = request.getCookies();
        for (Cookie cookie : cookieList) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 是否有Cookie值
     *
     * @param request HttpServletRequest
     * @return boolean
     * @author 孔明
     * @date 2019-11-18 15:03:19
     */
    public static boolean hasCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return cookies != null && cookies.length > 0;
    }

    /**
     * 是否存在指定Cookie的
     *
     * @param request    HttpServletRequest
     * @param cookieName Cookie值
     * @return boolean
     * @author 孔明
     * @date 2019-11-18 15:46:17
     */
    public static boolean hasCookie(HttpServletRequest request, String cookieName) {
        if (!hasCookie(request)) {
            return false;
        }
        return getCookie(request, cookieName) != null;
    }

    /**
     * 删除Cookie带cookie域名
     */
    public static void deleteCookie(HttpServletResponse response,
                                    String domainName,
                                    String cookieName,
                                    String path) {
        setCookie(response, cookieName, CommonConstant.CHAR_EMPTY, domainName, path, 0);
    }

    /**
     * 设置Cookie
     *
     * @param response    HttpServletResponse
     * @param cookieName  Cookie名称
     * @param cookieValue Cookie值
     * @return void
     * @author 孔明
     * @date 2019-11-18 15:56:14
     */
    public static void setCookie(HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue) {
        setCookie(response, cookieName, cookieValue, null, CommonConstant.CHAR_SLASH, -1);
    }

    /**
     * 设置Cookie
     *
     * @param response    HttpServletResponse
     * @param cookieName  Cookie名称
     * @param cookieValue Cookie值
     * @param domainName  域名
     * @return void
     * @author 孔明
     * @date 2019-11-18 15:58:19
     */
    public static void setCookie(HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue,
                                 String domainName) {
        setCookie(response, cookieName, cookieValue, domainName, CommonConstant.CHAR_SLASH, -1);
    }

    /**
     * 设置Cookie
     *
     * @param response    HttpServletResponse
     * @param cookieName  Cookie名称
     * @param cookieValue Cookie值
     * @param domainName  域名
     * @param path        路径
     * @return void
     * @author 孔明
     * @date 2019-11-18 15:59:53
     */
    public static void setCookie(HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue,
                                 String domainName,
                                 String path) {
        setCookie(response, cookieName, cookieValue, domainName, path, -1);
    }

    /**
     * 设置Cookie
     *
     * @param response    HttpServletResponse
     * @param cookieName  Cookie名称
     * @param cookieValue Cookie值
     * @param domainName  域名
     * @param path        路径
     * @param maxAge      存活时间
     * @return void
     * @author 孔明
     * @date 2019-11-18 16:02:47
     */
    public static void setCookie(HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue,
                                 String domainName,
                                 String path,
                                 int maxAge) {
        Cookie cookie = createCookie(cookieName, cookieValue, domainName, path, maxAge);
        response.addCookie(cookie);
    }

    /**
     * 创建Cookie对象
     *
     * @param cookieName   Cookie 名称
     * @param cookieValue  Cookie 值
     * @param domainName   域名
     * @param path         路径
     * @param cookieMaxAge 存活时间
     * @return javax.servlet.http.Cookie
     * @author 孔明
     * @date 2019-11-18 15:51:52
     */
    private static Cookie createCookie(final String cookieName,
                                       final String cookieValue,
                                       final String domainName,
                                       final String path,
                                       int cookieMaxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setHttpOnly(true);
        if (cookieMaxAge > 0) {
            cookie.setMaxAge(cookieMaxAge);
        }
        if (!StringUtil.isBlank(domainName)) {
            cookie.setDomain(domainName);
        }
        if (!StringUtil.isBlank(path)) {
            cookie.setPath(path);
        }
        return cookie;
    }

}
