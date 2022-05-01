package ch.felberto.service.impl;

import ch.felberto.domain.Gruppen;
import ch.felberto.repository.GruppenRepository;
import ch.felberto.service.GruppenService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gruppen}.
 */
@Service
@Transactional
public class GruppenServiceImpl implements GruppenService {

    private final Logger log = LoggerFactory.getLogger(GruppenServiceImpl.class);

    private final GruppenRepository gruppenRepository;

    public GruppenServiceImpl(GruppenRepository gruppenRepository) {
        this.gruppenRepository = gruppenRepository;
    }

    @Override
    public Gruppen save(Gruppen gruppen) {
        log.debug("Request to save Gruppen : {}", gruppen);
        return gruppenRepository.save(gruppen);
    }

    @Override
    public Optional<Gruppen> partialUpdate(Gruppen gruppen) {
        log.debug("Request to partially update Gruppen : {}", gruppen);

        return gruppenRepository
            .findById(gruppen.getId())
            .map(
                existingGruppen -> {
                    if (gruppen.getName() != null) {
                        existingGruppen.setName(gruppen.getName());
                    }

                    return existingGruppen;
                }
            )
            .map(gruppenRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Gruppen> findAll(Pageable pageable) {
        log.debug("Request to get all Gruppens");
        return gruppenRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Gruppen> findOne(Long id) {
        log.debug("Request to get Gruppen : {}", id);
        return gruppenRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gruppen : {}", id);
        gruppenRepository.deleteById(id);
    }
}
