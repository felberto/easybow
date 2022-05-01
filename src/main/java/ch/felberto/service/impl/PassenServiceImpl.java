package ch.felberto.service.impl;

import ch.felberto.domain.Passen;
import ch.felberto.repository.PassenRepository;
import ch.felberto.service.PassenService;
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

    public PassenServiceImpl(PassenRepository passenRepository) {
        this.passenRepository = passenRepository;
    }

    @Override
    public Passen save(Passen passen) {
        log.debug("Request to save Passen : {}", passen);
        return passenRepository.save(passen);
    }

    @Override
    public Optional<Passen> partialUpdate(Passen passen) {
        log.debug("Request to partially update Passen : {}", passen);

        return passenRepository
            .findById(passen.getId())
            .map(
                existingPassen -> {
                    if (passen.getp1() != null) {
                        existingPassen.setp1(passen.getp1());
                    }
                    if (passen.getp2() != null) {
                        existingPassen.setp2(passen.getp2());
                    }
                    if (passen.getp3() != null) {
                        existingPassen.setp3(passen.getp3());
                    }
                    if (passen.getp4() != null) {
                        existingPassen.setp4(passen.getp4());
                    }
                    if (passen.getp5() != null) {
                        existingPassen.setp5(passen.getp5());
                    }
                    if (passen.getp6() != null) {
                        existingPassen.setp6(passen.getp6());
                    }
                    if (passen.getp7() != null) {
                        existingPassen.setp7(passen.getp7());
                    }
                    if (passen.getp8() != null) {
                        existingPassen.setp8(passen.getp8());
                    }
                    if (passen.getp9() != null) {
                        existingPassen.setp9(passen.getp9());
                    }
                    if (passen.getp10() != null) {
                        existingPassen.setp10(passen.getp10());
                    }
                    if (passen.getResultat() != null) {
                        existingPassen.setResultat(passen.getResultat());
                    }

                    return existingPassen;
                }
            )
            .map(passenRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Passen> findAll(Pageable pageable) {
        log.debug("Request to get all Passens");
        return passenRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Passen> findOne(Long id) {
        log.debug("Request to get Passen : {}", id);
        return passenRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passen : {}", id);
        passenRepository.deleteById(id);
    }
}
