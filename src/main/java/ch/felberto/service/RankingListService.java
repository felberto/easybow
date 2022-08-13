package ch.felberto.service;

import ch.felberto.domain.Competition;
import ch.felberto.domain.RankingList;
import ch.felberto.domain.Series;

/**
 * Service Interface for managing RankingList.
 */
public interface RankingListService {
    /**
     * Generate rankingList.
     *
     * @param competitionId the id of competition for the rankingList.
     * @param type        the type for the rankingList.
     * @return the persisted entity.
     */
    RankingList generateRankingList(Long competitionId, Integer type);

    /**
     * get all athletes by competition
     *
     * @param competition
     * @param type
     * @return rankingList
     */
    RankingList getAllAthletesByCompetition(Competition competition, Integer type);

    /**
     * sort list by 1 round and 1 serie
     *
     * @param rankingList
     * @return rankingList
     */
    RankingList sortRankingListRound1Serie1(RankingList rankingList);

    /**
     * sort list by 1 round and 2 series
     *
     * @param rankingList
     * @param round
     * @return rankingList
     */
    RankingList sortRankingListRound1Series2(RankingList rankingList, Integer round);

    /**
     * sort list by 2 round and 1 serie
     *
     * @param rankingList
     * @return rankingList
     */
    RankingList sortRankingListRound2Serie1(RankingList rankingList);

    /**
     * sort list by 2 round and 2 series
     *
     * @param rankingList
     * @return rankingList
     */
    RankingList sortRankingListRound2Series2(RankingList rankingList);

    /**
     * sort list by 2 rounds, final and 1 serie
     *
     * @param rankingList
     * @return rankingList
     */
    RankingList sortRankingListRounds2FinalSerie1(RankingList rankingList);

    /**
     * sort list by 2 rounds, final and 2 series
     *
     * @param rankingList
     * @return rankingList
     */
    RankingList sortRankingListRounds2FinalSeries2(RankingList rankingList);

    /**
     * get deepshots by number
     *
     * @param series
     * @param number
     * @return rankingList
     */
    Integer getDeepshotsForSerie(Series series, Integer number);

    /**
     * Create final.
     *
     * @param competitionId the id of competition for the rankingList.
     * @param type        the type for the rankingList.
     * @return the persisted entity.
     */
    RankingList createFinal(Long competitionId, Integer type);
}
