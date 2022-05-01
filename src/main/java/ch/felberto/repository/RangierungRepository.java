package ch.felberto.repository;

import ch.felberto.domain.Rangierung;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rangierung entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RangierungRepository extends JpaRepository<Rangierung, Long>, JpaSpecificationExecutor<Rangierung> {
    List<Rangierung> findByWettkampf_Id(Long wettkampfId);

    void deleteByWettkampf_Id(Long wettkampfId);
}
