package ch.felberto.repository;

import ch.felberto.domain.Group;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the group entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Boolean existsByName(String name);

    Optional<Group> findByName(String name);

    List<Group> findByCompetition_Id(Long id);

    List<Group> findByCompetition_IdAndClub_Id(Long competitionId, Long clubId);
    List<Group> findByCompetition_IdAndClub_IdAndRound(Long competitionId, Long clubId, Integer round);
}
