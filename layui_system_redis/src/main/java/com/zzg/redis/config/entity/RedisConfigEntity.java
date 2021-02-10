package com.zzg.redis.config.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * redis 缓存数据库配置对象定义
 * 
 * @author zzg
 *
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "zzg.redis")
@PropertySource("classpath:redis.properties")
public class RedisConfigEntity {
	// Redis数据库索引（默认为0）
	private String database;
	// Redis服务器地址
	private String host;
	// Redis服务器连接端口
	private String port;
	// Redis服务器连接密码（默认为空）
	private String password;
	// 连接池最大连接数（使用负值表示没有限制）
	private String maxActive;
	// 连接池最大阻塞等待时间（使用负值表示没有限制）
	private String maxWait;
	// 连接池中的最大空闲连接
	private String maxIdle;
	// 连接池中的最小空闲连接
	private String minIdle;
	// 连接超时时间（毫秒）
	private String timeout;

	// set 和 get 方法
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

}
