package at.cgimpl.bike_monitor.services;

import at.cgimpl.bike_monitor.entities.Location;
import at.cgimpl.bike_monitor.entities.Record;
import at.cgimpl.bike_monitor.exceptions.ExternalApiException;
import at.cgimpl.bike_monitor.exceptions.LocationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataFetchServiceImpl implements DataFetchService {

    @Value("${radmonitors.api.url}")
    private String radmonitorsApiUrl;

    private final LocationService locationService;

    private final RecordService recordService;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Fetches new locations and records from the external API and saves them to the database.
     * Exceptions, including those related to unreachable external APIs or non-200 response status codes,
     * are handled by the exceptions/GlobalExceptionHandler.java.
     *
     * @return A map with the count of new locations and records saved to the database
     */
    public Map<String, Integer> fetchAndSaveNewDataToDatabase() {
        Map<String, Integer> response = new HashMap<>();
        response.put("newLocationsCount", fetchLocations());
        response.put("newRecordsCount", fetchRecords());
        return response;
    }

    /**
     * This method fetches locations from the external service and saves them to the database.
     *
     * @return The count of new locations saved
     */
    private int fetchLocations() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("resource_id", "5efac66a-e9c0-4ecc-9c14-1bab1bfd6f45");
        JSONArray locations = sendApiRequest("/datastore_search", queryParams);

        int newSavedLocationsCount = 0;
        // using regular for loop instead of forEach to avoid casting
        for (int i = 0; i < locations.length(); i++) {
            JSONObject location = locations.getJSONObject(i);

            long id = location.getLong("id");
            if(locationService.getLocationById(id).isEmpty()) {
                JSONArray coordinates = location.getJSONObject("geometrie").getJSONArray("coordinates");

                Location newLocation = Location.builder()
                        .id(id)
                        .name(location.getString("bezeichnung"))
                        .longitude(coordinates.getDouble(0))
                        .latitude(coordinates.getDouble(1))
                        .build();

                locationService.saveLocation(newLocation);
                ++newSavedLocationsCount;
            }
        }
        return newSavedLocationsCount;
    }

    /**
     * Fetches records from the external service and saves them to the database.
     * The external API supports SQL queries as URL parameters for filtering.
     *
     * @return The count of new records saved
     */
    private int fetchRecords() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        String sqlStatement = """
            SELECT * from "c25195ce-d8d1-484f-829c-73773c54d1d5" WHERE standort_id IS NOT NULL ORDER BY zeitpunkt DESC LIMIT 150
        """;
        queryParams.add("sql", sqlStatement);
        JSONArray records = sendApiRequest("/datastore_search_sql", queryParams);

        int newRecordsCount = 0;
        for (int i = 0; i < records.length(); i++) {
            JSONObject record = records.getJSONObject(i);

            long locationId = record.getLong("standort_id");
            OffsetDateTime dateTime = OffsetDateTime.parse(record.getString("zeitpunkt"));
            int cyclistCount = record.getInt("summe");

            if(recordService.getExactRecord(locationId, dateTime, cyclistCount).isEmpty()) {
                Location location = locationService.getLocationById(locationId)
                        .orElseThrow(LocationNotFoundException::new);
                Record newRecord = Record.builder()
                        .location(location)
                        .dateTime(dateTime)
                        .cyclistCount(cyclistCount)
                        .build();
                recordService.saveRecord(newRecord);
                ++newRecordsCount;
            }
        }
        return newRecordsCount;
    }

    /**
     * Sends an API request to the given endpoint with specified query parameters.
     *
     * @param endpoint The API endpoint
     * @param queryParams The query parameters
     * @return The array of records from the response
     * @throws ExternalApiException if the API response indicates failure
     */
    private JSONArray sendApiRequest(String endpoint, MultiValueMap<String, String> queryParams) {
        String httpUrl = UriComponentsBuilder.fromHttpUrl(radmonitorsApiUrl + endpoint)
                .queryParams(queryParams)
                .build()
                .toUriString();

        String response = restTemplate.getForObject(httpUrl, String.class);
        JSONObject jsonResponse = new JSONObject(response);

        if(!jsonResponse.getBoolean("success")) {
            throw new ExternalApiException();
        }

        return jsonResponse.getJSONObject("result").getJSONArray("records");
    }

}
