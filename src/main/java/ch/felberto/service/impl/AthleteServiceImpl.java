package ch.felberto.service.impl;

import ch.felberto.domain.Athlete;
import ch.felberto.repository.AthleteRepository;
import ch.felberto.service.AthleteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ch.felberto.domain.Athlete}.
 */
@Service
@Transactional
public class AthleteServiceImpl implements AthleteService {

    private final Logger log = LoggerFactory.getLogger(AthleteServiceImpl.class);

    private final AthleteRepository athleteRepository;

    public AthleteServiceImpl(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Athlete save(Athlete athlete) {
        log.debug("Request to save Athlete : {}", athlete);
        return athleteRepository.save(athlete);
    }

    @Override
    public Optional<Athlete> partialUpdate(Athlete athlete) {
        log.debug("Request to partially update Athlete : {}", athlete);

        return athleteRepository
            .findById(athlete.getId())
            .map(
                existingAthlete -> {
                    if (athlete.getName() != null) {
                        existingAthlete.setName(athlete.getName());
                    }
                    if (athlete.getFirstName() != null) {
                        existingAthlete.setFirstName(athlete.getFirstName());
                    }
                    if (athlete.getYearOfBirth() != null) {
                        existingAthlete.setYearOfBirth(athlete.getYearOfBirth());
                    }
                    if (athlete.getNationality() != null) {
                        existingAthlete.setNationality(athlete.getNationality());
                    }
                    if (athlete.getGender() != null) {
                        existingAthlete.setGender(athlete.getGender());
                    }
                    if (athlete.getPosition() != null) {
                        existingAthlete.setPosition(athlete.getPosition());
                    }
                    if (athlete.getRole() != null) {
                        existingAthlete.setRole(athlete.getRole());
                    }

                    return existingAthlete;
                }
            )
            .map(athleteRepository::save);
    }

    @Override
    public Optional<Athlete> partialUpdateByName(Athlete athlete) {
        log.debug("Request to partially update Athlete : {}", athlete);

        return athleteRepository
            .findByNameAndFirstName(athlete.getName(), athlete.getFirstName())
            .map(
                existingAthlete -> {
                    if (athlete.getName() != null) {
                        existingAthlete.setName(athlete.getName());
                    }
                    if (athlete.getFirstName() != null) {
                        existingAthlete.setFirstName(athlete.getFirstName());
                    }
                    if (athlete.getYearOfBirth() != null) {
                        existingAthlete.setYearOfBirth(athlete.getYearOfBirth());
                    }
                    if (athlete.getNationality() != null) {
                        existingAthlete.setNationality(athlete.getNationality());
                    }
                    if (athlete.getGender() != null) {
                        existingAthlete.setGender(athlete.getGender());
                    }
                    if (athlete.getPosition() != null) {
                        existingAthlete.setPosition(athlete.getPosition());
                    }
                    if (athlete.getRole() != null) {
                        existingAthlete.setRole(athlete.getRole());
                    }

                    return existingAthlete;
                }
            )
            .map(athleteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Athlete> findAll(Pageable pageable) {
        log.debug("Request to get all Athletes");
        return athleteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Athlete> findOne(Long id) {
        log.debug("Request to get Athlete : {}", id);
        return athleteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Athlete : {}", id);
        athleteRepository.deleteById(id);
    }

    @Override
    public List<Athlete> findAll() {
        return athleteRepository.findAll();
    }
}
