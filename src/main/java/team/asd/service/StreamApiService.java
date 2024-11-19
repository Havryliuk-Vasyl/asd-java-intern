package team.asd.service;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamApiService implements IsStreamApiService {
	@Override
	public @NonNull Stream<?> getNonNullStreamItems(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection) ?
				Stream.empty() :
				collection.stream()
						.filter(Objects::nonNull);
	}

	@Override
	public @NonNull List<Integer> defineListFromRange(Integer start, Integer end) throws NumberFormatException {
		if (start == null || end == null)
			return Collections.emptyList();

		if (start > end)
			start = (start + end) - (end = start);

		List<Integer> integers = new ArrayList<>();
		IntStream.rangeClosed(start, end)
				.forEach(integers::add);

		return integers;
	}

	@Override
	public @NonNull List<Integer> convertStringListToIntegerList(List<String> stringList) {
		if (CollectionUtils.isEmpty(stringList))
			return Collections.emptyList();

		return stringList.stream()
				.filter(str -> str != null && str.matches("-?\\d+"))
				.filter(this::canConvertStringToInt)
				.map(Integer::valueOf)
				.collect(Collectors.toList());
	}

	@Override
	public @NonNull IntStream convertStringToLegalChars(String value) {
		if (value == null || value.isEmpty()) {
			return IntStream.empty();
		}

		value = value.replaceAll("[^a-zA-Z0-9]", "");
		char[] symbols = value.toCharArray();

		return IntStream.range(0, symbols.length)
				.map(i -> symbols[i]);
	}

	@Override
	public @NonNull BigDecimal sumAllValues(List<BigDecimal> values) {
		if (values == null)
			throw new NullPointerException("Null argument was provided");

		if (CollectionUtils.isEmpty(values))
			return BigDecimal.ZERO;

		return values.stream()
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public @NonNull Stream<LocalDate> sortLocalDateList(List<LocalDate> listOfDates) {
		if (CollectionUtils.isEmpty(listOfDates))
			return Stream.empty();

		return listOfDates.stream()
				.filter(Objects::nonNull)
				.sorted();
	}

	@Override
	public @NonNull Stream<LocalDate> skipDaysFromSpecifiedDate(List<LocalDate> listOfDates, LocalDate date, Integer daysToSkip) {
		if (CollectionUtils.isEmpty(listOfDates) || date == null || daysToSkip == null || daysToSkip < 0) {
			return Stream.empty();
		}

		List<LocalDate> sortedDates = listOfDates.stream()
				.filter(Objects::nonNull)
				.sorted()
				.collect(Collectors.toList());

		int startIndex = -1;
		for (int i = 0; i < sortedDates.size(); i++) {
			if (sortedDates.get(i)
					.equals(date)) {
				startIndex = i;
				break;
			} else if (sortedDates.get(i)
					.isAfter(date)) {
				startIndex = i;
				break;
			}
		}

		if (startIndex == -1) {
			return Stream.empty();
		}

		return sortedDates.subList(Math.min(startIndex + daysToSkip, sortedDates.size()), sortedDates.size())
				.stream();
	}

	@Override
	public @NonNull List<? extends Object> collectLists(List<?>... lists) {
		if (CollectionUtils.isEmpty(Arrays.asList(lists)))
			return Collections.emptyList();

		return Arrays.stream(lists)
				.filter(Objects::nonNull)
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}

	@Override
	public @NonNull List<? extends Object> removeDuplicates(List<?> listWithDuplicates) {
		if (CollectionUtils.isEmpty(listWithDuplicates))
			return Collections.emptyList();

		return listWithDuplicates.stream()
				.distinct()
				.collect(Collectors.toList());
	}

	private boolean canConvertStringToInt(String string) {
		try {
			new BigInteger(string).intValueExact();
			return true;
		} catch (ArithmeticException e) {
			return false;
		}
	}
}
