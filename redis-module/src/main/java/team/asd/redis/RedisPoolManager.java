package team.asd.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolManager {
	public static JedisPool getPool() {
		return new JedisPool(new JedisPoolConfig(), "185.126.115.194", 6379, 2000, "testP@ssword");
	}
}
