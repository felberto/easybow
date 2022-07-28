package ch.felberto.service;

import ch.felberto.domain.Round;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Round}.
 */
public interface RoundService {
    /**
     * Save a round.
     *
     * @param round the entity to save.
     * @return the persisted entity.
     */
    Round save(Round round);

    /**
     * Partially updates a round.
     *
     * @param round the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Round> partialUpdate(Round round);

    /**
     * Get all the rounds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Round> findAll(Pageable pageable);

    /**
     * Get the "id" round.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Round> findOne(Long id);

    /**
     * Get the "id" round.
     *
     * @param round         the id of the entity.
     * @param competitionId the id of the entity.
     * @return the entity.
     */
    Optional<Round> findOneByRoundAndCompetitionId(Integer round, Long competitionId);

    /**
     * Get the "id" round.
     *
     * @param competitionId the id of the entity.
     * @return the entity.
     */
    List<Round> findByCompetitionId(Long competitionId);

    /**
     * Delete the "id" round.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
