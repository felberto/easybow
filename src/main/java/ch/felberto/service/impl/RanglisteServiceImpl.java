package ch.felberto.service.impl;

import ch.felberto.domain.*;
import ch.felberto.repository.RangierungRepository;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.service.RanglisteService;
import com.google.common.collect.ComparisonChain;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RanglisteService}.
 */
@Service
@Transactional
public class RanglisteServiceImpl implements RanglisteService {

    private final ResultateRepository resultateRepository;

    private final WettkampfRepository wettkampfRepository;

    private final RangierungRepository rangierungRepository;

    public RanglisteServiceImpl(
        ResultateRepository resultateRepository,
        WettkampfRepository wettkampfRepository,
        RangierungRepository rangierungRepository
    ) {
        this.resultateRepository = resultateRepository;
        this.wettkampfRepository = wettkampfRepository;
        this.rangierungRepository = rangierungRepository;
    }

    @Override
    public Rangliste generateRangliste(Long wettkampfId, List<Integer> runden) throws DocumentException, FileNotFoundException {
        Wettkampf wettkampf = wettkampfRepository.getOne(wettkampfId);
        Rangliste rangliste = getAllSchuetzesByWettkampf(wettkampf, runden);
        rangliste = sortRangliste(rangliste, wettkampf);
        //generatePdf(rangliste, runden);
        return rangliste;
    }

    @Override
    public Rangliste getAllSchuetzesByWettkampf(Wettkampf wettkampf, List<Integer> runden) {
        Rangliste rangliste = new Rangliste();
        rangliste.setWettkampf(wettkampf);
        List<Resultate> resultate = runden
            .stream()
            .map(runde -> resultateRepository.findByWettkampf_IdAndRunde(wettkampf.getId(), runde))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
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
                schuetzeResultatList.add(schuetzeResultat);
            }
        );
        rangliste.setSchuetzeResultatList(schuetzeResultatList);
        return rangliste;
    }

    @Override
    public Rangliste sortRangliste(Rangliste rangliste, Wettkampf wettkampf) {
        List<Rangierung> rangierungList = rangierungRepository.findByWettkampf_Id(wettkampf.getId());
        rangierungList.sort(Comparator.comparing(Rangierung::getPosition));

        //todo sort by gesamtpunktzahl
        //todo einmal sortieren, dann überprüfen ob noch gleiche resultate vorliegen
        //todo liste mit offenen rangierungen machen und nur die prüfen, falls aufgelöst, diese aus der temp liste entfernen

        //todo fixe rangierung entfernen

        /**todo IDEE
         Rangierung hardcoded machen und für jede Wettkampf Art eine seperate Funktion
         in dieser ebenfalls verschiedene Möglichkeiten, je nach dem wie viele Runden und Passen geschossen wurden
         **/
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

        /*for (Rangierung rangierung : rangierungList) {
            switch (rangierung.getRangierungskriterien()) {
                case RESULTAT:
                    break;
                case SERIE:
                    //todo check if duplicatedarray empty or not
                    break;
                case TIEFSCHUESSE:
                    break;
                case MOUCHEN:
                    break;
                case ALTER:
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + rangierung.getRangierungskriterien());
            }
        }*/

        return rangliste;
    }

    //TODO auslagern in eigene Klasse
    @Override
    public void generatePdf(Rangliste rangliste, List<Integer> runden) throws FileNotFoundException, DocumentException {
        String runde = "";
        for (Integer r : runden) {
            if (runde.isEmpty()) {
                runde = runde.concat(r.toString() + ".");
            } else {
                runde = runde.concat(" + " + r.toString() + ".");
            }
        }
        runde = runde.concat(" Runde");

        Document document = new Document();
        PdfWriter.getInstance(
            document,
            new FileOutputStream(
                "Rangliste " + rangliste.getWettkampf().getName() + " " + rangliste.getWettkampf().getJahr() + " " + runde + ".pdf"
            )
        );

        document.open();
        for (int i = 0; i < rangliste.getSchuetzeResultatList().size(); i++) {
            Paragraph preface = new Paragraph(
                (i + 1) +
                ". Rang " +
                rangliste.getSchuetzeResultatList().get(i).getSchuetze().getName() +
                "                 " +
                rangliste.getSchuetzeResultatList().get(i).getResultat()
            );
            document.add(preface);
        }
        document.close();
    }
}
