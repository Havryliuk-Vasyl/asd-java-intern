package team.asd.redis;

import java.util.List;

public interface IsRedisClientService {
	String readByKey(String key);

	void saveValueByKey(String key, String value);

	void saveList(String keyList, List<String> list);

	void saveElementIntoList(String keyList, String value);

	List<String> retrieveList(String keyList);

	void saveValueInHashMap(String primaryKey, String secondaryKey, String value);

	String retrieveValueFromHashMap(String primaryKey, String secondaryKey);

	String retrieveValueFromHashMap(String primaryKey);

	void saveValueWithExpireDate(String key, String value, long expireDate);
}
