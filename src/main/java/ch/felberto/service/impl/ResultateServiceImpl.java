package ch.felberto.service.impl;

import ch.felberto.domain.Resultate;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.repository.SchuetzeRepository;
import ch.felberto.service.ResultateService;
import ch.felberto.service.dto.ResultateDTO;
import ch.felberto.service.mapper.ResultateMapper;
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
 * Service Implementation for managing {@link Resultate}.
 */
@Service
@Transactional
public class ResultateServiceImpl implements ResultateService {

    private final Logger log = LoggerFactory.getLogger(ResultateServiceImpl.class);

    private final ResultateRepository resultateRepository;

    private final SchuetzeRepository schuetzeRepository;

    private final ResultateMapper resultateMapper;

    public ResultateServiceImpl(
        ResultateRepository resultateRepository,
        SchuetzeRepository schuetzeRepository,
        ResultateMapper resultateMapper
    ) {
        this.resultateRepository = resultateRepository;
        this.schuetzeRepository = schuetzeRepository;
        this.resultateMapper = resultateMapper;
    }

    @Override
    public ResultateDTO save(ResultateDTO resultateDTO) {
        log.debug("Request to save Resultate : {}", resultateDTO);
        Resultate resultate = resultateMapper.toEntity(resultateDTO);
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
        resultate = resultateRepository.save(resultate);
        return resultateMapper.toDto(resultate);
    }

    @Override
    public Optional<ResultateDTO> partialUpdate(ResultateDTO resultateDTO) {
        log.debug("Request to partially update Resultate : {}", resultateDTO);

        return resultateRepository
            .findById(resultateDTO.getId())
            .map(
                existingResultate -> {
                    resultateMapper.partialUpdate(existingResultate, resultateDTO);

                    return existingResultate;
                }
            )
            .map(resultateRepository::save)
            .map(resultateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResultateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Resultates");
        return resultateRepository.findAll(pageable).map(resultateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Resultate> findOne(Long id) {
        log.debug("Request to get Resultate : {}", id);
        return resultateRepository.findById(id);
    }

    @Override
    public List<ResultateDTO> findByWettkampf(Long wettkampfId) {
        log.debug("Request to get Resultate : {}", wettkampfId);

        List<ResultateDTO> list = new ArrayList<ResultateDTO>(resultateRepository.findByWettkampf_Id(wettkampfId).size());
        for (Resultate resultate : resultateRepository.findByWettkampf_Id(wettkampfId)) {
            list.add(resultateMapper.toDto(resultate));
        }
        return list;
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
}
