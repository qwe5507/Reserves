package com.marketboro.point.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeUtils {
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public static long toTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZONE_ID).toEpochSecond();
    }

    public static LocalDateTime epochToLocalDateTime(long epochMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochMilli), ZONE_ID);
    }

    public static LocalDateTime epochMillToLocalDateTime(long epochMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZONE_ID);
    }


    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE_ID);
    }

    public static LocalDateTime tomorrowMinTime() { return LocalDateTime.now().plusDays(1).with(LocalTime.MIN); }

    public static String LocalDateTimeToString(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
