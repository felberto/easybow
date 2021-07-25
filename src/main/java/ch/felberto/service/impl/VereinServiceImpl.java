package ch.felberto.service.impl;

import ch.felberto.domain.Verein;
import ch.felberto.repository.VereinRepository;
import ch.felberto.service.VereinService;
import ch.felberto.service.dto.VereinDTO;
import ch.felberto.service.mapper.VereinMapper;
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

    private final VereinMapper vereinMapper;

    public VereinServiceImpl(VereinRepository vereinRepository, VereinMapper vereinMapper) {
        this.vereinRepository = vereinRepository;
        this.vereinMapper = vereinMapper;
    }

    @Override
    public VereinDTO save(VereinDTO vereinDTO) {
        log.debug("Request to save Verein : {}", vereinDTO);
        Verein verein = vereinMapper.toEntity(vereinDTO);
        verein = vereinRepository.save(verein);
        return vereinMapper.toDto(verein);
    }

    @Override
    public Optional<VereinDTO> partialUpdate(VereinDTO vereinDTO) {
        log.debug("Request to partially update Verein : {}", vereinDTO);

        return vereinRepository
            .findById(vereinDTO.getId())
            .map(
                existingVerein -> {
                    vereinMapper.partialUpdate(existingVerein, vereinDTO);

                    return existingVerein;
                }
            )
            .map(vereinRepository::save)
            .map(vereinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VereinDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vereins");
        return vereinRepository.findAll(pageable).map(vereinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VereinDTO> findOne(Long id) {
        log.debug("Request to get Verein : {}", id);
        return vereinRepository.findById(id).map(vereinMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Verein : {}", id);
        vereinRepository.deleteById(id);
    }
}
