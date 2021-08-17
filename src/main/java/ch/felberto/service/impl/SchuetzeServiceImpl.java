package ch.felberto.service.impl;

import ch.felberto.domain.Schuetze;
import ch.felberto.repository.SchuetzeRepository;
import ch.felberto.service.SchuetzeService;
import ch.felberto.service.dto.SchuetzeDTO;
import ch.felberto.service.mapper.SchuetzeMapper;
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
 * Service Implementation for managing {@link Schuetze}.
 */
@Service
@Transactional
public class SchuetzeServiceImpl implements SchuetzeService {

    private final Logger log = LoggerFactory.getLogger(SchuetzeServiceImpl.class);

    private final SchuetzeRepository schuetzeRepository;

    private final SchuetzeMapper schuetzeMapper;

    public SchuetzeServiceImpl(SchuetzeRepository schuetzeRepository, SchuetzeMapper schuetzeMapper) {
        this.schuetzeRepository = schuetzeRepository;
        this.schuetzeMapper = schuetzeMapper;
    }

    @Override
    public SchuetzeDTO save(SchuetzeDTO schuetzeDTO) {
        log.debug("Request to save Schuetze : {}", schuetzeDTO);
        Schuetze schuetze = schuetzeMapper.toEntity(schuetzeDTO);
        schuetze = schuetzeRepository.save(schuetze);
        return schuetzeMapper.toDto(schuetze);
    }

    @Override
    public Optional<SchuetzeDTO> partialUpdate(SchuetzeDTO schuetzeDTO) {
        log.debug("Request to partially update Schuetze : {}", schuetzeDTO);

        return schuetzeRepository
            .findById(schuetzeDTO.getId())
            .map(
                existingSchuetze -> {
                    schuetzeMapper.partialUpdate(existingSchuetze, schuetzeDTO);

                    return existingSchuetze;
                }
            )
            .map(schuetzeRepository::save)
            .map(schuetzeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchuetzeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Schuetzes");
        return schuetzeRepository.findAll(pageable).map(schuetzeMapper::toDto);
    }

    @Override
    public List<SchuetzeDTO> findAll() {
        List<SchuetzeDTO> list = new ArrayList<SchuetzeDTO>(schuetzeRepository.findAll().size());
        for (Schuetze schuetze : schuetzeRepository.findAll()) {
            list.add(schuetzeMapper.toDto(schuetze));
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchuetzeDTO> findOne(Long id) {
        log.debug("Request to get Schuetze : {}", id);
        return schuetzeRepository.findById(id).map(schuetzeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Schuetze : {}", id);
        schuetzeRepository.deleteById(id);
    }
}
