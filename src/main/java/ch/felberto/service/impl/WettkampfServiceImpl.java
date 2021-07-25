package ch.felberto.service.impl;

import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.service.WettkampfService;
import ch.felberto.service.dto.WettkampfDTO;
import ch.felberto.service.mapper.WettkampfMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Wettkampf}.
 */
@Service
@Transactional
public class WettkampfServiceImpl implements WettkampfService {

    private final Logger log = LoggerFactory.getLogger(WettkampfServiceImpl.class);

    private final WettkampfRepository wettkampfRepository;

    private final WettkampfMapper wettkampfMapper;

    public WettkampfServiceImpl(WettkampfRepository wettkampfRepository, WettkampfMapper wettkampfMapper) {
        this.wettkampfRepository = wettkampfRepository;
        this.wettkampfMapper = wettkampfMapper;
    }

    @Override
    public WettkampfDTO save(WettkampfDTO wettkampfDTO) {
        log.debug("Request to save Wettkampf : {}", wettkampfDTO);
        Wettkampf wettkampf = wettkampfMapper.toEntity(wettkampfDTO);
        wettkampf = wettkampfRepository.save(wettkampf);
        return wettkampfMapper.toDto(wettkampf);
    }

    @Override
    public Optional<WettkampfDTO> partialUpdate(WettkampfDTO wettkampfDTO) {
        log.debug("Request to partially update Wettkampf : {}", wettkampfDTO);

        return wettkampfRepository
            .findById(wettkampfDTO.getId())
            .map(
                existingWettkampf -> {
                    wettkampfMapper.partialUpdate(existingWettkampf, wettkampfDTO);

                    return existingWettkampf;
                }
            )
            .map(wettkampfRepository::save)
            .map(wettkampfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WettkampfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wettkampfs");
        return wettkampfRepository.findAll(pageable).map(wettkampfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WettkampfDTO> findOne(Long id) {
        log.debug("Request to get Wettkampf : {}", id);
        return wettkampfRepository.findById(id).map(wettkampfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wettkampf : {}", id);
        wettkampfRepository.deleteById(id);
    }
}
