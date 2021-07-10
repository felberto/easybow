package ch.felberto.service;

import ch.felberto.domain.Verein;
import ch.felberto.repository.VereinRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Verein}.
 */
@Service
@Transactional
public class VereinService {

    private final Logger log = LoggerFactory.getLogger(VereinService.class);

    private final VereinRepository vereinRepository;

    public VereinService(VereinRepository vereinRepository) {
        this.vereinRepository = vereinRepository;
    }

    /**
     * Save a verein.
     *
     * @param verein the entity to save.
     * @return the persisted entity.
     */
    public Verein save(Verein verein) {
        log.debug("Request to save Verein : {}", verein);
        return vereinRepository.save(verein);
    }

    /**
     * Partially update a verein.
     *
     * @param verein the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Verein> partialUpdate(Verein verein) {
        log.debug("Request to partially update Verein : {}", verein);

        return vereinRepository
            .findById(verein.getId())
            .map(
                existingVerein -> {
                    if (verein.getName() != null) {
                        existingVerein.setName(verein.getName());
                    }

                    return existingVerein;
                }
            )
            .map(vereinRepository::save);
    }

    /**
     * Get all the vereins.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Verein> findAll() {
        log.debug("Request to get all Vereins");
        return vereinRepository.findAll();
    }

    /**
     * Get one verein by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Verein> findOne(Long id) {
        log.debug("Request to get Verein : {}", id);
        return vereinRepository.findById(id);
    }

    /**
     * Delete the verein by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Verein : {}", id);
        vereinRepository.deleteById(id);
    }
}
