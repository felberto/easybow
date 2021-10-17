package ch.felberto.service.impl;

import ch.felberto.domain.Rangierung;
import ch.felberto.repository.RangierungRepository;
import ch.felberto.service.RangierungService;
import ch.felberto.service.dto.RangierungDTO;
import ch.felberto.service.mapper.RangierungMapper;
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
 * Service Implementation for managing {@link Rangierung}.
 */
@Service
@Transactional
public class RangierungServiceImpl implements RangierungService {

    private final Logger log = LoggerFactory.getLogger(RangierungServiceImpl.class);

    private final RangierungRepository rangierungRepository;

    private final RangierungMapper rangierungMapper;

    public RangierungServiceImpl(RangierungRepository rangierungRepository, RangierungMapper rangierungMapper) {
        this.rangierungRepository = rangierungRepository;
        this.rangierungMapper = rangierungMapper;
    }

    @Override
    public RangierungDTO save(RangierungDTO rangierungDTO) {
        log.debug("Request to save Rangierung : {}", rangierungDTO);
        Rangierung rangierung = rangierungMapper.toEntity(rangierungDTO);
        rangierung = rangierungRepository.save(rangierung);
        return rangierungMapper.toDto(rangierung);
    }

    @Override
    public Optional<RangierungDTO> partialUpdate(RangierungDTO rangierungDTO) {
        log.debug("Request to partially update Rangierung : {}", rangierungDTO);

        return rangierungRepository
            .findById(rangierungDTO.getId())
            .map(
                existingRangierung -> {
                    rangierungMapper.partialUpdate(existingRangierung, rangierungDTO);

                    return existingRangierung;
                }
            )
            .map(rangierungRepository::save)
            .map(rangierungMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RangierungDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rangierungs");
        return rangierungRepository.findAll(pageable).map(rangierungMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RangierungDTO> findOne(Long id) {
        log.debug("Request to get Rangierung : {}", id);
        return rangierungRepository.findById(id).map(rangierungMapper::toDto);
    }

    @Override
    public List<RangierungDTO> findByWettkampf(Long wettkampfId) {
        log.debug("Request to get Rangierung : {}", wettkampfId);

        List<RangierungDTO> list = new ArrayList<RangierungDTO>(rangierungRepository.findByWettkampf_Id(wettkampfId).size());
        for (Rangierung rangierung : rangierungRepository.findByWettkampf_Id(wettkampfId)) {
            list.add(rangierungMapper.toDto(rangierung));
        }
        return list;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rangierung : {}", id);
        rangierungRepository.deleteById(id);
    }

    @Override
    public void deleteByWettkampf(Long id) {
        log.debug("Request to delete Rangierung by Wettkampf: {}", id);
        rangierungRepository.deleteByWettkampf_Id(id);
    }
}
