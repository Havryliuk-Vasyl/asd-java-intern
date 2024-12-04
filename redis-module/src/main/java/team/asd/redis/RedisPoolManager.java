package team.asd.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisPoolManager {
	@Value("${redis.host}")
	private String redisHost;

	@Value("${redis.port}")
	private int redisPort;

	@Value("${redis.timeout}")
	private int redisTimeout;

	@Value("${redis.password}")
	private String redisPassword;

	public JedisPool getPool() {
		return new JedisPool(new JedisPoolConfig(), redisHost, redisPort, redisTimeout, redisPassword);
	}
}
