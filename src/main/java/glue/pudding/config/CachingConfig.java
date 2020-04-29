package glue.pudding.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * Created by GUIXu on 2020/4/22.
 */
@Configuration
@EnableCaching
public class CachingConfig {

    private final int maxIdle = 8;
    private final long maxWaitMills = Long.MAX_VALUE;
    private final int maxTotal = 8;
    private final boolean testOnBorrow = true;

    private final String host = "127.0.0.1";
    private final int port = 6379;
    private final String password = "pudding";
    @Bean
    public JedisPoolConfig poolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMills);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        return jedisPoolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        return  template;
    }

    @Bean
    public CacheManager cacheManager(JedisConnectionFactory factory) {
        return RedisCacheManager.create(factory);
    }
}
