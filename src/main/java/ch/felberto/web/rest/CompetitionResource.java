package ch.felberto.web.rest;

import ch.felberto.domain.Competition;
import ch.felberto.repository.CompetitionRepository;
import ch.felberto.service.CompetitionQueryService;
import ch.felberto.service.CompetitionService;
import ch.felberto.service.criteria.CompetitionCriteria;
import ch.felberto.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Competition}.
 */
@RestController
@RequestMapping("/api")
public class CompetitionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitionResource.class);

    private static final String ENTITY_NAME = "competition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitionService competitionService;

    private final CompetitionRepository competitionRepository;

    private final CompetitionQueryService competitionQueryService;

    public CompetitionResource(
        CompetitionService competitionService,
        CompetitionRepository competitionRepository,
        CompetitionQueryService competitionQueryService
    ) {
        this.competitionService = competitionService;
        this.competitionRepository = competitionRepository;
        this.competitionQueryService = competitionQueryService;
    }

    /**
     * {@code POST  /competitions} : Create a new competition.
     *
     * @param competition the competition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competition, or with status {@code 400 (Bad Request)} if the competition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitions")
    public ResponseEntity<Competition> createCompetition(@Valid @RequestBody Competition competition) throws URISyntaxException {
        log.info("REST request to save Competition : {}", competition);
        if (competition.getId() != null) {
            throw new BadRequestAlertException("A new competition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competition result = competitionService.save(competition);
        return ResponseEntity
            .created(new URI("/api/competitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitions/:id} : Updates an existing competition.
     *
     * @param id          the id of the competition to save.
     * @param competition the competition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competition,
     * or with status {@code 400 (Bad Request)} if the competition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitions/{id}")
    public ResponseEntity<Competition> updateCompetition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Competition competition
    ) throws URISyntaxException {
        log.info("REST request to update Competition : {}, {}", id, competition);
        if (competition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Competition result = competitionService.save(competition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competitions/:id} : Partial updates given fields of an existing competition, field will ignore if it is null
     *
     * @param id          the id of the competition to save.
     * @param competition the competition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competition,
     * or with status {@code 400 (Bad Request)} if the competition is not valid,
     * or with status {@code 404 (Not Found)} if the competition is not found,
     * or with status {@code 500 (Internal Server Error)} if the competition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competitions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Competition> partialUpdateCompetition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Competition competition
    ) throws URISyntaxException {
        log.info("REST request to partial update Competition partially : {}, {}", id, competition);
        if (competition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Competition> result = competitionService.partialUpdate(competition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competition.getId().toString())
        );
    }

    /**
     * {@code GET  /competitions} : get all the competitions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitions in body.
     */
    @GetMapping("/competitions")
    public ResponseEntity<List<Competition>> getAllCompetitions(CompetitionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Competitions by criteria: {}", criteria);
        Page<Competition> page = competitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/competitions/year/{year}")
    public ResponseEntity<List<Competition>> getAllCompetitionByYear(@PathVariable Integer year) {
        log.debug("REST request to get Competitions by year: {}", year);
        List<Competition> competitionList = competitionService.findByYear(year);
        return ResponseEntity.ok().body(competitionList);
    }

    /**
     * {@code GET  /competitions/count} : count all the competitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/competitions/count")
    public ResponseEntity<Long> countCompetitions(CompetitionCriteria criteria) {
        log.debug("REST request to count Competitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(competitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /competitions/:id} : get the "id" competition.
     *
     * @param id the id of the competition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitions/{id}")
    public ResponseEntity<Competition> getCompetition(@PathVariable Long id) {
        log.debug("REST request to get Competition : {}", id);
        Optional<Competition> competition = competitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competition);
    }

    /**
     * {@code DELETE  /competitions/:id} : delete the "id" competition.
     *
     * @param id the id of the competition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitions/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        log.debug("REST request to delete Competition : {}", id);
        competitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
