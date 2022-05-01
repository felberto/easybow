package ch.felberto.service;

import ch.felberto.domain.Passen;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Passen}.
 */
public interface PassenService {
    /**
     * Save a passen.
     *
     * @param passenDTO the entity to save.
     * @return the persisted entity.
     */
    Passen save(Passen passenDTO);

    /**
     * Partially updates a passen.
     *
     * @param passenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Passen> partialUpdate(Passen passenDTO);

    /**
     * Get all the passens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Passen> findAll(Pageable pageable);

    /**
     * Get the "id" passen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Passen> findOne(Long id);

    /**
     * Delete the "id" passen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
