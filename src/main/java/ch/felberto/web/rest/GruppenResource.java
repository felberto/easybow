package ch.felberto.web.rest;

import ch.felberto.domain.Gruppen;
import ch.felberto.repository.GruppenRepository;
import ch.felberto.service.GruppenQueryService;
import ch.felberto.service.GruppenService;
import ch.felberto.service.criteria.GruppenCriteria;
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
 * REST controller for managing {@link ch.felberto.domain.Gruppen}.
 */
@RestController
@RequestMapping("/api")
public class GruppenResource {

    private final Logger log = LoggerFactory.getLogger(GruppenResource.class);

    private static final String ENTITY_NAME = "gruppen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GruppenService gruppenService;

    private final GruppenRepository gruppenRepository;

    private final GruppenQueryService gruppenQueryService;

    public GruppenResource(GruppenService gruppenService, GruppenRepository gruppenRepository, GruppenQueryService gruppenQueryService) {
        this.gruppenService = gruppenService;
        this.gruppenRepository = gruppenRepository;
        this.gruppenQueryService = gruppenQueryService;
    }

    /**
     * {@code POST  /gruppens} : Create a new gruppen.
     *
     * @param gruppenDTO the gruppenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gruppenDTO, or with status {@code 400 (Bad Request)} if the gruppen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gruppens")
    public ResponseEntity<Gruppen> createGruppen(@Valid @RequestBody Gruppen gruppenDTO) throws URISyntaxException {
        log.debug("REST request to save Gruppen : {}", gruppenDTO);
        if (gruppenDTO.getId() != null) {
            throw new BadRequestAlertException("A new gruppen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gruppen result = gruppenService.save(gruppenDTO);
        return ResponseEntity
            .created(new URI("/api/gruppens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gruppens/:id} : Updates an existing gruppen.
     *
     * @param id         the id of the gruppenDTO to save.
     * @param gruppenDTO the gruppenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gruppenDTO,
     * or with status {@code 400 (Bad Request)} if the gruppenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gruppenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gruppens/{id}")
    public ResponseEntity<Gruppen> updateGruppen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Gruppen gruppenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Gruppen : {}, {}", id, gruppenDTO);
        if (gruppenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gruppenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gruppenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Gruppen result = gruppenService.save(gruppenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gruppenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gruppens/:id} : Partial updates given fields of an existing gruppen, field will ignore if it is null
     *
     * @param id         the id of the gruppenDTO to save.
     * @param gruppenDTO the gruppenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gruppenDTO,
     * or with status {@code 400 (Bad Request)} if the gruppenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gruppenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gruppenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gruppens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Gruppen> partialUpdateGruppen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Gruppen gruppenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gruppen partially : {}, {}", id, gruppenDTO);
        if (gruppenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gruppenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gruppenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gruppen> result = gruppenService.partialUpdate(gruppenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gruppenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gruppens} : get all the gruppens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gruppens in body.
     */
    @GetMapping("/gruppens")
    public ResponseEntity<List<Gruppen>> getAllGruppens(GruppenCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Gruppens by criteria: {}", criteria);
        Page<Gruppen> page = gruppenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gruppens/count} : count all the gruppens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/gruppens/count")
    public ResponseEntity<Long> countGruppens(GruppenCriteria criteria) {
        log.debug("REST request to count Gruppens by criteria: {}", criteria);
        return ResponseEntity.ok().body(gruppenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /gruppens/:id} : get the "id" gruppen.
     *
     * @param id the id of the gruppenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gruppenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gruppens/{id}")
    public ResponseEntity<Gruppen> getGruppen(@PathVariable Long id) {
        log.debug("REST request to get Gruppen : {}", id);
        Optional<Gruppen> gruppenDTO = gruppenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gruppenDTO);
    }

    /**
     * {@code DELETE  /gruppens/:id} : delete the "id" gruppen.
     *
     * @param id the id of the gruppenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gruppens/{id}")
    public ResponseEntity<Void> deleteGruppen(@PathVariable Long id) {
        log.debug("REST request to delete Gruppen : {}", id);
        gruppenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
