package ch.felberto.service.impl;

import ch.felberto.domain.Verband;
import ch.felberto.repository.VerbandRepository;
import ch.felberto.service.VerbandService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Verband}.
 */
@Service
@Transactional
public class VerbandServiceImpl implements VerbandService {

    private final Logger log = LoggerFactory.getLogger(VerbandServiceImpl.class);

    private final VerbandRepository verbandRepository;

    public VerbandServiceImpl(VerbandRepository verbandRepository) {
        this.verbandRepository = verbandRepository;
    }

    @Override
    public Verband save(Verband verband) {
        log.debug("Request to save Verband : {}", verband);
        return verbandRepository.save(verband);
    }

    @Override
    public Optional<Verband> partialUpdate(Verband verband) {
        log.debug("Request to partially update Verband : {}", verband);

        return verbandRepository
            .findById(verband.getId())
            .map(
                existingVerband -> {
                    if (verband.getName() != null) {
                        existingVerband.setName(verband.getName());
                    }

                    return existingVerband;
                }
            )
            .map(verbandRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Verband> findAll(Pageable pageable) {
        log.debug("Request to get all Verbands");
        return verbandRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Verband> findOne(Long id) {
        log.debug("Request to get Verband : {}", id);
        return verbandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Verband : {}", id);
        verbandRepository.deleteById(id);
    }
}
