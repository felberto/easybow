package ch.felberto.service;

import ch.felberto.domain.Association;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Association}.
 */
public interface AssociationService {
    /**
     * Save a association.
     *
     * @param association the entity to save.
     * @return the persisted entity.
     */
    Association save(Association association);

    /**
     * Partially updates a association.
     *
     * @param association the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Association> partialUpdate(Association association);

    /**
     * Get all the associations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Association> findAll(Pageable pageable);

    /**
     * Get the "id" association.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Association> findOne(Long id);

    /**
     * Delete the "id" association.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
