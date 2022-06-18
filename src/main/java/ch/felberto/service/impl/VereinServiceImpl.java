package ch.felberto.service.impl;

import ch.felberto.domain.Verein;
import ch.felberto.repository.VereinRepository;
import ch.felberto.service.VereinService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Verein}.
 */
@Service
@Transactional
public class VereinServiceImpl implements VereinService {

    private final Logger log = LoggerFactory.getLogger(VereinServiceImpl.class);

    private final VereinRepository vereinRepository;

    public VereinServiceImpl(VereinRepository vereinRepository) {
        this.vereinRepository = vereinRepository;
    }

    @Override
    public Verein save(Verein verein) {
        log.debug("Request to save Verein : {}", verein);
        return vereinRepository.save(verein);
    }

    @Override
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

    @Override
    public Optional<Verein> partialUpdateByName(Verein verein) {
        log.debug("Request to partially update Verein : {}", verein);

        return vereinRepository
            .findByName(verein.getName())
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

    @Override
    @Transactional(readOnly = true)
    public Page<Verein> findAll(Pageable pageable) {
        log.debug("Request to get all Vereins");
        return vereinRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Verein> findOne(Long id) {
        log.debug("Request to get Verein : {}", id);
        return vereinRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Verein : {}", id);
        vereinRepository.deleteById(id);
    }
}
