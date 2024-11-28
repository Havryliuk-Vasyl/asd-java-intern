package team.asd.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class RedisClientService implements IsRedisClientService {
	private final JedisPool jedisPool = RedisPoolManager.getPool();

	@Override
	public String readByKey(String key) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.get(key);
		}
	}

	@Override
	public void saveValueByKey(String key, String value) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.set(key, value);
		}
	}

	@Override
	public void saveList(String keyList, List<String> list) {
		try (Jedis jedis = jedisPool.getResource()) {
			for (String value : list) {
				jedis.rpush(keyList, value);
			}
		}
	}

	@Override
	public void saveElementIntoList(String keyList, String value) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.sadd(keyList, value);
		}
	}

	@Override
	public List<String> retrieveList(String keyList) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.lrange(keyList, 0, -1);
		}
	}

	@Override
	public void saveValueInHashMap(String primaryKey, String secondaryKey, String value) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.hset(primaryKey, secondaryKey, value);
		}
	}

	@Override
	public String retrieveValueFromHashMap(String primaryKey, String secondaryKey) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.hget(primaryKey, secondaryKey);
		}
	}

	@Override
	public String retrieveValueFromHashMap(String primaryKey) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.get(primaryKey);
		}
	}

	@Override
	public void saveValueWithExpireDate(String key, String value, long expireDate) {
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.setex(key, expireDate, value);
		}
	}
}
