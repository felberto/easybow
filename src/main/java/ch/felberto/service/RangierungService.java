package ch.felberto.service;

import ch.felberto.domain.Rangierung;
import java.util.List;
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
    Rangierung save(Rangierung rangierungDTO);

    /**
     * Partially updates a rangierung.
     *
     * @param rangierungDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Rangierung> partialUpdate(Rangierung rangierungDTO);

    /**
     * Get all the rangierungs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Rangierung> findAll(Pageable pageable);

    /**
     * Get the "id" rangierung.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rangierung> findOne(Long id);

    /**
     * Get all "wettkampf" rangierung.
     *
     * @param wettkampfId the id of the entity.
     * @return the entity.
     */
    List<Rangierung> findByWettkampf(Long wettkampfId);

    /**
     * Delete the "id" rangierung.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Delete by wettkampf.
     *
     * @param id the id of the wettkampf.
     */
    void deleteByWettkampf(Long id);
}
