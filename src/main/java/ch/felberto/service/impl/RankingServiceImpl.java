package ch.felberto.service.impl;

import ch.felberto.domain.Ranking;
import ch.felberto.repository.RankingRepository;
import ch.felberto.service.RankingService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ranking}.
 */
@Service
@Transactional
public class RankingServiceImpl implements RankingService {

    private final Logger log = LoggerFactory.getLogger(RankingServiceImpl.class);

    private final RankingRepository rankingRepository;

    public RankingServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public Ranking save(Ranking ranking) {
        log.debug("Request to save Ranking : {}", ranking);
        return rankingRepository.save(ranking);
    }

    @Override
    public Optional<Ranking> partialUpdate(Ranking ranking) {
        log.debug("Request to partially update Ranking : {}", ranking);

        return rankingRepository
            .findById(ranking.getId())
            .map(
                existingRanking -> {
                    if (ranking.getPosition() != null) {
                        existingRanking.setPosition(ranking.getPosition());
                    }
                    if (ranking.getRankingCriteria() != null) {
                        existingRanking.setRankingCriteria(ranking.getRankingCriteria());
                    }

                    return existingRanking;
                }
            )
            .map(rankingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ranking> findAll(Pageable pageable) {
        log.debug("Request to get all Rankings");
        return rankingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ranking> findOne(Long id) {
        log.debug("Request to get Ranking : {}", id);
        return rankingRepository.findById(id);
    }

    @Override
    public List<Ranking> findByCompetition(Long competitionId) {
        log.debug("Request to get Ranking : {}", competitionId);
        return rankingRepository.findByCompetition_Id(competitionId);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ranking : {}", id);
        rankingRepository.deleteById(id);
    }

    @Override
    public void deleteByCompetition(Long id) {
        log.debug("Request to delete Rangierung by Ranking: {}", id);
        rankingRepository.deleteByCompetition_Id(id);
    }
}
