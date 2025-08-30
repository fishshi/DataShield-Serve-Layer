package com.datashield.component;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis 组件类
 */
@Component
public class RedisComponent {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置缓存, 无过期时间
     *
     * @param key   缓存的 key
     * @param value 缓存的 value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存, 带过期时间
     *
     * @param key      缓存的 key
     * @param value    缓存的 value
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存的 key
     *
     * @return 缓存的 value
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 缓存的 key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 判断缓存是否存在
     *
     * @param key 缓存的 key
     * @return true: 存在; false: 不存在
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置缓存过期时间
     *
     * @param key      缓存的 key
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获取缓存的剩余过期时间
     *
     * @param key      缓存的 key
     * @param timeUnit 时间单位
     *
     * @return 缓存的剩余过期时间
     */
    public long getExpire(String key, TimeUnit timeUnit) {
        return stringRedisTemplate.getExpire(key, timeUnit);
    }
}
