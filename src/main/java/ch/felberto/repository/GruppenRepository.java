package ch.felberto.repository;

import ch.felberto.domain.Gruppen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Gruppen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GruppenRepository extends JpaRepository<Gruppen, Long> {}
