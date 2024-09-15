package at.cgimpl.bike_monitor.repositories;

import at.cgimpl.bike_monitor.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
