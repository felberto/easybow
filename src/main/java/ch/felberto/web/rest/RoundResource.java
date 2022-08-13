package ch.felberto.web.rest;

import ch.felberto.domain.Round;
import ch.felberto.repository.RoundRepository;
import ch.felberto.service.RoundQueryService;
import ch.felberto.service.RoundService;
import ch.felberto.service.criteria.RoundCriteria;
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
 * REST controller for managing {@link ch.felberto.domain.Round}.
 */
@RestController
@RequestMapping("/api")
public class RoundResource {

    private final Logger log = LoggerFactory.getLogger(RoundResource.class);

    private static final String ENTITY_NAME = "round";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoundService roundService;

    private final RoundRepository roundRepository;

    private final RoundQueryService roundQueryService;

    public RoundResource(RoundService roundService, RoundRepository roundRepository, RoundQueryService roundQueryService) {
        this.roundService = roundService;
        this.roundRepository = roundRepository;
        this.roundQueryService = roundQueryService;
    }

    /**
     * {@code POST  /rounds} : Create a new round.
     *
     * @param round the round to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new round, or with status {@code 400 (Bad Request)} if the round has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rounds")
    public ResponseEntity<Round> createRound(@Valid @RequestBody Round round) throws URISyntaxException {
        log.info("REST request to save Round : {}", round);
        if (round.getId() != null) {
            throw new BadRequestAlertException("A new round cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Round result = roundService.save(round);
        return ResponseEntity
            .created(new URI("/api/rounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rounds/:id} : Updates an existing round.
     *
     * @param id    the id of the round to save.
     * @param round the round to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated round,
     * or with status {@code 400 (Bad Request)} if the round is not valid,
     * or with status {@code 500 (Internal Server Error)} if the round couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rounds/{id}")
    public ResponseEntity<Round> updateRound(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Round round)
        throws URISyntaxException {
        log.info("REST request to update Round : {}, {}", id, round);
        if (round.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, round.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Round result = roundService.save(round);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, round.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rounds/:id} : Partial updates given fields of an existing round, field will ignore if it is null
     *
     * @param id    the id of the round to save.
     * @param round the round to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated round,
     * or with status {@code 400 (Bad Request)} if the round is not valid,
     * or with status {@code 404 (Not Found)} if the round is not found,
     * or with status {@code 500 (Internal Server Error)} if the round couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rounds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Round> partialUpdateRound(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Round round
    ) throws URISyntaxException {
        log.info("REST request to partial update Round partially : {}, {}", id, round);
        if (round.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, round.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roundRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Round> result = roundService.partialUpdate(round);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, round.getId().toString())
        );
    }

    /**
     * {@code GET  /rounds} : get all the rounds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rounds in body.
     */
    @GetMapping("/rounds")
    public ResponseEntity<List<Round>> getAllRounds(RoundCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Rounds by criteria: {}", criteria);
        Page<Round> page = roundQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rounds/count} : count all the rounds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rounds/count")
    public ResponseEntity<Long> countRounds(RoundCriteria criteria) {
        log.debug("REST request to count Rounds by criteria: {}", criteria);
        return ResponseEntity.ok().body(roundQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rounds/:id} : get the "id" round.
     *
     * @param id the id of the round to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the round, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rounds/{id}")
    public ResponseEntity<Round> getRound(@PathVariable Long id) {
        log.debug("REST request to get Round : {}", id);
        Optional<Round> round = roundService.findOne(id);
        return ResponseUtil.wrapOrNotFound(round);
    }

    /**
     * {@code GET  /rounds/:id} : get the "id" round.
     *
     * @param round         the id of the round to retrieve.
     * @param competitionId the id of the round to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the round, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rounds/{round}/{competitionId}")
    public ResponseEntity<Round> getRoundByRoundAndCompetitionId(@PathVariable Integer round, @PathVariable Long competitionId) {
        log.debug("REST request to get Round : {}", round);
        Optional<Round> resRound = roundService.findOneByRoundAndCompetitionId(round, competitionId);
        return ResponseUtil.wrapOrNotFound(resRound);
    }

    /**
     * {@code GET  /rounds/:id} : get the "id" round.
     *
     * @param competitionId the id of the round to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the round, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rounds/competition/{competitionId}")
    public List<Round> getRoundByCompetitionId(@PathVariable Long competitionId) {
        log.debug("REST request to get Round : {}", competitionId);
        List<Round> resRound = roundService.findByCompetitionId(competitionId);
        return resRound;
    }

    /**
     * {@code DELETE  /rounds/:id} : delete the "id" round.
     *
     * @param id the id of the round to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rounds/{id}")
    public ResponseEntity<Void> deleteRound(@PathVariable Long id) {
        log.debug("REST request to delete Round : {}", id);
        roundService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
