package com.restaurant.dinner.portal.util;

import com.restaurant.dinner.portal.constant.ProjectConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 国际化工具类
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017/10/18
 */
@Component
public class LocaleUtil {
    private static final String REQUEST_PARAM_NAME = "locale";
    private static Locale defaultLocale = null;

    private static MessageSource messageSource;
    @Autowired
    protected void setMessageSource(MessageSource ms) {
        messageSource = ms;
    }

    public static String message(String key) {
        return message(key, null, null);
    }
    public static String message(String key, HttpServletRequest request) {
        return message(key, request, null);
    }
    public static String message(String key, String... params) {
        return message(key, null, params);
    }
    public static String message(String key, HttpServletRequest request, String... params) {
        Locale locale;

        if (request != null && request.getParameter(REQUEST_PARAM_NAME) != null) {
            // 使用请求的
            String strLocale = request.getParameter(REQUEST_PARAM_NAME);
            String[] arrLocale = strLocale.split("_");
            locale = new Locale(arrLocale[0], arrLocale[1]);

        } else {
            // 使用默认
            locale = getDefaultLocale();
        }
        return messageSource.getMessage(key, params, locale);
    }

    public static Locale getDefaultLocale() {
        if (defaultLocale == null) {
            String strLocale = ProjectConstant.GLOBAL_LANGUAGE_DEFAULT;
            if (strLocale == null || strLocale.isEmpty()) {
                strLocale = "zh_CN";
            }
            String[] arrLocale = strLocale.split("_");
            defaultLocale = new Locale(arrLocale[0], arrLocale[1]);
        }
        return defaultLocale;
    }
}
