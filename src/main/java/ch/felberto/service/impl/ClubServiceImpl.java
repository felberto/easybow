package ch.felberto.service.impl;

import ch.felberto.domain.Club;
import ch.felberto.repository.ClubRepository;
import ch.felberto.service.ClubService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Club}.
 */
@Service
@Transactional
public class ClubServiceImpl implements ClubService {

    private final Logger log = LoggerFactory.getLogger(ClubServiceImpl.class);

    private final ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public Club save(Club club) {
        log.debug("Request to save Club : {}", club);
        return clubRepository.save(club);
    }

    @Override
    public Optional<Club> partialUpdate(Club club) {
        log.debug("Request to partially update Club : {}", club);

        return clubRepository
            .findById(club.getId())
            .map(
                existingVerein -> {
                    if (club.getName() != null) {
                        existingVerein.setName(club.getName());
                    }

                    return existingVerein;
                }
            )
            .map(clubRepository::save);
    }

    @Override
    public Optional<Club> partialUpdateByName(Club club) {
        log.debug("Request to partially update Club : {}", club);

        return clubRepository
            .findByName(club.getName())
            .map(
                existingClub -> {
                    if (club.getName() != null) {
                        existingClub.setName(club.getName());
                    }

                    return existingClub;
                }
            )
            .map(clubRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Club> findAll(Pageable pageable) {
        log.debug("Request to get all Clubs");
        return clubRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Club> findOne(Long id) {
        log.debug("Request to get Club : {}", id);
        return clubRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Club : {}", id);
        clubRepository.deleteById(id);
    }
}
