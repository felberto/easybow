package ch.felberto.repository;

import ch.felberto.domain.Resultate;
import ch.felberto.domain.Schuetze;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Resultate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultateRepository extends JpaRepository<Resultate, Long>, JpaSpecificationExecutor<Resultate> {
    List<Resultate> findByWettkampf_Id(Long wettkampfId);

    List<Resultate> findByWettkampf_IdAndRunde(Long wettkampfId, Integer runde);

    void deleteBySchuetze(Schuetze schuetze);
    void deleteByWettkampf_IdAndRunde(Long wettkampfId, Integer runde);
}
