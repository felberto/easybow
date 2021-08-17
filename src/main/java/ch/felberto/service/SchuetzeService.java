package ch.felberto.service;

import ch.felberto.service.dto.SchuetzeDTO;
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
    SchuetzeDTO save(SchuetzeDTO schuetzeDTO);

    /**
     * Partially updates a schuetze.
     *
     * @param schuetzeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SchuetzeDTO> partialUpdate(SchuetzeDTO schuetzeDTO);

    /**
     * Get all the schuetzes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchuetzeDTO> findAll(Pageable pageable);

    /**
     * Get all the schuetzes.
     *
     * @return the list of entities.
     */
    List<SchuetzeDTO> findAll();

    /**
     * Get the "id" schuetze.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchuetzeDTO> findOne(Long id);

    /**
     * Delete the "id" schuetze.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
