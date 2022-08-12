package ch.felberto.service;

import ch.felberto.domain.Results;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Results}.
 */
public interface ResultsService {
    /**
     * Save a result.
     *
     * @param results the entity to save.
     * @return the persisted entity.
     */
    Results save(Results results);

    /**
     * Partially updates a result.
     *
     * @param results the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Results> partialUpdate(Results results);

    /**
     * Get all the results.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Results> findAll(Pageable pageable);

    /**
     * Get the "id" result.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Results> findOne(Long id);

    /**
     * Get the "id" result.
     *
     * @param competitionId the id of the entity.
     * @return the entity.
     */
    List<Results> findByCompetition(Long competitionId);

    /**
     * Delete the "id" result.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Delete the "id" result.
     *
     * @param id the id of the entity.
     */
    void deleteByAthleteAndCompetition(Long id, Long competitionId);

    /**
     * Delete the "id" result.
     *
     * @param competitionId the id of the entity.
     * @param round round
     */
    void deleteByCompetition_IdAndRound(Long competitionId, Integer round);
}
