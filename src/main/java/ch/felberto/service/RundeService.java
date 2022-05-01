package ch.felberto.service;

import ch.felberto.domain.Runde;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Runde}.
 */
public interface RundeService {
    /**
     * Save a runde.
     *
     * @param runde the entity to save.
     * @return the persisted entity.
     */
    Runde save(Runde runde);

    /**
     * Partially updates a runde.
     *
     * @param runde the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Runde> partialUpdate(Runde runde);

    /**
     * Get all the rundes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Runde> findAll(Pageable pageable);

    /**
     * Get the "id" runde.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Runde> findOne(Long id);

    /**
     * Get the "id" runde.
     *
     * @param runde the id of the entity.
     * @param wettkampfId the id of the entity.
     * @return the entity.
     */
    Optional<Runde> findOneByRundeAndWettkampfId(Integer runde, Long wettkampfId);

    /**
     * Get the "id" runde.
     *
     * @param wettkampfId the id of the entity.
     * @return the entity.
     */
    List<Runde> findByWettkampfId(Long wettkampfId);

    /**
     * Delete the "id" runde.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
