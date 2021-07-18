package ch.felberto.repository;

import ch.felberto.domain.Verein;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Verein entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VereinRepository extends JpaRepository<Verein, Long> {}
