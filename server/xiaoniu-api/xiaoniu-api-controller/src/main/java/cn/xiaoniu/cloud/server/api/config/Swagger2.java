//package cn.xiaoniu.cloud.server.api.config;
//
//import cn.xiaoniu.cloud.server.web.util.SpringUtil;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class Swagger2 {
//
//    private String basePackage;
//    private String title;
//    private String description;
//    private String contackName;
//    private String contackUrl;
//    private String contackEmail;
//    private String version;
//
//    @Bean
//    @ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
//    public Docket createRestApi() {
//        this.basePackage = SpringUtil.getProperty("swagger.base.package");
//        this.title = SpringUtil.getProperty("swagger.title");
//        this.description = SpringUtil.getProperty("swagger.description");
//        this.contackName = SpringUtil.getProperty("swagger.concat.name");
//        this.contackUrl = SpringUtil.getProperty("swagger.concat.url");
//        this.contackEmail = SpringUtil.getProperty("swagger.concat.email");
//        this.version = SpringUtil.getProperty("spring.application.version");
//
//        List<Parameter> parameters = new ArrayList<Parameter>();
//
//        // Token 请求头
//        ParameterBuilder tokenParameter = new ParameterBuilder();
//        tokenParameter.name("Access-Token")
//                .description("登录发放令牌,非需登录接口可不传")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false).build();
//        parameters.add(tokenParameter.build());
//
//        return (new Docket(DocumentationType.SWAGGER_2))
//                .apiInfo(this.apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(basePackage))
//                .paths(PathSelectors.any())
//                .build()
//                .globalOperationParameters(parameters)
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return (new ApiInfoBuilder())
//                .title(title)
//                .description(description)
//                .version(this.version)
//                .build();
//    }
//}
