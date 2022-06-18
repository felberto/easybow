package ch.felberto.service.impl;

import ch.felberto.domain.*;
import ch.felberto.repository.RangierungRepository;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.service.PassenService;
import ch.felberto.service.RanglisteService;
import ch.felberto.service.ResultateService;
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
 * Service Implementation for managing {@link RanglisteService}.
 */
@Service
@Transactional
public class RanglisteServiceImpl implements RanglisteService {

    private final Logger log = LoggerFactory.getLogger(RanglisteServiceImpl.class);

    private final ResultateRepository resultateRepository;

    private final WettkampfRepository wettkampfRepository;

    private final RangierungRepository rangierungRepository;

    private final ResultateService resultateService;

    private final PassenService passenService;

    public RanglisteServiceImpl(
        ResultateRepository resultateRepository,
        WettkampfRepository wettkampfRepository,
        RangierungRepository rangierungRepository,
        ResultateService resultateService,
        PassenService passenService
    ) {
        this.resultateRepository = resultateRepository;
        this.wettkampfRepository = wettkampfRepository;
        this.rangierungRepository = rangierungRepository;
        this.resultateService = resultateService;
        this.passenService = passenService;
    }

    @Override
    public Rangliste createFinal(Long wettkampfId, Integer type) {
        log.info("createFinal for wettkampfId: {} and type: {}", wettkampfId, type);
        resultateService.deleteByWettkampf_IdAndRunde(wettkampfId, type);
        Rangliste rangliste = generateRangliste(wettkampfId, 100);
        List<SchuetzeResultat> tempFinalList;
        if (rangliste.getSchuetzeResultatList().size() >= rangliste.getWettkampf().getAnzahlFinalteilnehmer()) {
            tempFinalList = rangliste.getSchuetzeResultatList().subList(0, rangliste.getWettkampf().getAnzahlFinalteilnehmer());
        } else {
            tempFinalList = rangliste.getSchuetzeResultatList();
        }
        tempFinalList.forEach(
            schuetzeResultat -> {
                Resultate resultate = new Resultate();
                resultate.setWettkampf(wettkampfRepository.getOne(wettkampfId));
                resultate.setSchuetze(schuetzeResultat.getSchuetze());
                resultate.setRunde(type);

                Passen tempP1 = new Passen();
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
                tempP1.setResultat(0);
                Passen passe1 = passenService.save(tempP1);
                resultate.setPasse1(passe1);

                if (wettkampfRepository.getOne(wettkampfId).getAnzahlPassenFinal() == 2) {
                    Passen tempP2 = new Passen();
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
                    tempP2.setResultat(0);
                    Passen passe2 = passenService.save(tempP2);
                    resultate.setPasse2(passe2);
                }

                resultateService.save(resultate);
            }
        );
        return rangliste;
    }

    @Override
    public Rangliste generateRangliste(Long wettkampfId, Integer type) {
        log.info("generateRangliste for wettkampfId: {} and type: {}", wettkampfId, type);
        Wettkampf wettkampf = wettkampfRepository.getOne(wettkampfId);
        Rangliste rangliste = getAllSchuetzesByWettkampf(wettkampf, type);
        rangliste.setType(type);

        switch (type) {
            case 1:
                if (wettkampf.getAnzahlPassen() == 1) {
                    sortRanglisteRunde1Passe1(rangliste);
                } else if (wettkampf.getAnzahlPassen() == 2) {
                    sortRanglisteRunde1Passe2(rangliste, 1);
                }
                break;
            case 2:
                if (wettkampf.getAnzahlPassen() == 1) {
                    sortRanglisteRunde1Passe1(rangliste);
                } else if (wettkampf.getAnzahlPassen() == 2) {
                    sortRanglisteRunde1Passe2(rangliste, 2);
                }
                break;
            case 100:
                if (wettkampf.getAnzahlPassen() == 1) {
                    sortRanglisteRunde2Passe1(rangliste);
                } else if (wettkampf.getAnzahlPassen() == 2) {
                    sortRanglisteRunde2Passe2(rangliste);
                }
                break;
            case 99:
                if (wettkampf.getAnzahlPassen() == 1) {
                    sortRanglisteRunde1Passe1(rangliste);
                } else if (wettkampf.getAnzahlPassen() == 2) {
                    sortRanglisteRunde1Passe2(rangliste, 99);
                }
                break;
            case 101:
                if (wettkampf.getAnzahlPassen() == 1) {
                    sortRanglisteRunde2FinalPasse1(rangliste);
                } else if (wettkampf.getAnzahlPassen() == 2) {
                    sortRanglisteRunde2FinalPasse2(rangliste);
                }
                break;
            default:
                break;
        }
        log.info("generated rangliste: {}", rangliste);
        return rangliste;
    }

    @Override
    public Rangliste getAllSchuetzesByWettkampf(Wettkampf wettkampf, Integer type) {
        //TODO Resultat 11 stimmt noch nicht

        Rangliste rangliste = new Rangliste();
        rangliste.setWettkampf(wettkampf);

        List<Resultate> resultate = new ArrayList<>();
        switch (type) {
            case 1:
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 1));
                break;
            case 2:
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 2));
                break;
            case 100:
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 1));
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 2));
                break;
            case 99:
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 99));
                break;
            case 101:
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 1));
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 2));
                resultate.addAll(resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), 99));
                break;
            default:
                break;
        }
        List<Schuetze> schuetzeList = new ArrayList<>();
        resultate
            .stream()
            .filter(resultat -> !schuetzeList.contains(resultat.getSchuetze()))
            .forEach(resultat -> schuetzeList.add(resultat.getSchuetze()));
        List<SchuetzeResultat> schuetzeResultatList = new ArrayList<>();
        schuetzeList.forEach(
            schuetze -> {
                SchuetzeResultat schuetzeResultat = new SchuetzeResultat();
                schuetzeResultat.setSchuetze(schuetze);
                List<Resultate> resultateSchuetze = resultate
                    .stream()
                    .filter(resultat -> resultat.getSchuetze() == schuetze)
                    .sorted(Comparator.comparing(Resultate::getRunde))
                    .collect(Collectors.toList());
                schuetzeResultat.setResultateList(resultateSchuetze);
                schuetzeResultat.setResultat(resultateSchuetze.stream().mapToInt(Resultate::getResultat).sum());
                schuetzeResultat.setT0(0);
                schuetzeResultat.setT1(0);
                schuetzeResultat.setT2(0);
                schuetzeResultat.setT3(0);
                schuetzeResultat.setT4(0);
                schuetzeResultat.setT5(0);
                schuetzeResultat.setT6(0);
                schuetzeResultat.setT7(0);
                schuetzeResultat.setT8(0);
                schuetzeResultat.setT9(0);
                schuetzeResultat.setT10(0);
                schuetzeResultat.setT11(0);
                resultateSchuetze.forEach(
                    result -> {
                        if (result.getRunde() == 1) {
                            if (result.getWettkampf().getAnzahlPassen() == 1) {
                                schuetzeResultat.setResultatRunde1(result.getResultat());
                                schuetzeResultat.setResultatRunde1Passe1(result.getPasse1().getResultat());
                            } else if (result.getWettkampf().getAnzahlPassen() == 2) {
                                schuetzeResultat.setResultatRunde1(result.getResultat());
                                schuetzeResultat.setResultatRunde1Passe1(result.getPasse1().getResultat());
                                schuetzeResultat.setResultatRunde1Passe2(result.getPasse2().getResultat());
                            }
                        } else if (result.getRunde() == 2) {
                            if (result.getWettkampf().getAnzahlPassen() == 1) {
                                schuetzeResultat.setResultatRunde2(result.getResultat());
                                schuetzeResultat.setResultatRunde2Passe1(result.getPasse1().getResultat());
                            } else if (result.getWettkampf().getAnzahlPassen() == 2) {
                                schuetzeResultat.setResultatRunde2(result.getResultat());
                                schuetzeResultat.setResultatRunde2Passe1(result.getPasse1().getResultat());
                                schuetzeResultat.setResultatRunde2Passe2(result.getPasse2().getResultat());
                            }
                        } else if (result.getRunde() == 99) {
                            if (result.getWettkampf().getAnzahlPassen() == 1) {
                                schuetzeResultat.setResultatFinal(result.getResultat());
                                schuetzeResultat.setResultatFinalPasse1(result.getPasse1().getResultat());
                            } else if (result.getWettkampf().getAnzahlPassen() == 2) {
                                schuetzeResultat.setResultatFinal(result.getResultat());
                                schuetzeResultat.setResultatFinalPasse1(result.getPasse1().getResultat());
                                schuetzeResultat.setResultatFinalPasse2(result.getPasse2().getResultat());
                            }
                        }
                        if (result.getWettkampf().getAnzahlPassen() == 1) {
                            schuetzeResultat.setT0(schuetzeResultat.getT0() + getTiefschuesseForPasse(result.getPasse1(), 0));
                            schuetzeResultat.setT1(schuetzeResultat.getT1() + getTiefschuesseForPasse(result.getPasse1(), 1));
                            schuetzeResultat.setT2(schuetzeResultat.getT2() + getTiefschuesseForPasse(result.getPasse1(), 2));
                            schuetzeResultat.setT3(schuetzeResultat.getT3() + getTiefschuesseForPasse(result.getPasse1(), 3));
                            schuetzeResultat.setT4(schuetzeResultat.getT4() + getTiefschuesseForPasse(result.getPasse1(), 4));
                            schuetzeResultat.setT5(schuetzeResultat.getT5() + getTiefschuesseForPasse(result.getPasse1(), 5));
                            schuetzeResultat.setT6(schuetzeResultat.getT6() + getTiefschuesseForPasse(result.getPasse1(), 6));
                            schuetzeResultat.setT7(schuetzeResultat.getT7() + getTiefschuesseForPasse(result.getPasse1(), 7));
                            schuetzeResultat.setT8(schuetzeResultat.getT8() + getTiefschuesseForPasse(result.getPasse1(), 8));
                            schuetzeResultat.setT9(schuetzeResultat.getT9() + getTiefschuesseForPasse(result.getPasse1(), 9));
                            schuetzeResultat.setT10(
                                schuetzeResultat.getT10() +
                                getTiefschuesseForPasse(result.getPasse1(), 10) +
                                schuetzeResultat.getT11() +
                                getTiefschuesseForPasse(result.getPasse1(), 11)
                            );
                            schuetzeResultat.setT11(schuetzeResultat.getT11() + getTiefschuesseForPasse(result.getPasse1(), 11));
                        } else if (result.getWettkampf().getAnzahlPassen() == 2) {
                            schuetzeResultat.setT0(
                                schuetzeResultat.getT0() +
                                getTiefschuesseForPasse(result.getPasse1(), 0) +
                                getTiefschuesseForPasse(result.getPasse2(), 0)
                            );
                            schuetzeResultat.setT1(
                                schuetzeResultat.getT1() +
                                getTiefschuesseForPasse(result.getPasse1(), 1) +
                                getTiefschuesseForPasse(result.getPasse2(), 1)
                            );
                            schuetzeResultat.setT2(
                                schuetzeResultat.getT2() +
                                getTiefschuesseForPasse(result.getPasse1(), 2) +
                                getTiefschuesseForPasse(result.getPasse2(), 2)
                            );
                            schuetzeResultat.setT3(
                                schuetzeResultat.getT3() +
                                getTiefschuesseForPasse(result.getPasse1(), 3) +
                                getTiefschuesseForPasse(result.getPasse2(), 3)
                            );
                            schuetzeResultat.setT4(
                                schuetzeResultat.getT4() +
                                getTiefschuesseForPasse(result.getPasse1(), 4) +
                                getTiefschuesseForPasse(result.getPasse2(), 4)
                            );
                            schuetzeResultat.setT5(
                                schuetzeResultat.getT5() +
                                getTiefschuesseForPasse(result.getPasse1(), 5) +
                                getTiefschuesseForPasse(result.getPasse2(), 5)
                            );
                            schuetzeResultat.setT6(
                                schuetzeResultat.getT6() +
                                getTiefschuesseForPasse(result.getPasse1(), 6) +
                                getTiefschuesseForPasse(result.getPasse2(), 6)
                            );
                            schuetzeResultat.setT7(
                                schuetzeResultat.getT7() +
                                getTiefschuesseForPasse(result.getPasse1(), 7) +
                                getTiefschuesseForPasse(result.getPasse2(), 7)
                            );
                            schuetzeResultat.setT8(
                                schuetzeResultat.getT8() +
                                getTiefschuesseForPasse(result.getPasse1(), 8) +
                                getTiefschuesseForPasse(result.getPasse2(), 8)
                            );
                            schuetzeResultat.setT9(
                                schuetzeResultat.getT9() +
                                getTiefschuesseForPasse(result.getPasse1(), 9) +
                                getTiefschuesseForPasse(result.getPasse2(), 9)
                            );
                            schuetzeResultat.setT10(
                                schuetzeResultat.getT10() +
                                getTiefschuesseForPasse(result.getPasse1(), 10) +
                                getTiefschuesseForPasse(result.getPasse2(), 10) +
                                schuetzeResultat.getT11() +
                                getTiefschuesseForPasse(result.getPasse1(), 11) +
                                getTiefschuesseForPasse(result.getPasse2(), 11)
                            );
                            schuetzeResultat.setT11(
                                schuetzeResultat.getT11() +
                                getTiefschuesseForPasse(result.getPasse1(), 11) +
                                getTiefschuesseForPasse(result.getPasse2(), 11)
                            );
                        }
                    }
                );
                schuetzeResultatList.add(schuetzeResultat);
            }
        );

        rangliste.setSchuetzeResultatList(schuetzeResultatList);
        return rangliste;
    }

    @Override
    public Rangliste sortRanglisteRunde1Passe1(Rangliste rangliste) {
        //evtl alles modular zusammenbauen
        /*
         * 1. höhere letzte, dann zweitletzte Serie, etc. zu 10 Schuss
         * 2. die Tiefschüsse
         * 3. die höhere Anzahl Mouchen
         * 4. die bessere Schussfolge von hinten, letzter, zweitletzter Schuss usw. unter Berücksichtigung der Mouchen
         * */
        rangliste
            .getSchuetzeResultatList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResultat(), o1.getResultat())
                        //Resultat Passe 1
                        .compare(
                            o2.getResultateList().get(0).getPasse1().getResultat(),
                            o1.getResultateList().get(0).getPasse1().getResultat()
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
                        .compare(o2.getResultateList().get(0).getPasse1().getp10(), o1.getResultateList().get(0).getPasse1().getp10())
                        .compare(o2.getResultateList().get(0).getPasse1().getp9(), o1.getResultateList().get(0).getPasse1().getp9())
                        .compare(o2.getResultateList().get(0).getPasse1().getp8(), o1.getResultateList().get(0).getPasse1().getp8())
                        .compare(o2.getResultateList().get(0).getPasse1().getp7(), o1.getResultateList().get(0).getPasse1().getp7())
                        .compare(o2.getResultateList().get(0).getPasse1().getp6(), o1.getResultateList().get(0).getPasse1().getp6())
                        .compare(o2.getResultateList().get(0).getPasse1().getp5(), o1.getResultateList().get(0).getPasse1().getp5())
                        .compare(o2.getResultateList().get(0).getPasse1().getp4(), o1.getResultateList().get(0).getPasse1().getp4())
                        .compare(o2.getResultateList().get(0).getPasse1().getp3(), o1.getResultateList().get(0).getPasse1().getp3())
                        .compare(o2.getResultateList().get(0).getPasse1().getp2(), o1.getResultateList().get(0).getPasse1().getp2())
                        .compare(o2.getResultateList().get(0).getPasse1().getp1(), o1.getResultateList().get(0).getPasse1().getp1())
                        .result()
            );

        return rangliste;
    }

    @Override
    public Rangliste sortRanglisteRunde1Passe2(Rangliste rangliste, Integer runde) {
        //evtl alles modular zusammenbauen
        /*
         * 1. höhere letzte, dann zweitletzte Serie, etc. zu 10 Schuss
         * 2. die Tiefschüsse
         * 3. die höhere Anzahl Mouchen
         * 4. die bessere Schussfolge von hinten, letzter, zweitletzter Schuss usw. unter Berücksichtigung der Mouchen
         * */
        rangliste
            .getSchuetzeResultatList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResultat(), o1.getResultat())
                        //Resultat Passe 2
                        .compare(
                            o2.getResultateList().get(0).getPasse2().getResultat(),
                            o1.getResultateList().get(0).getPasse2().getResultat()
                        )
                        //Resultat Passe 1
                        .compare(
                            o2.getResultateList().get(0).getPasse1().getResultat(),
                            o1.getResultateList().get(0).getPasse1().getResultat()
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
                        .compare(o2.getResultateList().get(0).getPasse2().getp10(), o1.getResultateList().get(0).getPasse2().getp10())
                        .compare(o2.getResultateList().get(0).getPasse2().getp9(), o1.getResultateList().get(0).getPasse2().getp9())
                        .compare(o2.getResultateList().get(0).getPasse2().getp8(), o1.getResultateList().get(0).getPasse2().getp8())
                        .compare(o2.getResultateList().get(0).getPasse2().getp7(), o1.getResultateList().get(0).getPasse2().getp7())
                        .compare(o2.getResultateList().get(0).getPasse2().getp6(), o1.getResultateList().get(0).getPasse2().getp6())
                        .compare(o2.getResultateList().get(0).getPasse2().getp5(), o1.getResultateList().get(0).getPasse2().getp5())
                        .compare(o2.getResultateList().get(0).getPasse2().getp4(), o1.getResultateList().get(0).getPasse2().getp4())
                        .compare(o2.getResultateList().get(0).getPasse2().getp3(), o1.getResultateList().get(0).getPasse2().getp3())
                        .compare(o2.getResultateList().get(0).getPasse2().getp2(), o1.getResultateList().get(0).getPasse2().getp2())
                        .compare(o2.getResultateList().get(0).getPasse2().getp1(), o1.getResultateList().get(0).getPasse2().getp1())
                        .compare(o2.getResultateList().get(0).getPasse1().getp10(), o1.getResultateList().get(0).getPasse1().getp10())
                        .compare(o2.getResultateList().get(0).getPasse1().getp9(), o1.getResultateList().get(0).getPasse1().getp9())
                        .compare(o2.getResultateList().get(0).getPasse1().getp8(), o1.getResultateList().get(0).getPasse1().getp8())
                        .compare(o2.getResultateList().get(0).getPasse1().getp7(), o1.getResultateList().get(0).getPasse1().getp7())
                        .compare(o2.getResultateList().get(0).getPasse1().getp6(), o1.getResultateList().get(0).getPasse1().getp6())
                        .compare(o2.getResultateList().get(0).getPasse1().getp5(), o1.getResultateList().get(0).getPasse1().getp5())
                        .compare(o2.getResultateList().get(0).getPasse1().getp4(), o1.getResultateList().get(0).getPasse1().getp4())
                        .compare(o2.getResultateList().get(0).getPasse1().getp3(), o1.getResultateList().get(0).getPasse1().getp3())
                        .compare(o2.getResultateList().get(0).getPasse1().getp2(), o1.getResultateList().get(0).getPasse1().getp2())
                        .compare(o2.getResultateList().get(0).getPasse1().getp1(), o1.getResultateList().get(0).getPasse1().getp1())
                        .result()
            );

        return rangliste;
    }

    @Override
    public Rangliste sortRanglisteRunde2Passe1(Rangliste rangliste) {
        //evtl alles modular zusammenbauen
        /*
         * 1. höhere letzte, dann zweitletzte Serie, etc. zu 10 Schuss
         * 2. die Tiefschüsse
         * 3. die höhere Anzahl Mouchen
         * 4. die bessere Schussfolge von hinten, letzter, zweitletzter Schuss usw. unter Berücksichtigung der Mouchen
         * */
        //TODO überprüfen ob get(0) 1 runde ist und get(1) 2 runde
        rangliste
            .getSchuetzeResultatList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResultat(), o1.getResultat())
                        //Resultat Runde 2
                        .compare(o2.getResultateList().get(1).getResultat(), o1.getResultateList().get(1).getResultat())
                        //Resultat Runde 1
                        .compare(o2.getResultateList().get(0).getResultat(), o1.getResultateList().get(0).getResultat())
                        //Resultat Runde 2 Passe 1
                        .compare(
                            o2.getResultateList().get(1).getPasse1().getResultat(),
                            o1.getResultateList().get(1).getPasse1().getResultat()
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            o2.getResultateList().get(0).getPasse1().getResultat(),
                            o1.getResultateList().get(0).getPasse1().getResultat()
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
                        .compare(o2.getResultateList().get(1).getPasse1().getp10(), o1.getResultateList().get(1).getPasse1().getp10())
                        .compare(o2.getResultateList().get(1).getPasse1().getp9(), o1.getResultateList().get(1).getPasse1().getp9())
                        .compare(o2.getResultateList().get(1).getPasse1().getp8(), o1.getResultateList().get(1).getPasse1().getp8())
                        .compare(o2.getResultateList().get(1).getPasse1().getp7(), o1.getResultateList().get(1).getPasse1().getp7())
                        .compare(o2.getResultateList().get(1).getPasse1().getp6(), o1.getResultateList().get(1).getPasse1().getp6())
                        .compare(o2.getResultateList().get(1).getPasse1().getp5(), o1.getResultateList().get(1).getPasse1().getp5())
                        .compare(o2.getResultateList().get(1).getPasse1().getp4(), o1.getResultateList().get(1).getPasse1().getp4())
                        .compare(o2.getResultateList().get(1).getPasse1().getp3(), o1.getResultateList().get(1).getPasse1().getp3())
                        .compare(o2.getResultateList().get(1).getPasse1().getp2(), o1.getResultateList().get(1).getPasse1().getp2())
                        .compare(o2.getResultateList().get(1).getPasse1().getp1(), o1.getResultateList().get(1).getPasse1().getp1())
                        .compare(o2.getResultateList().get(0).getPasse1().getp10(), o1.getResultateList().get(0).getPasse1().getp10())
                        .compare(o2.getResultateList().get(0).getPasse1().getp9(), o1.getResultateList().get(0).getPasse1().getp9())
                        .compare(o2.getResultateList().get(0).getPasse1().getp8(), o1.getResultateList().get(0).getPasse1().getp8())
                        .compare(o2.getResultateList().get(0).getPasse1().getp7(), o1.getResultateList().get(0).getPasse1().getp7())
                        .compare(o2.getResultateList().get(0).getPasse1().getp6(), o1.getResultateList().get(0).getPasse1().getp6())
                        .compare(o2.getResultateList().get(0).getPasse1().getp5(), o1.getResultateList().get(0).getPasse1().getp5())
                        .compare(o2.getResultateList().get(0).getPasse1().getp4(), o1.getResultateList().get(0).getPasse1().getp4())
                        .compare(o2.getResultateList().get(0).getPasse1().getp3(), o1.getResultateList().get(0).getPasse1().getp3())
                        .compare(o2.getResultateList().get(0).getPasse1().getp2(), o1.getResultateList().get(0).getPasse1().getp2())
                        .compare(o2.getResultateList().get(0).getPasse1().getp1(), o1.getResultateList().get(0).getPasse1().getp1())
                        .result()
            );

        return rangliste;
    }

    @Override
    public Rangliste sortRanglisteRunde2Passe2(Rangliste rangliste) {
        rangliste
            .getSchuetzeResultatList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResultat(), o1.getResultat())
                        //Resultat Runde 2
                        .compare(o2.getResultateList().get(1).getResultat(), o1.getResultateList().get(1).getResultat())
                        //Resultat Runde 1
                        .compare(o2.getResultateList().get(0).getResultat(), o1.getResultateList().get(0).getResultat())
                        //Resultat Runde 2 Passe 2
                        .compare(
                            o2.getResultateList().get(1).getPasse2().getResultat(),
                            o1.getResultateList().get(1).getPasse2().getResultat()
                        )
                        //Resultat Runde 2 Passe 1
                        .compare(
                            o2.getResultateList().get(1).getPasse1().getResultat(),
                            o1.getResultateList().get(1).getPasse1().getResultat()
                        )
                        //Resultat Runde 1 Passe 2
                        .compare(
                            o2.getResultateList().get(0).getPasse2().getResultat(),
                            o1.getResultateList().get(0).getPasse2().getResultat()
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            o2.getResultateList().get(0).getPasse1().getResultat(),
                            o1.getResultateList().get(0).getPasse1().getResultat()
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
                        .compare(o2.getResultateList().get(1).getPasse2().getp10(), o1.getResultateList().get(1).getPasse2().getp10())
                        .compare(o2.getResultateList().get(1).getPasse2().getp9(), o1.getResultateList().get(1).getPasse2().getp9())
                        .compare(o2.getResultateList().get(1).getPasse2().getp8(), o1.getResultateList().get(1).getPasse2().getp8())
                        .compare(o2.getResultateList().get(1).getPasse2().getp7(), o1.getResultateList().get(1).getPasse2().getp7())
                        .compare(o2.getResultateList().get(1).getPasse2().getp6(), o1.getResultateList().get(1).getPasse2().getp6())
                        .compare(o2.getResultateList().get(1).getPasse2().getp5(), o1.getResultateList().get(1).getPasse2().getp5())
                        .compare(o2.getResultateList().get(1).getPasse2().getp4(), o1.getResultateList().get(1).getPasse2().getp4())
                        .compare(o2.getResultateList().get(1).getPasse2().getp3(), o1.getResultateList().get(1).getPasse2().getp3())
                        .compare(o2.getResultateList().get(1).getPasse2().getp2(), o1.getResultateList().get(1).getPasse2().getp2())
                        .compare(o2.getResultateList().get(1).getPasse2().getp1(), o1.getResultateList().get(1).getPasse2().getp1())
                        .compare(o2.getResultateList().get(0).getPasse2().getp10(), o1.getResultateList().get(0).getPasse2().getp10())
                        .compare(o2.getResultateList().get(0).getPasse2().getp9(), o1.getResultateList().get(0).getPasse2().getp9())
                        .compare(o2.getResultateList().get(0).getPasse2().getp8(), o1.getResultateList().get(0).getPasse2().getp8())
                        .compare(o2.getResultateList().get(0).getPasse2().getp7(), o1.getResultateList().get(0).getPasse2().getp7())
                        .compare(o2.getResultateList().get(0).getPasse2().getp6(), o1.getResultateList().get(0).getPasse2().getp6())
                        .compare(o2.getResultateList().get(0).getPasse2().getp5(), o1.getResultateList().get(0).getPasse2().getp5())
                        .compare(o2.getResultateList().get(0).getPasse2().getp4(), o1.getResultateList().get(0).getPasse2().getp4())
                        .compare(o2.getResultateList().get(0).getPasse2().getp3(), o1.getResultateList().get(0).getPasse2().getp3())
                        .compare(o2.getResultateList().get(0).getPasse2().getp2(), o1.getResultateList().get(0).getPasse2().getp2())
                        .compare(o2.getResultateList().get(0).getPasse2().getp1(), o1.getResultateList().get(0).getPasse2().getp1())
                        .compare(o2.getResultateList().get(1).getPasse1().getp10(), o1.getResultateList().get(1).getPasse1().getp10())
                        .compare(o2.getResultateList().get(1).getPasse1().getp9(), o1.getResultateList().get(1).getPasse1().getp9())
                        .compare(o2.getResultateList().get(1).getPasse1().getp8(), o1.getResultateList().get(1).getPasse1().getp8())
                        .compare(o2.getResultateList().get(1).getPasse1().getp7(), o1.getResultateList().get(1).getPasse1().getp7())
                        .compare(o2.getResultateList().get(1).getPasse1().getp6(), o1.getResultateList().get(1).getPasse1().getp6())
                        .compare(o2.getResultateList().get(1).getPasse1().getp5(), o1.getResultateList().get(1).getPasse1().getp5())
                        .compare(o2.getResultateList().get(1).getPasse1().getp4(), o1.getResultateList().get(1).getPasse1().getp4())
                        .compare(o2.getResultateList().get(1).getPasse1().getp3(), o1.getResultateList().get(1).getPasse1().getp3())
                        .compare(o2.getResultateList().get(1).getPasse1().getp2(), o1.getResultateList().get(1).getPasse1().getp2())
                        .compare(o2.getResultateList().get(1).getPasse1().getp1(), o1.getResultateList().get(1).getPasse1().getp1())
                        .compare(o2.getResultateList().get(0).getPasse1().getp10(), o1.getResultateList().get(0).getPasse1().getp10())
                        .compare(o2.getResultateList().get(0).getPasse1().getp9(), o1.getResultateList().get(0).getPasse1().getp9())
                        .compare(o2.getResultateList().get(0).getPasse1().getp8(), o1.getResultateList().get(0).getPasse1().getp8())
                        .compare(o2.getResultateList().get(0).getPasse1().getp7(), o1.getResultateList().get(0).getPasse1().getp7())
                        .compare(o2.getResultateList().get(0).getPasse1().getp6(), o1.getResultateList().get(0).getPasse1().getp6())
                        .compare(o2.getResultateList().get(0).getPasse1().getp5(), o1.getResultateList().get(0).getPasse1().getp5())
                        .compare(o2.getResultateList().get(0).getPasse1().getp4(), o1.getResultateList().get(0).getPasse1().getp4())
                        .compare(o2.getResultateList().get(0).getPasse1().getp3(), o1.getResultateList().get(0).getPasse1().getp3())
                        .compare(o2.getResultateList().get(0).getPasse1().getp2(), o1.getResultateList().get(0).getPasse1().getp2())
                        .compare(o2.getResultateList().get(0).getPasse1().getp1(), o1.getResultateList().get(0).getPasse1().getp1())
                        .result()
            );

        return rangliste;
    }

    @Override
    public Rangliste sortRanglisteRunde2FinalPasse1(Rangliste rangliste) {
        rangliste
            .getSchuetzeResultatList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResultat(), o1.getResultat())
                        //Resultat Final
                        .compare(o2.getResultateList().get(2).getResultat(), o1.getResultateList().get(2).getResultat())
                        //Resultat Runde 2
                        .compare(o2.getResultateList().get(1).getResultat(), o1.getResultateList().get(1).getResultat())
                        //Resultat Runde 1
                        .compare(o2.getResultateList().get(0).getResultat(), o1.getResultateList().get(0).getResultat())
                        //Resultat Final Passe 1
                        .compare(
                            o2.getResultateList().get(2).getPasse1().getResultat(),
                            o1.getResultateList().get(2).getPasse1().getResultat()
                        )
                        //Resultat Runde 2 Passe 1
                        .compare(
                            o2.getResultateList().get(1).getPasse1().getResultat(),
                            o1.getResultateList().get(1).getPasse1().getResultat()
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            o2.getResultateList().get(0).getPasse1().getResultat(),
                            o1.getResultateList().get(0).getPasse1().getResultat()
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
                        .compare(o2.getResultateList().get(2).getPasse1().getp10(), o1.getResultateList().get(2).getPasse1().getp10())
                        .compare(o2.getResultateList().get(2).getPasse1().getp9(), o1.getResultateList().get(2).getPasse1().getp9())
                        .compare(o2.getResultateList().get(2).getPasse1().getp8(), o1.getResultateList().get(2).getPasse1().getp8())
                        .compare(o2.getResultateList().get(2).getPasse1().getp7(), o1.getResultateList().get(2).getPasse1().getp7())
                        .compare(o2.getResultateList().get(2).getPasse1().getp6(), o1.getResultateList().get(2).getPasse1().getp6())
                        .compare(o2.getResultateList().get(2).getPasse1().getp5(), o1.getResultateList().get(2).getPasse1().getp5())
                        .compare(o2.getResultateList().get(2).getPasse1().getp4(), o1.getResultateList().get(2).getPasse1().getp4())
                        .compare(o2.getResultateList().get(2).getPasse1().getp3(), o1.getResultateList().get(2).getPasse1().getp3())
                        .compare(o2.getResultateList().get(2).getPasse1().getp2(), o1.getResultateList().get(2).getPasse1().getp2())
                        .compare(o2.getResultateList().get(2).getPasse1().getp1(), o1.getResultateList().get(2).getPasse1().getp1())
                        .compare(o2.getResultateList().get(1).getPasse1().getp10(), o1.getResultateList().get(1).getPasse1().getp10())
                        .compare(o2.getResultateList().get(1).getPasse1().getp9(), o1.getResultateList().get(1).getPasse1().getp9())
                        .compare(o2.getResultateList().get(1).getPasse1().getp8(), o1.getResultateList().get(1).getPasse1().getp8())
                        .compare(o2.getResultateList().get(1).getPasse1().getp7(), o1.getResultateList().get(1).getPasse1().getp7())
                        .compare(o2.getResultateList().get(1).getPasse1().getp6(), o1.getResultateList().get(1).getPasse1().getp6())
                        .compare(o2.getResultateList().get(1).getPasse1().getp5(), o1.getResultateList().get(1).getPasse1().getp5())
                        .compare(o2.getResultateList().get(1).getPasse1().getp4(), o1.getResultateList().get(1).getPasse1().getp4())
                        .compare(o2.getResultateList().get(1).getPasse1().getp3(), o1.getResultateList().get(1).getPasse1().getp3())
                        .compare(o2.getResultateList().get(1).getPasse1().getp2(), o1.getResultateList().get(1).getPasse1().getp2())
                        .compare(o2.getResultateList().get(1).getPasse1().getp1(), o1.getResultateList().get(1).getPasse1().getp1())
                        .compare(o2.getResultateList().get(0).getPasse1().getp10(), o1.getResultateList().get(0).getPasse1().getp10())
                        .compare(o2.getResultateList().get(0).getPasse1().getp9(), o1.getResultateList().get(0).getPasse1().getp9())
                        .compare(o2.getResultateList().get(0).getPasse1().getp8(), o1.getResultateList().get(0).getPasse1().getp8())
                        .compare(o2.getResultateList().get(0).getPasse1().getp7(), o1.getResultateList().get(0).getPasse1().getp7())
                        .compare(o2.getResultateList().get(0).getPasse1().getp6(), o1.getResultateList().get(0).getPasse1().getp6())
                        .compare(o2.getResultateList().get(0).getPasse1().getp5(), o1.getResultateList().get(0).getPasse1().getp5())
                        .compare(o2.getResultateList().get(0).getPasse1().getp4(), o1.getResultateList().get(0).getPasse1().getp4())
                        .compare(o2.getResultateList().get(0).getPasse1().getp3(), o1.getResultateList().get(0).getPasse1().getp3())
                        .compare(o2.getResultateList().get(0).getPasse1().getp2(), o1.getResultateList().get(0).getPasse1().getp2())
                        .compare(o2.getResultateList().get(0).getPasse1().getp1(), o1.getResultateList().get(0).getPasse1().getp1())
                        .result()
            );

        return rangliste;
    }

    @Override
    public Rangliste sortRanglisteRunde2FinalPasse2(Rangliste rangliste) {
        rangliste
            .getSchuetzeResultatList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        //Resultat
                        .compare(o2.getResultat(), o1.getResultat())
                        //Resultat Final
                        .compare(o2.getResultateList().get(2).getResultat(), o1.getResultateList().get(2).getResultat())
                        //Resultat Runde 2
                        .compare(o2.getResultateList().get(1).getResultat(), o1.getResultateList().get(1).getResultat())
                        //Resultat Runde 1
                        .compare(o2.getResultateList().get(0).getResultat(), o1.getResultateList().get(0).getResultat())
                        //Resultat Final Passe 2
                        .compare(
                            o2.getResultateList().get(2).getPasse2().getResultat(),
                            o1.getResultateList().get(2).getPasse2().getResultat()
                        )
                        //Resultat Final Passe 1
                        .compare(
                            o2.getResultateList().get(2).getPasse1().getResultat(),
                            o1.getResultateList().get(2).getPasse1().getResultat()
                        )
                        //Resultat Runde 2 Passe 2
                        .compare(
                            o2.getResultateList().get(1).getPasse2().getResultat(),
                            o1.getResultateList().get(1).getPasse2().getResultat()
                        )
                        //Resultat Runde 2 Passe 1
                        .compare(
                            o2.getResultateList().get(1).getPasse1().getResultat(),
                            o1.getResultateList().get(1).getPasse1().getResultat()
                        )
                        //Resultat Runde 1 Passe 2
                        .compare(
                            o2.getResultateList().get(0).getPasse2().getResultat(),
                            o1.getResultateList().get(0).getPasse2().getResultat()
                        )
                        //Resultat Runde 1 Passe 1
                        .compare(
                            o2.getResultateList().get(0).getPasse1().getResultat(),
                            o1.getResultateList().get(0).getPasse1().getResultat()
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
                        .compare(o2.getResultateList().get(2).getPasse2().getp10(), o1.getResultateList().get(2).getPasse2().getp10())
                        .compare(o2.getResultateList().get(2).getPasse2().getp9(), o1.getResultateList().get(2).getPasse2().getp9())
                        .compare(o2.getResultateList().get(2).getPasse2().getp8(), o1.getResultateList().get(2).getPasse2().getp8())
                        .compare(o2.getResultateList().get(2).getPasse2().getp7(), o1.getResultateList().get(2).getPasse2().getp7())
                        .compare(o2.getResultateList().get(2).getPasse2().getp6(), o1.getResultateList().get(2).getPasse2().getp6())
                        .compare(o2.getResultateList().get(2).getPasse2().getp5(), o1.getResultateList().get(2).getPasse2().getp5())
                        .compare(o2.getResultateList().get(2).getPasse2().getp4(), o1.getResultateList().get(2).getPasse2().getp4())
                        .compare(o2.getResultateList().get(2).getPasse2().getp3(), o1.getResultateList().get(2).getPasse2().getp3())
                        .compare(o2.getResultateList().get(2).getPasse2().getp2(), o1.getResultateList().get(2).getPasse2().getp2())
                        .compare(o2.getResultateList().get(2).getPasse2().getp1(), o1.getResultateList().get(2).getPasse2().getp1())
                        .compare(o2.getResultateList().get(1).getPasse2().getp10(), o1.getResultateList().get(1).getPasse2().getp10())
                        .compare(o2.getResultateList().get(1).getPasse2().getp9(), o1.getResultateList().get(1).getPasse2().getp9())
                        .compare(o2.getResultateList().get(1).getPasse2().getp8(), o1.getResultateList().get(1).getPasse2().getp8())
                        .compare(o2.getResultateList().get(1).getPasse2().getp7(), o1.getResultateList().get(1).getPasse2().getp7())
                        .compare(o2.getResultateList().get(1).getPasse2().getp6(), o1.getResultateList().get(1).getPasse2().getp6())
                        .compare(o2.getResultateList().get(1).getPasse2().getp5(), o1.getResultateList().get(1).getPasse2().getp5())
                        .compare(o2.getResultateList().get(1).getPasse2().getp4(), o1.getResultateList().get(1).getPasse2().getp4())
                        .compare(o2.getResultateList().get(1).getPasse2().getp3(), o1.getResultateList().get(1).getPasse2().getp3())
                        .compare(o2.getResultateList().get(1).getPasse2().getp2(), o1.getResultateList().get(1).getPasse2().getp2())
                        .compare(o2.getResultateList().get(1).getPasse2().getp1(), o1.getResultateList().get(1).getPasse2().getp1())
                        .compare(o2.getResultateList().get(0).getPasse2().getp10(), o1.getResultateList().get(0).getPasse2().getp10())
                        .compare(o2.getResultateList().get(0).getPasse2().getp9(), o1.getResultateList().get(0).getPasse2().getp9())
                        .compare(o2.getResultateList().get(0).getPasse2().getp8(), o1.getResultateList().get(0).getPasse2().getp8())
                        .compare(o2.getResultateList().get(0).getPasse2().getp7(), o1.getResultateList().get(0).getPasse2().getp7())
                        .compare(o2.getResultateList().get(0).getPasse2().getp6(), o1.getResultateList().get(0).getPasse2().getp6())
                        .compare(o2.getResultateList().get(0).getPasse2().getp5(), o1.getResultateList().get(0).getPasse2().getp5())
                        .compare(o2.getResultateList().get(0).getPasse2().getp4(), o1.getResultateList().get(0).getPasse2().getp4())
                        .compare(o2.getResultateList().get(0).getPasse2().getp3(), o1.getResultateList().get(0).getPasse2().getp3())
                        .compare(o2.getResultateList().get(0).getPasse2().getp2(), o1.getResultateList().get(0).getPasse2().getp2())
                        .compare(o2.getResultateList().get(0).getPasse2().getp1(), o1.getResultateList().get(0).getPasse2().getp1())
                        .compare(o2.getResultateList().get(2).getPasse1().getp10(), o1.getResultateList().get(2).getPasse1().getp10())
                        .compare(o2.getResultateList().get(2).getPasse1().getp9(), o1.getResultateList().get(2).getPasse1().getp9())
                        .compare(o2.getResultateList().get(2).getPasse1().getp8(), o1.getResultateList().get(2).getPasse1().getp8())
                        .compare(o2.getResultateList().get(2).getPasse1().getp7(), o1.getResultateList().get(2).getPasse1().getp7())
                        .compare(o2.getResultateList().get(2).getPasse1().getp6(), o1.getResultateList().get(2).getPasse1().getp6())
                        .compare(o2.getResultateList().get(2).getPasse1().getp5(), o1.getResultateList().get(2).getPasse1().getp5())
                        .compare(o2.getResultateList().get(2).getPasse1().getp4(), o1.getResultateList().get(2).getPasse1().getp4())
                        .compare(o2.getResultateList().get(2).getPasse1().getp3(), o1.getResultateList().get(2).getPasse1().getp3())
                        .compare(o2.getResultateList().get(2).getPasse1().getp2(), o1.getResultateList().get(2).getPasse1().getp2())
                        .compare(o2.getResultateList().get(2).getPasse1().getp1(), o1.getResultateList().get(2).getPasse1().getp1())
                        .compare(o2.getResultateList().get(1).getPasse1().getp10(), o1.getResultateList().get(1).getPasse1().getp10())
                        .compare(o2.getResultateList().get(1).getPasse1().getp9(), o1.getResultateList().get(1).getPasse1().getp9())
                        .compare(o2.getResultateList().get(1).getPasse1().getp8(), o1.getResultateList().get(1).getPasse1().getp8())
                        .compare(o2.getResultateList().get(1).getPasse1().getp7(), o1.getResultateList().get(1).getPasse1().getp7())
                        .compare(o2.getResultateList().get(1).getPasse1().getp6(), o1.getResultateList().get(1).getPasse1().getp6())
                        .compare(o2.getResultateList().get(1).getPasse1().getp5(), o1.getResultateList().get(1).getPasse1().getp5())
                        .compare(o2.getResultateList().get(1).getPasse1().getp4(), o1.getResultateList().get(1).getPasse1().getp4())
                        .compare(o2.getResultateList().get(1).getPasse1().getp3(), o1.getResultateList().get(1).getPasse1().getp3())
                        .compare(o2.getResultateList().get(1).getPasse1().getp2(), o1.getResultateList().get(1).getPasse1().getp2())
                        .compare(o2.getResultateList().get(1).getPasse1().getp1(), o1.getResultateList().get(1).getPasse1().getp1())
                        .compare(o2.getResultateList().get(0).getPasse1().getp10(), o1.getResultateList().get(0).getPasse1().getp10())
                        .compare(o2.getResultateList().get(0).getPasse1().getp9(), o1.getResultateList().get(0).getPasse1().getp9())
                        .compare(o2.getResultateList().get(0).getPasse1().getp8(), o1.getResultateList().get(0).getPasse1().getp8())
                        .compare(o2.getResultateList().get(0).getPasse1().getp7(), o1.getResultateList().get(0).getPasse1().getp7())
                        .compare(o2.getResultateList().get(0).getPasse1().getp6(), o1.getResultateList().get(0).getPasse1().getp6())
                        .compare(o2.getResultateList().get(0).getPasse1().getp5(), o1.getResultateList().get(0).getPasse1().getp5())
                        .compare(o2.getResultateList().get(0).getPasse1().getp4(), o1.getResultateList().get(0).getPasse1().getp4())
                        .compare(o2.getResultateList().get(0).getPasse1().getp3(), o1.getResultateList().get(0).getPasse1().getp3())
                        .compare(o2.getResultateList().get(0).getPasse1().getp2(), o1.getResultateList().get(0).getPasse1().getp2())
                        .compare(o2.getResultateList().get(0).getPasse1().getp1(), o1.getResultateList().get(0).getPasse1().getp1())
                        .result()
            );

        return rangliste;
    }

    @Override
    public Integer getTiefschuesseForPasse(Passen passen, Integer number) {
        Integer returnCount = 0;
        if (passen.getp1().intValue() == number) {
            returnCount++;
        }
        if (passen.getp2().intValue() == number) {
            returnCount++;
        }
        if (passen.getp3().intValue() == number) {
            returnCount++;
        }
        if (passen.getp4().intValue() == number) {
            returnCount++;
        }
        if (passen.getp5().intValue() == number) {
            returnCount++;
        }
        if (passen.getp6().intValue() == number) {
            returnCount++;
        }
        if (passen.getp7().intValue() == number) {
            returnCount++;
        }
        if (passen.getp8().intValue() == number) {
            returnCount++;
        }
        if (passen.getp9().intValue() == number) {
            returnCount++;
        }
        if (passen.getp10().intValue() == number) {
            returnCount++;
        }
        return returnCount;
    }
    /*@Override
    public Rangliste sortRangliste(Rangliste rangliste, Wettkampf wettkampf) {
        List<Rangierung> rangierungList = rangierungRepository.findByWettkampf_Id(wettkampf.getId());
        rangierungList.sort(Comparator.comparing(Rangierung::getPosition));

        rangliste
            .getSchuetzeResultatList()
            .sort(
                (o1, o2) ->
                    ComparisonChain
                        .start()
                        .compare(o2.getResultat(), o1.getResultat())
                        .compare(o2.getResultateList().get(0).getResultat(), o1.getResultateList().get(0).getResultat())
                        .compare(
                            o2.getResultateList().get(0).getPasse2().getResultat(),
                            o1.getResultateList().get(0).getPasse2().getResultat()
                        )
                        .compare(
                            o2.getResultateList().get(0).getPasse1().getResultat(),
                            o1.getResultateList().get(0).getPasse1().getResultat()
                        )
                        .compare(o2.getSchuetze().getJahrgang(), o1.getSchuetze().getJahrgang())
                        .result()
            );

        return rangliste;
    }*/
}
