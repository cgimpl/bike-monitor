package at.cgimpl.bike_monitor.controllers;

import at.cgimpl.bike_monitor.services.DataFetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fetch")
@RequiredArgsConstructor
public class DataFetchController {

    private final DataFetchService dataFetchService;

    @PostMapping
    public ResponseEntity<Map<String, Integer>> fetchData() {
        Map<String,Integer> dataStoredCounts = dataFetchService.fetchAndSaveNewDataToDatabase();
        return ResponseEntity.ok(dataStoredCounts);
    }

}
