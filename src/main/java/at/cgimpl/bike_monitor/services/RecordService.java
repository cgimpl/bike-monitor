package at.cgimpl.bike_monitor.services;

import at.cgimpl.bike_monitor.dtos.RecordDto;
import at.cgimpl.bike_monitor.entities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

public interface RecordService {

    Page<RecordDto> getAllRecordDtos(Pageable pageable);
    Optional<Record> getExactRecord(Long locationId, OffsetDateTime dateTime, Integer cyclistCount);
    void saveRecord(Record record);
    Map<String, Double> getStats();

}
