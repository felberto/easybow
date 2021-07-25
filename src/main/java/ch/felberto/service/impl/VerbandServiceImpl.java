package ch.felberto.service.impl;

import ch.felberto.domain.Verband;
import ch.felberto.repository.VerbandRepository;
import ch.felberto.service.VerbandService;
import ch.felberto.service.dto.VerbandDTO;
import ch.felberto.service.mapper.VerbandMapper;
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

    private final VerbandMapper verbandMapper;

    public VerbandServiceImpl(VerbandRepository verbandRepository, VerbandMapper verbandMapper) {
        this.verbandRepository = verbandRepository;
        this.verbandMapper = verbandMapper;
    }

    @Override
    public VerbandDTO save(VerbandDTO verbandDTO) {
        log.debug("Request to save Verband : {}", verbandDTO);
        Verband verband = verbandMapper.toEntity(verbandDTO);
        verband = verbandRepository.save(verband);
        return verbandMapper.toDto(verband);
    }

    @Override
    public Optional<VerbandDTO> partialUpdate(VerbandDTO verbandDTO) {
        log.debug("Request to partially update Verband : {}", verbandDTO);

        return verbandRepository
            .findById(verbandDTO.getId())
            .map(
                existingVerband -> {
                    verbandMapper.partialUpdate(existingVerband, verbandDTO);

                    return existingVerband;
                }
            )
            .map(verbandRepository::save)
            .map(verbandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VerbandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Verbands");
        return verbandRepository.findAll(pageable).map(verbandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VerbandDTO> findOne(Long id) {
        log.debug("Request to get Verband : {}", id);
        return verbandRepository.findById(id).map(verbandMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Verband : {}", id);
        verbandRepository.deleteById(id);
    }
}
