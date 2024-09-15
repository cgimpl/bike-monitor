package at.cgimpl.bike_monitor.services;

import at.cgimpl.bike_monitor.dtos.LocationDto;
import at.cgimpl.bike_monitor.entities.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LocationService {

    Page<LocationDto> getAllLocationDtos(Pageable pageable);
    Optional<Location> getLocationById(Long id);
    void saveLocation(Location location);
    Location saveLocation(LocationDto locationDto);
    Location updateLocation(Long id, LocationDto locationDto);
    void deleteLocation(Long id);
    LocationDto convertToDto(Location location);

}
