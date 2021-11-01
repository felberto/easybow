package ch.felberto.service.impl;

import ch.felberto.domain.Schuetze;
import ch.felberto.repository.SchuetzeRepository;
import ch.felberto.service.SchuetzeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Schuetze}.
 */
@Service
@Transactional
public class SchuetzeServiceImpl implements SchuetzeService {

    private final Logger log = LoggerFactory.getLogger(SchuetzeServiceImpl.class);

    private final SchuetzeRepository schuetzeRepository;

    public SchuetzeServiceImpl(SchuetzeRepository schuetzeRepository) {
        this.schuetzeRepository = schuetzeRepository;
    }

    @Override
    public Schuetze save(Schuetze schuetze) {
        log.debug("Request to save Schuetze : {}", schuetze);
        return schuetzeRepository.save(schuetze);
    }

    @Override
    public Optional<Schuetze> partialUpdate(Schuetze schuetze) {
        log.debug("Request to partially update Schuetze : {}", schuetze);

        return schuetzeRepository
            .findById(schuetze.getId())
            .map(
                existingSchuetze -> {
                    if (schuetze.getName() != null) {
                        existingSchuetze.setName(schuetze.getName());
                    }
                    if (schuetze.getJahrgang() != null) {
                        existingSchuetze.setJahrgang(schuetze.getJahrgang());
                    }
                    if (schuetze.getStellung() != null) {
                        existingSchuetze.setStellung(schuetze.getStellung());
                    }
                    if (schuetze.getRolle() != null) {
                        existingSchuetze.setRolle(schuetze.getRolle());
                    }

                    return existingSchuetze;
                }
            )
            .map(schuetzeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Schuetze> findAll(Pageable pageable) {
        log.debug("Request to get all Schuetzes");
        return schuetzeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Schuetze> findOne(Long id) {
        log.debug("Request to get Schuetze : {}", id);
        return schuetzeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Schuetze : {}", id);
        schuetzeRepository.deleteById(id);
    }

    @Override
    public List<Schuetze> findAll() {
        return schuetzeRepository.findAll();
    }
}
