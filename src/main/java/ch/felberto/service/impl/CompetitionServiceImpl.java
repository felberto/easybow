package ch.felberto.service.impl;

import ch.felberto.domain.Competition;
import ch.felberto.repository.CompetitionRepository;
import ch.felberto.service.CompetitionService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Competition}.
 */
@Service
@Transactional
public class CompetitionServiceImpl implements CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionServiceImpl.class);

    private final CompetitionRepository competitionRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @Override
    public Competition save(Competition competition) {
        log.debug("Request to save Competition : {}", competition);
        return competitionRepository.save(competition);
    }

    @Override
    public Optional<Competition> partialUpdate(Competition competition) {
        log.debug("Request to partially update Competition : {}", competition);

        return competitionRepository
            .findById(competition.getId())
            .map(
                existingCompetition -> {
                    if (competition.getName() != null) {
                        existingCompetition.setName(competition.getName());
                    }
                    if (competition.getYear() != null) {
                        existingCompetition.setYear(competition.getYear());
                    }
                    if (competition.getNumberOfRounds() != null) {
                        existingCompetition.setNumberOfRounds(competition.getNumberOfRounds());
                    }
                    if (competition.getNumberOfSeries() != null) {
                        existingCompetition.setNumberOfSeries(competition.getNumberOfSeries());
                    }
                    if (competition.getFinalRound() != null) {
                        existingCompetition.setFinalRound(competition.getFinalRound());
                    }
                    if (competition.getFinalPreparation() != null) {
                        existingCompetition.setFinalPreparation(competition.getFinalPreparation());
                    }
                    if (competition.getNumberOfFinalAthletes() != null) {
                        existingCompetition.setNumberOfFinalAthletes(competition.getNumberOfFinalAthletes());
                    }
                    if (competition.getNumberOfFinalSeries() != null) {
                        existingCompetition.setNumberOfFinalSeries(competition.getNumberOfFinalSeries());
                    }
                    if (competition.getTeamSize() != null) {
                        existingCompetition.setTeamSize(competition.getTeamSize());
                    }
                    if (competition.getTemplate() != null) {
                        existingCompetition.setTemplate(competition.getTemplate());
                    }

                    return existingCompetition;
                }
            )
            .map(competitionRepository::save);
    }

    @Override
    public Optional<Competition> partialUpdateByName(Competition competition) {
        log.debug("Request to partially update Competition : {}", competition);

        return competitionRepository
            .findByNameAndYear(competition.getName(), competition.getYear())
            .map(
                existingCompetition -> {
                    if (competition.getName() != null) {
                        existingCompetition.setName(competition.getName());
                    }
                    if (competition.getYear() != null) {
                        existingCompetition.setYear(competition.getYear());
                    }
                    if (competition.getNumberOfRounds() != null) {
                        existingCompetition.setNumberOfRounds(competition.getNumberOfRounds());
                    }
                    if (competition.getNumberOfSeries() != null) {
                        existingCompetition.setNumberOfSeries(competition.getNumberOfSeries());
                    }
                    if (competition.getFinalRound() != null) {
                        existingCompetition.setFinalRound(competition.getFinalRound());
                    }
                    if (competition.getFinalPreparation() != null) {
                        existingCompetition.setFinalPreparation(competition.getFinalPreparation());
                    }
                    if (competition.getNumberOfFinalAthletes() != null) {
                        existingCompetition.setNumberOfFinalAthletes(competition.getNumberOfFinalAthletes());
                    }
                    if (competition.getNumberOfFinalSeries() != null) {
                        existingCompetition.setNumberOfFinalSeries(competition.getNumberOfFinalSeries());
                    }
                    if (competition.getTeamSize() != null) {
                        existingCompetition.setTeamSize(competition.getTeamSize());
                    }
                    if (competition.getTemplate() != null) {
                        existingCompetition.setTemplate(competition.getTemplate());
                    }

                    return existingCompetition;
                }
            )
            .map(competitionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Competition> findAll(Pageable pageable) {
        log.debug("Request to get all Competitions");
        return competitionRepository.findAll(pageable);
    }

    @Override
    public List<Competition> findByYear(Integer year) {
        log.debug("Request to get all Competition by year");
        return competitionRepository.findByYear(year);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Competition> findOne(Long id) {
        log.debug("Request to get Competition : {}", id);
        return competitionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competition : {}", id);
        competitionRepository.deleteById(id);
    }
}
