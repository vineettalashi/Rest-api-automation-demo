package com.qe.vt.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
	public static LocalDate localDate = LocalDate.now();
    public static String datePattern = "yyyy-MM-dd";
    
    public static LocalDate getLocalDate() {
    	return localDate;
    }
    
    public static String getTodaysDate() {
        return localDate.format(DateTimeFormatter.ofPattern(datePattern));
    }

    public static String getPastDate(long numberOfDaysToSubtract) {
        return localDate.minus(numberOfDaysToSubtract, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
    }

    public static String getFutureDate(long numberOfDaysToAdd) {
        return localDate.plus(numberOfDaysToAdd, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
    }
    
    public static String getWeekdayDate() {
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return localDate.plus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
        } else if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return localDate.plus(1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
        } else {
            return getTodaysDate();
        }
    }
    
    public static String getWeekdayDateAhead(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        localDate = LocalDate.parse(date, formatter);
        localDate = localDate.plus(2, ChronoUnit.DAYS);
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return localDate.plus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
        } else if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return localDate.plus(1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
        } else {
            return localDate.toString();
        }
    }

    public static String getWeekDayDateBehind(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        localDate = LocalDate.parse(date, formatter);
        localDate = localDate.minus(2, ChronoUnit.DAYS);
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return localDate.plus(2, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
        } else if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return localDate.plus(1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(datePattern));
        } else {
            return localDate.toString();
        }
    }

    public static String getWeekendDate() {
        while (localDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
            localDate = localDate.plus(1, ChronoUnit.DAYS);
        }
        return localDate.format(DateTimeFormatter.ofPattern(datePattern));
    }

}
