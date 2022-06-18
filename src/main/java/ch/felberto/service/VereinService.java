package ch.felberto.service;

import ch.felberto.domain.Verein;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Verein}.
 */
public interface VereinService {
    /**
     * Save a verein.
     *
     * @param vereinDTO the entity to save.
     * @return the persisted entity.
     */
    Verein save(Verein vereinDTO);

    /**
     * Partially updates a verein.
     *
     * @param vereinDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Verein> partialUpdate(Verein vereinDTO);

    Optional<Verein> partialUpdateByName(Verein verein);

    /**
     * Get all the vereins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Verein> findAll(Pageable pageable);

    /**
     * Get the "id" verein.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Verein> findOne(Long id);

    /**
     * Delete the "id" verein.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
