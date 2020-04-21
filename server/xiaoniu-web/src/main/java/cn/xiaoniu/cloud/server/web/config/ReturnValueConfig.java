package cn.xiaoniu.cloud.server.web.config;

import cn.xiaoniu.cloud.server.util.context.ZGMContext;
import cn.xiaoniu.cloud.server.web.response.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孔明
 * @date 2019/11/22 17:11
 * @description cn.xiaoniu.cloud.server.common.config.ReturnValueConfig
 */
@Configuration
public class ReturnValueConfig implements InitializingBean {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> unmodifiableList = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> list = new ArrayList<>(unmodifiableList.size());
        for (HandlerMethodReturnValueHandler returnValueHandler : unmodifiableList) {
            if (returnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                list.add(new ReturnHandler(returnValueHandler));
            } else {
                list.add(returnValueHandler);
            }
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(list);
    }
}

class ReturnHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ReturnHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        Object newReturnValue = returnValue;
        if (!newReturnValue.getClass().isAssignableFrom(Result.class)) {
            newReturnValue = Result.success(newReturnValue);
        }
        ZGMContext.setResponseData(newReturnValue);
        delegate.handleReturnValue(newReturnValue, returnType, mavContainer, webRequest);
    }
}
