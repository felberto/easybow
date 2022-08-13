package ch.felberto.repository;

import ch.felberto.domain.Ranking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ranking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long>, JpaSpecificationExecutor<Ranking> {
    List<Ranking> findByCompetition_Id(Long competitionId);

    void deleteByCompetition_Id(Long athleteResultList);
}
