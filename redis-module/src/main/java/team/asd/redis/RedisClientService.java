package team.asd.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class RedisClientService implements IsRedisClientService {
	private final JedisPool jedisPool;

	public RedisClientService(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public String readByKey(String key) {
		if (!isValid(key)) {
			return null;
		}

		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.get(key);
		}
	}

	@Override
	public void saveValueByKey(String key, String value) {
		if (isValid(key) && isValid(value)) {
			try (Jedis jedis = jedisPool.getResource()) {
				jedis.set(key, value);
			}
		}
	}

	@Override
	public void saveList(String keyList, List<String> list) {
		if (!isValid(keyList)) {
			return;
		}

		List<String> newList = list.stream()
				.filter(this::isValid)
				.toList();

		if (!newList.equals(list)) {
			return;
		}

		try (Jedis jedis = jedisPool.getResource()) {
			for (String value : newList) {
				jedis.rpush(keyList, value);
			}
		}
	}

	@Override
	public void saveElementIntoList(String keyList, String value) {
		if (isValid(keyList) && isValid(value)) {
			try (Jedis jedis = jedisPool.getResource()) {
				jedis.sadd(keyList, value);
			}
		}
	}

	@Override
	public List<String> retrieveList(String keyList) {
		if (!isValid(keyList)) {
			return null;
		}

		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.lrange(keyList, 0, -1);
		}
	}

	@Override
	public void saveValueInHashMap(String primaryKey, String secondaryKey, String value) {
		if (isValid(primaryKey) && isValid(secondaryKey) && isValid(value)) {
			try (Jedis jedis = jedisPool.getResource()) {
				jedis.hset(primaryKey, secondaryKey, value);
			}
		}
	}

	@Override
	public String retrieveValueFromHashMap(String primaryKey, String secondaryKey) {
		if (!isValid(primaryKey) && !isValid(secondaryKey)) {
			return null;
		}

		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.hget(primaryKey, secondaryKey);
		}
	}

	@Override
	public String retrieveValueFromHashMap(String primaryKey) {
		if (!isValid(primaryKey)) {
			return null;
		}

		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.get(primaryKey);
		}
	}

	@Override
	public void saveValueWithExpireDate(String key, String value, long expireDate) {
		if (isValid(key) && isValid(value) && expireDate != 0L) {
			try (Jedis jedis = jedisPool.getResource()) {
				jedis.setex(key, expireDate, value);
			}
		}
	}

	private boolean isValid(String str) {
		return str != null || !str.isEmpty();
	}
}
