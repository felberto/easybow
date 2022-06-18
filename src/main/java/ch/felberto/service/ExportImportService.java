package ch.felberto.service;

import ch.felberto.domain.*;
import ch.felberto.repository.*;
import com.google.gson.Gson;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for exporting and importing data
 */
@RestController
@RequestMapping("/api/wettkampfs")
public class ExportImportService {

    private final Logger log = LoggerFactory.getLogger(ExportImportService.class);

    private static final String ENTITY_NAME = "wettkampf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultateRepository resultateRepository;
    private final WettkampfRepository wettkampfRepository;
    private final SchuetzeRepository schuetzeRepository;
    private final RundeRepository rundeRepository;
    private final VereinRepository vereinRepository;

    private final VerbandRepository verbandRepository;

    private final WettkampfService wettkampfService;
    private final SchuetzeService schuetzeService;
    private final PassenService passenService;
    private final ResultateService resultateService;
    private final RundeService rundeService;
    private final VereinService vereinService;

    public ExportImportService(
        ResultateRepository resultateRepository,
        WettkampfRepository wettkampfRepository,
        SchuetzeRepository schuetzeRepository,
        RundeRepository rundeRepository,
        VereinRepository vereinRepository,
        VerbandRepository verbandRepository,
        WettkampfService wettkampfService,
        SchuetzeService schuetzeService,
        PassenService passenService,
        ResultateService resultateService,
        RundeService rundeService,
        VereinService vereinService
    ) {
        this.resultateRepository = resultateRepository;
        this.wettkampfRepository = wettkampfRepository;
        this.schuetzeRepository = schuetzeRepository;
        this.rundeRepository = rundeRepository;
        this.vereinRepository = vereinRepository;
        this.verbandRepository = verbandRepository;
        this.wettkampfService = wettkampfService;
        this.schuetzeService = schuetzeService;
        this.passenService = passenService;
        this.resultateService = resultateService;
        this.rundeService = rundeService;
        this.vereinService = vereinService;
    }

    /**
     * {@code POST  /export} : export data
     *
     * @param wettkampf wettkampf
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ranglisteDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> exportData(@RequestBody Wettkampf wettkampf) {
        ImportExport importExport = new ImportExport();
        importExport.setWettkampf(wettkampf);
        importExport.setRunden(rundeRepository.findByWettkampf_Id(wettkampf.getId()));

        List<Resultate> resultate = new ArrayList<>(resultateRepository.findByWettkampf_Id(wettkampf.getId()));

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
                schuetzeResultatList.add(schuetzeResultat);
            }
        );
        importExport.setSchuetzeResultatList(schuetzeResultatList);

        Gson gson = new Gson();
        String ranglisteJson = gson.toJson(importExport);
        log.info(ranglisteJson);

        byte[] ranglisteJsonBytes = ranglisteJson.getBytes();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=data.json")
            .contentType(MediaType.APPLICATION_JSON)
            .contentLength(ranglisteJsonBytes.length)
            .body(ranglisteJsonBytes);
    }

    /**
     * {@code POST  /import} : Import wettkampf.
     *
     * @param string
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wettkampfDTO, or with status {@code 400 (Bad Request)} if the wettkampf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/import")
    public ResponseEntity<Boolean> importData(@RequestBody String string) throws URISyntaxException {
        log.debug("REST request to import data : {}", string);
        Gson gson = new Gson();
        ImportExport importExport = gson.fromJson(string, ImportExport.class);
        Wettkampf wettkampf;

        if (wettkampfRepository.existsByNameAndJahr(importExport.getWettkampf().getName(), importExport.getWettkampf().getJahr())) {
            log.info(
                "Wettkampf={} {} already exists and will be updated",
                importExport.getWettkampf().getName(),
                importExport.getWettkampf().getJahr()
            );
            wettkampf = wettkampfService.partialUpdateByName(importExport.getWettkampf()).get();
        } else {
            log.info(
                "Wettkampf={} {} does not exists and will be imported",
                importExport.getWettkampf().getName(),
                importExport.getWettkampf().getJahr()
            );
            Wettkampf wettkampf1 = importExport.getWettkampf();
            wettkampf1.setId(null);
            wettkampf = wettkampfService.save(wettkampf1);
        }

        for (Runde runde : importExport.getRunden()) {
            if (rundeRepository.existsByRundeAndWettkampf_Id(runde.getRunde(), wettkampf.getId())) {
                log.info("Runde={} already exists and will be updated", runde.getRunde());
                runde.setWettkampf(wettkampf);
                rundeService.partialUpdate(runde);
            } else {
                log.info("Runde={} does not exists and will be imported", runde.getRunde());
                runde.setId(null);
                runde.setWettkampf(wettkampf);
                rundeService.save(runde);
            }
        }

        for (SchuetzeResultat schuetzeResultat : importExport.getSchuetzeResultatList()) {
            Verein vereinSchuetze;
            if (vereinRepository.existsByName(schuetzeResultat.getSchuetze().getVerein().getName())) {
                log.info("Verein={} already exists and will be updated", schuetzeResultat.getSchuetze().getVerein().getName());
                vereinSchuetze = vereinService.partialUpdateByName(schuetzeResultat.getSchuetze().getVerein()).get();
            } else {
                log.info("Verein={} does not exists and will be imported", schuetzeResultat.getSchuetze().getVerein().getName());
                Verein verein = schuetzeResultat.getSchuetze().getVerein();
                verein.setId(null);
                verein.setVerband(verbandRepository.findByName(verein.getVerband().getName()).get());
                vereinSchuetze = vereinService.save(verein);
            }

            if (schuetzeRepository.existsByName(schuetzeResultat.getSchuetze().getName())) {
                log.info("Schuetze={} already exists and will be updated", schuetzeResultat.getSchuetze().getName());
                schuetzeService.partialUpdateByName(schuetzeResultat.getSchuetze());
            } else {
                log.info("Schuetze={} does not exists and will be imported", schuetzeResultat.getSchuetze().getName());
                Schuetze schuetze = schuetzeResultat.getSchuetze();
                schuetze.setId(null);
                schuetze.setVerein(vereinSchuetze);
                schuetzeService.save(schuetze);
            }
        }

        for (SchuetzeResultat schuetzeResultat : importExport.getSchuetzeResultatList()) {
            Schuetze schuetze = schuetzeRepository.findByName(schuetzeResultat.getSchuetze().getName()).get();

            for (Resultate resultat : schuetzeResultat.getResultateList()) {
                if (
                    resultateRepository.existsByWettkampf_IdAndRundeAndSchuetze_Id(wettkampf.getId(), resultat.getRunde(), schuetze.getId())
                ) {
                    log.info("Resultat already exists and will be updated");
                    Resultate resultateDB = resultateRepository.findByWettkampf_IdAndRundeAndSchuetze_Id(
                        wettkampf.getId(),
                        resultat.getRunde(),
                        schuetze.getId()
                    );
                    if (resultat.getPasse1() != null) {
                        log.info("Passe 1 already exists and will be updated");
                        resultat.getPasse1().setId(resultateDB.getPasse1().getId());
                        passenService.partialUpdate(resultat.getPasse1());
                    } else {
                        log.info("Passe 1 does not exists and will be deleted");
                        if (resultateDB.getPasse1() != null) {
                            passenService.delete(resultateDB.getPasse1().getId());
                        }
                    }
                    if (resultat.getPasse2() != null) {
                        log.info("Passe 2 already exists and will be updated");
                        resultat.getPasse2().setId(resultateDB.getPasse2().getId());
                        passenService.partialUpdate(resultat.getPasse2());
                    } else {
                        log.info("Passe 2 does not exists and will be deleted");
                        if (resultateDB.getPasse2() != null) {
                            passenService.delete(resultateDB.getPasse2().getId());
                        }
                    }
                    if (resultat.getPasse3() != null) {
                        log.info("Passe 3 already exists and will be updated");
                        resultat.getPasse3().setId(resultateDB.getPasse3().getId());
                        passenService.partialUpdate(resultat.getPasse3());
                    } else {
                        log.info("Passe 3 does not exists and will be deleted");
                        if (resultateDB.getPasse3() != null) {
                            passenService.delete(resultateDB.getPasse3().getId());
                        }
                    }
                    if (resultat.getPasse4() != null) {
                        log.info("Passe 4 already exists and will be updated");
                        resultat.getPasse4().setId(resultateDB.getPasse4().getId());
                        passenService.partialUpdate(resultat.getPasse4());
                    } else {
                        log.info("Passe 4 does not exists and will be deleted");
                        if (resultateDB.getPasse4() != null) {
                            passenService.delete(resultateDB.getPasse4().getId());
                        }
                    }
                } else {
                    log.info("Resultat does not exists and will be created");
                    if (resultat.getPasse1() != null) {
                        resultat.getPasse1().setId(null);
                    }
                    if (resultat.getPasse2() != null) {
                        resultat.getPasse2().setId(null);
                    }
                    if (resultat.getPasse3() != null) {
                        resultat.getPasse3().setId(null);
                    }
                    if (resultat.getPasse4() != null) {
                        resultat.getPasse4().setId(null);
                    }

                    Resultate newResultate = new Resultate();
                    newResultate.setRunde(resultat.getRunde());
                    newResultate.setResultat(resultat.getResultat());
                    newResultate.setSchuetze(schuetze);
                    newResultate.setWettkampf(wettkampf);
                    newResultate.setPasse1(resultat.getPasse1());
                    newResultate.setPasse2(resultat.getPasse2());
                    newResultate.setPasse3(resultat.getPasse3());
                    newResultate.setPasse4(resultat.getPasse4());
                    newResultate.setGruppe(null);

                    resultateService.save(newResultate);
                }
            }
        }
        Optional<Boolean> rangliste1 = Optional.ofNullable(true);
        return ResponseUtil.wrapOrNotFound(rangliste1);
    }
}
