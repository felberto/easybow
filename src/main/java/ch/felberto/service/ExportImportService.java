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
@RequestMapping("/api/competitions")
public class ExportImportService {

    private final Logger log = LoggerFactory.getLogger(ExportImportService.class);

    private static final String ENTITY_NAME = "competition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultsRepository resultsRepository;
    private final CompetitionRepository competitionRepository;
    private final AthleteRepository athleteRepository;
    private final RoundRepository roundRepository;
    private final ClubRepository clubRepository;

    private final AssociationRepository associationRepository;
    private final GroupRepository groupRepository;

    private final CompetitionService competitionService;
    private final AthleteService athleteService;
    private final SeriesService seriesService;
    private final ResultsService resultsService;
    private final GroupService groupService;
    private final RoundService roundService;
    private final ClubService clubService;

    public ExportImportService(
        ResultsRepository resultsRepository,
        CompetitionRepository competitionRepository,
        AthleteRepository athleteRepository,
        RoundRepository roundRepository,
        ClubRepository clubRepository,
        AssociationRepository associationRepository,
        GroupRepository groupRepository,
        CompetitionService competitionService,
        AthleteService athleteService,
        SeriesService seriesService,
        ResultsService resultsService,
        GroupService groupService,
        RoundService roundService,
        ClubService clubService
    ) {
        this.resultsRepository = resultsRepository;
        this.competitionRepository = competitionRepository;
        this.athleteRepository = athleteRepository;
        this.roundRepository = roundRepository;
        this.clubRepository = clubRepository;
        this.associationRepository = associationRepository;
        this.groupRepository = groupRepository;
        this.competitionService = competitionService;
        this.athleteService = athleteService;
        this.seriesService = seriesService;
        this.resultsService = resultsService;
        this.groupService = groupService;
        this.roundService = roundService;
        this.clubService = clubService;
    }

    /**
     * {@code POST  /export} : export data
     *
     * @param competition competition
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankingList, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> exportData(@RequestBody Competition competition) {
        ImportExport importExport = new ImportExport();
        importExport.setCompetition(competition);
        importExport.setRoundList(roundRepository.findByCompetition_Id(competition.getId()));

        List<Results> results = new ArrayList<>(resultsRepository.findByCompetition_Id(competition.getId()));

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
                athleteResultList.add(athleteResult);
            }
        );
        importExport.setAthleteResultList(athleteResultList);

        Gson gson = new Gson();
        String rankingListJson = gson.toJson(importExport);
        log.info(rankingListJson);

        byte[] rankingListJsonBytes = rankingListJson.getBytes();

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=data.json")
            .contentType(MediaType.APPLICATION_JSON)
            .contentLength(rankingListJsonBytes.length)
            .body(rankingListJsonBytes);
    }

    /**
     * {@code POST  /import} : Import competition.
     *
     * @param string
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competition, or with status {@code 400 (Bad Request)} if the wettkampf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/import")
    public ResponseEntity<Boolean> importData(@RequestBody String string) throws URISyntaxException {
        log.debug("REST request to import data : {}", string);
        Gson gson = new Gson();
        ImportExport importExport = gson.fromJson(string, ImportExport.class);
        Competition competition;

        if (competitionRepository.existsByNameAndYear(importExport.getCompetition().getName(), importExport.getCompetition().getYear())) {
            log.info(
                "Competition={} {} already exists and will be updated",
                importExport.getCompetition().getName(),
                importExport.getCompetition().getYear()
            );
            competition = competitionService.partialUpdateByName(importExport.getCompetition()).get();
        } else {
            log.info(
                "Competition={} {} does not exists and will be imported",
                importExport.getCompetition().getName(),
                importExport.getCompetition().getYear()
            );
            Competition competition1 = importExport.getCompetition();
            competition1.setId(null);
            competition = competitionService.save(competition1);
        }

        for (Round round : importExport.getRoundList()) {
            if (roundRepository.existsByRoundAndCompetition_Id(round.getRound(), competition.getId())) {
                log.info("Round={} already exists and will be updated", round.getRound());
                round.setCompetition(competition);
                roundService.partialUpdate(round);
            } else {
                log.info("Round={} does not exists and will be imported", round.getRound());
                round.setId(null);
                round.setCompetition(competition);
                roundService.save(round);
            }
        }

        for (AthleteResult athleteResult : importExport.getAthleteResultList()) {
            Club clubAthlete = null;
            if (athleteResult.getAthlete().getClub() != null) {
                if (clubRepository.existsByName(athleteResult.getAthlete().getClub().getName())) {
                    log.info("Club={} already exists and will be updated", athleteResult.getAthlete().getClub().getName());
                    clubAthlete = clubService.partialUpdateByName(athleteResult.getAthlete().getClub()).get();
                } else {
                    log.info("Club={} does not exists and will be imported", athleteResult.getAthlete().getClub().getName());
                    Club club = athleteResult.getAthlete().getClub();
                    club.setId(null);
                    club.setAssociation(associationRepository.findByName(club.getAssociation().getName()).get());
                    clubAthlete = clubService.save(club);
                }
            }

            if (
                athleteRepository.existsByNameAndFirstName(athleteResult.getAthlete().getName(), athleteResult.getAthlete().getFirstName())
            ) {
                log.info("Athlete={} already exists and will be updated", athleteResult.getAthlete().getName());
                athleteService.partialUpdateByName(athleteResult.getAthlete());
            } else {
                log.info("Athlete={} does not exists and will be imported", athleteResult.getAthlete().getName());
                Athlete athlete = athleteResult.getAthlete();
                athlete.setId(null);
                if (athleteResult.getAthlete().getClub() != null) {
                    athlete.setClub(clubAthlete);
                }
                athleteService.save(athlete);
            }
        }

        for (AthleteResult athleteResult : importExport.getAthleteResultList()) {
            Athlete athlete = athleteRepository
                .findByNameAndFirstName(athleteResult.getAthlete().getName(), athleteResult.getAthlete().getFirstName())
                .get();

            for (Results results : athleteResult.getResultsList()) {
                if (
                    resultsRepository.existsByCompetition_IdAndRoundAndAthlete_Id(competition.getId(), results.getRound(), athlete.getId())
                ) {
                    log.info("Result already exists and will be updated");
                    Results resultsDB = resultsRepository.findByCompetition_IdAndRoundAndAthlete_Id(
                        competition.getId(),
                        results.getRound(),
                        athlete.getId()
                    );
                    if (results.getSerie1() != null) {
                        log.info("Serie 1 already exists and will be updated");
                        results.getSerie1().setId(resultsDB.getSerie1().getId());
                        seriesService.partialUpdate(results.getSerie1());
                    } else {
                        log.info("Serie 1 does not exists and will be deleted");
                        if (resultsDB.getSerie1() != null) {
                            seriesService.delete(resultsDB.getSerie1().getId());
                        }
                    }
                    if (results.getSerie2() != null) {
                        log.info("Serie 2 already exists and will be updated");
                        results.getSerie2().setId(resultsDB.getSerie2().getId());
                        seriesService.partialUpdate(results.getSerie2());
                    } else {
                        log.info("Serie 2 does not exists and will be deleted");
                        if (resultsDB.getSerie2() != null) {
                            seriesService.delete(resultsDB.getSerie2().getId());
                        }
                    }
                    if (results.getSerie3() != null) {
                        log.info("Serie 3 already exists and will be updated");
                        results.getSerie3().setId(resultsDB.getSerie3().getId());
                        seriesService.partialUpdate(results.getSerie3());
                    } else {
                        log.info("Serie 3 does not exists and will be deleted");
                        if (resultsDB.getSerie3() != null) {
                            seriesService.delete(resultsDB.getSerie3().getId());
                        }
                    }
                    if (results.getSerie4() != null) {
                        log.info("Serie 4 already exists and will be updated");
                        results.getSerie4().setId(resultsDB.getSerie4().getId());
                        seriesService.partialUpdate(results.getSerie4());
                    } else {
                        log.info("Serie 4 does not exists and will be deleted");
                        if (resultsDB.getSerie4() != null) {
                            seriesService.delete(resultsDB.getSerie4().getId());
                        }
                    }
                } else {
                    log.info("Result does not exists and will be created");
                    if (results.getSerie1() != null) {
                        results.getSerie1().setId(null);
                    }
                    if (results.getSerie2() != null) {
                        results.getSerie2().setId(null);
                    }
                    if (results.getSerie3() != null) {
                        results.getSerie3().setId(null);
                    }
                    if (results.getSerie4() != null) {
                        results.getSerie4().setId(null);
                    }

                    Results newResults = new Results();
                    newResults.setRound(results.getRound());
                    newResults.setResult(results.getResult());
                    newResults.setAthlete(athlete);
                    newResults.setAthleteNumber(results.getAthleteNumber());
                    newResults.setCompetition(competition);
                    newResults.setSerie1(results.getSerie1());
                    newResults.setSerie2(results.getSerie2());
                    newResults.setSerie3(results.getSerie3());
                    newResults.setSerie4(results.getSerie4());
                    if (groupRepository.existsByName(results.getGroup().getName())) {
                        log.info("Group={} already exists and will be updated", results.getGroup().getName());
                        newResults.setGroup(groupRepository.findByName(results.getGroup().getName()).get());
                    } else {
                        log.info("Group={} does not exists and will be imported", results.getGroup().getName());
                        Group newGroup = new Group();
                        newGroup.setName(results.getGroup().getName());
                        groupService.save(newGroup);
                        newResults.setGroup(newGroup);
                    }

                    resultsService.save(newResults);
                }
            }
        }
        Optional<Boolean> rankingList1 = Optional.ofNullable(true);
        return ResponseUtil.wrapOrNotFound(rankingList1);
    }
}
