package com.restaurant.dinner.portal.extension;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域访问相关配置
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/4
 */
@Configuration
public class CustomCorsMapping extends WebMvcConfigurerAdapter {
    /**
     * 是否开启CORS跨域请求
     */
    @Value("${kitchen.gateway.access-control.cors.open:false}")
    public boolean isOpen;
    /**
     * 允许跨域请求的路径映射
     */
    @Value("${kitchen.gateway.access-control.cors.mapping:/**}")
    public String mapping;
    /**
     * 允许跨域请求的白名单。
     * 该项控制数据的可见范围，如果希望数据对任何人都可见，可以填写“*”。
     * 允许跨域访问的IP或域名，多个IP或域名之间用逗号隔开，设置为空则不允许任何跨域的访问。
     */
    @Value("${kitchen.gateway.access-control.cors.allow-origins:*}")
    public String allowedOrigins;
    /**
     * 这是对预请求当中Access-Control-Request-Method的回复，这一回复将是一个以逗号分隔的列表。
     * 尽管客户端或许只请求某一方法，但服务端仍然可以返回所有允许的方法，以便客户端将其缓存。
     */
    @Value("${kitchen.gateway.access-control.cors.allow-methods:*}")
    public String allowedMethods;
    /**
     * Access-Control-Allow-Credentials（可选）
     * 该项标志着请求当中是否包含cookies信息，只有一个可选值：true（必为小写）。
     * 如果不包含cookies，请略去该项，而不是填写false。
     * 这一项与XmlHttpRequest2对象当中的withCredentials属性应保持一致，
     * 即withCredentials为true时该项也为true；withCredentials为false时，省略该项不写。
     */
    @Value("${kitchen.gateway.access-control.cors.allow-credentials:false}")
    public boolean allowCredentials;
    /**
     * OPTIONS请求的缓存时间（以秒为单位）
     * 如果不设置或设置为0，则每次跨域的请求都会先发起一个OPTIONS请求，再发起真实请求
     */
    @Value("${kitchen.gateway.access-control.cors.max-age:0}")
    public Integer maxAge;

    /**
     * 弃用
     * 该项确定XmlHttpRequest2对象当中getResponseHeader()方法所能获得的额外信息。
     * 当你需要访问额外的信息时，就需要在这一项当中填写并以逗号进行分隔。
     * 不过目前浏览器对这一项的实现仍然有一些问题
     */
    @Deprecated
    public String allowedHeaders = "*";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (!isOpen) {
            super.addCorsMappings(registry);
            return;
        }

        // 开启跨域请求，并设置相关参数
        CorsRegistration registration = registry.addMapping(mapping);
        registration.allowedOrigins(allowedOrigins);
        registration.allowedMethods(allowedMethods);
        registration.maxAge(maxAge);
        if (allowCredentials) {
            registration.allowCredentials(allowCredentials);
        }

        //registration.allowedHeaders(allowedHeaders);
    }
}
