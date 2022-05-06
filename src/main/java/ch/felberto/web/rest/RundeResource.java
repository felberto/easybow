package ch.felberto.web.rest;

import ch.felberto.domain.Runde;
import ch.felberto.repository.RundeRepository;
import ch.felberto.service.RundeQueryService;
import ch.felberto.service.RundeService;
import ch.felberto.service.criteria.RundeCriteria;
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
 * REST controller for managing {@link ch.felberto.domain.Runde}.
 */
@RestController
@RequestMapping("/api")
public class RundeResource {

    private final Logger log = LoggerFactory.getLogger(RundeResource.class);

    private static final String ENTITY_NAME = "runde";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RundeService rundeService;

    private final RundeRepository rundeRepository;

    private final RundeQueryService rundeQueryService;

    public RundeResource(RundeService rundeService, RundeRepository rundeRepository, RundeQueryService rundeQueryService) {
        this.rundeService = rundeService;
        this.rundeRepository = rundeRepository;
        this.rundeQueryService = rundeQueryService;
    }

    /**
     * {@code POST  /rundes} : Create a new runde.
     *
     * @param runde the runde to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new runde, or with status {@code 400 (Bad Request)} if the runde has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rundes")
    public ResponseEntity<Runde> createRunde(@Valid @RequestBody Runde runde) throws URISyntaxException {
        log.info("REST request to save Runde : {}", runde);
        if (runde.getId() != null) {
            throw new BadRequestAlertException("A new runde cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Runde result = rundeService.save(runde);
        return ResponseEntity
            .created(new URI("/api/rundes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rundes/:id} : Updates an existing runde.
     *
     * @param id    the id of the runde to save.
     * @param runde the runde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated runde,
     * or with status {@code 400 (Bad Request)} if the runde is not valid,
     * or with status {@code 500 (Internal Server Error)} if the runde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rundes/{id}")
    public ResponseEntity<Runde> updateRunde(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Runde runde)
        throws URISyntaxException {
        log.info("REST request to update Runde : {}, {}", id, runde);
        if (runde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, runde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rundeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Runde result = rundeService.save(runde);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, runde.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rundes/:id} : Partial updates given fields of an existing runde, field will ignore if it is null
     *
     * @param id    the id of the runde to save.
     * @param runde the runde to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated runde,
     * or with status {@code 400 (Bad Request)} if the runde is not valid,
     * or with status {@code 404 (Not Found)} if the runde is not found,
     * or with status {@code 500 (Internal Server Error)} if the runde couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rundes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Runde> partialUpdateRunde(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Runde runde
    ) throws URISyntaxException {
        log.info("REST request to partial update Runde partially : {}, {}", id, runde);
        if (runde.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, runde.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rundeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Runde> result = rundeService.partialUpdate(runde);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, runde.getId().toString())
        );
    }

    /**
     * {@code GET  /rundes} : get all the rundes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rundes in body.
     */
    @GetMapping("/rundes")
    public ResponseEntity<List<Runde>> getAllRundes(RundeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Rundes by criteria: {}", criteria);
        Page<Runde> page = rundeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rundes/count} : count all the rundes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rundes/count")
    public ResponseEntity<Long> countRundes(RundeCriteria criteria) {
        log.debug("REST request to count Rundes by criteria: {}", criteria);
        return ResponseEntity.ok().body(rundeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rundes/:id} : get the "id" runde.
     *
     * @param id the id of the runde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the runde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rundes/{id}")
    public ResponseEntity<Runde> getRunde(@PathVariable Long id) {
        log.debug("REST request to get Runde : {}", id);
        Optional<Runde> runde = rundeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(runde);
    }

    /**
     * {@code GET  /rundes/:id} : get the "id" runde.
     *
     * @param runde the id of the runde to retrieve.
     * @param wettkampfId the id of the runde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the runde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rundes/{runde}/{wettkampfId}")
    public ResponseEntity<Runde> getRundeByRundeAndWettkampfId(@PathVariable Integer runde, @PathVariable Long wettkampfId) {
        log.debug("REST request to get Runde : {}", runde);
        Optional<Runde> resRunde = rundeService.findOneByRundeAndWettkampfId(runde, wettkampfId);
        return ResponseUtil.wrapOrNotFound(resRunde);
    }

    /**
     * {@code GET  /rundes/:id} : get the "id" runde.
     *
     * @param wettkampfId the id of the runde to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the runde, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rundes/wettkampf/{wettkampfId}")
    public List<Runde> getRundeByWettkampfId(@PathVariable Long wettkampfId) {
        log.debug("REST request to get Runde : {}", wettkampfId);
        List<Runde> resRunde = rundeService.findByWettkampfId(wettkampfId);
        return resRunde;
    }

    /**
     * {@code DELETE  /rundes/:id} : delete the "id" runde.
     *
     * @param id the id of the runde to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rundes/{id}")
    public ResponseEntity<Void> deleteRunde(@PathVariable Long id) {
        log.debug("REST request to delete Runde : {}", id);
        rundeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
