package at.cgimpl.bike_monitor.services;

import java.util.Map;

public interface DataFetchService {

    Map<String, Integer> fetchAndSaveNewDataToDatabase();

}
