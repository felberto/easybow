package ch.felberto.service;

import ch.felberto.service.dto.VerbandDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Verband}.
 */
public interface VerbandService {
    /**
     * Save a verband.
     *
     * @param verbandDTO the entity to save.
     * @return the persisted entity.
     */
    VerbandDTO save(VerbandDTO verbandDTO);

    /**
     * Partially updates a verband.
     *
     * @param verbandDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VerbandDTO> partialUpdate(VerbandDTO verbandDTO);

    /**
     * Get all the verbands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VerbandDTO> findAll(Pageable pageable);

    /**
     * Get the "id" verband.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VerbandDTO> findOne(Long id);

    /**
     * Delete the "id" verband.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
