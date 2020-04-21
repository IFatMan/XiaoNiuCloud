package cn.xiaoniu.cloud.server.web.util;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.xiaoniu.cloud.server.util.character.StringUtil;
import cn.xiaoniu.cloud.server.util.ip.IPUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author 孔明
 * @date 2020/4/21 21:04
 * @description cn.xiaoniu.cloud.server.web.util.HttpServletRequestUtil
 */
public final class HttpServletRequestUtil {
    private HttpServletRequestUtil() {
    }

    /**
     * 获取请求路径
     *
     * @param request 请求
     * @return
     */
    public static String getRequestURI(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getRequestURI();
    }

    /**
     * 获取请求参数值
     *
     * @param request 请求
     * @param name    参数名称
     * @return 请求参数值
     */
    public static String getRequestParam(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        return request.getParameter(name);
    }

    /**
     * 忽略大小写获取请求参数值
     *
     * @param name
     * @return 请求参数值
     */
    public static String getRequestParamIgnoreCase(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }

        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.equalsIgnoreCase(name)) {
                return getRequestParam(request, paramName);
            }
        }
        return null;
    }

    /**
     * 获取请求头值
     *
     * @param name 请求头名称
     * @return 请求头值
     */
    public static String getHeader(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        return request.getHeader(name);
    }

    /**
     * 忽略大小写获取请求头值
     *
     * @param name 请求头名称
     * @return 请求头值
     */
    public static String getHeaderIgnoreCase(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.equalsIgnoreCase(name)) {
                return getHeader(request, headerName);
            }
        }
        return null;
    }

    /**
     * 请求头中有指定name请求头
     *
     * @param name 请求头名称
     * @return boolean
     */
    public static boolean hasHeader(HttpServletRequest request, String name) {
        if (request == null) {
            return false;
        }
        return !(StringUtil.isBlank(request.getHeader(name)));
    }

    /**
     * 请求头中有指定name请求头 忽略大小写
     *
     * @param name 根据名称获取请求头
     * @return boolean
     */
    public static boolean hasHeaderIgnoreCase(HttpServletRequest request, String name) {
        if (request == null) {
            return false;
        }
        return !StringUtil.isBlank(getHeaderIgnoreCase(request, name));
    }

    /**
     * 获取真实IP
     *
     * @return String
     */
    public static String getClientIP(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return IPUtil.getIpFromRequest(request);
    }

    /**
     * 获取请求方式 GET/POST/DELETE/PUT
     *
     * @return GET/POST/DELETE/PUT
     */
    public static String getMethod(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getMethod();
    }

    /**
     * 获取用户设备信息
     *
     * @return
     */
    public static UserAgent getDeviceInfo(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String userAgent = getHeader(request, "User-Agent");
        if (StringUtil.isEmpty(userAgent)) {
            return null;
        }
        return UserAgentUtil.parse(userAgent);
    }
}
