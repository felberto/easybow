package ch.felberto.service;

import ch.felberto.service.dto.ResultateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Resultate}.
 */
public interface ResultateService {
    /**
     * Save a resultate.
     *
     * @param resultateDTO the entity to save.
     * @return the persisted entity.
     */
    ResultateDTO save(ResultateDTO resultateDTO);

    /**
     * Partially updates a resultate.
     *
     * @param resultateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResultateDTO> partialUpdate(ResultateDTO resultateDTO);

    /**
     * Get all the resultates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResultateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" resultate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultateDTO> findOne(Long id);

    /**
     * Delete the "id" resultate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
