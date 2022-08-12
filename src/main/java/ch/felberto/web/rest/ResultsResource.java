package ch.felberto.web.rest;

import ch.felberto.domain.Results;
import ch.felberto.repository.ResultsRepository;
import ch.felberto.service.MailService;
import ch.felberto.service.ResultsQueryService;
import ch.felberto.service.ResultsService;
import ch.felberto.service.criteria.ResultsCriteria;
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
 * REST controller for managing {@link Results}.
 */
@RestController
@RequestMapping("/api")
public class ResultsResource {

    private final Logger log = LoggerFactory.getLogger(ResultsResource.class);

    private static final String ENTITY_NAME = "results";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultsService resultsService;

    private final ResultsRepository resultsRepository;

    private final ResultsQueryService resultsQueryService;

    private final MailService mailService;

    public ResultsResource(
        ResultsService resultsService,
        ResultsRepository resultsRepository,
        ResultsQueryService resultsQueryService,
        MailService mailService
    ) {
        this.resultsService = resultsService;
        this.resultsRepository = resultsRepository;
        this.resultsQueryService = resultsQueryService;
        this.mailService = mailService;
    }

    //TODO gemsamtresultat updaten wenn passe hinzugefügt oder geupdatet wird

    /**
     * {@code POST  /results} : Create a new result.
     *
     * @param result the result to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new result, or with status {@code 400 (Bad Request)} if the resultate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/results")
    public ResponseEntity<Results> createResult(@Valid @RequestBody Results result) throws URISyntaxException {
        log.info("REST request to save Result : {}", result);
        if (result.getId() != null) {
            throw new BadRequestAlertException("A new result cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Results results = resultsService.save(result);
        return ResponseEntity
            .created(new URI("/api/results/" + results.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, results.getId().toString()))
            .body(results);
    }

    /**
     * {@code PUT  /results/:id} : Updates an existing result.
     *
     * @param id     the id of the result to save.
     * @param result the result to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated result,
     * or with status {@code 400 (Bad Request)} if the result is not valid,
     * or with status {@code 500 (Internal Server Error)} if the result couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/results/{id}")
    public ResponseEntity<Results> updateResult(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Results result
    ) throws URISyntaxException {
        log.info("REST request to update Result : {}, {}", id, result);
        if (result.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, result.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Results results = resultsService.save(result);
        //mailService.sendUpdateResultMail(result);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(results);
    }

    /**
     * {@code PATCH  /results/:id} : Partial updates given fields of an existing result, field will ignore if it is null
     *
     * @param id     the id of the result to save.
     * @param result the result to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated result,
     * or with status {@code 400 (Bad Request)} if the result is not valid,
     * or with status {@code 404 (Not Found)} if the result is not found,
     * or with status {@code 500 (Internal Server Error)} if the result couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/results/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Results> partialUpdateResult(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Results result
    ) throws URISyntaxException {
        log.info("REST request to partial update Result partially : {}, {}", id, result);
        if (result.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, result.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Results> results = resultsService.partialUpdate(result);

        return ResponseUtil.wrapOrNotFound(
            results,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString())
        );
    }

    /**
     * {@code GET  /results} : get all the results.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of results in body.
     */
    @GetMapping("/results")
    public ResponseEntity<List<Results>> getAllResults(ResultsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Results by criteria: {}", criteria);
        Page<Results> page = resultsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /results/count} : count all the results.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/results/count")
    public ResponseEntity<Long> countResults(ResultsCriteria criteria) {
        log.debug("REST request to count Results by criteria: {}", criteria);
        return ResponseEntity.ok().body(resultsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /results/:id} : get the "id" result.
     *
     * @param id the id of the result to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the result, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/results/{id}")
    public ResponseEntity<Results> getResult(@PathVariable Long id) {
        log.debug("REST request to get Result : {}", id);
        Optional<Results> resultate = resultsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultate);
    }

    /**
     * {@code GET  /results/:competition} : get the "competition" resultate.
     *
     * @param competitionId the id of the competition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the result, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/results/competition/{competitionId}")
    public ResponseEntity<List<Results>> getResultByCompetition(@PathVariable Long competitionId) {
        log.debug("REST request to get Result : {}", competitionId);
        return ResponseEntity.ok().body(resultsService.findByCompetition(competitionId));
    }

    /**
     * {@code DELETE  /results/:id} : delete the "id" result.
     *
     * @param id the id of the result to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/results/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        log.debug("REST request to delete Result : {}", id);
        resultsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code DELETE  /results/athlete/:id} : delete the athlete result.
     *
     * @param id the id of athlete of the result to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/results/athlete/{id}/{competitionId}")
    public ResponseEntity<Void> deleteResultByAthleteAndCompetition(@PathVariable Long id, @PathVariable Long competitionId) {
        log.debug("REST request to delete Result : {}", id);
        resultsService.deleteByAthleteAndCompetition(id, competitionId);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
