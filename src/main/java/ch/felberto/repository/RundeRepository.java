package ch.felberto.repository;

import ch.felberto.domain.Runde;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Runde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RundeRepository extends JpaRepository<Runde, Long>, JpaSpecificationExecutor<Runde> {
    Optional<Runde> findByRundeAndWettkampf_Id(Integer runde, Long wettkampfId);

    List<Runde> findByWettkampf_Id(Long wettkampfId);

    Boolean existsByRundeAndWettkampf_Id(Integer runde, Long wettkampfId);
}
