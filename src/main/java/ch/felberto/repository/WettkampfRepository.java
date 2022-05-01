package ch.felberto.repository;

import ch.felberto.domain.Wettkampf;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Wettkampf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WettkampfRepository extends JpaRepository<Wettkampf, Long>, JpaSpecificationExecutor<Wettkampf> {
    Boolean existsByNameAndJahr(String name, Integer jahr);

    Optional<Wettkampf> findByNameAndJahr(String name, Integer jahr);
    List<Wettkampf> findByJahr(Integer jahr);
}
