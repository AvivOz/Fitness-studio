package gym.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER_WITH_TIME = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Parses a date string in either "dd-MM-yyyy HH:mm" or "dd-MM-yyyy" format.
     * @param date the date string to parse
     * @return a LocalDateTime object
     */
    public static LocalDateTime parseDate(String date) {
        try {
            return LocalDateTime.parse(date, FORMATTER_WITH_TIME);
        } catch (Exception e) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay();
        }
    }

    /**
     * Formats a LocalDateTime to "dd-MM-yyyy HH:mm" format.
     * @param date the LocalDateTime to format
     * @return a formatted string
     */
    public static String formatDateWithTime(LocalDateTime date) {
        return date.format(FORMATTER_WITH_TIME);
    }

    /**
     * Formats a LocalDateTime to "yyyy-MM-dd'T'HH:mm" format.
     * @param date the LocalDateTime to format
     * @return a formatted string
     */
    public static String formatISODate(LocalDateTime date) {
        return date.format(ISO_FORMATTER);
    }

    /**
     * Formats a LocalDateTime to "yyyy-MM-dd" format (date only).
     * @param date the LocalDateTime to format
     * @return a formatted string
     */
    public static String formatISODateOnly(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}