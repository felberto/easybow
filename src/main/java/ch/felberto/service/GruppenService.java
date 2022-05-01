package ch.felberto.service;

import ch.felberto.domain.Gruppen;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Gruppen}.
 */
public interface GruppenService {
    /**
     * Save a gruppen.
     *
     * @param gruppenDTO the entity to save.
     * @return the persisted entity.
     */
    Gruppen save(Gruppen gruppenDTO);

    /**
     * Partially updates a gruppen.
     *
     * @param gruppenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Gruppen> partialUpdate(Gruppen gruppenDTO);

    /**
     * Get all the gruppens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Gruppen> findAll(Pageable pageable);

    /**
     * Get the "id" gruppen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Gruppen> findOne(Long id);

    /**
     * Delete the "id" gruppen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
