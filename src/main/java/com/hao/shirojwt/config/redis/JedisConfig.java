package com.hao.shirojwt.config.redis;

import com.hao.shirojwt.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis配置，项目启动注入JedisPool
 */
@Slf4j
@Configuration
public class JedisConfig {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.database}")
    private int database;
    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.pool.max-active}")
    private int maxActive;
    @Value("${redis.pool.max-wait}")
    private int maxWait;
    @Value("${redis.pool.max-idle}")
    private int maxIdle;
    @Value("${redis.pool.min-idle}")
    private int minIdle;

    @Bean
    public JedisPool redisPoolFactory() {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMinIdle(minIdle);

            String pwd = StringUtil.isBlank(password) ? null : password;
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, pwd, database);
            log.info("初始化Redis连接池JedisPool成功!地址: " + host + ":" + port);
            return jedisPool;
        } catch (Exception e) {
            log.error("初始化Redis连接池JedisPool异常:" + e.getMessage());
        }
        return null;
    }

}
