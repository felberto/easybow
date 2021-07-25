package ch.felberto.service.impl;

import ch.felberto.domain.Gruppen;
import ch.felberto.repository.GruppenRepository;
import ch.felberto.service.GruppenService;
import ch.felberto.service.dto.GruppenDTO;
import ch.felberto.service.mapper.GruppenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gruppen}.
 */
@Service
@Transactional
public class GruppenServiceImpl implements GruppenService {

    private final Logger log = LoggerFactory.getLogger(GruppenServiceImpl.class);

    private final GruppenRepository gruppenRepository;

    private final GruppenMapper gruppenMapper;

    public GruppenServiceImpl(GruppenRepository gruppenRepository, GruppenMapper gruppenMapper) {
        this.gruppenRepository = gruppenRepository;
        this.gruppenMapper = gruppenMapper;
    }

    @Override
    public GruppenDTO save(GruppenDTO gruppenDTO) {
        log.debug("Request to save Gruppen : {}", gruppenDTO);
        Gruppen gruppen = gruppenMapper.toEntity(gruppenDTO);
        gruppen = gruppenRepository.save(gruppen);
        return gruppenMapper.toDto(gruppen);
    }

    @Override
    public Optional<GruppenDTO> partialUpdate(GruppenDTO gruppenDTO) {
        log.debug("Request to partially update Gruppen : {}", gruppenDTO);

        return gruppenRepository
            .findById(gruppenDTO.getId())
            .map(
                existingGruppen -> {
                    gruppenMapper.partialUpdate(existingGruppen, gruppenDTO);

                    return existingGruppen;
                }
            )
            .map(gruppenRepository::save)
            .map(gruppenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GruppenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gruppens");
        return gruppenRepository.findAll(pageable).map(gruppenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GruppenDTO> findOne(Long id) {
        log.debug("Request to get Gruppen : {}", id);
        return gruppenRepository.findById(id).map(gruppenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gruppen : {}", id);
        gruppenRepository.deleteById(id);
    }
}
