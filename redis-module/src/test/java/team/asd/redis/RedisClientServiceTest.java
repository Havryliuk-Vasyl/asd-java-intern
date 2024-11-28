package team.asd.redis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisClientServiceTest {
	@Mock
	RedisClientService redisClientService;

	@Test
	void testReadByKey() {
		String key = "key";
		String value = "value";

		when(redisClientService.readByKey(key)).thenReturn(value);

		String result = redisClientService.readByKey(key);

		verify(redisClientService).readByKey(key);
		assert result.equals(value);
	}

	@Test
	void testSaveValueByKey() {
		String key = "lol";
		String value = "value";

		redisClientService.saveValueByKey(key, value);

		verify(redisClientService).saveValueByKey(key, value);
	}

	@Test
	void testSaveList() {
		String key = "listKey";
		List<String> values = Arrays.asList("value1", "value2");

		redisClientService.saveList(key, values);

		verify(redisClientService).saveList(key, values);
	}

	@Test
	void testSaveElementIntoList() {
		String key = "listKey";
		String element = "newElement";

		redisClientService.saveElementIntoList(key, element);

		verify(redisClientService).saveElementIntoList(key, element);
	}

	@Test
	void testRetrieveList() {
		String key = "listKey";
		List<String> expectedList = Arrays.asList("value1", "value2");

		when(redisClientService.retrieveList(key)).thenReturn(expectedList);

		List<String> result = redisClientService.retrieveList(key);

		verify(redisClientService).retrieveList(key);
		assert result.equals(expectedList);
	}

	@Test
	void testSaveValueInHashMap() {
		String mapKey = "hashKey";
		String field = "field";
		String value = "value";

		redisClientService.saveValueInHashMap(mapKey, field, value);

		verify(redisClientService).saveValueInHashMap(mapKey, field, value);
	}

	@Test
	void testRetrieveValueFromHashMap() {
		String mapKey = "hashKey";
		String field = "field";
		String expectedValue = "value";

		when(redisClientService.retrieveValueFromHashMap(mapKey, field)).thenReturn(expectedValue);

		String result = redisClientService.retrieveValueFromHashMap(mapKey, field);

		verify(redisClientService).retrieveValueFromHashMap(mapKey, field);
		assert result.equals(expectedValue);
	}

	@Test
	void testSaveValueWithExpireDate() {
		String key = "expiringKey";
		String value = "value";
		long ttl = 3600L;

		redisClientService.saveValueWithExpireDate(key, value, ttl);

		verify(redisClientService).saveValueWithExpireDate(key, value, ttl);
	}
}
