package cn.xiaoniu.cloud.server.util.context;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.xiaoniu.cloud.server.util.character.StringUtil;
import cn.xiaoniu.cloud.server.util.collection.MapUtil;
import cn.xiaoniu.cloud.server.util.id.IdUtil;
import cn.xiaoniu.cloud.server.util.ip.IPUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 诸葛明自定义上下文
 *
 * @author 孔明
 * @date 2019/11/18 10:34
 * @description cn.xiaoniu.cloud.server.util.ZGMContext
 */
public class ZGMContext {

    private ZGMContext() {
    }

    /**
     * 使用 ThreadLocal 存储信息
     */
    private static final ThreadLocal<Context> VALUES = new ThreadLocal<>();

    /**
     * 获取上下文
     *
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-18 10:59:58
     */
    public static Context getContext() {
        Context context = VALUES.get();
        return context == null ? new Context() : context;
    }

    /**
     * 设置上下文
     *
     * @param context 上下文内容
     * @return void
     * @author 孔明
     * @date 2019-11-18 11:00:09
     */
    public static void setContext(Context context) {
        VALUES.set(context);
    }

    /**
     * 记录方法返回值 , 并不写到HttpResponseBody
     *
     * @param data 返回值
     */
    public static void setResponseData(Object data) {
        getContext().setResponseData(data);
    }

    /**
     * 清理资源
     */
    public static void clean() {
        Context context = VALUES.get();
        if (context != null) {
            context.setRequest(null);
            context.setResponse(null);
            context.setRequestId(null);
            context.setCustomer(null);
            context.setCookies(null);
            context.setResponseData(null);
            context.setRequestStartMillis(null);
        }
        VALUES.remove();
    }

    /**
     * 创建Context
     *
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context<T>
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext() {
        return createContext(null, null, null);
    }

    /**
     * 创建Context
     *
     * @param request HttpServletRequest
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext(HttpServletRequest request) {
        return createContext(request, null, null);
    }

    /**
     * 创建Context
     *
     * @param response HttpServletResponse
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext(HttpServletResponse response) {
        return createContext(null, response, null);
    }

    /**
     * 创建Context
     *
     * @param customer 当前线程用户信息
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext(CacheCustomer customer) {
        return createContext(null, null, customer);
    }

    /**
     * 创建Context
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext(HttpServletRequest request, HttpServletResponse response) {
        return createContext(request, response, null);
    }

    /**
     * 创建Context
     *
     * @param request  HttpServletRequest
     * @param customer 当前线程用户信息
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext(HttpServletRequest request, CacheCustomer customer) {
        return createContext(request, null, customer);
    }

    /**
     * 创建Context
     *
     * @param response HttpServletResponse
     * @param customer 当前线程用户信息
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext(HttpServletResponse response, CacheCustomer customer) {
        return createContext(null, response, customer);
    }

    /**
     * 创建Context
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param customer 当前线程用户信息
     * @return cn.xiaoniu.cloud.server.util.context.ZGMContext.Context
     * @author 孔明
     * @date 2019-11-27 10:59:38
     */
    public static Context createContext(HttpServletRequest request, HttpServletResponse response, CacheCustomer customer) {
        Context context = new Context();
        context.requestId = IdUtil.getUUID();
        context.request = request;
        context.response = response;
        context.customer = customer;
        context.cookies = CookieUtil.getAllCookie(request);
        VALUES.set(context);
        return context;
    }


    /**
     * 诸葛明自定义上下文
     *
     * @author 孔明
     * @date 2019/11/18 10:34
     * @description cn.xiaoniu.cloud.server.util.ZGMContext
     */
    public static class Context {
        /***
         * 请求对象
         */
        private HttpServletRequest request;

        /***
         * 响应对象
         */
        private HttpServletResponse response;

        /**
         * 请求ID
         */
        private String requestId;

        /**
         * 内容
         */
        private CacheCustomer customer;

        /**
         * 请求开始毫秒值
         */
        private Long requestStartMillis;

        /**
         * 响应数据
         */
        private Object responseData;

        /**
         * Cookie
         */
        private Map<String, Cookie> cookies;

        // ---------------------------------- HttpServletRequest 区 ----------------------------------

        /**
         * 获取请求路径
         *
         * @return
         */
        public String getRequestURI() {
            if (request == null) {
                return null;
            }
            return request.getRequestURI();
        }

        /**
         * 获取请求参数值
         *
         * @param name 参数名称
         * @return 请求参数值
         */
        public String getRequestParam(String name) {
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
        public String getRequestParamIgnoreCase(String name) {
            if (request == null) {
                return null;
            }

            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                if (paramName.equalsIgnoreCase(name)) {
                    return getRequestParam(paramName);
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
        public String getHeader(String name) {
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
        public String getHeaderIgnoreCase(String name) {
            if (request == null) {
                return null;
            }
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (headerName.equalsIgnoreCase(name)) {
                    return getHeader(headerName);
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
        public boolean hasHeader(String name) {
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
        public boolean hasHeaderIgnoreCase(String name) {
            if (request == null) {
                return false;
            }
            return !StringUtil.isBlank(getHeaderIgnoreCase(name));
        }

        /**
         * 获取真实IP
         *
         * @return String
         */
        public String getClientIP() {
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
        public String getMethod() {
            if (request == null) {
                return null;
            }
            return request.getMethod();
        }

        /**
         * 获取所有Cookie
         *
         * @return java.util.Set<javax.servlet.http.Cookie>
         * @author 孔明
         * @date 2019-11-18 16:23:58
         */
        public Set<Cookie> getAllCookie() {
            return MapUtil.getValues(this.cookies);
        }

        /**
         * 获取Cookie
         *
         * @param cookieName cookieName
         * @return javax.servlet.http.Cookie
         * @author 孔明
         * @date 2019-11-18 16:24:56
         */
        public Cookie getCookie(String cookieName) {
            return MapUtil.isEmpty(this.cookies) ? null : this.cookies.get(cookieName);
        }

        /**
         * 获取Cookie值
         *
         * @param cookieName cookieName
         * @return java.lang.String
         * @author 孔明
         * @date 2019-11-18 16:26:15
         */
        public String getCookieValue(String cookieName) {
            return getCookie(cookieName) == null ? null : getCookie(cookieName).getValue();
        }

        /**
         * 获取用户设备信息
         *
         * @return
         */
        public UserAgent getDeviceInfo() {
            if (this.request == null) {
                return null;
            }

            String userAgent = getHeader("User-Agent");
            if (StringUtil.isEmpty(userAgent)) {
                return null;
            }
            return UserAgentUtil.parse(userAgent);
        }

        // ---------------------------------- HttpServletResponse 方法区 ----------------------------------

        /**
         * 设置响应头
         *
         * @param name  响应头名称
         * @param value 响应头值
         * @return true:设置成功 ; false:设置失败
         */
        public boolean setResponseHeader(String name, String value) {
            if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value) || response == null) {
                return false;
            }
            response.setHeader(name, value);
            return true;
        }

        /**
         * 设置响应头
         *
         * @param headers 响应头
         * @return true:设置成功 ; false:设置失败
         */
        public boolean setResponseHeader(Map<String, String> headers) {
            if (MapUtil.isEmpty(headers) || response == null) {
                return false;
            }
            for (Map.Entry<String, String> header : headers.entrySet()) {
                setResponseHeader(header.getKey(), header.getKey());
            }
            return true;
        }

        /**
         * 设置Response ContentType
         *
         * @param contentType Response ContentType
         * @return boolean
         */
        public boolean setResponseContentType(String contentType) {
            if (StringUtil.isEmpty(contentType) || response == null) {
                return false;
            }
            response.setContentType(contentType);
            return true;
        }

        /**
         * 获取Response 输出流
         *
         * @return OutputStream
         * @throws IOException
         */
        public OutputStream getResponseOutputStream() throws IOException {
            if (response == null) {
                return null;
            }
            return response.getOutputStream();
        }

        /**
         * 获取Response Writer
         *
         * @return Writer
         * @throws IOException
         */
        public Writer getResponseWriter() throws IOException {
            if (response == null) {
                return null;
            }
            return response.getWriter();
        }

        // ---------------------------------- 延伸 方法区 ----------------------------------

        /**
         * 是否已经登录
         *
         * @return boolean TRUE: 已经登录; FALSE:未登录
         * @author 孔明
         * @date 2019-12-19 14:40:14
         */
        public boolean isLogin() {
            return !Objects.isNull(this.customer);
        }


        // ---------------------------------- Getter/Setter 方法区 ----------------------------------

        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

        public HttpServletResponse getResponse() {
            return response;
        }

        public void setResponse(HttpServletResponse response) {
            this.response = response;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public CacheCustomer getCustomer() {
            return customer;
        }

        public void setCustomer(CacheCustomer customer) {
            this.customer = customer;
        }

        public Object getResponseData() {
            return responseData;
        }

        public void setResponseData(Object responseData) {
            this.responseData = responseData;
        }

        public Long getRequestStartMillis() {
            return Objects.isNull(requestStartMillis) ? 0 : requestStartMillis;
        }

        public void setRequestStartMillis(Long requestStartMillis) {
            this.requestStartMillis = requestStartMillis;
        }

        public Map<String, Cookie> getCookies() {
            return cookies;
        }

        public void setCookies(Map<String, Cookie> cookies) {
            this.cookies = cookies;
        }
    }

}
