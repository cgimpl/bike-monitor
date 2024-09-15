package at.cgimpl.bike_monitor.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_id_seq")
    @SequenceGenerator(name = "record_id_seq", sequenceName = "record_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "date", nullable = false)
    private OffsetDateTime dateTime;

    @Column(name = "count", nullable = false)
    private Integer cyclistCount;

}
