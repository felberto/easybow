package ch.felberto.repository;

import ch.felberto.domain.Wettkampf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Wettkampf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WettkampfRepository extends JpaRepository<Wettkampf, Long>, JpaSpecificationExecutor<Wettkampf> {}
