package ru.practicum.stateserver.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getStart() {
        return LocalDateTime.now().minusDays(2).format(dtf);
    }

    public static String getEnd() {
        return LocalDateTime.now().plusDays(2).format(dtf);
    }

    public static String toString(Timestamp timestamp) {
        return timestamp.toLocalDateTime().format(dtf);
    }
}
