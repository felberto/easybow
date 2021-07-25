package ch.felberto.service;

import ch.felberto.service.dto.WettkampfDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ch.felberto.domain.Wettkampf}.
 */
public interface WettkampfService {
    /**
     * Save a wettkampf.
     *
     * @param wettkampfDTO the entity to save.
     * @return the persisted entity.
     */
    WettkampfDTO save(WettkampfDTO wettkampfDTO);

    /**
     * Partially updates a wettkampf.
     *
     * @param wettkampfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WettkampfDTO> partialUpdate(WettkampfDTO wettkampfDTO);

    /**
     * Get all the wettkampfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WettkampfDTO> findAll(Pageable pageable);

    /**
     * Get the "id" wettkampf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WettkampfDTO> findOne(Long id);

    /**
     * Delete the "id" wettkampf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
