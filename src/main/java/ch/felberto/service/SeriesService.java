package ch.felberto.service;

import ch.felberto.domain.Series;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Series}.
 */
public interface SeriesService {
    /**
     * Save a serie.
     *
     * @param series the entity to save.
     * @return the persisted entity.
     */
    Series save(Series series);

    /**
     * Partially updates a serie.
     *
     * @param series the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Series> partialUpdate(Series series);

    /**
     * Get all the series.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Series> findAll(Pageable pageable);

    /**
     * Get the "id" serie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Series> findOne(Long id);

    /**
     * Delete the "id" serie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
