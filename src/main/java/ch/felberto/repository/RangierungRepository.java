package ch.felberto.repository;

import ch.felberto.domain.Rangierung;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rangierung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RangierungRepository extends JpaRepository<Rangierung, Long>, JpaSpecificationExecutor<Rangierung> {}
