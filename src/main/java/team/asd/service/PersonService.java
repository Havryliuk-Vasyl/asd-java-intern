package team.asd.service;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import team.asd.entities.IsPerson;

import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonService implements IsPersonService {
	@Override
	public @NonNull List<IsPerson> collectPersonsWithNameStartsWith(List<IsPerson> personList, String prefix) {
		if (CollectionUtils.isEmpty(personList))
			return Collections.emptyList();

		if (StringUtils.isEmpty(prefix))
			return personList;

		return personList.stream()
				.filter(person -> person != null && person.getName() != null)
				.filter(person -> person.getName()
						.startsWith(prefix))
				.collect(Collectors.toList());
	}

	@Override
	public @NonNull Map<Integer, List<IsPerson>> collectPersonsByAge(List<IsPerson> personList) {
		if (personList == null || personList.isEmpty()) {
			return Collections.emptyMap();
		}

		return personList.stream()
				.filter(this::isPersonValid)
				.collect(Collectors.groupingBy(IsPerson::getAge, Collectors.toList()));

	}

	@Override
	public @NonNull Double calculateAverageAge(List<IsPerson> personList) {
		if (personList == null || personList.isEmpty())
			return 0.0;

		return personList.stream()
				.filter(this::isPersonValid)
				.mapToInt(IsPerson::getAge)
				.average()
				.orElse(0.0);
	}

	@Override
	public @NonNull IntSummaryStatistics sumAndCountAge(List<IsPerson> personList) {
		if (personList == null || personList.isEmpty())
			return new IntSummaryStatistics();

		return personList.stream()
				.filter(this::isPersonValid)
				.mapToInt(IsPerson::getAge)
				.summaryStatistics();
	}

	private boolean isPersonValid(IsPerson person) {
		return person != null && person.getAge() != null && person.getAge() >= 0;
	}
}
