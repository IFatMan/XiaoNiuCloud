package cn.xiaoniu.cloud.server.web.swagger;

import cn.hutool.core.collection.CollUtil;
import cn.xiaoniu.cloud.server.util.operator.OperatorUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 孔明
 * @date 2020/4/22 15:32
 * @description cn.xiaoniu.cloud.server.web.swagger.Swagger2Config
 */
@Component
@EnableConfigurationProperties(Swagger2Properties.class)
public class Swagger2Config {

    @Autowired
    private Swagger2Properties properties;

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.enable(properties.getEnable());
        docket.globalOperationParameters(builderHeader());
        docket.apiInfo(buildApiInfo());
        ApiSelectorBuilder selectorBuilder = docket.select();
        selectorBuilder.apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()));
        selectorBuilder.paths(PathSelectors.any());
        return selectorBuilder.build();
    }

    /**
     * 构建ApiInfo
     *
     * @return ApiInfo
     */
    private ApiInfo buildApiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        OperatorUtil.ternaryOperator(
                StringUtils.isNotBlank(properties.getTitle()),
                () -> apiInfoBuilder.title(properties.getTitle()),
                () -> apiInfoBuilder.title(Swagger2Properties.Constatnt.TITLE));

        OperatorUtil.ternaryOperator(
                StringUtils.isNotBlank(properties.getDescription()),
                () -> apiInfoBuilder.description(properties.getDescription()),
                () -> apiInfoBuilder.description(Swagger2Properties.Constatnt.DESCRIPTION));

        OperatorUtil.ternaryOperator(
                StringUtils.isNotBlank(properties.getVersion()),
                () -> apiInfoBuilder.version(properties.getVersion()),
                () -> apiInfoBuilder.version(Swagger2Properties.Constatnt.VERSION));

        if (StringUtils.isNotBlank(properties.getConcatName())
                || StringUtils.isNotBlank(properties.getConcatEmail())
                || StringUtils.isNotBlank(properties.getConcatUrl())) {
            apiInfoBuilder.contact(new Contact(properties.getConcatName(), properties.getConcatUrl(), properties.getConcatEmail()));
        }

        return apiInfoBuilder.build();
    }

    /**
     * 构建请求头
     *
     * @return 请求头
     */
    private List<Parameter> builderHeader() {
        List<Swagger2Properties.Header> headers = properties.getHeader();
        if (CollUtil.isEmpty((headers))) {
            return Lists.newArrayList();
        }
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>(headers.size());

        for (Swagger2Properties.Header header : headers) {
            parameterBuilder.name(header.getName());
            if (StringUtils.isNotBlank(header.getDescription())) {
                parameterBuilder.description(header.getDescription());
            }
            OperatorUtil.ternaryOperator(Objects.nonNull(header.getRequired()), () -> parameterBuilder.required(header.getRequired()), () -> parameterBuilder.required(Boolean.TRUE));
            parameterBuilder.modelRef(Swagger2Properties.Constatnt.MODEL_REF);
            parameterBuilder.parameterType(Swagger2Properties.Constatnt.HEADER);
            parameters.add(parameterBuilder.build());
        }
        return parameters;
    }

}
