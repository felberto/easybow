package ch.felberto.web.rest;

import ch.felberto.domain.Competition;
import ch.felberto.domain.GroupRankingList;
import ch.felberto.domain.RankingList;
import ch.felberto.service.CompetitionService;
import ch.felberto.service.RankingListPrintService;
import ch.felberto.service.impl.*;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link RankingList}.
 */
@RestController
@RequestMapping("/api")
public class RankingListResource {

    private final Logger log = LoggerFactory.getLogger(RankingListResource.class);

    private static final String ENTITY_NAME = "rankingList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZsavNawuEinzelRankingListServiceImpl zsavNawuEinzelRankingListService;
    private final EasvWorldcupRankingListServiceImpl easvWorldcupRankingListService;
    private final EasvWorldcup30mRankingListServiceImpl easvWorldcup30mRankingListService;
    private final EasvSm10mRankingListServiceImpl easvSm10mRankingListService;
    private final ZsavNawuGmRankingListServiceImpl zsavNawuGmRankingListService;

    private final EasvNawuGmRankingListServiceImpl easvNawuGmRankingListService;
    private final EasvStaendematchGroupRankingListServiceImpl easvStaendematchGroupRankingListService;
    private final EasvVerbaendefinalGroupRankingListServiceImpl easvVerbaendefinalGroupRankingListService;
    private final RankingListPrintService rankingListPrintService;

    private final CompetitionService competitionService;

    public RankingListResource(
        ZsavNawuEinzelRankingListServiceImpl zsavNawuEinzelRankingListService,
        EasvWorldcupRankingListServiceImpl easvWorldcupRankingListService,
        EasvWorldcup30mRankingListServiceImpl easvWorldcup30mRankingListService,
        EasvSm10mRankingListServiceImpl easvSm10mRankingListService,
        ZsavNawuGmRankingListServiceImpl zsavNawuGmRankingListService,
        EasvNawuGmRankingListServiceImpl easvNawuGmRankingListService,
        EasvStaendematchGroupRankingListServiceImpl easvStaendematchGroupRankingListService,
        EasvVerbaendefinalGroupRankingListServiceImpl easvVerbaendefinalGroupRankingListService,
        RankingListPrintService rankingListPrintService,
        CompetitionService competitionService
    ) {
        this.zsavNawuEinzelRankingListService = zsavNawuEinzelRankingListService;
        this.easvWorldcup30mRankingListService = easvWorldcup30mRankingListService;
        this.easvSm10mRankingListService = easvSm10mRankingListService;
        this.zsavNawuGmRankingListService = zsavNawuGmRankingListService;
        this.easvWorldcupRankingListService = easvWorldcupRankingListService;
        this.easvNawuGmRankingListService = easvNawuGmRankingListService;
        this.easvStaendematchGroupRankingListService = easvStaendematchGroupRankingListService;
        this.easvVerbaendefinalGroupRankingListService = easvVerbaendefinalGroupRankingListService;
        this.rankingListPrintService = rankingListPrintService;
        this.competitionService = competitionService;
    }

    /**
     * {@code POST  /rankinglist/:id} : get the "id" competition.
     *
     * @param competitionId the id of the competition to retrieve.
     * @param type          the type to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankingList, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/rankinglist/{competitionId}")
    public ResponseEntity<RankingList> createRankingList(@PathVariable Long competitionId, @RequestBody Integer type) {
        log.info("REST request to get RankingList for Competition : {} and type : {}", competitionId, type);
        Optional<Competition> competition = competitionService.findOne(competitionId);
        Optional<RankingList> rankingList = null;
        switch (competition.get().getCompetitionType()) {
            case ZSAV_NAWU:
                rankingList = Optional.ofNullable(zsavNawuEinzelRankingListService.generateRankingList(competitionId, type));
                break;
            case ZSAV_NAWU_GM:
                rankingList = Optional.ofNullable(zsavNawuGmRankingListService.generateRankingList(competitionId, type));
                break;
            case EASV_NAWU_GM:
                rankingList = Optional.ofNullable(easvNawuGmRankingListService.generateRankingList(competitionId, type));
                break;
            case EASV_WORLDCUP:
                rankingList = Optional.ofNullable(easvWorldcupRankingListService.generateRankingList(competitionId, type));
                break;
            case EASV_WORLDCUP_30M:
                rankingList = Optional.ofNullable(easvWorldcup30mRankingListService.generateRankingList(competitionId, type));
                break;
            case EASV_SM_10M:
                rankingList = Optional.ofNullable(easvSm10mRankingListService.generateRankingList(competitionId, type));
                break;
            case EASV_STAENDEMATCH:
                rankingList = Optional.ofNullable(easvStaendematchGroupRankingListService.generateRankingList(competitionId, type));
                break;
            case EASV_VERBAENDEFINAL:
                rankingList = Optional.ofNullable(easvVerbaendefinalGroupRankingListService.generateRankingList(competitionId, type));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + competition.get().getCompetitionType());
        }
        return ResponseUtil.wrapOrNotFound(rankingList);
    }

    /**
     * {@code POST  /grouprankinglist/:id} : get the "id" competition.
     *
     * @param competitionId the id of the competition to retrieve.
     * @param type          the type to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankingList, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/grouprankinglist/{competitionId}")
    public ResponseEntity<GroupRankingList> createGroupRankingList(@PathVariable Long competitionId, @RequestBody Integer type) {
        log.info("REST request to get RankingList for Competition : {} and type : {}", competitionId, type);
        Optional<Competition> competition = competitionService.findOne(competitionId);
        Optional<GroupRankingList> rankingList = null;
        switch (competition.get().getCompetitionType()) {
            case ZSAV_NAWU_GM:
                rankingList = Optional.ofNullable(zsavNawuGmRankingListService.generateGroupRankingList(competitionId, type));
                break;
            case EASV_NAWU_GM:
                rankingList = Optional.ofNullable(easvNawuGmRankingListService.generateGroupRankingList(competitionId, type));
                break;
            case EASV_STAENDEMATCH:
                rankingList = Optional.ofNullable(easvStaendematchGroupRankingListService.generateGroupRankingList(competitionId, type));
                break;
            case EASV_VERBAENDEFINAL:
                rankingList = Optional.ofNullable(easvVerbaendefinalGroupRankingListService.generateGroupRankingList(competitionId, type));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + competition.get().getCompetitionType());
        }
        return ResponseUtil.wrapOrNotFound(rankingList);
    }

    /**
     * {@code POST  /rankinglist/final/:id} : get the "id" competition.
     *
     * @param competitionId the id of the competition to retrieve.
     * @param type          the type to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankingList, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/rankinglist/final/{competitionId}")
    public ResponseEntity<RankingList> createFinal(@PathVariable Long competitionId, @RequestBody Integer type) {
        log.info("REST request to create final for Competition : {} and type : {}", competitionId, type);
        //todo wie oben switch case
        Optional<RankingList> rangliste = Optional.ofNullable(zsavNawuEinzelRankingListService.createFinal(competitionId, type));
        return ResponseUtil.wrapOrNotFound(rangliste);
    }

    /**
     * {@code POST  /rankinglist/print} : print rankingList.
     *
     * @param rankingList rankingList
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankingList, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/rankinglist/print")
    public ResponseEntity<Resource> printRankingList(@RequestBody RankingList rankingList) throws IOException, DocumentException {
        log.info("REST request to print RankingList : {}", rankingList);
        try {
            Path file = Paths.get(rankingListPrintService.generatePdf(rankingList).getAbsolutePath());
            Resource resource = new UrlResource((file.toUri()));
            Path path = resource.getFile().toPath();
            if (Files.exists(file)) {
                return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
                /*response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();*/

            }
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * {@code POST  /grouprankinglist/print} : print rankingList.
     *
     * @param rankingList rankingList
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rankingList, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/grouprankinglist/print")
    public ResponseEntity<Resource> printGroupRankingList(@RequestBody GroupRankingList rankingList) throws IOException, DocumentException {
        log.info("REST request to print RankingList : {}", rankingList);
        try {
            Path file = Paths.get(rankingListPrintService.generatePdfGroup(rankingList).getAbsolutePath());
            Resource resource = new UrlResource((file.toUri()));
            Path path = resource.getFile().toPath();
            if (Files.exists(file)) {
                return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
                /*response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();*/

            }
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
