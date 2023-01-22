package ch.felberto.service.impl;

import ch.felberto.domain.*;
import ch.felberto.repository.CompetitionRepository;
import ch.felberto.repository.ResultsRepository;
import ch.felberto.service.RankingListService;
import com.google.common.collect.ComparisonChain;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RankingListService} for competitionType EASV_SM_10M.
 */
@Service
@Transactional
public class EasvSm10mRankingListServiceImpl {

    private final Logger log = LoggerFactory.getLogger(EasvSm10mRankingListServiceImpl.class);

    private final ResultsRepository resultsRepository;

    private final CompetitionRepository competitionRepository;

    public EasvSm10mRankingListServiceImpl(ResultsRepository resultsRepository, CompetitionRepository competitionRepository) {
        this.resultsRepository = resultsRepository;
        this.competitionRepository = competitionRepository;
    }

    public RankingList generateRankingList(Long competitionId, Integer type) {
        log.info("generate rankingList for competitionId: {}", competitionId);
        Competition competition = competitionRepository.getOne(competitionId);
        RankingList rankingList = getAllAthletesByCompetition(competition);

        sortRankingListRound1Series4(rankingList);

        log.info("generated rankingList: {}", rankingList);
        return rankingList;
    }

    public RankingList getAllAthletesByCompetition(Competition competition) {
        RankingList rankingList = new RankingList();
        rankingList.setCompetition(competition);

        List<Results> results = resultsRepository.findByCompetition_Id(competition.getId());

        List<Athlete> athleteList = new ArrayList<>();
        results
            .stream()
            .filter(result -> !athleteList.contains(result.getAthlete()))
            .forEach(result -> athleteList.add(result.getAthlete()));
        List<AthleteResult> athleteResultList = new ArrayList<>();
        athleteList
            .stream()
            .forEach(
                athlete -> {
                    AthleteResult athleteResult = new AthleteResult();
                    athleteResult.setAthlete(athlete);
                    List<Results> resultsAthlete = results
                        .stream()
                        .filter(result -> result.getAthlete() == athlete)
                        .sorted(Comparator.comparing(Results::getRound))
                        .collect(Collectors.toList());
                    athleteResult.setResultsList(resultsAthlete);
                    athleteResult.setResult(resultsAthlete.stream().mapToInt(Results::getResult).sum());
                    athleteResult.setT0(0);
                    athleteResult.setT1(0);
                    athleteResult.setT2(0);
                    athleteResult.setT3(0);
                    athleteResult.setT4(0);
                    athleteResult.setT5(0);
                    athleteResult.setT6(0);
                    athleteResult.setT7(0);
                    athleteResult.setT8(0);
                    athleteResult.setT9(0);
                    athleteResult.setT10(0);
                    athleteResult.setT11(0);
                    resultsAthlete.forEach(
                        result -> {
                            athleteResult.setResultRound1(result.getResult());
                            athleteResult.setResultatRunde1Passe1(checkIfNull(result.getSerie1()));
                            athleteResult.setResultatRunde1Passe2(checkIfNull(result.getSerie2()));
                            athleteResult.setResultatRunde1Passe3(checkIfNull(result.getSerie3()));
                            athleteResult.setResultatRunde1Passe4(checkIfNull(result.getSerie4()));
                            athleteResult.setT0(
                                athleteResult.getT0() +
                                getDeepshotsForSerie(result.getSerie1(), 0) +
                                getDeepshotsForSerie(result.getSerie2(), 0) +
                                getDeepshotsForSerie(result.getSerie3(), 0) +
                                getDeepshotsForSerie(result.getSerie4(), 0)
                            );
                            athleteResult.setT1(
                                athleteResult.getT1() +
                                getDeepshotsForSerie(result.getSerie1(), 1) +
                                getDeepshotsForSerie(result.getSerie2(), 1) +
                                getDeepshotsForSerie(result.getSerie3(), 1) +
                                getDeepshotsForSerie(result.getSerie4(), 1)
                            );
                            athleteResult.setT2(
                                athleteResult.getT2() +
                                getDeepshotsForSerie(result.getSerie1(), 2) +
                                getDeepshotsForSerie(result.getSerie2(), 2) +
                                getDeepshotsForSerie(result.getSerie3(), 2) +
                                getDeepshotsForSerie(result.getSerie4(), 2)
                            );
                            athleteResult.setT3(
                                athleteResult.getT3() +
                                getDeepshotsForSerie(result.getSerie1(), 3) +
                                getDeepshotsForSerie(result.getSerie2(), 3) +
                                getDeepshotsForSerie(result.getSerie3(), 3) +
                                getDeepshotsForSerie(result.getSerie4(), 3)
                            );
                            athleteResult.setT4(
                                athleteResult.getT4() +
                                getDeepshotsForSerie(result.getSerie1(), 4) +
                                getDeepshotsForSerie(result.getSerie2(), 4) +
                                getDeepshotsForSerie(result.getSerie3(), 4) +
                                getDeepshotsForSerie(result.getSerie4(), 4)
                            );
                            athleteResult.setT5(
                                athleteResult.getT5() +
                                getDeepshotsForSerie(result.getSerie1(), 5) +
                                getDeepshotsForSerie(result.getSerie2(), 5) +
                                getDeepshotsForSerie(result.getSerie3(), 5) +
                                getDeepshotsForSerie(result.getSerie4(), 5)
                            );
                            athleteResult.setT6(
                                athleteResult.getT6() +
                                getDeepshotsForSerie(result.getSerie1(), 6) +
                                getDeepshotsForSerie(result.getSerie2(), 6) +
                                getDeepshotsForSerie(result.getSerie3(), 6) +
                                getDeepshotsForSerie(result.getSerie4(), 6)
                            );
                            athleteResult.setT7(
                                athleteResult.getT7() +
                                getDeepshotsForSerie(result.getSerie1(), 7) +
                                getDeepshotsForSerie(result.getSerie2(), 7) +
                                getDeepshotsForSerie(result.getSerie3(), 7) +
                                getDeepshotsForSerie(result.getSerie4(), 7)
                            );
                            athleteResult.setT8(
                                athleteResult.getT8() +
                                getDeepshotsForSerie(result.getSerie1(), 8) +
                                getDeepshotsForSerie(result.getSerie2(), 8) +
                                getDeepshotsForSerie(result.getSerie3(), 8) +
                                getDeepshotsForSerie(result.getSerie4(), 8)
                            );
                            athleteResult.setT9(
                                athleteResult.getT9() +
                                getDeepshotsForSerie(result.getSerie1(), 9) +
                                getDeepshotsForSerie(result.getSerie2(), 9) +
                                getDeepshotsForSerie(result.getSerie3(), 9) +
                                getDeepshotsForSerie(result.getSerie4(), 9)
                            );
                            athleteResult.setT10(
                                athleteResult.getT10() +
                                getDeepshotsForSerie(result.getSerie1(), 10) +
                                getDeepshotsForSerie(result.getSerie2(), 10) +
                                getDeepshotsForSerie(result.getSerie3(), 10) +
                                getDeepshotsForSerie(result.getSerie4(), 10) +
                                athleteResult.getT11() +
                                getDeepshotsForSerie(result.getSerie1(), 11) +
                                getDeepshotsForSerie(result.getSerie2(), 11) +
                                getDeepshotsForSerie(result.getSerie3(), 11) +
                                getDeepshotsForSerie(result.getSerie4(), 11)
                            );
                            athleteResult.setT11(
                                athleteResult.getT11() +
                                getDeepshotsForSerie(result.getSerie1(), 11) +
                                getDeepshotsForSerie(result.getSerie2(), 11) +
                                getDeepshotsForSerie(result.getSerie3(), 11) +
                                getDeepshotsForSerie(result.getSerie4(), 11)
                            );
                        }
                    );
                    athleteResultList.add(athleteResult);
                }
            );

        rankingList.setAthleteResultList(athleteResultList);
        return rankingList;
    }

    public RankingList sortRankingListRound1Series4(RankingList rankingList) {
        /*
         * 1. höhere letzte, dann zweitletzte Serie, etc. zu 10 Schuss
         * 2. die Tiefschüsse
         * 3. die höhere Anzahl Mouchen
         * 4. gleicher Rang
         * */
        rankingList
            .getAthleteResultList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResult(), o1.getResult())
                        //Resultat Passe 4
                        .compare(checkIfNull(o2.getResultsList().get(0).getSerie4()), checkIfNull(o1.getResultsList().get(0).getSerie4()))
                        //Resultat Passe 3
                        .compare(checkIfNull(o2.getResultsList().get(0).getSerie3()), checkIfNull(o1.getResultsList().get(0).getSerie3()))
                        //Resultat Passe 2
                        .compare(checkIfNull(o2.getResultsList().get(0).getSerie2()), checkIfNull(o1.getResultsList().get(0).getSerie2()))
                        //Resultat Passe 1
                        .compare(checkIfNull(o2.getResultsList().get(0).getSerie1()), checkIfNull(o1.getResultsList().get(0).getSerie1()))
                        //Tiefschüsse
                        .compare(o2.getT10(), o1.getT10())
                        .compare(o2.getT9(), o1.getT9())
                        .compare(o2.getT8(), o1.getT8())
                        .compare(o2.getT7(), o1.getT7())
                        .compare(o2.getT6(), o1.getT6())
                        .compare(o2.getT5(), o1.getT5())
                        .compare(o2.getT4(), o1.getT4())
                        .compare(o2.getT3(), o1.getT3())
                        .compare(o2.getT2(), o1.getT2())
                        .compare(o2.getT1(), o1.getT1())
                        .compare(o2.getT0(), o1.getT0())
                        //Mouchen
                        .compare(o2.getT11(), o1.getT11())
                        .result()
            );

        return rankingList;
    }

    public Integer getDeepshotsForSerie(Series series, Integer number) {
        if (series == null) {
            return 0;
        } else {
            Integer returnCount = 0;
            if (series.getp1() != null && series.getp1().intValue() == number) {
                returnCount++;
            }
            if (series.getp2() != null && series.getp2().intValue() == number) {
                returnCount++;
            }
            if (series.getp3() != null && series.getp3().intValue() == number) {
                returnCount++;
            }
            if (series.getp4() != null && series.getp4().intValue() == number) {
                returnCount++;
            }
            if (series.getp5() != null && series.getp5().intValue() == number) {
                returnCount++;
            }
            if (series.getp6() != null && series.getp6().intValue() == number) {
                returnCount++;
            }
            if (series.getp7() != null && series.getp7().intValue() == number) {
                returnCount++;
            }
            if (series.getp8() != null && series.getp8().intValue() == number) {
                returnCount++;
            }
            if (series.getp9() != null && series.getp9().intValue() == number) {
                returnCount++;
            }
            if (series.getp10() != null && series.getp10().intValue() == number) {
                returnCount++;
            }
            return returnCount;
        }
    }

    private Integer checkIfNull(Series serie) {
        return serie == null ? 0 : serie.getResult();
    }
}
