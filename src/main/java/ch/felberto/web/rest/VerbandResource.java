package ch.felberto.web.rest;

import ch.felberto.domain.Verband;
import ch.felberto.repository.VerbandRepository;
import ch.felberto.service.VerbandQueryService;
import ch.felberto.service.VerbandService;
import ch.felberto.service.criteria.VerbandCriteria;
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
 * REST controller for managing {@link ch.felberto.domain.Verband}.
 */
@RestController
@RequestMapping("/api")
public class VerbandResource {

    private final Logger log = LoggerFactory.getLogger(VerbandResource.class);

    private static final String ENTITY_NAME = "verband";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerbandService verbandService;

    private final VerbandRepository verbandRepository;

    private final VerbandQueryService verbandQueryService;

    public VerbandResource(VerbandService verbandService, VerbandRepository verbandRepository, VerbandQueryService verbandQueryService) {
        this.verbandService = verbandService;
        this.verbandRepository = verbandRepository;
        this.verbandQueryService = verbandQueryService;
    }

    /**
     * {@code POST  /verbands} : Create a new verband.
     *
     * @param verbandDTO the verbandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verbandDTO, or with status {@code 400 (Bad Request)} if the verband has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/verbands")
    public ResponseEntity<Verband> createVerband(@Valid @RequestBody Verband verbandDTO) throws URISyntaxException {
        log.info("REST request to save Verband : {}", verbandDTO);
        if (verbandDTO.getId() != null) {
            throw new BadRequestAlertException("A new verband cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Verband result = verbandService.save(verbandDTO);
        return ResponseEntity
            .created(new URI("/api/verbands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /verbands/:id} : Updates an existing verband.
     *
     * @param id         the id of the verbandDTO to save.
     * @param verbandDTO the verbandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verbandDTO,
     * or with status {@code 400 (Bad Request)} if the verbandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verbandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/verbands/{id}")
    public ResponseEntity<Verband> updateVerband(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verband verbandDTO
    ) throws URISyntaxException {
        log.info("REST request to update Verband : {}, {}", id, verbandDTO);
        if (verbandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verbandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verbandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Verband result = verbandService.save(verbandDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verbandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /verbands/:id} : Partial updates given fields of an existing verband, field will ignore if it is null
     *
     * @param id         the id of the verbandDTO to save.
     * @param verbandDTO the verbandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verbandDTO,
     * or with status {@code 400 (Bad Request)} if the verbandDTO is not valid,
     * or with status {@code 404 (Not Found)} if the verbandDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the verbandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/verbands/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Verband> partialUpdateVerband(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verband verbandDTO
    ) throws URISyntaxException {
        log.info("REST request to partial update Verband partially : {}, {}", id, verbandDTO);
        if (verbandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verbandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verbandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verband> result = verbandService.partialUpdate(verbandDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verbandDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /verbands} : get all the verbands.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verbands in body.
     */
    @GetMapping("/verbands")
    public ResponseEntity<List<Verband>> getAllVerbands(VerbandCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Verbands by criteria: {}", criteria);
        Page<Verband> page = verbandQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /verbands/count} : count all the verbands.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/verbands/count")
    public ResponseEntity<Long> countVerbands(VerbandCriteria criteria) {
        log.debug("REST request to count Verbands by criteria: {}", criteria);
        return ResponseEntity.ok().body(verbandQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /verbands/:id} : get the "id" verband.
     *
     * @param id the id of the verbandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verbandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/verbands/{id}")
    public ResponseEntity<Verband> getVerband(@PathVariable Long id) {
        log.debug("REST request to get Verband : {}", id);
        Optional<Verband> verbandDTO = verbandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(verbandDTO);
    }

    /**
     * {@code DELETE  /verbands/:id} : delete the "id" verband.
     *
     * @param id the id of the verbandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/verbands/{id}")
    public ResponseEntity<Void> deleteVerband(@PathVariable Long id) {
        log.debug("REST request to delete Verband : {}", id);
        verbandService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
