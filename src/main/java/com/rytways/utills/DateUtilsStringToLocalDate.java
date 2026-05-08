package com.rytways.utills;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtilsStringToLocalDate {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

    // Format LocalDate to String
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(FORMATTER) : "-";
    }

    // Parse String to LocalDate
    public static LocalDate parseDate(String dateString) {
        return !"-".equals(dateString) ? LocalDate.parse(dateString, FORMATTER) : null;
    }

    // Format LocalDate to String and convert back to LocalDate
    public static LocalDate formatAndConvertDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        String formattedDate = date.format(FORMATTER); // Format the date
        return LocalDate.parse(formattedDate, FORMATTER); // Convert back to LocalDate
    }
}
