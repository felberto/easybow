package ch.felberto.repository;

import ch.felberto.domain.Round;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the round entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoundRepository extends JpaRepository<Round, Long>, JpaSpecificationExecutor<Round> {
    Optional<Round> findByRoundAndCompetition_Id(Integer round, Long competitionId);

    List<Round> findByCompetition_Id(Long competitionId);

    Boolean existsByRoundAndCompetition_Id(Integer round, Long competitionId);
}
