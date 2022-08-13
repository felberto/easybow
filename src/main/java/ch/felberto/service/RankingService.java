package ch.felberto.service;

import ch.felberto.domain.Ranking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Ranking}.
 */
public interface RankingService {
    /**
     * Save a ranking.
     *
     * @param ranking the entity to save.
     * @return the persisted entity.
     */
    Ranking save(Ranking ranking);

    /**
     * Partially updates a ranking.
     *
     * @param ranking the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Ranking> partialUpdate(Ranking ranking);

    /**
     * Get all the rankings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Ranking> findAll(Pageable pageable);

    /**
     * Get the "id" ranking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ranking> findOne(Long id);

    /**
     * Get all "competition" ranking.
     *
     * @param competitionId the id of the entity.
     * @return the entity.
     */
    List<Ranking> findByCompetition(Long competitionId);

    /**
     * Delete the "id" ranking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Delete by competition.
     *
     * @param id the id of the competition.
     */
    void deleteByCompetition(Long id);
}
