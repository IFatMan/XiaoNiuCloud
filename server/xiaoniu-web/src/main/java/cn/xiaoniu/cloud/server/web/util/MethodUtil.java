package cn.xiaoniu.cloud.server.web.util;

import cn.hutool.core.map.MapUtil;
import cn.xiaoniu.cloud.server.util.JsonUtil;
import cn.xiaoniu.cloud.server.util.RegexUtil;
import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import cn.xiaoniu.cloud.server.web.log.HideData;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Objects;

/**
 * MethodUtil 方法工具类
 *
 * @author 孔明
 * @date 2020/04/22 10:39
 * @description cn.xiaoniu.cloud.server.web.util.MethodUtil
 */
public class MethodUtil {
    private static MethodUtil ourInstance = new MethodUtil();

    public static MethodUtil getInstance() {
        return ourInstance;
    }

    private MethodUtil() {

    }

    /**
     * 获取请求参数JSON格式字符串
     *
     * @param methodSignature
     * @return
     */
    public String getJSONParams(MethodSignature methodSignature, Object[] args) {
        if (Objects.isNull(args) || args.length <= 0) {
            return CommonConstant.CHAR_EMPTY;
        }

        String[] parameterNames = methodSignature.getParameterNames();
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        if (Objects.isNull(parameterNames) || args.length != parameterNames.length || parameterNames.length != parameters.length) {
            return CommonConstant.CHAR_EMPTY;
        }

        Object arg;
        Map<String, Object> params = MapUtil.newHashMap(args.length);
        for (int i = 0; i < args.length; i++) {
            arg = args[i];
            if (isIgnoreByParamType(arg)) {
                continue;
            }
            if (isIgnoreByAnnotation(parameters[i])) {
                arg = RegexUtil.hideData(arg);
            }
            params.put(parameterNames[i], arg);
        }
        return JsonUtil.toJson(params);
    }

    /**
     * 是否根据参数类型忽略日志打印
     *
     * @param arg
     * @return
     */
    private boolean isIgnoreByParamType(Object arg) {
        return (arg instanceof HttpServletResponse) || (arg instanceof HttpServletRequest) || (arg instanceof MultipartFile) || (arg instanceof MultipartFile[]);
    }

    /**
     * 是否根据注解隐藏值 注解@HideData生效
     *
     * @param parameter
     * @return
     */
    private boolean isIgnoreByAnnotation(Parameter parameter) {
        return Objects.nonNull(parameter.getAnnotation(HideData.class));
    }


}
