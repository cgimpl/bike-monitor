package at.cgimpl.bike_monitor.controllers;

import at.cgimpl.bike_monitor.dtos.RecordDto;
import at.cgimpl.bike_monitor.services.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<Page<RecordDto>> getRecords(@PageableDefault Pageable pageable) {
        Page<RecordDto> records = recordService.getAllRecordDtos(pageable);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Double>> getStats() {
        Map<String, Double> stats = recordService.getStats();
        return ResponseEntity.ok(stats);
    }

}
