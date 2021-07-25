package ch.felberto.web.rest;

import ch.felberto.repository.SchuetzeRepository;
import ch.felberto.service.SchuetzeQueryService;
import ch.felberto.service.SchuetzeService;
import ch.felberto.service.criteria.SchuetzeCriteria;
import ch.felberto.service.dto.SchuetzeDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.felberto.domain.Schuetze}.
 */
@RestController
@RequestMapping("/api")
public class SchuetzeResource {

    private final Logger log = LoggerFactory.getLogger(SchuetzeResource.class);

    private static final String ENTITY_NAME = "schuetze";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchuetzeService schuetzeService;

    private final SchuetzeRepository schuetzeRepository;

    private final SchuetzeQueryService schuetzeQueryService;

    public SchuetzeResource(
        SchuetzeService schuetzeService,
        SchuetzeRepository schuetzeRepository,
        SchuetzeQueryService schuetzeQueryService
    ) {
        this.schuetzeService = schuetzeService;
        this.schuetzeRepository = schuetzeRepository;
        this.schuetzeQueryService = schuetzeQueryService;
    }

    /**
     * {@code POST  /schuetzes} : Create a new schuetze.
     *
     * @param schuetzeDTO the schuetzeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schuetzeDTO, or with status {@code 400 (Bad Request)} if the schuetze has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schuetzes")
    public ResponseEntity<SchuetzeDTO> createSchuetze(@Valid @RequestBody SchuetzeDTO schuetzeDTO) throws URISyntaxException {
        log.debug("REST request to save Schuetze : {}", schuetzeDTO);
        if (schuetzeDTO.getId() != null) {
            throw new BadRequestAlertException("A new schuetze cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchuetzeDTO result = schuetzeService.save(schuetzeDTO);
        return ResponseEntity
            .created(new URI("/api/schuetzes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schuetzes/:id} : Updates an existing schuetze.
     *
     * @param id the id of the schuetzeDTO to save.
     * @param schuetzeDTO the schuetzeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schuetzeDTO,
     * or with status {@code 400 (Bad Request)} if the schuetzeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schuetzeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schuetzes/{id}")
    public ResponseEntity<SchuetzeDTO> updateSchuetze(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchuetzeDTO schuetzeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Schuetze : {}, {}", id, schuetzeDTO);
        if (schuetzeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schuetzeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schuetzeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SchuetzeDTO result = schuetzeService.save(schuetzeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schuetzeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /schuetzes/:id} : Partial updates given fields of an existing schuetze, field will ignore if it is null
     *
     * @param id the id of the schuetzeDTO to save.
     * @param schuetzeDTO the schuetzeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schuetzeDTO,
     * or with status {@code 400 (Bad Request)} if the schuetzeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the schuetzeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the schuetzeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/schuetzes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SchuetzeDTO> partialUpdateSchuetze(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchuetzeDTO schuetzeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Schuetze partially : {}, {}", id, schuetzeDTO);
        if (schuetzeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schuetzeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schuetzeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SchuetzeDTO> result = schuetzeService.partialUpdate(schuetzeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schuetzeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /schuetzes} : get all the schuetzes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schuetzes in body.
     */
    @GetMapping("/schuetzes")
    public ResponseEntity<List<SchuetzeDTO>> getAllSchuetzes(SchuetzeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Schuetzes by criteria: {}", criteria);
        Page<SchuetzeDTO> page = schuetzeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schuetzes/count} : count all the schuetzes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/schuetzes/count")
    public ResponseEntity<Long> countSchuetzes(SchuetzeCriteria criteria) {
        log.debug("REST request to count Schuetzes by criteria: {}", criteria);
        return ResponseEntity.ok().body(schuetzeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /schuetzes/:id} : get the "id" schuetze.
     *
     * @param id the id of the schuetzeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schuetzeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schuetzes/{id}")
    public ResponseEntity<SchuetzeDTO> getSchuetze(@PathVariable Long id) {
        log.debug("REST request to get Schuetze : {}", id);
        Optional<SchuetzeDTO> schuetzeDTO = schuetzeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schuetzeDTO);
    }

    /**
     * {@code DELETE  /schuetzes/:id} : delete the "id" schuetze.
     *
     * @param id the id of the schuetzeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schuetzes/{id}")
    public ResponseEntity<Void> deleteSchuetze(@PathVariable Long id) {
        log.debug("REST request to delete Schuetze : {}", id);
        schuetzeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
