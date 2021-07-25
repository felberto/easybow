package ch.felberto.service;

import ch.felberto.service.dto.RangierungDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Rangierung}.
 */
public interface RangierungService {
    /**
     * Save a rangierung.
     *
     * @param rangierungDTO the entity to save.
     * @return the persisted entity.
     */
    RangierungDTO save(RangierungDTO rangierungDTO);

    /**
     * Partially updates a rangierung.
     *
     * @param rangierungDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RangierungDTO> partialUpdate(RangierungDTO rangierungDTO);

    /**
     * Get all the rangierungs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RangierungDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rangierung.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RangierungDTO> findOne(Long id);

    /**
     * Delete the "id" rangierung.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
