package com.restaurant.dinner.portal.extension.sign;

import com.kitchen.market.common.identity.KitUUID;
import com.kitchen.market.common.security.encryption.single.KitEncryptionMD5;
import com.restaurant.dinner.portal.constant.ProjectConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/16
 */
@Component
public class AuthTokenManager {

    private static final TimeUnit TOKEN_TIME_UNIT = TimeUnit.MILLISECONDS;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Integer USER_AUTH_TOKEN_CACHE_SIZE = 10000;
    private static Map<String, UserAuthTokenInfo> USER_AUTH_TOKEN_CACHE = new ConcurrentHashMap<>();

    /**
     * 根据请求中的用户信息，获取认证Token
     *
     * 步骤说明：
     * 1、通过Token索引，从本地缓存中查找对应的Token；
     * 2、若本地缓存中不存在，则从中心缓存（Redis）中，查找对应的Key；
     * 3、若没有找到，则返回NULL；
     * 4、若存在Token，则返回。
     */
    public String getToken(String authTokenKey) {
        if (authTokenKey == null || authTokenKey.isEmpty()) {
            return null;
        }
        // 从本地缓存获取
        UserAuthTokenInfo tokenInfo = USER_AUTH_TOKEN_CACHE.get(authTokenKey);

        if (tokenInfo != null) {
            // 命中本地缓存
            Long tokenEndTime = tokenInfo.getTokenEndTime();
            if (tokenEndTime == -1) {
                // token无时效
                return tokenInfo.getUserAuthToken();
            }
            Long curTime = System.currentTimeMillis();
            if (curTime > tokenEndTime) {
                // 本地Token超时，从中心获取
                return getTokenFromCenterCache(authTokenKey);
            }
            return tokenInfo.getUserAuthToken();
        } else {
            // 若本地缓存未命中，从中心缓存获取
            return getTokenFromCenterCache(authTokenKey);
        }
    }

    private String getTokenFromCenterCache(String authTokenKey) {
        String authToken = stringRedisTemplate.opsForValue().get(authTokenKey);
        if (authToken == null || StringUtils.isEmpty(authToken)) {
            return null;
        }
        Long expire = stringRedisTemplate.getExpire(authTokenKey, TOKEN_TIME_UNIT);

        UserAuthTokenInfo tokenInfo = new UserAuthTokenInfo();
        if (expire == -1) {
            tokenInfo.setTokenEndTime(-1L);
        } else {
            tokenInfo.setTokenEndTime(System.currentTimeMillis() + expire);
        }
        tokenInfo.setUserAuthToken(authToken);
        tokenInfo.setUserAuthTokenKey(authTokenKey);
        // 控制本地缓存的大小，超过则清空
        if (USER_AUTH_TOKEN_CACHE.size() > USER_AUTH_TOKEN_CACHE_SIZE) {
            USER_AUTH_TOKEN_CACHE.clear();
        }
        // 放到本地缓存
        USER_AUTH_TOKEN_CACHE.put(authTokenKey, tokenInfo);

        return authToken;
    }

    /**
     * 生成认证Token的索引Key
     * @param userId
     * @param deviceId
     * @return
     */
    public static String generateTokenKey(String userId, String deviceId) {
        String authTokenKey = ProjectConstant.CACHE_KEY_PREFIX_GATEWAY_AUTH_USER_TOKEN__ + userId;
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
    public boolean storageToken(String authTokenKey, String authToken) {
        if (authTokenKey == null || authTokenKey.isEmpty() || authToken == null || authToken.isEmpty()) {
            return false;
        }

        // 存储至中心缓存
        stringRedisTemplate.opsForValue().set(authTokenKey, authToken);
        // 删除本地缓存
        clearLocalToken(authTokenKey);

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
    public boolean storageToken(String authTokenKey, String authToken, Integer timeLimit, TimeUnit timeUnit) {
        if (authTokenKey == null || authTokenKey.isEmpty() || authToken == null || authToken.isEmpty()) {
            return false;
        }

        // 存储至中心缓存
        stringRedisTemplate.opsForValue().set(authTokenKey, authToken, timeLimit, timeUnit);
        // 删除本地缓存
        clearLocalToken(authTokenKey);

        return true;
    }

    /**
     * 清除本地和中心缓存
     *
     * @param authTokenKey
     * @return
     */
    public boolean clearToken(String authTokenKey) {
        USER_AUTH_TOKEN_CACHE.remove(authTokenKey);
        return stringRedisTemplate.delete(authTokenKey);
    }

    private void clearLocalToken(String authTokenKey) {
        USER_AUTH_TOKEN_CACHE.remove(authTokenKey);
    }

    /**
     * 重置Token的有效时间为永久有效
     * @param authTokenKey
     * @param authToken
     */
    public void resetTokenLimit(String authTokenKey, String authToken) {
        if (authTokenKey == null || authTokenKey.isEmpty() || authToken == null || authToken.isEmpty()) {
            return;
        }
        // 删除本地缓存
        clearLocalToken(authTokenKey);

        // 修改中心缓存
        stringRedisTemplate.opsForValue().set(authTokenKey, authToken);
    }

    /**
     * 重置Token的有效时间
     * @param authTokenKey
     * @param authToken
     */
    public void resetTokenLimit(String authTokenKey, String authToken, Integer timeLimit) {
        if (authTokenKey == null || authTokenKey.isEmpty() || authToken == null || authToken.isEmpty()) {
            return;
        }
        // 删除本地缓存
        clearLocalToken(authTokenKey);

        // 修改中心缓存
        stringRedisTemplate.opsForValue().set(authTokenKey, authToken, timeLimit, TOKEN_TIME_UNIT);
    }

    private class UserAuthTokenInfo {
        private String userAuthTokenKey;
        private String userAuthToken;
        // 单位：毫秒
        private Long tokenEndTime;

        public String getUserAuthTokenKey() {
            return userAuthTokenKey;
        }

        public void setUserAuthTokenKey(String userAuthTokenKey) {
            this.userAuthTokenKey = userAuthTokenKey;
        }

        public String getUserAuthToken() {
            return userAuthToken;
        }

        public void setUserAuthToken(String userAuthToken) {
            this.userAuthToken = userAuthToken;
        }

        public Long getTokenEndTime() {
            return tokenEndTime;
        }

        public void setTokenEndTime(Long tokenEndTime) {
            this.tokenEndTime = tokenEndTime;
        }
    }
}
