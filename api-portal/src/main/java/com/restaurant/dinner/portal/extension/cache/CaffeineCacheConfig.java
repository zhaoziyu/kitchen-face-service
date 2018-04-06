package com.restaurant.dinner.portal.extension.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/29
 */
@Configuration
public class CaffeineCacheConfig {

    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        ArrayList<CaffeineCache> caffeineCaches = new ArrayList<>();

        for(CacheNames caches : CacheNames.values()) {

            Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
            caffeine.maximumSize(caches.getMaximumSize());
            if (caches.getInitialCapacity() != null) {
                caffeine.initialCapacity(caches.getInitialCapacity());
            }
            if (caches.getExpireAfterWrite() != null) {
                caffeine.expireAfterWrite(caches.getExpireAfterWrite(), TimeUnit.SECONDS);
            } else {
                caffeine.expireAfterAccess(caches.getExpireAfterAccess(), TimeUnit.SECONDS);
            }

            CaffeineCache caffeineCache = new CaffeineCache(
                    caches.name(),
                    caffeine.build()
            );
            caffeineCaches.add(caffeineCache);
        }

        cacheManager.setCaches(caffeineCaches);

        return cacheManager;
    }
}
