package ch.felberto.service.impl;

import ch.felberto.domain.Rangierung;
import ch.felberto.repository.RangierungRepository;
import ch.felberto.service.RangierungService;
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

    public RangierungServiceImpl(RangierungRepository rangierungRepository) {
        this.rangierungRepository = rangierungRepository;
    }

    @Override
    public Rangierung save(Rangierung rangierung) {
        log.debug("Request to save Rangierung : {}", rangierung);
        return rangierungRepository.save(rangierung);
    }

    @Override
    public Optional<Rangierung> partialUpdate(Rangierung rangierung) {
        log.debug("Request to partially update Rangierung : {}", rangierung);

        return rangierungRepository
            .findById(rangierung.getId())
            .map(
                existingRangierung -> {
                    if (rangierung.getPosition() != null) {
                        existingRangierung.setPosition(rangierung.getPosition());
                    }
                    if (rangierung.getRangierungskriterien() != null) {
                        existingRangierung.setRangierungskriterien(rangierung.getRangierungskriterien());
                    }

                    return existingRangierung;
                }
            )
            .map(rangierungRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rangierung> findAll(Pageable pageable) {
        log.debug("Request to get all Rangierungs");
        return rangierungRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rangierung> findOne(Long id) {
        log.debug("Request to get Rangierung : {}", id);
        return rangierungRepository.findById(id);
    }

    @Override
    public List<Rangierung> findByWettkampf(Long wettkampfId) {
        log.debug("Request to get Rangierung : {}", wettkampfId);
        return rangierungRepository.findByWettkampf_Id(wettkampfId);
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
