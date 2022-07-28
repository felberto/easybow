package ch.felberto.repository;

import ch.felberto.domain.Athlete;
import ch.felberto.domain.Results;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the results entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultsRepository extends JpaRepository<Results, Long>, JpaSpecificationExecutor<Results> {
    List<Results> findByCompetition_Id(Long competitionId);

    List<Results> findByCompetition_IdAndRound(Long competitionId, Integer round);

    Results findByCompetition_IdAndRoundAndAthlete_Id(Long competitionId, Integer round, Long athleteId);

    Boolean existsByCompetition_IdAndRoundAndAthlete_Id(Long competitionId, Integer round, Long athleteId);

    void deleteByAthlete(Athlete athlete);

    void deleteByCompetition_IdAndRound(Long competitionId, Integer round);
}
