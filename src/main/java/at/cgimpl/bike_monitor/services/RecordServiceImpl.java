package at.cgimpl.bike_monitor.services;

import at.cgimpl.bike_monitor.dtos.RecordDto;
import at.cgimpl.bike_monitor.entities.Location;
import at.cgimpl.bike_monitor.entities.Record;
import at.cgimpl.bike_monitor.repositories.RecordRepository;
import at.cgimpl.bike_monitor.utils.Conversion;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    private final ModelMapper modelMapper;

    public Page<RecordDto> getAllRecordDtos(Pageable pageable) {
        return recordRepository.findAll(pageable)
                .map((record) -> modelMapper.map(record, RecordDto.class));
    }

    public Optional<Record> getExactRecord(Long locationId, OffsetDateTime dateTime, Integer cyclistCount) {
        return recordRepository.findExactRecord(locationId, dateTime, cyclistCount);
    }

    public void saveRecord(Record record) {
        recordRepository.save(record);
    }

    public Map<String, Double> getStats() {
        Map<String, Double> stats = new HashMap<>();

        List<Record> records = recordRepository.findAll();

        double totalCyclistCount = records.stream()
                .mapToInt(Record::getCyclistCount)
                .sum();

        long uniqueDaysCount = records.stream()
                .map(record -> record.getDateTime().toLocalDate())
                .distinct()
                .count();

        double averageCyclistCountPerDay = uniqueDaysCount > 0 ? totalCyclistCount / uniqueDaysCount : 0.0;

        int maxCyclistCount = records.stream()
                .mapToInt(Record::getCyclistCount)
                .max()
                .orElse(0);

        int minCyclistCount = records.stream()
                .mapToInt(Record::getCyclistCount)
                .min()
                .orElse(0);

        Map<Location, Double> averageCyclistCountPerLocation = records.stream()
                .collect(Collectors.groupingBy(
                        Record::getLocation,
                        Collectors.averagingInt(Record::getCyclistCount)
                ));

        double overallAverageCyclistCountPerLocation = averageCyclistCountPerLocation.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        stats.put("uniqueDaysCount", (double) uniqueDaysCount);
        stats.put("totalCyclistCount", totalCyclistCount);
        stats.put("maxCyclistCount", (double) maxCyclistCount);
        stats.put("minCyclistCount", (double) minCyclistCount);
        stats.put("averageCyclistCountPerDay", Conversion.roundTwoDecimals(averageCyclistCountPerDay));
        stats.put("averageCyclistCountPerLocation", Conversion.roundTwoDecimals(overallAverageCyclistCountPerLocation));

        return stats;
    }

}
