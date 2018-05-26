package com.restaurant.dinner.portal.extension;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义WebMvc配置类
 * 注意：一个工程中，仅可以存在一个继承WebMvcConfigurationSupport的自定义配置类
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/3
 */
@Configuration
public class CustomWebMvcSupport extends WebMvcConfigurationSupport {
    //----------------------------FastJSON----------------------------
    //自定义消息转换器，替换原Jackson为FastJson
    @Value("${kitchen.gateway.json.property-naming-strategy:CamelCase}")
    public PropertyNamingStrategy propertyNamingStrategy;
    @Value("${kitchen.gateway.json.date-format:yyyy-MM-dd HH:mm:ss}")
    public String dateFormat;
    @Value("${kitchen.gateway.json.charset:UTF-8}")
    public String charset;
    @Value("${kitchen.gateway.json.is-write-map-null-value:true}")
    public boolean writeMapNullValue;
    @Value("${kitchen.gateway.json.is-write-null-list-as-empty:true}")
    public boolean writeNullListAsEmpty;
    @Value("${kitchen.gateway.json.is-write-null-string-as-empty:true}")
    public boolean writeNullStringAsEmpty;
    @Value("${kitchen.gateway.json.is-write-null-number-as-zero:true}")
    public boolean writeNullNumberAsZero;
    @Value("${kitchen.gateway.json.is-write-null-boolean-as-false:true}")
    public boolean writeNullBooleanAsFalse;
    @Value("${kitchen.gateway.json.is-sort-field:true}")
    public boolean sortField;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        fastJsonConfig.setSerializerFeatures(this.loadFeature());

        fastJsonConfig.setDateFormat(dateFormat);
        fastJsonConfig.setCharset(Charset.forName(charset));
        //配置JSON字段的命名方言
        if (propertyNamingStrategy != null) {
            SerializeConfig.getGlobalInstance().propertyNamingStrategy = propertyNamingStrategy;
            fastJsonConfig.getSerializeConfig().propertyNamingStrategy = propertyNamingStrategy;
            fastJsonConfig.getParserConfig().propertyNamingStrategy = propertyNamingStrategy;
        }

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        // 设置FastJson配置
        fastConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastConverter);
    }
    private SerializerFeature[] loadFeature() {
        ArrayList<SerializerFeature> features = new ArrayList<>();
        if (writeMapNullValue) {
            //输出值为null的字段
            features.add(SerializerFeature.WriteMapNullValue);
        }
        if (writeNullListAsEmpty) {
            //将Collection类型字段的字段空值输出为
            features.add(SerializerFeature.WriteNullListAsEmpty);
        }
        if (writeNullStringAsEmpty) {
            //将字符串类型字段的空值输出为空字符串
            features.add(SerializerFeature.WriteNullStringAsEmpty);
        }
        if (writeNullNumberAsZero) {
            //将数值类型字段的空值输出为0
            features.add(SerializerFeature.WriteNullNumberAsZero);
        }
        if (writeNullBooleanAsFalse) {
            //将Boolean类型字段的空值输出为false
            features.add(SerializerFeature.WriteNullBooleanAsFalse);
        }
        if (sortField) {
            //输出字段排序
            features.add(SerializerFeature.SortField);
            features.add(SerializerFeature.MapSortField);
        }
        return features.toArray(new SerializerFeature[]{});
    }
    //----------------------------FastJSON----------------------------

    //-------------------------------CORS-----------------------------
    //跨域访问相关配置
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
    //-------------------------------CORS-----------------------------

}
