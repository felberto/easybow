package ch.felberto.service.impl;

import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.service.WettkampfService;
import java.util.List;
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

    public WettkampfServiceImpl(WettkampfRepository wettkampfRepository) {
        this.wettkampfRepository = wettkampfRepository;
    }

    @Override
    public Wettkampf save(Wettkampf wettkampf) {
        log.debug("Request to save Wettkampf : {}", wettkampf);
        return wettkampfRepository.save(wettkampf);
    }

    @Override
    public Optional<Wettkampf> partialUpdate(Wettkampf wettkampf) {
        log.debug("Request to partially update Wettkampf : {}", wettkampf);

        return wettkampfRepository
            .findById(wettkampf.getId())
            .map(
                existingWettkampf -> {
                    if (wettkampf.getName() != null) {
                        existingWettkampf.setName(wettkampf.getName());
                    }
                    if (wettkampf.getJahr() != null) {
                        existingWettkampf.setJahr(wettkampf.getJahr());
                    }
                    if (wettkampf.getAnzahlRunden() != null) {
                        existingWettkampf.setAnzahlRunden(wettkampf.getAnzahlRunden());
                    }
                    if (wettkampf.getAnzahlPassen() != null) {
                        existingWettkampf.setAnzahlPassen(wettkampf.getAnzahlPassen());
                    }
                    if (wettkampf.getFinalRunde() != null) {
                        existingWettkampf.setFinalRunde(wettkampf.getFinalRunde());
                    }
                    if (wettkampf.getFinalVorbereitung() != null) {
                        existingWettkampf.setFinalVorbereitung(wettkampf.getFinalVorbereitung());
                    }
                    if (wettkampf.getAnzahlFinalteilnehmer() != null) {
                        existingWettkampf.setAnzahlFinalteilnehmer(wettkampf.getAnzahlFinalteilnehmer());
                    }
                    if (wettkampf.getAnzahlPassenFinal() != null) {
                        existingWettkampf.setAnzahlPassenFinal(wettkampf.getAnzahlPassenFinal());
                    }
                    if (wettkampf.getAnzahlTeam() != null) {
                        existingWettkampf.setAnzahlTeam(wettkampf.getAnzahlTeam());
                    }
                    if (wettkampf.getTemplate() != null) {
                        existingWettkampf.setTemplate(wettkampf.getTemplate());
                    }

                    return existingWettkampf;
                }
            )
            .map(wettkampfRepository::save);
    }

    @Override
    public Optional<Wettkampf> partialUpdateByName(Wettkampf wettkampf) {
        log.debug("Request to partially update Wettkampf : {}", wettkampf);

        return wettkampfRepository
            .findByNameAndJahr(wettkampf.getName(), wettkampf.getJahr())
            .map(
                existingWettkampf -> {
                    if (wettkampf.getName() != null) {
                        existingWettkampf.setName(wettkampf.getName());
                    }
                    if (wettkampf.getJahr() != null) {
                        existingWettkampf.setJahr(wettkampf.getJahr());
                    }
                    if (wettkampf.getAnzahlRunden() != null) {
                        existingWettkampf.setAnzahlRunden(wettkampf.getAnzahlRunden());
                    }
                    if (wettkampf.getAnzahlPassen() != null) {
                        existingWettkampf.setAnzahlPassen(wettkampf.getAnzahlPassen());
                    }
                    if (wettkampf.getFinalRunde() != null) {
                        existingWettkampf.setFinalRunde(wettkampf.getFinalRunde());
                    }
                    if (wettkampf.getFinalVorbereitung() != null) {
                        existingWettkampf.setFinalVorbereitung(wettkampf.getFinalVorbereitung());
                    }
                    if (wettkampf.getAnzahlFinalteilnehmer() != null) {
                        existingWettkampf.setAnzahlFinalteilnehmer(wettkampf.getAnzahlFinalteilnehmer());
                    }
                    if (wettkampf.getAnzahlPassenFinal() != null) {
                        existingWettkampf.setAnzahlPassenFinal(wettkampf.getAnzahlPassenFinal());
                    }
                    if (wettkampf.getAnzahlTeam() != null) {
                        existingWettkampf.setAnzahlTeam(wettkampf.getAnzahlTeam());
                    }
                    if (wettkampf.getTemplate() != null) {
                        existingWettkampf.setTemplate(wettkampf.getTemplate());
                    }

                    return existingWettkampf;
                }
            )
            .map(wettkampfRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Wettkampf> findAll(Pageable pageable) {
        log.debug("Request to get all Wettkampfs");
        return wettkampfRepository.findAll(pageable);
    }

    @Override
    public List<Wettkampf> findByJahr(Integer jahr) {
        log.debug("Request to get all Wettkampf by Jahr");
        return wettkampfRepository.findByJahr(jahr);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Wettkampf> findOne(Long id) {
        log.debug("Request to get Wettkampf : {}", id);
        return wettkampfRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wettkampf : {}", id);
        wettkampfRepository.deleteById(id);
    }
}
