package ch.felberto.repository;

import ch.felberto.domain.Competition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the competition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long>, JpaSpecificationExecutor<Competition> {
    Boolean existsByNameAndYear(String name, Integer year);

    Optional<Competition> findByNameAndYear(String name, Integer year);
    List<Competition> findByYear(Integer year);
}
