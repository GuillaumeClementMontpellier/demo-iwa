package fr.polytech.demo.repositories;

import fr.polytech.demo.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
