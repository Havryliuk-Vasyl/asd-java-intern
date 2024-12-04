package team.asd.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisClientServiceTest {

    @InjectMocks
    RedisClientService redisClientService;

    @Mock
    JedisPool jedisPool;

    @Mock
    Jedis jedis;

    @BeforeEach
    void setUp() {
        when(jedisPool.getResource()).thenReturn(jedis);
        redisClientService = new RedisClientService(jedisPool);
    }

    @Test
    void testReadByKey() {
        String key = "testKey";
        String value = "testValue";

        when(jedis.get(key)).thenReturn(value);

        String result = redisClientService.readByKey(key);

        Assertions.assertEquals(value, result);

        verify(jedis).get(key);
        verify(jedis).close();
    }

    @Test
    void testSaveValueByKey() {
        String key = "testKey";
        String value = "testValue";

        redisClientService.saveValueByKey(key, value);

        verify(jedis).set(key, value);
        verify(jedis).close();
    }

    @Test
    void testSaveList() {
        String key = "listKey";
        List<String> values = Arrays.asList("value1", "value2");

        redisClientService.saveList(key, values);

        for (String value : values) verify(jedis).rpush(key, value);
        verify(jedis).close();
    }

    @Test
    void testSaveElementIntoList() {
        String key = "listKey";
        String value = "newElement";

        redisClientService.saveElementIntoList(key, value);

        verify(jedis).sadd(key, value);
        verify(jedis).close();
    }

    @Test
    void testRetrieveList() {
        String key = "listKey";
        List<String> expectedList = Arrays.asList("value1", "value2");

        when(jedis.lrange(key, 0, -1)).thenReturn(expectedList);

        List<String> result = redisClientService.retrieveList(key);

        Assertions.assertEquals(expectedList, result);
        verify(jedis).lrange(key, 0, -1);
        verify(jedis).close();
    }

    @Test
    void testSaveValueInHashMap() {
        String mapKey = "hashKey";
        String field = "field";
        String value = "value";

        redisClientService.saveValueInHashMap(mapKey, field, value);

        verify(jedis).hset(mapKey, field, value);
        verify(jedis).close();
    }

    @Test
    void testRetrieveValueFromHashMap() {
        String mapKey = "hashKey";
        String field = "field";
        String expectedValue = "value";

        when(jedis.hget(mapKey, field)).thenReturn(expectedValue);

        String result = redisClientService.retrieveValueFromHashMap(mapKey, field);

        Assertions.assertEquals(expectedValue, result);
        verify(jedis).hget(mapKey, field);
        verify(jedis).close();
    }

    @Test
    void testSaveValueWithExpireDate() {
        String key = "expiringKey";
        String value = "value";
        long ttl = 3600L;

        redisClientService.saveValueWithExpireDate(key, value, ttl);

        verify(jedis).setex(key, ttl, value);
        verify(jedis).close();
    }
}
