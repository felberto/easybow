package ch.felberto.repository;

import ch.felberto.domain.Verband;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Verband entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerbandRepository extends JpaRepository<Verband, Long> {}
