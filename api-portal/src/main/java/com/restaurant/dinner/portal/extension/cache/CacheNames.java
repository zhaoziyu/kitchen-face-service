package com.restaurant.dinner.portal.extension.cache;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/30
 */
enum CacheNames {

    //TODO 示例
    //枚举名称对应注解的value或cacheNames属性值
    DEMO_CACHE_USER_INFO(10L, 10L),
    DEMO_CACHE_PROJECT_CONFIG
    ;



    //------------------------------------------------------------------------------------
    /**
     * 默认配置，可根据业务需要调整
     */
    //初始的缓存条数，默认不填写此配置
    private Integer initialCapacity = null;
    //缓存的最大条数，默认最大1000条
    private Long maximumSize = 1000L;
    //最后一次写入或访问后经过固定时间过期[单位：秒]，默认5分钟后过期
    private Long expireAfterAccess = 300L;
    //最后一次写入后经过固定时间过期[单位：秒]，默认不填写此配置
    private Long expireAfterWrite = null;


    CacheNames() {
    }

    CacheNames(Long expireAfterAccess) {
        this.expireAfterAccess = expireAfterAccess;
    }

    CacheNames(Long expireAfterAccess, Long maximumSize) {
        this.expireAfterAccess = expireAfterAccess;
        this.maximumSize = maximumSize;
    }

    CacheNames(Long expireAfterAccess, Long maximumSize, Integer initialCapacity, Long expireAfterWrite) {
        this.expireAfterAccess = expireAfterAccess;
        this.maximumSize = maximumSize;
        this.initialCapacity = initialCapacity;
        this.expireAfterWrite = expireAfterWrite;
    }

    public Integer getInitialCapacity() {
        return initialCapacity;
    }

    public Long getMaximumSize() {
        return maximumSize;
    }

    public Long getExpireAfterAccess() {
        return expireAfterAccess;
    }

    public Long getExpireAfterWrite() {
        return expireAfterWrite;
    }
}
