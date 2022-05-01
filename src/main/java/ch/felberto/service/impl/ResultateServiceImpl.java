package ch.felberto.service.impl;

import ch.felberto.domain.Resultate;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.repository.SchuetzeRepository;
import ch.felberto.service.ResultateService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Resultate}.
 */
@Service
@Transactional
public class ResultateServiceImpl implements ResultateService {

    private final Logger log = LoggerFactory.getLogger(ResultateServiceImpl.class);

    private final ResultateRepository resultateRepository;

    private final SchuetzeRepository schuetzeRepository;

    public ResultateServiceImpl(ResultateRepository resultateRepository, SchuetzeRepository schuetzeRepository) {
        this.resultateRepository = resultateRepository;
        this.schuetzeRepository = schuetzeRepository;
    }

    @Override
    public Resultate save(Resultate resultate) {
        log.debug("Request to save Resultate : {}", resultate);
        int resultat = 0;
        if (resultate.getPasse1() != null && resultate.getPasse1().getResultat() != null) {
            resultat = resultat + resultate.getPasse1().getResultat();
        }
        if (resultate.getPasse2() != null && resultate.getPasse2().getResultat() != null) {
            resultat = resultat + resultate.getPasse2().getResultat();
        }
        if (resultate.getPasse3() != null && resultate.getPasse3().getResultat() != null) {
            resultat = resultat + resultate.getPasse3().getResultat();
        }
        if (resultate.getPasse4() != null && resultate.getPasse4().getResultat() != null) {
            resultat = resultat + resultate.getPasse4().getResultat();
        }
        resultate.setResultat(resultat);
        return resultateRepository.save(resultate);
    }

    @Override
    public Optional<Resultate> partialUpdate(Resultate resultate) {
        log.debug("Request to partially update Resultate : {}", resultate);

        return resultateRepository
            .findById(resultate.getId())
            .map(
                existingResultate -> {
                    if (resultate.getRunde() != null) {
                        existingResultate.setRunde(resultate.getRunde());
                    }
                    if (resultate.getResultat() != null) {
                        existingResultate.setResultat(resultate.getResultat());
                    }

                    return existingResultate;
                }
            )
            .map(resultateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Resultate> findAll(Pageable pageable) {
        log.debug("Request to get all Resultates");
        return resultateRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resultate> findOne(Long id) {
        log.debug("Request to get Resultate : {}", id);
        return resultateRepository.findById(id);
    }

    @Override
    public List<Resultate> findByWettkampf(Long wettkampfId) {
        log.debug("Request to get Resultate : {}", wettkampfId);
        return resultateRepository.findByWettkampf_Id(wettkampfId);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resultate : {}", id);
        resultateRepository.deleteById(id);
    }

    @Override
    public void deleteBySchuetze(Long id) {
        log.debug("Request to delete Resultate : {}", id);
        if (schuetzeRepository.findById(id).isPresent()) {
            resultateRepository.deleteBySchuetze(schuetzeRepository.findById(id).get());
        }
    }

    @Override
    public void deleteByWettkampf_IdAndRunde(Long wettkampfId, Integer runde) {
        log.debug("Request to delete Resultate : {}", wettkampfId);
        resultateRepository.deleteByWettkampf_IdAndRunde(wettkampfId, runde);
    }
}
