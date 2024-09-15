package at.cgimpl.bike_monitor.services;

import at.cgimpl.bike_monitor.dtos.LocationDto;
import at.cgimpl.bike_monitor.entities.Location;
import at.cgimpl.bike_monitor.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final ModelMapper modelMapper;

    public Page<LocationDto> getAllLocationDtos(Pageable pageable) {
        return locationRepository.findAll(pageable)
                .map((location) -> modelMapper.map(location, LocationDto.class));
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public Location saveLocation(LocationDto locationDto) {
        return locationRepository.save(modelMapper.map(locationDto, Location.class));
    }

    public Location updateLocation(Long id, LocationDto locationDto) {
        Location location = modelMapper.map(locationDto, Location.class);
        location.setId(id);
        return locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public LocationDto convertToDto(Location location) {
        return modelMapper.map(location, LocationDto.class);
    }
}
