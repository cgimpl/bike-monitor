package at.cgimpl.bike_monitor.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class Conversion {

    public static double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).toOffsetDateTime();
    }

}
