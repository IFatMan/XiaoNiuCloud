package cn.xiaoniu.cloud.server.web.interceptor;

import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.context.ZGMContext;
import cn.xiaoniu.cloud.server.web.util.Log;
import cn.xiaoniu.cloud.server.web.util.ParamsUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志打印拦截器
 */
public class LogInterceptor implements HandlerInterceptor {

    /**
     * 执行前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpServletRequestCustom.class != request.getClass()) {
            return true;
        }

        HttpServletRequestCustom customRequest = (HttpServletRequestCustom) request;
        HandlerMethod hand = (HandlerMethod) handler;
        ParamsUtil paramsUtil = new ParamsUtil(customRequest, hand);
        ZGMContext.Context context = ZGMContext.createContext(customRequest, response);
        context.setRequestStartMillis(System.currentTimeMillis());

        Log.info("-------------------- 请求日志 --------------------");
        Log.info("请求地址:{}", context.getRequestURI());
        Log.info("请求方式:{}", context.getMethod());
        Log.info("请求方法:{}.{}()", hand.getBeanType().getName(), hand.getMethod().getName());
        Log.info("请求参数:{}", paramsUtil.getParams());

        ZGMContext.setContext(context);
        return true;
    }

    /**
     * 执行后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Log.info("");
        if (ZGMContext.getContext() != null) {
            Log.info("请求耗时:{}毫秒", System.currentTimeMillis() - ZGMContext.getContext().getRequestStartMillis());
            Log.info("请求返回:{}", JsonUtil.toJson(ZGMContext.getContext().getResponseData()));
        }
    }

    /**
     * 渲染后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ZGMContext.clean();
    }
}
