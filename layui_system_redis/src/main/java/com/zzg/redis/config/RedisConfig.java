package com.zzg.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.zzg.redis.config.entity.RedisConfigEntity;
import com.zzg.redis.util.RedisUtil;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	@Autowired
	private RedisConfigEntity config;

	// 获取JedisPoolConfig对象
	@Bean
	public JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//		jedisPoolConfig.setMaxIdle(Integer.valueOf(config.getMaxIdle().trim()));
//		jedisPoolConfig.setMinIdle(Integer.valueOf(config.getMinIdle().trim()));
//		jedisPoolConfig.setMaxWaitMillis(Integer.valueOf(config.getMaxWait().trim()));
		return jedisPoolConfig;
	}

	// 获取JedisConnectionFactory 连接工厂对象
	@SuppressWarnings("deprecation")
	@Bean
	public JedisConnectionFactory getConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setPoolConfig(getJedisPoolConfig());
		connectionFactory.setDatabase(Integer.valueOf(config.getDatabase().trim()));
		connectionFactory.setHostName(config.getHost().trim());
		connectionFactory.setPassword(config.getPassword().trim());
		connectionFactory.setPort(Integer.valueOf(config.getPort().trim()));
//		connectionFactory.setTimeout(Integer.valueOf(config.getTimeout().trim()));
		return connectionFactory;
	}

	// 获取RedisTemplate对象
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		JedisConnectionFactory factory = getConnectionFactory();
		// 设置Jedis 连接工厂对象
		redisTemplate.setConnectionFactory(factory);
		// 设置是否启动事务
		redisTemplate.setEnableTransactionSupport(true);
		// 设置Key 序列化方式
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// 设置Hash Key 序列化方式
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// 设置Hash Value 序列化方式
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		// 设置Value 序列化方式
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		// 动态更新redisTemplate 设置
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	// 自定义redis 工具类
	@Bean(name = "redisUtil")
	public RedisUtil redisUtil() {
		RedisUtil redisUtil = new RedisUtil();
		redisUtil.setRedisTemplate(redisTemplate());
		return redisUtil;
	}

}
