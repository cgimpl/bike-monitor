package at.cgimpl.bike_monitor.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocationDto {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    // This field isn't stored in the database as it would be redundant
    private String websiteUrl;

}
