package com.restaurant.dinner.portal.extension.sign;

import com.alibaba.fastjson.JSON;
import com.kitchen.common.api.pojo.vo.BaseJsonVo;
import com.restaurant.dinner.portal.constant.SignConstant;
import com.restaurant.dinner.portal.extension.sign.rsa.KitRSASign;
import com.restaurant.dinner.portal.extension.sign.util.SignToBeStringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 接口响应结果签名
 * 在开启返回结果签名，且返回对象类型为指定JSON结构时有效
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/16
 */
@Aspect
@Component
public class ResponseSigner {
    private static final Logger logger = LoggerFactory.getLogger(ResponseSigner.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    private void controller(){}

    @Around("controller()")
    public Object signInterceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object[] args = proceedingJoinPoint.getArgs();
        // 调用Controller方法
        Object returnValue = proceedingJoinPoint.proceed(args);

        // 未开启返回参数签名，不做签名处理
        if (!SignConstant.ACCESS_CONTROL_OPEN_RETURN_SIGN) {
            return returnValue;
        }

        // 返回对象结构不是指定的JSON结构，不做签名处理
        if (!(returnValue instanceof BaseJsonVo)) {
            return returnValue;
        }

        BaseJsonVo baseJsonVo = (BaseJsonVo) returnValue;
        // 在返回结果参数中添加接口响应的时间戳
        long serverTime = System.currentTimeMillis();
        baseJsonVo.setTimestamp(Long.toString(serverTime));

        // 处理返回签名
        baseJsonVo.setSignType(SignConstant.SIGN_TYPE_RSA);
        String json = JSON.toJSONString(baseJsonVo);
        String toBeSignedString = SignToBeStringUtil.getToBeSignedString(json);
        String sign = KitRSASign.rsaSign(toBeSignedString, SecretKeyManager.SERVER_PRIVATE_KEY_PKCS8, SignConstant.SIGN_CHARSET_UTF8, SignConstant.SIGN_TYPE_RSA);
        baseJsonVo.setSign(sign);

        long endTime = System.currentTimeMillis();
        logger.info("返回参数签名耗时：" + (endTime - startTime) + "毫秒");

        return baseJsonVo;
    }
}
