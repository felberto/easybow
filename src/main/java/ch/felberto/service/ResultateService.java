package ch.felberto.service;

import ch.felberto.domain.Resultate;
import java.util.List;
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
    Resultate save(Resultate resultateDTO);

    /**
     * Partially updates a resultate.
     *
     * @param resultateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Resultate> partialUpdate(Resultate resultateDTO);

    /**
     * Get all the resultates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Resultate> findAll(Pageable pageable);

    /**
     * Get the "id" resultate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Resultate> findOne(Long id);

    /**
     * Get the "id" resultate.
     *
     * @param wettkampfId the id of the entity.
     * @return the entity.
     */
    List<Resultate> findByWettkampf(Long wettkampfId);

    /**
     * Delete the "id" resultate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Delete the "id" resultate.
     *
     * @param id the id of the entity.
     */
    void deleteBySchuetze(Long id);
}
