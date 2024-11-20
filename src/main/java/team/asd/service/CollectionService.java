package team.asd.service;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionService implements IsCollectionService {
	@Override
	public List<Object> unmodifiableList(Object... objects) {
		return Arrays.stream(objects)
				.filter(Objects::nonNull)
				.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public List<Object> immutableList(Object... objects) {
		return objects == null ? List.of() : List.of(objects);
	}

	@Override
	public Set<Object> retrieveObjectsThatPresentInBothLists(Set<Object> firstSet, Set<Object> secondSet) {
		return CollectionUtils.isEmpty(firstSet) || CollectionUtils.isEmpty(secondSet) ?
				Collections.emptySet() :
				new HashSet<>(CollectionUtils.intersection(firstSet, secondSet));
	}
}
