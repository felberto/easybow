package ch.felberto.service;

import ch.felberto.domain.Athlete;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Athlete}.
 */
public interface AthleteService {
    /**
     * Save a athlete.
     *
     * @param athlete the entity to save.
     * @return the persisted entity.
     */
    Athlete save(Athlete athlete);

    /**
     * Partially updates a athlete.
     *
     * @param athlete the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Athlete> partialUpdate(Athlete athlete);

    Optional<Athlete> partialUpdateByName(Athlete athlete);

    /**
     * Get all the athletes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Athlete> findAll(Pageable pageable);

    /**
     * Get all the athletes.
     *
     * @return the list of entities.
     */
    List<Athlete> findAll();

    /**
     * Get the "id" athlete.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Athlete> findOne(Long id);

    /**
     * Delete the "id" athlete.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
