package com.restaurant.dinner.portal.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 工程全局常量
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017/10/18
 */
@Component
public class ProjectConstant {
    /**
     * 默认语言（支持：zh_CN,en_US,ja_JP,zh_TW）
     */
    public static String GLOBAL_LANGUAGE_DEFAULT;

    @Value("${project.language.default:zh_CN}")
    public void setGlobalLanguageDefault(String globalLanguageDefault) {
        GLOBAL_LANGUAGE_DEFAULT = globalLanguageDefault;
    }

    /**
     * 用户认证令牌的缓存Key前缀
     */
    public static final String CACHE_KEY_PREFIX_GATEWAY_AUTH_USER_TOKEN__ = "gateway_auth_user_token__";


    //--------------------------------------------------------------------------------

    /**
     * springboot默认将HTTP异常跳转至/error
     */
    public static final String ERROR_PATH = "/error";

    // 部署环境
    public static final String DEPLOY_ENVIRONMENT_TYPE_DEV = "dev";
    public static final String DEPLOY_ENVIRONMENT_TYPE_TEST = "test";
    public static final String DEPLOY_ENVIRONMENT_TYPE_PROD = "prod";

    public static final Integer YES = 1; //是（true）
    public static final Integer NO = 0;  //否（false）
}
