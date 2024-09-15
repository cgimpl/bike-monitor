package at.cgimpl.bike_monitor.controllers;

import at.cgimpl.bike_monitor.dtos.LocationDto;
import at.cgimpl.bike_monitor.entities.Location;
import at.cgimpl.bike_monitor.services.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    @Test
    public void testSaveLocationWithExistingId() {
        Long existingId = 1L;
        LocationDto locationDto = new LocationDto();
        locationDto.setId(existingId);

        when(locationService.getLocationById(existingId)).thenReturn(Optional.of(new Location()));

        ResponseEntity<LocationDto> response = locationController.saveLocation(locationDto);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testSaveLocationWithNonExistingId() {
        Long nonExistingId = 1L;
        LocationDto locationDto = new LocationDto();
        locationDto.setId(nonExistingId);

        when(locationService.getLocationById(nonExistingId)).thenReturn(Optional.empty());
        when(locationService.saveLocation(locationDto)).thenReturn(new Location());

        ResponseEntity<LocationDto> response = locationController.saveLocation(locationDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
