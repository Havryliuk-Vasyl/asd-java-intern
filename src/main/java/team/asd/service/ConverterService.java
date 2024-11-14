package team.asd.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConverterService implements IsConverterService {

	@Override
	public String convertIntegerIntoString(Integer value) {
		return value != null ? String.valueOf(value) : null;
	}

	@Override
	public Integer convertStringIntoInteger(String value) {
		if (value == null)
			return null;

		int number;
		try {
			number = Integer.parseInt(value);
		}
		catch (NumberFormatException e){
			throw new NumberFormatException("String does not contain numbers");
		}
		return number;
	}

	@Override
	public Double convertStringIntoDouble(String value) {
		if (value == null)
			return null;

		double number;
		try {
			number = Double.parseDouble(value);
		}
		catch (NumberFormatException e){
			throw new NumberFormatException("String does not contain numbers");
		}
		return number;
	}

	@Override
	public LocalDate convertStringIntoLocalDate(String dateString) throws DateTimeParseException {
		if (dateString == null)
			return null;

		LocalDate date;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			date = LocalDate.parse(dateString, formatter);
		}
		catch (DateTimeParseException e){
			throw new DateTimeParseException("Cannot convert String to LocalDate", e.getParsedString(), e.getErrorIndex());
		}
		return date;
	}
}
