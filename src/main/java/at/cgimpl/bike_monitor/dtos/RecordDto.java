package at.cgimpl.bike_monitor.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RecordDto {

    @NotNull
    private LocationDto location;

    @NotNull
    private OffsetDateTime dateTime;

    @NotNull
    private Integer cyclistCount;

}
