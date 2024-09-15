package at.cgimpl.bike_monitor.services;

import at.cgimpl.bike_monitor.entities.Location;
import at.cgimpl.bike_monitor.entities.Record;
import at.cgimpl.bike_monitor.repositories.RecordRepository;
import at.cgimpl.bike_monitor.utils.Conversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordServiceImpl recordService;

    @Test
    public void testStats() {
        Location location1 = new Location();
        Location location2 = new Location();

        ZoneId zoneId = ZoneId.of("Europe/Vienna");

        List<Record> records = Arrays.asList(
                new Record(2L, location1, Conversion.toOffsetDateTime(LocalDateTime.of(2023, 1, 1, 12, 0), zoneId), 10),
                new Record(2L, location1, Conversion.toOffsetDateTime(LocalDateTime.of(2024, 2, 1, 12, 0), zoneId), 20),
                new Record(3L, location2, Conversion.toOffsetDateTime(LocalDateTime.of(2024, 1, 1, 12, 0), zoneId), 30),
                new Record(4L, location2, Conversion.toOffsetDateTime(LocalDateTime.of(2024, 1, 1, 12, 0), zoneId), 40)
        );

        when(recordRepository.findAll()).thenReturn(records);

        Map<String, Double> stats = recordService.getStats();

        assertEquals(3.0, stats.get("uniqueDaysCount"));
        assertEquals(100.0, stats.get("totalCyclistCount"));
        assertEquals(40.0, stats.get("maxCyclistCount"));
        assertEquals(10.0, stats.get("minCyclistCount"));
        assertEquals(33.33, stats.get("averageCyclistCountPerDay"));
        assertEquals(25.0, stats.get("averageCyclistCountPerLocation"));
    }

}
