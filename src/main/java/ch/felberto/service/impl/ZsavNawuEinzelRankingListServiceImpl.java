package ch.felberto.service.impl;

import ch.felberto.domain.*;
import ch.felberto.repository.CompetitionRepository;
import ch.felberto.repository.RankingRepository;
import ch.felberto.repository.ResultsRepository;
import ch.felberto.service.RankingListService;
import ch.felberto.service.ResultsService;
import ch.felberto.service.SeriesService;
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
 * Service Implementation for managing {@link RankingListService} for competitionType ZSAV_NAWU.
 */
@Service
@Transactional
public class ZsavNawuEinzelRankingListServiceImpl implements RankingListService {

    private final Logger log = LoggerFactory.getLogger(ZsavNawuEinzelRankingListServiceImpl.class);

    private final ResultsRepository resultsRepository;

    private final CompetitionRepository competitionRepository;

    private final RankingRepository rankingRepository;

    private final ResultsService resultsService;

    private final SeriesService seriesService;

    public ZsavNawuEinzelRankingListServiceImpl(
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

    @Override
    public RankingList createFinal(Long competitionId, Integer type) {
        log.info("createFinal for competitionId: {} and type: {}", competitionId, type);
        resultsService.deleteByCompetition_IdAndRound(competitionId, type);
        RankingList rankingList = generateRankingList(competitionId, 100);
        List<AthleteResult> tempFinalList;
        if (rankingList.getAthleteResultList().size() >= rankingList.getCompetition().getNumberOfFinalAthletes()) {
            tempFinalList = rankingList.getAthleteResultList().subList(0, rankingList.getCompetition().getNumberOfFinalAthletes());
        } else {
            tempFinalList = rankingList.getAthleteResultList();
        }
        tempFinalList.forEach(
            athleteResult -> {
                Results results = new Results();
                results.setCompetition(competitionRepository.getOne(competitionId));
                results.setAthlete(athleteResult.getAthlete());
                results.setRound(type);

                Series tempP1 = new Series();
                tempP1.setp1(0);
                tempP1.setp2(0);
                tempP1.setp3(0);
                tempP1.setp4(0);
                tempP1.setp5(0);
                tempP1.setp6(0);
                tempP1.setp7(0);
                tempP1.setp8(0);
                tempP1.setp9(0);
                tempP1.setp10(0);
                tempP1.setResult(0);
                Series serie1 = seriesService.save(tempP1);
                results.setSerie1(serie1);

                if (competitionRepository.getOne(competitionId).getNumberOfFinalSeries() == 2) {
                    Series tempP2 = new Series();
                    tempP2.setp1(0);
                    tempP2.setp2(0);
                    tempP2.setp3(0);
                    tempP2.setp4(0);
                    tempP2.setp5(0);
                    tempP2.setp6(0);
                    tempP2.setp7(0);
                    tempP2.setp8(0);
                    tempP2.setp9(0);
                    tempP2.setp10(0);
                    tempP2.setResult(0);
                    Series serie2 = seriesService.save(tempP2);
                    results.setSerie2(serie2);
                }

                resultsService.save(results);
            }
        );
        return rankingList;
    }

    @Override
    public RankingList generateRankingList(Long competitionId, Integer type) {
        log.info("generate rankingList for competitionId: {} and type: {}", competitionId, type);
        Competition competition = competitionRepository.getOne(competitionId);
        RankingList rankingList = getAllAthletesByCompetition(competition, type);
        rankingList.setType(type);

        switch (type) {
            case 1:
                if (competition.getNumberOfSeries() == 1) {
                    sortRankingListRound1Serie1(rankingList);
                } else if (competition.getNumberOfSeries() == 2) {
                    sortRankingListRound1Series2(rankingList, 1);
                }
                break;
            case 2:
                if (competition.getNumberOfSeries() == 1) {
                    sortRankingListRound1Serie1(rankingList);
                } else if (competition.getNumberOfSeries() == 2) {
                    sortRankingListRound1Series2(rankingList, 2);
                }
                break;
            case 100:
                if (competition.getNumberOfSeries() == 1) {
                    sortRankingListRound2Serie1(rankingList);
                } else if (competition.getNumberOfSeries() == 2) {
                    sortRankingListRound2Series2(rankingList);
                }
                break;
            case 99:
                if (competition.getNumberOfSeries() == 1) {
                    sortRankingListRound1Serie1(rankingList);
                } else if (competition.getNumberOfSeries() == 2) {
                    sortRankingListRound1Series2(rankingList, 99);
                }
                break;
            case 101:
                if (competition.getNumberOfSeries() == 1) {
                    sortRankingListRounds2FinalSerie1(rankingList);
                } else if (competition.getNumberOfSeries() == 2) {
                    sortRankingListRounds2FinalSeries2(rankingList);
                }
                break;
            default:
                break;
        }
        log.info("generated rankingList: {}", rankingList);
        return rankingList;
    }

    @Override
    public RankingList getAllAthletesByCompetition(Competition competition, Integer type) {
        //TODO Resultat 11 stimmt noch nicht

        RankingList rankingList = new RankingList();
        rankingList.setCompetition(competition);

        List<Results> results = new ArrayList<>();
        switch (type) {
            case 1:
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 1));
                break;
            case 2:
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 2));
                break;
            case 100:
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 1));
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 2));
                break;
            case 99:
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 99));
                break;
            case 101:
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 1));
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 2));
                results.addAll(resultsRepository.findByCompetition_IdAndRound(competition.getId(), 99));
                break;
            default:
                break;
        }
        List<Athlete> athleteList = new ArrayList<>();
        results
            .stream()
            .filter(resultat -> !athleteList.contains(resultat.getAthlete()))
            .forEach(resultat -> athleteList.add(resultat.getAthlete()));
        List<AthleteResult> athleteResultList = new ArrayList<>();
        athleteList.forEach(
            athlete -> {
                AthleteResult athleteResult = new AthleteResult();
                athleteResult.setAthlete(athlete);
                List<Results> resultsAthlete = results
                    .stream()
                    .filter(resultat -> resultat.getAthlete() == athlete)
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
                        if (result.getRound() == 1) {
                            if (result.getCompetition().getNumberOfSeries() == 1) {
                                athleteResult.setResultRound1(result.getResult());
                                athleteResult.setResultatRunde1Passe1(checkIfNull(result.getSerie1().getResult()));
                            } else if (result.getCompetition().getNumberOfSeries() == 2) {
                                athleteResult.setResultRound1(result.getResult());
                                athleteResult.setResultatRunde1Passe1(checkIfNull(result.getSerie1().getResult()));
                                athleteResult.setResultatRunde1Passe2(checkIfNull(result.getSerie2().getResult()));
                            }
                        } else if (result.getRound() == 2) {
                            if (result.getCompetition().getNumberOfSeries() == 1) {
                                athleteResult.setResultRound2(result.getResult());
                                athleteResult.setResultatRunde2Passe1(checkIfNull(result.getSerie1().getResult()));
                            } else if (result.getCompetition().getNumberOfSeries() == 2) {
                                athleteResult.setResultRound2(result.getResult());
                                athleteResult.setResultatRunde2Passe1(checkIfNull(result.getSerie1().getResult()));
                                athleteResult.setResultatRunde2Passe2(checkIfNull(result.getSerie2().getResult()));
                            }
                        } else if (result.getRound() == 99) {
                            if (result.getCompetition().getNumberOfSeries() == 1) {
                                athleteResult.setResultFinal(result.getResult());
                                athleteResult.setResultFinalSerie1(checkIfNull(result.getSerie1().getResult()));
                            } else if (result.getCompetition().getNumberOfSeries() == 2) {
                                athleteResult.setResultFinal(result.getResult());
                                athleteResult.setResultFinalSerie1(checkIfNull(result.getSerie1().getResult()));
                                athleteResult.setResultFinalSerie2(checkIfNull(result.getSerie2().getResult()));
                            }
                        }
                        if (result.getCompetition().getNumberOfSeries() == 1) {
                            athleteResult.setT0(athleteResult.getT0() + getDeepshotsForSerie(result.getSerie1(), 0));
                            athleteResult.setT1(athleteResult.getT1() + getDeepshotsForSerie(result.getSerie1(), 1));
                            athleteResult.setT2(athleteResult.getT2() + getDeepshotsForSerie(result.getSerie1(), 2));
                            athleteResult.setT3(athleteResult.getT3() + getDeepshotsForSerie(result.getSerie1(), 3));
                            athleteResult.setT4(athleteResult.getT4() + getDeepshotsForSerie(result.getSerie1(), 4));
                            athleteResult.setT5(athleteResult.getT5() + getDeepshotsForSerie(result.getSerie1(), 5));
                            athleteResult.setT6(athleteResult.getT6() + getDeepshotsForSerie(result.getSerie1(), 6));
                            athleteResult.setT7(athleteResult.getT7() + getDeepshotsForSerie(result.getSerie1(), 7));
                            athleteResult.setT8(athleteResult.getT8() + getDeepshotsForSerie(result.getSerie1(), 8));
                            athleteResult.setT9(athleteResult.getT9() + getDeepshotsForSerie(result.getSerie1(), 9));
                            athleteResult.setT10(
                                athleteResult.getT10() +
                                getDeepshotsForSerie(result.getSerie1(), 10) +
                                athleteResult.getT11() +
                                getDeepshotsForSerie(result.getSerie1(), 11)
                            );
                            athleteResult.setT11(athleteResult.getT11() + getDeepshotsForSerie(result.getSerie1(), 11));
                        } else if (result.getCompetition().getNumberOfSeries() == 2) {
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
                    }
                );
                athleteResultList.add(athleteResult);
            }
        );

        rankingList.setAthleteResultList(athleteResultList);
        return rankingList;
    }

    @Override
    public RankingList sortRankingListRound1Serie1(RankingList rankingList) {
        //evtl alles modular zusammenbauen
        /*
         * 1. höhere letzte, dann zweitletzte Serie, etc. zu 10 Schuss
         * 2. die Tiefschüsse
         * 3. die höhere Anzahl Mouchen
         * 4. die bessere Schussfolge von hinten, letzter, zweitletzter Schuss usw. unter Berücksichtigung der Mouchen
         * */
        rankingList
            .getAthleteResultList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResult(), o1.getResult())
                        //Resultat Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie1().getResult())
                        )
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
                        //Schussfolge von hinten
                        .compare(o2.getResultsList().get(0).getSerie1().getp10(), o1.getResultsList().get(0).getSerie1().getp10())
                        .compare(o2.getResultsList().get(0).getSerie1().getp9(), o1.getResultsList().get(0).getSerie1().getp9())
                        .compare(o2.getResultsList().get(0).getSerie1().getp8(), o1.getResultsList().get(0).getSerie1().getp8())
                        .compare(o2.getResultsList().get(0).getSerie1().getp7(), o1.getResultsList().get(0).getSerie1().getp7())
                        .compare(o2.getResultsList().get(0).getSerie1().getp6(), o1.getResultsList().get(0).getSerie1().getp6())
                        .compare(o2.getResultsList().get(0).getSerie1().getp5(), o1.getResultsList().get(0).getSerie1().getp5())
                        .compare(o2.getResultsList().get(0).getSerie1().getp4(), o1.getResultsList().get(0).getSerie1().getp4())
                        .compare(o2.getResultsList().get(0).getSerie1().getp3(), o1.getResultsList().get(0).getSerie1().getp3())
                        .compare(o2.getResultsList().get(0).getSerie1().getp2(), o1.getResultsList().get(0).getSerie1().getp2())
                        .compare(o2.getResultsList().get(0).getSerie1().getp1(), o1.getResultsList().get(0).getSerie1().getp1())
                        .result()
            );

        return rankingList;
    }

    @Override
    public RankingList sortRankingListRound1Series2(RankingList rankingList, Integer runde) {
        //evtl alles modular zusammenbauen
        /*
         * 1. höhere letzte, dann zweitletzte Serie, etc. zu 10 Schuss
         * 2. die Tiefschüsse
         * 3. die höhere Anzahl Mouchen
         * 4. die bessere Schussfolge von hinten, letzter, zweitletzter Schuss usw. unter Berücksichtigung der Mouchen
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
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie2().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie2().getResult())
                        )
                        //Resultat Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie1().getResult())
                        )
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
                        //Schussfolge von hinten
                        .compare(o2.getResultsList().get(0).getSerie2().getp10(), o1.getResultsList().get(0).getSerie2().getp10())
                        .compare(o2.getResultsList().get(0).getSerie2().getp9(), o1.getResultsList().get(0).getSerie2().getp9())
                        .compare(o2.getResultsList().get(0).getSerie2().getp8(), o1.getResultsList().get(0).getSerie2().getp8())
                        .compare(o2.getResultsList().get(0).getSerie2().getp7(), o1.getResultsList().get(0).getSerie2().getp7())
                        .compare(o2.getResultsList().get(0).getSerie2().getp6(), o1.getResultsList().get(0).getSerie2().getp6())
                        .compare(o2.getResultsList().get(0).getSerie2().getp5(), o1.getResultsList().get(0).getSerie2().getp5())
                        .compare(o2.getResultsList().get(0).getSerie2().getp4(), o1.getResultsList().get(0).getSerie2().getp4())
                        .compare(o2.getResultsList().get(0).getSerie2().getp3(), o1.getResultsList().get(0).getSerie2().getp3())
                        .compare(o2.getResultsList().get(0).getSerie2().getp2(), o1.getResultsList().get(0).getSerie2().getp2())
                        .compare(o2.getResultsList().get(0).getSerie2().getp1(), o1.getResultsList().get(0).getSerie2().getp1())
                        .compare(o2.getResultsList().get(0).getSerie1().getp10(), o1.getResultsList().get(0).getSerie1().getp10())
                        .compare(o2.getResultsList().get(0).getSerie1().getp9(), o1.getResultsList().get(0).getSerie1().getp9())
                        .compare(o2.getResultsList().get(0).getSerie1().getp8(), o1.getResultsList().get(0).getSerie1().getp8())
                        .compare(o2.getResultsList().get(0).getSerie1().getp7(), o1.getResultsList().get(0).getSerie1().getp7())
                        .compare(o2.getResultsList().get(0).getSerie1().getp6(), o1.getResultsList().get(0).getSerie1().getp6())
                        .compare(o2.getResultsList().get(0).getSerie1().getp5(), o1.getResultsList().get(0).getSerie1().getp5())
                        .compare(o2.getResultsList().get(0).getSerie1().getp4(), o1.getResultsList().get(0).getSerie1().getp4())
                        .compare(o2.getResultsList().get(0).getSerie1().getp3(), o1.getResultsList().get(0).getSerie1().getp3())
                        .compare(o2.getResultsList().get(0).getSerie1().getp2(), o1.getResultsList().get(0).getSerie1().getp2())
                        .compare(o2.getResultsList().get(0).getSerie1().getp1(), o1.getResultsList().get(0).getSerie1().getp1())
                        .result()
            );

        return rankingList;
    }

    @Override
    public RankingList sortRankingListRound2Serie1(RankingList rankingList) {
        //evtl alles modular zusammenbauen
        /*
         * 1. höhere letzte, dann zweitletzte Serie, etc. zu 10 Schuss
         * 2. die Tiefschüsse
         * 3. die höhere Anzahl Mouchen
         * 4. die bessere Schussfolge von hinten, letzter, zweitletzter Schuss usw. unter Berücksichtigung der Mouchen
         * */
        //TODO überprüfen ob get(0) 1 runde ist und get(1) 2 runde
        rankingList
            .getAthleteResultList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResult(), o1.getResult())
                        //Resultat Runde 2
                        .compare(checkIfNull(o2.getResultsList().get(1).getResult()), checkIfNull(o1.getResultsList().get(1).getResult()))
                        //Resultat Runde 1
                        .compare(checkIfNull(o2.getResultsList().get(0).getResult()), checkIfNull(o1.getResultsList().get(0).getResult()))
                        //Resultat Runde 2 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(1).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(1).getSerie1().getResult())
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie1().getResult())
                        )
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
                        //Schussfolge von hinten
                        .compare(o2.getResultsList().get(1).getSerie1().getp10(), o1.getResultsList().get(1).getSerie1().getp10())
                        .compare(o2.getResultsList().get(1).getSerie1().getp9(), o1.getResultsList().get(1).getSerie1().getp9())
                        .compare(o2.getResultsList().get(1).getSerie1().getp8(), o1.getResultsList().get(1).getSerie1().getp8())
                        .compare(o2.getResultsList().get(1).getSerie1().getp7(), o1.getResultsList().get(1).getSerie1().getp7())
                        .compare(o2.getResultsList().get(1).getSerie1().getp6(), o1.getResultsList().get(1).getSerie1().getp6())
                        .compare(o2.getResultsList().get(1).getSerie1().getp5(), o1.getResultsList().get(1).getSerie1().getp5())
                        .compare(o2.getResultsList().get(1).getSerie1().getp4(), o1.getResultsList().get(1).getSerie1().getp4())
                        .compare(o2.getResultsList().get(1).getSerie1().getp3(), o1.getResultsList().get(1).getSerie1().getp3())
                        .compare(o2.getResultsList().get(1).getSerie1().getp2(), o1.getResultsList().get(1).getSerie1().getp2())
                        .compare(o2.getResultsList().get(1).getSerie1().getp1(), o1.getResultsList().get(1).getSerie1().getp1())
                        .compare(o2.getResultsList().get(0).getSerie1().getp10(), o1.getResultsList().get(0).getSerie1().getp10())
                        .compare(o2.getResultsList().get(0).getSerie1().getp9(), o1.getResultsList().get(0).getSerie1().getp9())
                        .compare(o2.getResultsList().get(0).getSerie1().getp8(), o1.getResultsList().get(0).getSerie1().getp8())
                        .compare(o2.getResultsList().get(0).getSerie1().getp7(), o1.getResultsList().get(0).getSerie1().getp7())
                        .compare(o2.getResultsList().get(0).getSerie1().getp6(), o1.getResultsList().get(0).getSerie1().getp6())
                        .compare(o2.getResultsList().get(0).getSerie1().getp5(), o1.getResultsList().get(0).getSerie1().getp5())
                        .compare(o2.getResultsList().get(0).getSerie1().getp4(), o1.getResultsList().get(0).getSerie1().getp4())
                        .compare(o2.getResultsList().get(0).getSerie1().getp3(), o1.getResultsList().get(0).getSerie1().getp3())
                        .compare(o2.getResultsList().get(0).getSerie1().getp2(), o1.getResultsList().get(0).getSerie1().getp2())
                        .compare(o2.getResultsList().get(0).getSerie1().getp1(), o1.getResultsList().get(0).getSerie1().getp1())
                        .result()
            );

        return rankingList;
    }

    @Override
    public RankingList sortRankingListRound2Series2(RankingList rankingList) {
        rankingList
            .getAthleteResultList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResult(), o1.getResult())
                        //Resultat Runde 2
                        .compare(checkIfNull(o2.getResultsList().get(1).getResult()), checkIfNull(o1.getResultsList().get(1).getResult()))
                        //Resultat Runde 1
                        .compare(checkIfNull(o2.getResultsList().get(0).getResult()), checkIfNull(o1.getResultsList().get(0).getResult()))
                        //Resultat Runde 2 Passe 2
                        .compare(
                            checkIfNull(o2.getResultsList().get(1).getSerie2().getResult()),
                            checkIfNull(o1.getResultsList().get(1).getSerie2().getResult())
                        )
                        //Resultat Runde 2 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(1).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(1).getSerie1().getResult())
                        )
                        //Resultat Runde 1 Passe 2
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie2().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie2().getResult())
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie1().getResult())
                        )
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
                        //Schussfolge von hinten
                        .compare(o2.getResultsList().get(1).getSerie2().getp10(), o1.getResultsList().get(1).getSerie2().getp10())
                        .compare(o2.getResultsList().get(1).getSerie2().getp9(), o1.getResultsList().get(1).getSerie2().getp9())
                        .compare(o2.getResultsList().get(1).getSerie2().getp8(), o1.getResultsList().get(1).getSerie2().getp8())
                        .compare(o2.getResultsList().get(1).getSerie2().getp7(), o1.getResultsList().get(1).getSerie2().getp7())
                        .compare(o2.getResultsList().get(1).getSerie2().getp6(), o1.getResultsList().get(1).getSerie2().getp6())
                        .compare(o2.getResultsList().get(1).getSerie2().getp5(), o1.getResultsList().get(1).getSerie2().getp5())
                        .compare(o2.getResultsList().get(1).getSerie2().getp4(), o1.getResultsList().get(1).getSerie2().getp4())
                        .compare(o2.getResultsList().get(1).getSerie2().getp3(), o1.getResultsList().get(1).getSerie2().getp3())
                        .compare(o2.getResultsList().get(1).getSerie2().getp2(), o1.getResultsList().get(1).getSerie2().getp2())
                        .compare(o2.getResultsList().get(1).getSerie2().getp1(), o1.getResultsList().get(1).getSerie2().getp1())
                        .compare(o2.getResultsList().get(0).getSerie2().getp10(), o1.getResultsList().get(0).getSerie2().getp10())
                        .compare(o2.getResultsList().get(0).getSerie2().getp9(), o1.getResultsList().get(0).getSerie2().getp9())
                        .compare(o2.getResultsList().get(0).getSerie2().getp8(), o1.getResultsList().get(0).getSerie2().getp8())
                        .compare(o2.getResultsList().get(0).getSerie2().getp7(), o1.getResultsList().get(0).getSerie2().getp7())
                        .compare(o2.getResultsList().get(0).getSerie2().getp6(), o1.getResultsList().get(0).getSerie2().getp6())
                        .compare(o2.getResultsList().get(0).getSerie2().getp5(), o1.getResultsList().get(0).getSerie2().getp5())
                        .compare(o2.getResultsList().get(0).getSerie2().getp4(), o1.getResultsList().get(0).getSerie2().getp4())
                        .compare(o2.getResultsList().get(0).getSerie2().getp3(), o1.getResultsList().get(0).getSerie2().getp3())
                        .compare(o2.getResultsList().get(0).getSerie2().getp2(), o1.getResultsList().get(0).getSerie2().getp2())
                        .compare(o2.getResultsList().get(0).getSerie2().getp1(), o1.getResultsList().get(0).getSerie2().getp1())
                        .compare(o2.getResultsList().get(1).getSerie1().getp10(), o1.getResultsList().get(1).getSerie1().getp10())
                        .compare(o2.getResultsList().get(1).getSerie1().getp9(), o1.getResultsList().get(1).getSerie1().getp9())
                        .compare(o2.getResultsList().get(1).getSerie1().getp8(), o1.getResultsList().get(1).getSerie1().getp8())
                        .compare(o2.getResultsList().get(1).getSerie1().getp7(), o1.getResultsList().get(1).getSerie1().getp7())
                        .compare(o2.getResultsList().get(1).getSerie1().getp6(), o1.getResultsList().get(1).getSerie1().getp6())
                        .compare(o2.getResultsList().get(1).getSerie1().getp5(), o1.getResultsList().get(1).getSerie1().getp5())
                        .compare(o2.getResultsList().get(1).getSerie1().getp4(), o1.getResultsList().get(1).getSerie1().getp4())
                        .compare(o2.getResultsList().get(1).getSerie1().getp3(), o1.getResultsList().get(1).getSerie1().getp3())
                        .compare(o2.getResultsList().get(1).getSerie1().getp2(), o1.getResultsList().get(1).getSerie1().getp2())
                        .compare(o2.getResultsList().get(1).getSerie1().getp1(), o1.getResultsList().get(1).getSerie1().getp1())
                        .compare(o2.getResultsList().get(0).getSerie1().getp10(), o1.getResultsList().get(0).getSerie1().getp10())
                        .compare(o2.getResultsList().get(0).getSerie1().getp9(), o1.getResultsList().get(0).getSerie1().getp9())
                        .compare(o2.getResultsList().get(0).getSerie1().getp8(), o1.getResultsList().get(0).getSerie1().getp8())
                        .compare(o2.getResultsList().get(0).getSerie1().getp7(), o1.getResultsList().get(0).getSerie1().getp7())
                        .compare(o2.getResultsList().get(0).getSerie1().getp6(), o1.getResultsList().get(0).getSerie1().getp6())
                        .compare(o2.getResultsList().get(0).getSerie1().getp5(), o1.getResultsList().get(0).getSerie1().getp5())
                        .compare(o2.getResultsList().get(0).getSerie1().getp4(), o1.getResultsList().get(0).getSerie1().getp4())
                        .compare(o2.getResultsList().get(0).getSerie1().getp3(), o1.getResultsList().get(0).getSerie1().getp3())
                        .compare(o2.getResultsList().get(0).getSerie1().getp2(), o1.getResultsList().get(0).getSerie1().getp2())
                        .compare(o2.getResultsList().get(0).getSerie1().getp1(), o1.getResultsList().get(0).getSerie1().getp1())
                        .result()
            );

        return rankingList;
    }

    @Override
    public RankingList sortRankingListRounds2FinalSerie1(RankingList rankingList) {
        rankingList
            .getAthleteResultList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResult(), o1.getResult())
                        //Resultat Final
                        .compare(checkIfNull(o2.getResultsList().get(2).getResult()), checkIfNull(o1.getResultsList().get(2).getResult()))
                        //Resultat Runde 2
                        .compare(checkIfNull(o2.getResultsList().get(1).getResult()), checkIfNull(o1.getResultsList().get(1).getResult()))
                        //Resultat Runde 1
                        .compare(checkIfNull(o2.getResultsList().get(0).getResult()), checkIfNull(o1.getResultsList().get(0).getResult()))
                        //Resultat Final Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(2).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(2).getSerie1().getResult())
                        )
                        //Resultat Runde 2 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(1).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(1).getSerie1().getResult())
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie1().getResult())
                        )
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
                        //Schussfolge von hinten
                        .compare(o2.getResultsList().get(2).getSerie1().getp10(), o1.getResultsList().get(2).getSerie1().getp10())
                        .compare(o2.getResultsList().get(2).getSerie1().getp9(), o1.getResultsList().get(2).getSerie1().getp9())
                        .compare(o2.getResultsList().get(2).getSerie1().getp8(), o1.getResultsList().get(2).getSerie1().getp8())
                        .compare(o2.getResultsList().get(2).getSerie1().getp7(), o1.getResultsList().get(2).getSerie1().getp7())
                        .compare(o2.getResultsList().get(2).getSerie1().getp6(), o1.getResultsList().get(2).getSerie1().getp6())
                        .compare(o2.getResultsList().get(2).getSerie1().getp5(), o1.getResultsList().get(2).getSerie1().getp5())
                        .compare(o2.getResultsList().get(2).getSerie1().getp4(), o1.getResultsList().get(2).getSerie1().getp4())
                        .compare(o2.getResultsList().get(2).getSerie1().getp3(), o1.getResultsList().get(2).getSerie1().getp3())
                        .compare(o2.getResultsList().get(2).getSerie1().getp2(), o1.getResultsList().get(2).getSerie1().getp2())
                        .compare(o2.getResultsList().get(2).getSerie1().getp1(), o1.getResultsList().get(2).getSerie1().getp1())
                        .compare(o2.getResultsList().get(1).getSerie1().getp10(), o1.getResultsList().get(1).getSerie1().getp10())
                        .compare(o2.getResultsList().get(1).getSerie1().getp9(), o1.getResultsList().get(1).getSerie1().getp9())
                        .compare(o2.getResultsList().get(1).getSerie1().getp8(), o1.getResultsList().get(1).getSerie1().getp8())
                        .compare(o2.getResultsList().get(1).getSerie1().getp7(), o1.getResultsList().get(1).getSerie1().getp7())
                        .compare(o2.getResultsList().get(1).getSerie1().getp6(), o1.getResultsList().get(1).getSerie1().getp6())
                        .compare(o2.getResultsList().get(1).getSerie1().getp5(), o1.getResultsList().get(1).getSerie1().getp5())
                        .compare(o2.getResultsList().get(1).getSerie1().getp4(), o1.getResultsList().get(1).getSerie1().getp4())
                        .compare(o2.getResultsList().get(1).getSerie1().getp3(), o1.getResultsList().get(1).getSerie1().getp3())
                        .compare(o2.getResultsList().get(1).getSerie1().getp2(), o1.getResultsList().get(1).getSerie1().getp2())
                        .compare(o2.getResultsList().get(1).getSerie1().getp1(), o1.getResultsList().get(1).getSerie1().getp1())
                        .compare(o2.getResultsList().get(0).getSerie1().getp10(), o1.getResultsList().get(0).getSerie1().getp10())
                        .compare(o2.getResultsList().get(0).getSerie1().getp9(), o1.getResultsList().get(0).getSerie1().getp9())
                        .compare(o2.getResultsList().get(0).getSerie1().getp8(), o1.getResultsList().get(0).getSerie1().getp8())
                        .compare(o2.getResultsList().get(0).getSerie1().getp7(), o1.getResultsList().get(0).getSerie1().getp7())
                        .compare(o2.getResultsList().get(0).getSerie1().getp6(), o1.getResultsList().get(0).getSerie1().getp6())
                        .compare(o2.getResultsList().get(0).getSerie1().getp5(), o1.getResultsList().get(0).getSerie1().getp5())
                        .compare(o2.getResultsList().get(0).getSerie1().getp4(), o1.getResultsList().get(0).getSerie1().getp4())
                        .compare(o2.getResultsList().get(0).getSerie1().getp3(), o1.getResultsList().get(0).getSerie1().getp3())
                        .compare(o2.getResultsList().get(0).getSerie1().getp2(), o1.getResultsList().get(0).getSerie1().getp2())
                        .compare(o2.getResultsList().get(0).getSerie1().getp1(), o1.getResultsList().get(0).getSerie1().getp1())
                        .result()
            );

        return rankingList;
    }

    @Override
    public RankingList sortRankingListRounds2FinalSeries2(RankingList rankingList) {
        rankingList
            .getAthleteResultList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResult(), o1.getResult())
                        //Resultat Final
                        .compare(checkIfNull(o2.getResultsList().get(2).getResult()), checkIfNull(o1.getResultsList().get(2).getResult()))
                        //Resultat Runde 2
                        .compare(checkIfNull(o2.getResultsList().get(1).getResult()), checkIfNull(o1.getResultsList().get(1).getResult()))
                        //Resultat Runde 1
                        .compare(checkIfNull(o2.getResultsList().get(0).getResult()), checkIfNull(o1.getResultsList().get(0).getResult()))
                        //Resultat Final Passe 2
                        .compare(
                            checkIfNull(o2.getResultsList().get(2).getSerie2().getResult()),
                            checkIfNull(o1.getResultsList().get(2).getSerie2().getResult())
                        )
                        //Resultat Final Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(2).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(2).getSerie1().getResult())
                        )
                        //Resultat Runde 2 Passe 2
                        .compare(
                            checkIfNull(o2.getResultsList().get(1).getSerie2().getResult()),
                            checkIfNull(o1.getResultsList().get(1).getSerie2().getResult())
                        )
                        //Resultat Runde 2 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(1).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(1).getSerie1().getResult())
                        )
                        //Resultat Runde 1 Passe 2
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie2().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie2().getResult())
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            checkIfNull(o2.getResultsList().get(0).getSerie1().getResult()),
                            checkIfNull(o1.getResultsList().get(0).getSerie1().getResult())
                        )
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
                        //Schussfolge von hinten
                        .compare(o2.getResultsList().get(2).getSerie2().getp10(), o1.getResultsList().get(2).getSerie2().getp10())
                        .compare(o2.getResultsList().get(2).getSerie2().getp9(), o1.getResultsList().get(2).getSerie2().getp9())
                        .compare(o2.getResultsList().get(2).getSerie2().getp8(), o1.getResultsList().get(2).getSerie2().getp8())
                        .compare(o2.getResultsList().get(2).getSerie2().getp7(), o1.getResultsList().get(2).getSerie2().getp7())
                        .compare(o2.getResultsList().get(2).getSerie2().getp6(), o1.getResultsList().get(2).getSerie2().getp6())
                        .compare(o2.getResultsList().get(2).getSerie2().getp5(), o1.getResultsList().get(2).getSerie2().getp5())
                        .compare(o2.getResultsList().get(2).getSerie2().getp4(), o1.getResultsList().get(2).getSerie2().getp4())
                        .compare(o2.getResultsList().get(2).getSerie2().getp3(), o1.getResultsList().get(2).getSerie2().getp3())
                        .compare(o2.getResultsList().get(2).getSerie2().getp2(), o1.getResultsList().get(2).getSerie2().getp2())
                        .compare(o2.getResultsList().get(2).getSerie2().getp1(), o1.getResultsList().get(2).getSerie2().getp1())
                        .compare(o2.getResultsList().get(1).getSerie2().getp10(), o1.getResultsList().get(1).getSerie2().getp10())
                        .compare(o2.getResultsList().get(1).getSerie2().getp9(), o1.getResultsList().get(1).getSerie2().getp9())
                        .compare(o2.getResultsList().get(1).getSerie2().getp8(), o1.getResultsList().get(1).getSerie2().getp8())
                        .compare(o2.getResultsList().get(1).getSerie2().getp7(), o1.getResultsList().get(1).getSerie2().getp7())
                        .compare(o2.getResultsList().get(1).getSerie2().getp6(), o1.getResultsList().get(1).getSerie2().getp6())
                        .compare(o2.getResultsList().get(1).getSerie2().getp5(), o1.getResultsList().get(1).getSerie2().getp5())
                        .compare(o2.getResultsList().get(1).getSerie2().getp4(), o1.getResultsList().get(1).getSerie2().getp4())
                        .compare(o2.getResultsList().get(1).getSerie2().getp3(), o1.getResultsList().get(1).getSerie2().getp3())
                        .compare(o2.getResultsList().get(1).getSerie2().getp2(), o1.getResultsList().get(1).getSerie2().getp2())
                        .compare(o2.getResultsList().get(1).getSerie2().getp1(), o1.getResultsList().get(1).getSerie2().getp1())
                        .compare(o2.getResultsList().get(0).getSerie2().getp10(), o1.getResultsList().get(0).getSerie2().getp10())
                        .compare(o2.getResultsList().get(0).getSerie2().getp9(), o1.getResultsList().get(0).getSerie2().getp9())
                        .compare(o2.getResultsList().get(0).getSerie2().getp8(), o1.getResultsList().get(0).getSerie2().getp8())
                        .compare(o2.getResultsList().get(0).getSerie2().getp7(), o1.getResultsList().get(0).getSerie2().getp7())
                        .compare(o2.getResultsList().get(0).getSerie2().getp6(), o1.getResultsList().get(0).getSerie2().getp6())
                        .compare(o2.getResultsList().get(0).getSerie2().getp5(), o1.getResultsList().get(0).getSerie2().getp5())
                        .compare(o2.getResultsList().get(0).getSerie2().getp4(), o1.getResultsList().get(0).getSerie2().getp4())
                        .compare(o2.getResultsList().get(0).getSerie2().getp3(), o1.getResultsList().get(0).getSerie2().getp3())
                        .compare(o2.getResultsList().get(0).getSerie2().getp2(), o1.getResultsList().get(0).getSerie2().getp2())
                        .compare(o2.getResultsList().get(0).getSerie2().getp1(), o1.getResultsList().get(0).getSerie2().getp1())
                        .compare(o2.getResultsList().get(2).getSerie1().getp10(), o1.getResultsList().get(2).getSerie1().getp10())
                        .compare(o2.getResultsList().get(2).getSerie1().getp9(), o1.getResultsList().get(2).getSerie1().getp9())
                        .compare(o2.getResultsList().get(2).getSerie1().getp8(), o1.getResultsList().get(2).getSerie1().getp8())
                        .compare(o2.getResultsList().get(2).getSerie1().getp7(), o1.getResultsList().get(2).getSerie1().getp7())
                        .compare(o2.getResultsList().get(2).getSerie1().getp6(), o1.getResultsList().get(2).getSerie1().getp6())
                        .compare(o2.getResultsList().get(2).getSerie1().getp5(), o1.getResultsList().get(2).getSerie1().getp5())
                        .compare(o2.getResultsList().get(2).getSerie1().getp4(), o1.getResultsList().get(2).getSerie1().getp4())
                        .compare(o2.getResultsList().get(2).getSerie1().getp3(), o1.getResultsList().get(2).getSerie1().getp3())
                        .compare(o2.getResultsList().get(2).getSerie1().getp2(), o1.getResultsList().get(2).getSerie1().getp2())
                        .compare(o2.getResultsList().get(2).getSerie1().getp1(), o1.getResultsList().get(2).getSerie1().getp1())
                        .compare(o2.getResultsList().get(1).getSerie1().getp10(), o1.getResultsList().get(1).getSerie1().getp10())
                        .compare(o2.getResultsList().get(1).getSerie1().getp9(), o1.getResultsList().get(1).getSerie1().getp9())
                        .compare(o2.getResultsList().get(1).getSerie1().getp8(), o1.getResultsList().get(1).getSerie1().getp8())
                        .compare(o2.getResultsList().get(1).getSerie1().getp7(), o1.getResultsList().get(1).getSerie1().getp7())
                        .compare(o2.getResultsList().get(1).getSerie1().getp6(), o1.getResultsList().get(1).getSerie1().getp6())
                        .compare(o2.getResultsList().get(1).getSerie1().getp5(), o1.getResultsList().get(1).getSerie1().getp5())
                        .compare(o2.getResultsList().get(1).getSerie1().getp4(), o1.getResultsList().get(1).getSerie1().getp4())
                        .compare(o2.getResultsList().get(1).getSerie1().getp3(), o1.getResultsList().get(1).getSerie1().getp3())
                        .compare(o2.getResultsList().get(1).getSerie1().getp2(), o1.getResultsList().get(1).getSerie1().getp2())
                        .compare(o2.getResultsList().get(1).getSerie1().getp1(), o1.getResultsList().get(1).getSerie1().getp1())
                        .compare(o2.getResultsList().get(0).getSerie1().getp10(), o1.getResultsList().get(0).getSerie1().getp10())
                        .compare(o2.getResultsList().get(0).getSerie1().getp9(), o1.getResultsList().get(0).getSerie1().getp9())
                        .compare(o2.getResultsList().get(0).getSerie1().getp8(), o1.getResultsList().get(0).getSerie1().getp8())
                        .compare(o2.getResultsList().get(0).getSerie1().getp7(), o1.getResultsList().get(0).getSerie1().getp7())
                        .compare(o2.getResultsList().get(0).getSerie1().getp6(), o1.getResultsList().get(0).getSerie1().getp6())
                        .compare(o2.getResultsList().get(0).getSerie1().getp5(), o1.getResultsList().get(0).getSerie1().getp5())
                        .compare(o2.getResultsList().get(0).getSerie1().getp4(), o1.getResultsList().get(0).getSerie1().getp4())
                        .compare(o2.getResultsList().get(0).getSerie1().getp3(), o1.getResultsList().get(0).getSerie1().getp3())
                        .compare(o2.getResultsList().get(0).getSerie1().getp2(), o1.getResultsList().get(0).getSerie1().getp2())
                        .compare(o2.getResultsList().get(0).getSerie1().getp1(), o1.getResultsList().get(0).getSerie1().getp1())
                        .result()
            );

        return rankingList;
    }

    @Override
    public Integer getDeepshotsForSerie(Series series, Integer number) {
        Integer returnCount = 0;
        if (series.getp1().intValue() == number) {
            returnCount++;
        }
        if (series.getp2().intValue() == number) {
            returnCount++;
        }
        if (series.getp3().intValue() == number) {
            returnCount++;
        }
        if (series.getp4().intValue() == number) {
            returnCount++;
        }
        if (series.getp5().intValue() == number) {
            returnCount++;
        }
        if (series.getp6().intValue() == number) {
            returnCount++;
        }
        if (series.getp7().intValue() == number) {
            returnCount++;
        }
        if (series.getp8().intValue() == number) {
            returnCount++;
        }
        if (series.getp9().intValue() == number) {
            returnCount++;
        }
        if (series.getp10().intValue() == number) {
            returnCount++;
        }
        return returnCount;
    }

    private Integer checkIfNull(Series serie) {
        return serie == null ? 0 : serie.getResult();
    }

    private Integer checkIfNull(Integer number) {
        return number == null ? 0 : number;
    }
}
