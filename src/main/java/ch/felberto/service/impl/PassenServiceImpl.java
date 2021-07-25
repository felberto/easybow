package ch.felberto.service.impl;

import ch.felberto.domain.Passen;
import ch.felberto.repository.PassenRepository;
import ch.felberto.service.PassenService;
import ch.felberto.service.dto.PassenDTO;
import ch.felberto.service.mapper.PassenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Passen}.
 */
@Service
@Transactional
public class PassenServiceImpl implements PassenService {

    private final Logger log = LoggerFactory.getLogger(PassenServiceImpl.class);

    private final PassenRepository passenRepository;

    private final PassenMapper passenMapper;

    public PassenServiceImpl(PassenRepository passenRepository, PassenMapper passenMapper) {
        this.passenRepository = passenRepository;
        this.passenMapper = passenMapper;
    }

    @Override
    public PassenDTO save(PassenDTO passenDTO) {
        log.debug("Request to save Passen : {}", passenDTO);
        Passen passen = passenMapper.toEntity(passenDTO);
        passen = passenRepository.save(passen);
        return passenMapper.toDto(passen);
    }

    @Override
    public Optional<PassenDTO> partialUpdate(PassenDTO passenDTO) {
        log.debug("Request to partially update Passen : {}", passenDTO);

        return passenRepository
            .findById(passenDTO.getId())
            .map(
                existingPassen -> {
                    passenMapper.partialUpdate(existingPassen, passenDTO);

                    return existingPassen;
                }
            )
            .map(passenRepository::save)
            .map(passenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PassenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Passens");
        return passenRepository.findAll(pageable).map(passenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PassenDTO> findOne(Long id) {
        log.debug("Request to get Passen : {}", id);
        return passenRepository.findById(id).map(passenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passen : {}", id);
        passenRepository.deleteById(id);
    }
}
