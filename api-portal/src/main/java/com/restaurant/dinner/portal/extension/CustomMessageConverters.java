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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义消息转换器，替换原Jackson为FastJson
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/3
 */
@Configuration
public class CustomMessageConverters extends WebMvcConfigurerAdapter {
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
}
