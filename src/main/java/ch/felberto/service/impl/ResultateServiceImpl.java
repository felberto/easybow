package ch.felberto.service.impl;

import ch.felberto.domain.Resultate;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.service.ResultateService;
import ch.felberto.service.dto.ResultateDTO;
import ch.felberto.service.mapper.ResultateMapper;
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

    private final ResultateMapper resultateMapper;

    public ResultateServiceImpl(ResultateRepository resultateRepository, ResultateMapper resultateMapper) {
        this.resultateRepository = resultateRepository;
        this.resultateMapper = resultateMapper;
    }

    @Override
    public ResultateDTO save(ResultateDTO resultateDTO) {
        log.debug("Request to save Resultate : {}", resultateDTO);
        Resultate resultate = resultateMapper.toEntity(resultateDTO);
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
    public Optional<ResultateDTO> findOne(Long id) {
        log.debug("Request to get Resultate : {}", id);
        return resultateRepository.findById(id).map(resultateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resultate : {}", id);
        resultateRepository.deleteById(id);
    }
}