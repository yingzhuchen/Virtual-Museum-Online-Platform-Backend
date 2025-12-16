package org.openapitools.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:/application.properties")
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.password}")
    private String redisPassword;

    //延迟加载JedisPool
    private static JedisPool jedisPool=null;
    public JedisPool getJedisPool(){
        if(jedisPool==null){
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(100); // 设置最大连接数
            poolConfig.setMaxIdle(10); // 设置最大空闲连接数
            poolConfig.setMinIdle(5); // 设置最小空闲连接数
            jedisPool = new JedisPool(poolConfig, redisHost, redisPort, 0,redisPassword);
        }
        return jedisPool;
    }
}
