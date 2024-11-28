package team.asd.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.asd.redis.IsRedisClientService;
import team.asd.redis.RedisClientService;

import java.util.List;

@RestController
@RequestMapping("/")
public class Rest {
	private final IsRedisClientService isRedisClientService = new RedisClientService();

	@GetMapping("/value")
	public String readByKey(@RequestParam String key) {
		return isRedisClientService.readByKey(key);
	}

	@PostMapping("/saveByKey")
	public void saveByKey(@RequestParam String key, @RequestParam String value) {
		isRedisClientService.saveValueByKey(key, value);
	}

	@PostMapping("/saveList")
	public void saveList(@RequestParam String keyList, @RequestBody List<String> list) {
		isRedisClientService.saveList(keyList, list);
	}

	@GetMapping("/list")
	public List<String> retrieveList(@RequestParam String keyList) {
		return isRedisClientService.retrieveList(keyList);
	}

	@PostMapping("/saveElement")
	public void saveElementIntoList(@RequestParam String keyList, @RequestParam String value) {
		isRedisClientService.saveElementIntoList(keyList, value);
	}

	@PostMapping("/saveInHashMap")
	public void saveValueInHashMap(@RequestParam String primaryKey, @RequestParam String secondaryKey, @RequestParam String value) {
		isRedisClientService.saveValueInHashMap(primaryKey, secondaryKey, value);
	}

	@GetMapping("/retrieveFromHashMap")
	public String retrieveValueFromHashMap(@RequestParam String primaryKey, @RequestParam(required = false) String secondaryKey) {
		if (secondaryKey != null) {
			return isRedisClientService.retrieveValueFromHashMap(primaryKey, secondaryKey);
		} else {
			return isRedisClientService.retrieveValueFromHashMap(primaryKey);
		}
	}

	@PostMapping("/saveWithExpire")
	public void saveValueWithExpireDate(@RequestParam String key, @RequestParam String value, @RequestParam long expireDate) {
		isRedisClientService.saveValueWithExpireDate(key, value, expireDate);
	}
}
