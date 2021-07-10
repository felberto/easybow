package ch.felberto.repository;

import ch.felberto.domain.Resultate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Resultate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultateRepository extends JpaRepository<Resultate, Long> {}
