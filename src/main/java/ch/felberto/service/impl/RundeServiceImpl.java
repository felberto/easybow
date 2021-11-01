package ch.felberto.service.impl;

import ch.felberto.domain.Runde;
import ch.felberto.repository.RundeRepository;
import ch.felberto.service.RundeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Runde}.
 */
@Service
@Transactional
public class RundeServiceImpl implements RundeService {

    private final Logger log = LoggerFactory.getLogger(RundeServiceImpl.class);

    private final RundeRepository rundeRepository;

    public RundeServiceImpl(RundeRepository rundeRepository) {
        this.rundeRepository = rundeRepository;
    }

    @Override
    public Runde save(Runde runde) {
        log.debug("Request to save Runde : {}", runde);
        return rundeRepository.save(runde);
    }

    @Override
    public Optional<Runde> partialUpdate(Runde runde) {
        log.debug("Request to partially update Runde : {}", runde);

        return rundeRepository
            .findById(runde.getId())
            .map(
                existingRunde -> {
                    if (runde.getRunde() != null) {
                        existingRunde.setRunde(runde.getRunde());
                    }
                    if (runde.getDatum() != null) {
                        existingRunde.setDatum(runde.getDatum());
                    }

                    return existingRunde;
                }
            )
            .map(rundeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Runde> findAll(Pageable pageable) {
        log.debug("Request to get all Rundes");
        return rundeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Runde> findOne(Long id) {
        log.debug("Request to get Runde : {}", id);
        return rundeRepository.findById(id);
    }

    @Override
    public Optional<Runde> findOneByRundeAndWettkampfId(Integer runde, Long wettkampfId) {
        log.debug("Request to get Runde : {}", runde);
        return rundeRepository.findByRundeAndWettkampf_Id(runde, wettkampfId);
    }

    @Override
    public List<Runde> findByWettkampfId(Long wettkampfId) {
        log.debug("Request to get Runde : {}", wettkampfId);
        return rundeRepository.findByWettkampf_Id(wettkampfId);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Runde : {}", id);
        rundeRepository.deleteById(id);
    }
}
