package com.restaurant.dinner.portal.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 工程环境工具类
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/8
 */
@Component
public class EnvironmentUtil {
    private static boolean DEBUG = true;

    @Value("${debug:false}")
    public void setDebug(boolean debug) {
        DEBUG = debug;
    }

    /**
     * 是否为生产环境
     * @return
     */
    public static boolean isDebug() {
        return DEBUG;
    }
}
