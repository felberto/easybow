package ch.felberto.repository;

import ch.felberto.domain.Schuetze;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Schuetze entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchuetzeRepository extends JpaRepository<Schuetze, Long>, JpaSpecificationExecutor<Schuetze> {}
