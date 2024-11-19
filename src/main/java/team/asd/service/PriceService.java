package team.asd.service;

import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import team.asd.entities.IsPerDayPrice;
import team.asd.entities.IsPrice;
import team.asd.exceptions.WrongPriceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PriceService implements IsPriceService {
	@Override
	public @NonNull BigDecimal defineAverageValueFromPerDayPrice(List<IsPerDayPrice> prices) throws WrongPriceException {
		if (CollectionUtils.isEmpty(prices)) {
			return BigDecimal.ZERO;
		}

		List<IsPerDayPrice> validatedPrices = validateIsPerDayPrices(prices);

		BigDecimal summaryValue = validatedPrices.stream()
				.map(IsPerDayPrice::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		int count = validatedPrices.size();
		return count > 0 ? summaryValue.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP) : BigDecimal.ZERO;
	}

	@Override
	public @NonNull BigDecimal defineAverageValueFromPrices(List<IsPrice> prices) throws WrongPriceException {
		if (CollectionUtils.isEmpty(prices)) {
			return BigDecimal.ZERO;
		}

		List<IsPrice> validatedPrices = validateIsPrices(prices);

		BigDecimal summaryValue = BigDecimal.ZERO;
		int totalDays = 0;

		for (IsPrice price : validatedPrices) {
			long countDays = ChronoUnit.DAYS.between(price.getFromDate(), price.getToDate()) + 1;
			if (countDays <= 0) {
				throw new WrongPriceException("Invalid date range");
			}

			summaryValue = summaryValue.add(price.getPrice()
					.multiply(BigDecimal.valueOf(countDays)));
			totalDays += (int) countDays;
		}

		return totalDays > 0 ? summaryValue.divide(BigDecimal.valueOf(totalDays), RoundingMode.HALF_UP) : BigDecimal.ZERO;
	}

	private List<IsPerDayPrice> validateIsPerDayPrices(List<IsPerDayPrice> prices) throws WrongPriceException {
		if (prices.stream()
				.anyMatch(price -> price == null || price.getPrice() == null || price.getDate() == null)) {
			throw new WrongPriceException("There is a null price or item in the list");
		}

		List<IsPerDayPrice> sortedPrices = prices.stream()
				.sorted(Comparator.comparing(IsPerDayPrice::getDate))
				.collect(Collectors.toList());

		if (datesOverlap(sortedPrices)) {
			throw new WrongPriceException("There are two prices with the same date");
		}

		return sortedPrices;
	}

	private List<IsPrice> validateIsPrices(List<IsPrice> prices) throws WrongPriceException {
		List<IsPrice> validatedPrices = new ArrayList<>();
		for (IsPrice price : prices) {
			if (price == null || price.getPrice() == null || price.getFromDate() == null || price.getToDate() == null) {
				throw new WrongPriceException("There is a null price or item in the list");
			}

			for (IsPrice otherPrice : validatedPrices) {
				if (datesOverlap(price, otherPrice)) {
					throw new WrongPriceException("There are two prices with the same date");
				}
			}
			validatedPrices.add(price);
		}
		return validatedPrices;
	}

	private boolean datesOverlap(IsPrice price1, IsPrice price2) {
		return !(price1.getToDate()
				.isBefore(price2.getFromDate()) || price1.getFromDate()
				.isAfter(price2.getToDate()));
	}

	private boolean datesOverlap(List<IsPerDayPrice> prices) {
		return prices.stream()
				.filter(p -> p != null && p.getDate() != null)
				.collect(Collectors.groupingBy(IsPerDayPrice::getDate, Collectors.counting()))
				.values()
				.stream()
				.anyMatch(count -> count > 1);
	}
}
