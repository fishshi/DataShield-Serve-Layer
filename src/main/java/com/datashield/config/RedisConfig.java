package com.datashield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 配置类
 */
@Configuration
class RedisConfiguration {
    /**
     * 实例化 Redis 连接工厂对象
     *
     * @return Redis 连接工厂对象
     */
    @Bean
    LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * 实例化 StringRedisTemplate 对象
     *
     * @param redisConnectionFactory Redis 连接工厂对象
     * @return StringRedisTemplate 对象
     */
    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}