package ch.felberto.service;

import ch.felberto.service.dto.GruppenDTO;
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
    GruppenDTO save(GruppenDTO gruppenDTO);

    /**
     * Partially updates a gruppen.
     *
     * @param gruppenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GruppenDTO> partialUpdate(GruppenDTO gruppenDTO);

    /**
     * Get all the gruppens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GruppenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gruppen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GruppenDTO> findOne(Long id);

    /**
     * Delete the "id" gruppen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
