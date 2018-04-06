package com.restaurant.dinner.portal.extension.sign;

import com.kitchen.cache.KitchenCacheConfig;
import com.kitchen.cache.KitchenCacheKeys;
import com.kitchen.cache.local.LocalCacheHelper;
import com.kitchen.cache.redis.RedisCacheHelper;
import com.kitchen.market.common.identity.KitUUID;
import com.kitchen.market.common.security.encryption.single.KitEncryptionMD5;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/16
 */
public class AuthTokenManager {

    /**
     * 根据请求中的用户信息，获取认证Token
     *
     * 步骤说明：
     * 1、通过Token索引，从本地缓存中查找对应的Token；
     * 2、若本地缓存中不存在，则从中心缓存（Redis）中，查找对应的Key；
     * 3、若没有找到，抛出Token不存在或已过期的异常；
     * 4、若存在Token，则返回。
     */
    public static String getToken(String authTokenKey) {
        if (authTokenKey == null || authTokenKey.isEmpty()) {
            return null;
        }
        String authToken = null;
        if (KitchenCacheConfig.KITCHEN_CACHE_LOCAL_OPEN) {
            authToken = LocalCacheHelper.get(authTokenKey, String.class);
        }
        if ((authToken == null || authToken.isEmpty()) && KitchenCacheConfig.KITCHEN_CACHE_REDIS_OPEN) {
            authToken = RedisCacheHelper.StringHelper.getString(authTokenKey);
        }
        return authToken;
    }

    /**
     * 生成认证Token的索引Key
     * @param userId
     * @param deviceId
     * @return
     */
    public static String generateTokenKey(String userId, String deviceId) {
        String authTokenKey = KitchenCacheKeys.Prefix.API_PORTAL_AUTH_USER_TOKEN__ + userId;
        // 若请求参数中的设备标识不为空，则将其作为Token索引的一部分
        if (deviceId != null && !deviceId.isEmpty()) {
            authTokenKey = authTokenKey + "_" + deviceId;
        }

        return authTokenKey;
    }

    /**
     * 生成新的Token
     * @param userId
     * @return
     */
    public static String generateNewToken(String userId) {
        String uuid = KitUUID.getPureUUID();
        String token = KitEncryptionMD5.encrypt(uuid + "_" + userId);
        return token;
    }

    /**
     * 存储Token（永久）
     * 将Token存储至本地缓存和中心缓存
     * TODO 可改为异步执行
     *
     * @return
     */
    public static boolean storageToken(String authTokenKey, String authToken) {
        if ((authTokenKey == null && authTokenKey.isEmpty()) || (authToken == null && authToken.isEmpty())) {
            return false;
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_LOCAL_OPEN) {
            LocalCacheHelper.set(authTokenKey, authToken);
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_REDIS_OPEN) {
            RedisCacheHelper.StringHelper.setString(authTokenKey, authToken);
        }
        return true;
    }

    /**
     * 存储Token（时限）
     * 将Token存储至本地缓存和中心缓存
     * TODO 可改为异步执行
     *
     * @param authTokenKey
     * @param authToken
     * @param timeLimit 超时时间，多少毫秒后失效。单位：毫秒
     * @return
     */
    public static boolean storageToken(String authTokenKey, String authToken, Integer timeLimit) {
        if ((authTokenKey == null && authTokenKey.isEmpty()) || (authToken == null && authToken.isEmpty())) {
            return false;
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_LOCAL_OPEN) {
            // TODO 需改为根据时限存储
            LocalCacheHelper.set(authTokenKey, authToken);
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_REDIS_OPEN) {
            RedisCacheHelper.StringHelper.setString(authTokenKey, authToken, timeLimit);
        }
        return true;
    }
    
    /**
     * 重置Token的有效时间
     * @param authTokenKey
     * @param authToken
     */
    public static void resetTokenLimit(String authTokenKey, String authToken, Integer timeLimit) {
        if ((authTokenKey == null && authTokenKey.isEmpty()) || (authToken == null && authToken.isEmpty())) {
            return;
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_LOCAL_OPEN) {
            // TODO 需改为根据时限存储
            LocalCacheHelper.set(authTokenKey, authToken);
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_REDIS_OPEN) {
            RedisCacheHelper.StringHelper.setString(authTokenKey, authToken, timeLimit);
        }
    }

    /**
     * 重置Token的有效时间为永久有效
     * @param authTokenKey
     * @param authToken
     */
    public static void resetTokenLimit(String authTokenKey, String authToken) {
        if ((authTokenKey == null && authTokenKey.isEmpty()) || (authToken == null && authToken.isEmpty())) {
            return;
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_LOCAL_OPEN) {
            LocalCacheHelper.set(authTokenKey, authToken);
        }
        if (KitchenCacheConfig.KITCHEN_CACHE_REDIS_OPEN) {
            RedisCacheHelper.StringHelper.setString(authTokenKey, authToken);
        }
    }
}
