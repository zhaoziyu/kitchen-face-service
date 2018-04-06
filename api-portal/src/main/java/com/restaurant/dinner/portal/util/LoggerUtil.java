package com.restaurant.dinner.portal.util;

import com.kitchen.common.api.constant.code.IReturnCode;
import org.slf4j.Logger;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/9
 */
public class LoggerUtil {
    /**
     * 根据返回参数记录日志
     * @param logger
     * @param returnCode
     */
    public static void logWarnReturnCode(Logger logger, IReturnCode returnCode) {
        logger.warn("{}-{}-{}-{}",
                returnCode.getCode(),
                returnCode.getMsg(),
                returnCode.getBizCode(),
                returnCode.getBizMsg());
    }

    /**
     * 根据返回参数记录日志
     * @param logger
     * @param returnCode
     */
    public static void logErrorReturnCode(Logger logger, IReturnCode returnCode) {
        logger.error("{}-{}-{}-{}",
                returnCode.getCode(),
                returnCode.getMsg(),
                returnCode.getBizCode(),
                returnCode.getBizMsg());
    }
}
