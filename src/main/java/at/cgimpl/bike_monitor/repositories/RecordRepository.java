package at.cgimpl.bike_monitor.repositories;

import at.cgimpl.bike_monitor.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT r FROM Record r WHERE r.location.id = :locationId AND r.dateTime = :dateTime AND r.cyclistCount = :cyclistCount")
    Optional<Record> findExactRecord(
            @Param("locationId") Long locationId,
            @Param("dateTime") OffsetDateTime dateTime,
            @Param("cyclistCount") Integer cyclistCount
    );
}
