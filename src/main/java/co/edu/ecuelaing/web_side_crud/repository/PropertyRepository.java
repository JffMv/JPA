package co.edu.ecuelaing.web_side_crud.repository;

import co.edu.ecuelaing.web_side_crud.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findById(Long id);
}
