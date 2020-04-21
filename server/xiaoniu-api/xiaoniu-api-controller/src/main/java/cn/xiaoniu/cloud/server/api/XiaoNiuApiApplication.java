package cn.xiaoniu.cloud.server.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("cn.xiaoniu.cloud.server.api.dao.db")
@ComponentScan(basePackages = "cn.xiaoniu.cloud.server")
public class XiaoNiuApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoNiuApiApplication.class, args);
    }

}
