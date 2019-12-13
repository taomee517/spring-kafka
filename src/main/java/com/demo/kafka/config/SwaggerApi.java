package com.demo.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerApi{


    /**
     * 创建API基本信息
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                // 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
                .apis(RequestHandlerSelectors.basePackage("com.demo"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建API的基本信息，这些信息会在Swagger UI中进行显示
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // API 标题
                .title("kafka测试项目")
                // API描述
                .description("kafka服务")
                // 联系人
                .contact("taomee517@qq.com")
                // 版本号
                .version("1.0")
                .build();
    }




}
