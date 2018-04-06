package com.restaurant.dinner.portal.extension;

import com.restaurant.dinner.portal.constant.ProjectConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 * 在开发环境和测试环境开启
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/12
 */
@EnableSwagger2
@Profile({ProjectConstant.DEPLOY_ENVIRONMENT_TYPE_DEV, ProjectConstant.DEPLOY_ENVIRONMENT_TYPE_TEST})
@Configuration
public class Swagger2 {
    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))//扫描路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("标题")
                .termsOfServiceUrl("https://xxxxxx/openapi/")
                .description("描述")
                .contact(new Contact("联系人", "主页地址", "邮箱地址")) //(name,url,email)
                .version("v1.0")
                .build();

    }
}
