package team.asd.service;

import team.asd.constants.DateElement;
import team.asd.exceptions.WrongArgumentException;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CalendarService implements IsCalendarService {
	@Override
	public String toString(LocalDate date) {
		return date == null ? null : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	@Override
	public LocalDate toLocalDate(String stringDate) {
		if (stringDate == null || stringDate.isEmpty()) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			return LocalDate.parse(stringDate, formatter);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	@Override
	public long defineCountInRange(LocalDate fromDate, LocalDate toDate, ChronoUnit unit) {
		if (fromDate == null || toDate == null || unit == null) {
			throw new WrongArgumentException("Arguments 'fromDate', 'toDate', and 'unit' must not be null.");
		}

		if (fromDate.isAfter(toDate)) {
			throw new WrongArgumentException("'fromDate' must not be after 'toDate'.");
		}

		try {
			if (unit.isDateBased()) {
				return unit.between(fromDate, toDate);
			} else if (unit.isTimeBased()) {
				return unit.between(fromDate.atStartOfDay(), toDate.atStartOfDay());
			} else {
				throw new WrongArgumentException("The provided ChronoUnit is not supported for LocalDate.");
			}
		} catch (UnsupportedTemporalTypeException e) {
			throw new WrongArgumentException("The provided ChronoUnit is not supported for LocalDate.", e);
		}
	}

	@Override
	public String getInfo(LocalDate date, DateElement dateElement) {
		if (date == null || dateElement == null) {
			throw new WrongArgumentException("Parameters 'date' and 'dateElement' must not be null.");
		}

		switch (dateElement) {
		case DAY_OF_WEEK:
			return date.getDayOfWeek()
					.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		case WEEK_NUMBER:
			WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
			int weekNumber = date.get(weekFields.weekOfYear());
			return String.valueOf(weekNumber);
		case MONTH:
			return date.getMonth()
					.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		case IS_LEAP_YEAR:
			return date.isLeapYear() ? "Yes" : "No";
		default:
			throw new WrongArgumentException("Unsupported 'dateElement' value: " + dateElement);
		}
	}

	@Override
	public LocalDate reformatToLocalDate(String dateString) throws DateTimeException {
		if (dateString == null || dateString.trim()
				.isEmpty()) {
			throw new DateTimeException("Date string is empty or null");
		}

		dateString = dateString.replaceAll("(\\d{1,2})(st|nd|rd|th|ft)", "$1")
				.trim();

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
			LocalDate date = LocalDate.parse(dateString, formatter);

			if (date.getYear() < 1000 || date.getYear() > 3000)
				throw new DateTimeException("Year out of range [1000, 3000]");

			return date;
		} catch (DateTimeParseException e) {
			throw new DateTimeException("Invalid date format. Expected format: 'Day Month Year'. Input: " + dateString, e);
		}
	}

}
