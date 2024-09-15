package at.cgimpl.bike_monitor.controllers;

import at.cgimpl.bike_monitor.dtos.LocationDto;
import at.cgimpl.bike_monitor.entities.Location;
import at.cgimpl.bike_monitor.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<Page<LocationDto>> getLocations(@PageableDefault Pageable pageable) {
        Page<LocationDto> locations = locationService.getAllLocationDtos(pageable);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id)
                .map(locationService::convertToDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<LocationDto> saveLocation(@Valid @RequestBody LocationDto locationDto) {
        if (locationService.getLocationById(locationDto.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Location location = locationService.saveLocation(locationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.convertToDto(location));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Long id, @Valid @RequestBody LocationDto locationDto) {
        if (locationService.getLocationById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Location location = locationService.updateLocation(id, locationDto);
        return ResponseEntity.ok(locationService.convertToDto(location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        if (locationService.getLocationById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        locationService.deleteLocation(id);
        return ResponseEntity.ok().build();
    }

}
