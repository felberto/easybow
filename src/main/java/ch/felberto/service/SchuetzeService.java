package ch.felberto.service;

import ch.felberto.domain.Schuetze;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Schuetze}.
 */
public interface SchuetzeService {
    /**
     * Save a schuetze.
     *
     * @param schuetzeDTO the entity to save.
     * @return the persisted entity.
     */
    Schuetze save(Schuetze schuetzeDTO);

    /**
     * Partially updates a schuetze.
     *
     * @param schuetzeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Schuetze> partialUpdate(Schuetze schuetzeDTO);

    Optional<Schuetze> partialUpdateByName(Schuetze schuetze);

    /**
     * Get all the schuetzes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Schuetze> findAll(Pageable pageable);

    /**
     * Get all the schuetzes.
     *
     * @return the list of entities.
     */
    List<Schuetze> findAll();

    /**
     * Get the "id" schuetze.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Schuetze> findOne(Long id);

    /**
     * Delete the "id" schuetze.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
