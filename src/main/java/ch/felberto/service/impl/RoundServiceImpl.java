package ch.felberto.service.impl;

import ch.felberto.domain.Round;
import ch.felberto.repository.RoundRepository;
import ch.felberto.service.RoundService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ch.felberto.domain.Round}.
 */
@Service
@Transactional
public class RoundServiceImpl implements RoundService {

    private final Logger log = LoggerFactory.getLogger(RoundServiceImpl.class);

    private final RoundRepository roundRepository;

    public RoundServiceImpl(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    @Override
    public Round save(Round round) {
        log.debug("Request to save Round : {}", round);
        return roundRepository.save(round);
    }

    @Override
    public Optional<Round> partialUpdate(Round round) {
        log.debug("Request to partially update Round : {}", round);

        return roundRepository
            .findById(round.getId())
            .map(
                existingRound -> {
                    if (round.getRound() != null) {
                        existingRound.setRound(round.getRound());
                    }
                    if (round.getDate() != null) {
                        existingRound.setDate(round.getDate());
                    }

                    return existingRound;
                }
            )
            .map(roundRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Round> findAll(Pageable pageable) {
        log.debug("Request to get all Rounds");
        return roundRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Round> findOne(Long id) {
        log.debug("Request to get Round : {}", id);
        return roundRepository.findById(id);
    }

    @Override
    public Optional<Round> findOneByRoundAndCompetitionId(Integer round, Long competitionId) {
        log.debug("Request to get Round : {}", round);
        return roundRepository.findByRoundAndCompetition_Id(round, competitionId);
    }

    @Override
    public List<Round> findByCompetitionId(Long competitionId) {
        log.debug("Request to get Round : {}", competitionId);
        return roundRepository.findByCompetition_Id(competitionId);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Round : {}", id);
        roundRepository.deleteById(id);
    }
}
