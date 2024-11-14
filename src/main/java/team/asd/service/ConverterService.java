package team.asd.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class ConverterService implements IsConverterService {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public String convertIntegerIntoString(Integer value) {
		return value != null ? String.valueOf(value) : null;
	}

	@Override
	public Integer convertStringIntoInteger(String value) {
		return Optional.ofNullable(value)
				.map(val -> {
					int number;
					try {
						return number = Integer.parseInt(val);
					} catch (NumberFormatException e) {
						throw new NumberFormatException(e.getMessage());
					}
				})
				.orElse(null);
	}

	@Override
	public Double convertStringIntoDouble(String value) {
		return Optional.ofNullable(value)
				.map(val -> {
					double number;
					try {
						number = Double.parseDouble(val);
					} catch (NumberFormatException e) {
						throw new NumberFormatException(e.getMessage());
					}
					return number;
				})
				.orElse(null);
	}

	@Override
	public LocalDate convertStringIntoLocalDate(String dateString) throws DateTimeParseException {
		return Optional.ofNullable(dateString)
				.map(value -> {
					LocalDate date;
					try {
						date = LocalDate.parse(value, formatter);
					} catch (DateTimeParseException e) {
						throw new DateTimeParseException(e.getMessage(), e.getParsedString(), e.getErrorIndex());
					}
					return date;
				})
				.orElse(null);
	}
}
