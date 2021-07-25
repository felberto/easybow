package ch.felberto.service;

import ch.felberto.service.dto.PassenDTO;
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
    PassenDTO save(PassenDTO passenDTO);

    /**
     * Partially updates a passen.
     *
     * @param passenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PassenDTO> partialUpdate(PassenDTO passenDTO);

    /**
     * Get all the passens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PassenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" passen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PassenDTO> findOne(Long id);

    /**
     * Delete the "id" passen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
