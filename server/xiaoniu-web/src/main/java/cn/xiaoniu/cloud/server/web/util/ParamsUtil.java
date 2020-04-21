package cn.xiaoniu.cloud.server.web.util;

import cn.xiaoniu.cloud.server.util.RegexUtil;
import cn.xiaoniu.cloud.server.web.annotation.HideData;
import cn.xiaoniu.cloud.server.web.interceptor.HttpServletRequestCustom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 获取参数工具
 */
public class ParamsUtil {

    private Map<String, Object> params = new HashMap<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private HttpServletRequestCustom request = null;

    private HandlerMethod handlerMethod;

    public ParamsUtil(HttpServletRequestCustom request, HandlerMethod handlerMethod) {
        this.request = request;
        this.handlerMethod = handlerMethod;
    }

    public Map<String, Object> getParams() {
        // 获取URL中的参数
        this.getUrlPatterParams();

        // 获取参数列表里的参数
        this.getMethodParams();
        return this.params;
    }

    /**
     * 获取第一个匹配路径中的参数值
     */
    public void getUrlPatterParams() {
        // 获取到所有可能的路径
        List<String> allUrl = getAllUrl();

        // 获取第一个匹配路径的的参数值
        String requestUri = request.getRequestURI();
        for (String url : allUrl) {
            if (antPathMatcher.match(url, requestUri)) {
                Map<String, String> urlParams = antPathMatcher.extractUriTemplateVariables(url, requestUri);
                params.putAll(urlParams);
                break;
            }
        }
    }

    public void getMethodParams() {
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        if (parameters != null && parameters.length > 0) {
            for (Parameter parameter : parameters) {

                // 参数类型
                Class parameterType = parameter.getType();

                // 参数名
                String paramName = parameter.getName();

                // 如果有RequestParam 注解 , 参数名以RequestParam 中指定为准
                RequestParam requestParam;
                if ((requestParam = parameter.getDeclaredAnnotation(RequestParam.class)) != null) {
                    paramName = requestParam.name();
                    if (StringUtils.isBlank(paramName)) {
                        paramName = requestParam.value();
                    }
                }

                // 当前参数值是否需要隐藏
                boolean isHideData = false;
                if (parameter.getDeclaredAnnotation(HideData.class) != null) {
                    isHideData = true;
                }

                // 参数类型为 ServletRequset 不做处理
                if (ServletRequest.class.isAssignableFrom(parameterType)) {
                    Log.debug("参数[{}]类型为[{}],忽略打印参数值", paramName, parameterType.getTypeName());
                    continue;
                }

                // 参数类型为 ServletResponse 不做处理
                if (ServletResponse.class.isAssignableFrom(parameterType)) {
                    Log.debug("参数[{}]类型为[{}],忽略打印参数值", paramName, parameterType.getTypeName());
                    continue;
                }

                // 参数类型为 InputStreamSource 不做处理
                if (InputStreamSource.class.isAssignableFrom(parameterType)) {
                    Log.debug("参数[{}]类型为[{}],忽略打印参数值", paramName, parameterType.getTypeName());
                    continue;
                }

                //参数有注解-PathVariable,忽略参数打印 , 因为在路径中参数已经打印
                if (parameter.getDeclaredAnnotation(PathVariable.class) != null) {
                    if (isHideData) {
                        this.hideData(paramName);
                    }
                    Log.debug("参数[{}]被PathVariable标注,忽略打印参数值", paramName);
                    continue;
                }

                // 参数标记RequstBody后为JSON数据,从请求体重获取
                RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
                if (requestBody != null) {
                    params.put(paramName, request.getBody());
                } else {
                    params.put(paramName, request.getParameter(paramName));
                }

                if (isHideData) {
                    this.hideData(paramName);
                }
            }
        }
    }

    private void hideData(String paramName) {
        Object paramValue = this.params.get(paramName);
        this.params.put(paramName, RegexUtil.hideData(paramValue));
    }

    /**
     * 将类RequestMapping value和方法RequestMapper value两两的方式组合后返回
     * 数量为二者的乘积
     *
     * @return 方法所有的可用URL
     */
    private List<String> getAllUrl() {
        // 类RequstMapping
        RequestMapping beanRequestMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);

        // 方法RequstMapping
        RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);

        // 如果类未标注RequestMapping , 则方法一定有标注 , 返回方法标注的URL即可
        if (beanRequestMapping == null) {
            return Arrays.asList(methodRequestMapping.value());
        }

        // 如果类有标注RequestMapping , 方法未标注RequestMapping , 放回类标注的URL即可
        if (beanRequestMapping != null && methodRequestMapping == null) {
            return Arrays.asList(beanRequestMapping.value());
        }

        // 如果两者都有标注RequestMapping , 则将他们的value 两两组合 , 数量为二者value数量乘积
        String[] beanUrls = beanRequestMapping.value();
        String[] methodUrls = methodRequestMapping.value();
        List<String> allUrl = new ArrayList<>(beanUrls.length * methodUrls.length);
        for (String beanUrl : beanUrls) {
            for (String methodUrl : methodUrls) {
                allUrl.add(joinUrl(beanUrl, methodUrl));
            }
        }
        return allUrl;
    }

    /**
     * 组合 类RequestMapping value和方法RequestMapper value
     *
     * @param beanUrl
     * @param methodUrl
     * @return
     */
    private String joinUrl(String beanUrl, String methodUrl) {
        if (beanUrl.endsWith("/")) {
            beanUrl = beanUrl.substring(0, beanUrl.length() - 1);
        }
        if (methodUrl.startsWith("/")) {
            methodUrl = methodUrl.substring(1);
        }
        return StringUtils.join(beanUrl, "/", methodUrl);
    }
}
