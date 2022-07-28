package ch.felberto.service;

import ch.felberto.domain.Competition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Competition}.
 */
public interface CompetitionService {
    /**
     * Save a competition.
     *
     * @param competition the entity to save.
     * @return the persisted entity.
     */
    Competition save(Competition competition);

    /**
     * Partially updates a competition.
     *
     * @param competition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Competition> partialUpdate(Competition competition);

    Optional<Competition> partialUpdateByName(Competition competition);

    /**
     * Get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Competition> findAll(Pageable pageable);

    List<Competition> findByYear(Integer jahr);

    /**
     * Get the "id" competition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Competition> findOne(Long id);

    /**
     * Delete the "id" competition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
