package ch.felberto.service.impl;

import ch.felberto.domain.*;
import ch.felberto.domain.enumeration.Position;
import ch.felberto.repository.CompetitionRepository;
import ch.felberto.repository.RankingRepository;
import ch.felberto.repository.ResultsRepository;
import ch.felberto.service.RankingListService;
import ch.felberto.service.ResultsService;
import ch.felberto.service.SeriesService;
import com.google.common.collect.ComparisonChain;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RankingListService} for competitionType ZSAV_NAWU_GM.
 */
@Service
@Transactional
public class ZsavNawuGmRankingListServiceImpl {

    private final Logger log = LoggerFactory.getLogger(ZsavNawuGmRankingListServiceImpl.class);

    private final ResultsRepository resultsRepository;

    private final CompetitionRepository competitionRepository;

    private final RankingRepository rankingRepository;

    private final ResultsService resultsService;

    private final SeriesService seriesService;

    public ZsavNawuGmRankingListServiceImpl(
        ResultsRepository resultsRepository,
        CompetitionRepository competitionRepository,
        RankingRepository rankingRepository,
        ResultsService resultsService,
        SeriesService seriesService
    ) {
        this.resultsRepository = resultsRepository;
        this.competitionRepository = competitionRepository;
        this.rankingRepository = rankingRepository;
        this.resultsService = resultsService;
        this.seriesService = seriesService;
    }

    public GroupRankingList generateGroupRankingList(Long competitionId, Integer type) {
        log.info("generate groupRankingList for competitionId: {} and type: {}", competitionId, type);
        Competition competition = competitionRepository.getOne(competitionId);
        GroupRankingList groupRankingList = getAllAthletesByCompetitionGroup(competition);

        groupRankingList.setGroupAthleteResultList(
            groupRankingList.getGroupAthleteResultList().stream().filter(s -> (s.getGroup() != null)).collect(Collectors.toList())
        );

        sortRankingListFinalSeries2(groupRankingList);
        sortRankingListWithinGroup(groupRankingList);
        log.info("generated groupRankingList: {}", groupRankingList);
        return groupRankingList;
    }

    public RankingList generateRankingList(Long competitionId, Integer type) {
        log.info("generate rankingList for competitionId: {} and type: {}", competitionId, type);
        Competition competition = competitionRepository.getOne(competitionId);
        RankingList rankingList = getAllAthletesByCompetition(competition);

        sortRankingListRound1Series2(rankingList);
        log.info("generated rankingList: {}", rankingList);
        return rankingList;
    }

    public GroupRankingList getAllAthletesByCompetitionGroup(Competition competition) {
        GroupRankingList groupRankingList = new GroupRankingList();
        groupRankingList.setCompetition(competition);

        List<Results> results = new ArrayList<>(resultsRepository.findByCompetition_Id(competition.getId()));
        List<GroupAthleteResult> groupAthleteResultList = new ArrayList<>();
        List<Group> groupList = new ArrayList<>();
        results.forEach(
            result -> {
                if (!groupList.contains(result.getGroup())) {
                    groupList.add(result.getGroup());
                }
            }
        );
        groupList.forEach(
            group -> {
                GroupAthleteResult groupAthleteResult = new GroupAthleteResult();
                groupAthleteResult.setGroup(group);
                List<Athlete> athleteList = new ArrayList<>();
                results.forEach(
                    result -> {
                        if (result.getGroup() != null && result.getGroup().equals(group)) {
                            athleteList.add(result.getAthlete());
                        }
                    }
                );
                List<AthleteResult> athleteResultList = new ArrayList<>();
                athleteList.forEach(
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
                                athleteResult.setT0(
                                    athleteResult.getT0() +
                                    getDeepshotsForSerie(result.getSerie1(), 0) +
                                    getDeepshotsForSerie(result.getSerie2(), 0)
                                );
                                athleteResult.setT1(
                                    athleteResult.getT1() +
                                    getDeepshotsForSerie(result.getSerie1(), 1) +
                                    getDeepshotsForSerie(result.getSerie2(), 1)
                                );
                                athleteResult.setT2(
                                    athleteResult.getT2() +
                                    getDeepshotsForSerie(result.getSerie1(), 2) +
                                    getDeepshotsForSerie(result.getSerie2(), 2)
                                );
                                athleteResult.setT3(
                                    athleteResult.getT3() +
                                    getDeepshotsForSerie(result.getSerie1(), 3) +
                                    getDeepshotsForSerie(result.getSerie2(), 3)
                                );
                                athleteResult.setT4(
                                    athleteResult.getT4() +
                                    getDeepshotsForSerie(result.getSerie1(), 4) +
                                    getDeepshotsForSerie(result.getSerie2(), 4)
                                );
                                athleteResult.setT5(
                                    athleteResult.getT5() +
                                    getDeepshotsForSerie(result.getSerie1(), 5) +
                                    getDeepshotsForSerie(result.getSerie2(), 5)
                                );
                                athleteResult.setT6(
                                    athleteResult.getT6() +
                                    getDeepshotsForSerie(result.getSerie1(), 6) +
                                    getDeepshotsForSerie(result.getSerie2(), 6)
                                );
                                athleteResult.setT7(
                                    athleteResult.getT7() +
                                    getDeepshotsForSerie(result.getSerie1(), 7) +
                                    getDeepshotsForSerie(result.getSerie2(), 7)
                                );
                                athleteResult.setT8(
                                    athleteResult.getT8() +
                                    getDeepshotsForSerie(result.getSerie1(), 8) +
                                    getDeepshotsForSerie(result.getSerie2(), 8)
                                );
                                athleteResult.setT9(
                                    athleteResult.getT9() +
                                    getDeepshotsForSerie(result.getSerie1(), 9) +
                                    getDeepshotsForSerie(result.getSerie2(), 9)
                                );
                                athleteResult.setT10(
                                    athleteResult.getT10() +
                                    getDeepshotsForSerie(result.getSerie1(), 10) +
                                    getDeepshotsForSerie(result.getSerie2(), 10) +
                                    athleteResult.getT11() +
                                    getDeepshotsForSerie(result.getSerie1(), 11) +
                                    getDeepshotsForSerie(result.getSerie2(), 11)
                                );
                                athleteResult.setT11(
                                    athleteResult.getT11() +
                                    getDeepshotsForSerie(result.getSerie1(), 11) +
                                    getDeepshotsForSerie(result.getSerie2(), 11)
                                );
                            }
                        );
                        athleteResultList.add(athleteResult);
                    }
                );
                groupAthleteResult.setAthleteResultList(athleteResultList);
                groupAthleteResult
                    .getAthleteResultList()
                    .forEach(
                        athleteResult -> {
                            groupAthleteResult.setResult(
                                checkIfNull(groupAthleteResult.getResult()) + checkIfNull(athleteResult.getResult())
                            );
                        }
                    );
                //todo test this
                groupAthleteResult.setResult(groupAthleteResult.getResult() / groupAthleteResult.getAthleteResultList().size());
                groupAthleteResultList.add(groupAthleteResult);
            }
        );
        groupRankingList.setGroupAthleteResultList(groupAthleteResultList);

        return groupRankingList;
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
        athleteList.forEach(
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
                        athleteResult.setT0(
                            athleteResult.getT0() +
                            getDeepshotsForSerie(result.getSerie1(), 0) +
                            getDeepshotsForSerie(result.getSerie2(), 0)
                        );
                        athleteResult.setT1(
                            athleteResult.getT1() +
                            getDeepshotsForSerie(result.getSerie1(), 1) +
                            getDeepshotsForSerie(result.getSerie2(), 1)
                        );
                        athleteResult.setT2(
                            athleteResult.getT2() +
                            getDeepshotsForSerie(result.getSerie1(), 2) +
                            getDeepshotsForSerie(result.getSerie2(), 2)
                        );
                        athleteResult.setT3(
                            athleteResult.getT3() +
                            getDeepshotsForSerie(result.getSerie1(), 3) +
                            getDeepshotsForSerie(result.getSerie2(), 3)
                        );
                        athleteResult.setT4(
                            athleteResult.getT4() +
                            getDeepshotsForSerie(result.getSerie1(), 4) +
                            getDeepshotsForSerie(result.getSerie2(), 4)
                        );
                        athleteResult.setT5(
                            athleteResult.getT5() +
                            getDeepshotsForSerie(result.getSerie1(), 5) +
                            getDeepshotsForSerie(result.getSerie2(), 5)
                        );
                        athleteResult.setT6(
                            athleteResult.getT6() +
                            getDeepshotsForSerie(result.getSerie1(), 6) +
                            getDeepshotsForSerie(result.getSerie2(), 6)
                        );
                        athleteResult.setT7(
                            athleteResult.getT7() +
                            getDeepshotsForSerie(result.getSerie1(), 7) +
                            getDeepshotsForSerie(result.getSerie2(), 7)
                        );
                        athleteResult.setT8(
                            athleteResult.getT8() +
                            getDeepshotsForSerie(result.getSerie1(), 8) +
                            getDeepshotsForSerie(result.getSerie2(), 8)
                        );
                        athleteResult.setT9(
                            athleteResult.getT9() +
                            getDeepshotsForSerie(result.getSerie1(), 9) +
                            getDeepshotsForSerie(result.getSerie2(), 9)
                        );
                        athleteResult.setT10(
                            athleteResult.getT10() +
                            getDeepshotsForSerie(result.getSerie1(), 10) +
                            getDeepshotsForSerie(result.getSerie2(), 10) +
                            athleteResult.getT11() +
                            getDeepshotsForSerie(result.getSerie1(), 11) +
                            getDeepshotsForSerie(result.getSerie2(), 11)
                        );
                        athleteResult.setT11(
                            athleteResult.getT11() +
                            getDeepshotsForSerie(result.getSerie1(), 11) +
                            getDeepshotsForSerie(result.getSerie2(), 11)
                        );
                    }
                );
                athleteResultList.add(athleteResult);
            }
        );

        rankingList.setAthleteResultList(athleteResultList);
        return rankingList;
    }

    public GroupRankingList sortRankingListFinalSeries2(GroupRankingList groupRankingList) {
        groupRankingList
            .getGroupAthleteResultList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResult(), o1.getResult())
                        /// bestes der Einzelresultat
                        .compare(getHighestResult(o2), getHighestResult(o1))
                        //Tiefschüsse aller Einzelresultate
                        .compare(getDeepshotsForAllAthletes(o2, 10), getDeepshotsForAllAthletes(o1, 10))
                        .compare(getDeepshotsForAllAthletes(o2, 9), getDeepshotsForAllAthletes(o1, 9))
                        .compare(getDeepshotsForAllAthletes(o2, 8), getDeepshotsForAllAthletes(o1, 8))
                        .compare(getDeepshotsForAllAthletes(o2, 7), getDeepshotsForAllAthletes(o1, 7))
                        .compare(getDeepshotsForAllAthletes(o2, 6), getDeepshotsForAllAthletes(o1, 6))
                        .compare(getDeepshotsForAllAthletes(o2, 5), getDeepshotsForAllAthletes(o1, 5))
                        .compare(getDeepshotsForAllAthletes(o2, 4), getDeepshotsForAllAthletes(o1, 4))
                        .compare(getDeepshotsForAllAthletes(o2, 3), getDeepshotsForAllAthletes(o1, 3))
                        .compare(getDeepshotsForAllAthletes(o2, 2), getDeepshotsForAllAthletes(o1, 2))
                        .compare(getDeepshotsForAllAthletes(o2, 1), getDeepshotsForAllAthletes(o1, 1))
                        .compare(getDeepshotsForAllAthletes(o2, 0), getDeepshotsForAllAthletes(o1, 0))
                        .result()
            );

        return groupRankingList;
    }

    public RankingList sortRankingListRound1Series2(RankingList rankingList) {
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

    public GroupRankingList sortRankingListWithinGroup(GroupRankingList groupRankingList) {
        groupRankingList
            .getGroupAthleteResultList()
            .forEach(
                groupAthleteResult ->
                    groupAthleteResult
                        .getAthleteResultList()
                        .sort((o1, o2) -> ComparisonChain.start().compare(o2.getResult(), o1.getResult()).result())
            );

        return groupRankingList;
    }

    public Integer getDeepshotsForAllAthletes(GroupAthleteResult groupAthleteResult, Integer number) {
        AtomicReference<Integer> deepshots = new AtomicReference<>(0);
        groupAthleteResult
            .getAthleteResultList()
            .forEach(
                athleteResult -> {
                    if (number == 10) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT10());
                    } else if (number == 9) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT9());
                    } else if (number == 8) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT8());
                    } else if (number == 7) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT7());
                    } else if (number == 6) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT6());
                    } else if (number == 5) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT5());
                    } else if (number == 4) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT4());
                    } else if (number == 3) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT3());
                    } else if (number == 2) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT2());
                    } else if (number == 1) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT1());
                    } else if (number == 0) {
                        deepshots.getAndSet(deepshots.get() + athleteResult.getT0());
                    }
                }
            );

        return deepshots.get();
    }

    public Integer getHighestResult(GroupAthleteResult groupAthleteResult) {
        AtomicReference<Integer> highestResult = new AtomicReference<>(0);
        groupAthleteResult
            .getAthleteResultList()
            .forEach(
                athleteResult -> {
                    if (athleteResult.getResult() > highestResult.get()) {
                        highestResult.set(athleteResult.getResult());
                    }
                }
            );

        return highestResult.get();
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

    private Integer checkIfNull(Integer number) {
        return number == null ? 0 : number;
    }
}
