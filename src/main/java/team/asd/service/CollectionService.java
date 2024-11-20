package team.asd.service;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionService implements IsCollectionService {
	@Override
	public List<Object> unmodifiableList(Object... objects) {
		if (objects.length == 0) {
			return Collections.unmodifiableList(new ArrayList<>());
		}

		List<Object> list = new ArrayList<>();
		for (Object object : objects) {
			if (object != null) {
				list.add(object);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<Object> immutableList(Object... objects) {
		if (objects.length == 0) {
			return Collections.unmodifiableList(new ArrayList<>());
		}

		List<Object> list = new ArrayList<>();
		for (Object object : objects) {
			if (object != null) {
				list.add(object);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public Set<Object> retrieveObjectsThatPresentInBothLists(Set<Object> firstSet, Set<Object> secondSet) {
		if (CollectionUtils.isEmpty(firstSet) || CollectionUtils.isEmpty(secondSet))
			return Collections.emptySet();

		Set<Object> newSet = new HashSet<>();
		for (Object object : firstSet) {
			if (secondSet.contains(object))
				newSet.add(object);
		}
		return newSet;
	}
}
