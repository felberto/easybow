package ch.felberto.service.impl;

import ch.felberto.domain.Results;
import ch.felberto.repository.AthleteRepository;
import ch.felberto.repository.ResultsRepository;
import ch.felberto.service.ResultsService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Results}.
 */
@Service
@Transactional
public class ResultsServiceImpl implements ResultsService {

    private final Logger log = LoggerFactory.getLogger(ResultsServiceImpl.class);

    private final ResultsRepository resultsRepository;

    private final AthleteRepository athleteRepository;

    public ResultsServiceImpl(ResultsRepository resultsRepository, AthleteRepository athleteRepository) {
        this.resultsRepository = resultsRepository;
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Results save(Results results) {
        log.debug("Request to save Result : {}", results);
        int result = 0;
        if (results.getSerie1() != null && results.getSerie1().getResult() != null) {
            result = result + results.getSerie1().getResult();
        }
        if (results.getSerie2() != null && results.getSerie2().getResult() != null) {
            result = result + results.getSerie2().getResult();
        }
        if (results.getSerie3() != null && results.getSerie3().getResult() != null) {
            result = result + results.getSerie3().getResult();
        }
        if (results.getSerie4() != null && results.getSerie4().getResult() != null) {
            result = result + results.getSerie4().getResult();
        }
        if (results.getSerie5() != null && results.getSerie5().getResult() != null) {
            result = result + results.getSerie5().getResult();
        }
        if (results.getSerie6() != null && results.getSerie6().getResult() != null) {
            result = result + results.getSerie6().getResult();
        }
        results.setResult(result);
        return resultsRepository.save(results);
    }

    @Override
    public Optional<Results> partialUpdate(Results results) {
        log.debug("Request to partially update Result : {}", results);

        return resultsRepository
            .findById(results.getId())
            .map(
                existingResult -> {
                    if (results.getRound() != null) {
                        existingResult.setRound(results.getRound());
                    }
                    if (results.getResult() != null) {
                        existingResult.setResult(results.getResult());
                    }

                    return existingResult;
                }
            )
            .map(resultsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Results> findAll(Pageable pageable) {
        log.debug("Request to get all Results");
        return resultsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Results> findOne(Long id) {
        log.debug("Request to get Result : {}", id);
        return resultsRepository.findById(id);
    }

    @Override
    public List<Results> findByCompetition(Long competitionId) {
        log.debug("Request to get Result : {}", competitionId);
        return resultsRepository.findByCompetition_Id(competitionId);
    }

    @Override
    public List<Results> findByCompetitionAndClub(Long competitionId, Long clubId) {
        List<Results> results = resultsRepository.findByCompetition_Id(competitionId);
        return results.stream().filter(r -> r.getAthlete().getClub().getId().equals(clubId)).collect(Collectors.toList());
    }

    @Override
    public List<Results> findByRoundAndCompetition(Integer round, Long competitionId) {
        return resultsRepository.findByCompetition_IdAndRound(competitionId, round);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Result : {}", id);
        resultsRepository.deleteById(id);
    }

    @Override
    public void deleteByAthleteAndCompetition(Long id, Long competitionId) {
        log.debug("Request to delete Result : {}", id);
        if (athleteRepository.findById(id).isPresent()) {
            resultsRepository.deleteByAthleteAndCompetition_Id(athleteRepository.findById(id).get(), competitionId);
        }
    }

    @Override
    public void deleteByCompetition_IdAndRound(Long competitionId, Integer round) {
        log.debug("Request to delete Result : {}", competitionId);
        resultsRepository.deleteByCompetition_IdAndRound(competitionId, round);
    }
}
