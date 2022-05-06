package ch.felberto.web.rest;

import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.service.WettkampfQueryService;
import ch.felberto.service.WettkampfService;
import ch.felberto.service.criteria.WettkampfCriteria;
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
 * REST controller for managing {@link ch.felberto.domain.Wettkampf}.
 */
@RestController
@RequestMapping("/api")
public class WettkampfResource {

    private final Logger log = LoggerFactory.getLogger(WettkampfResource.class);

    private static final String ENTITY_NAME = "wettkampf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WettkampfService wettkampfService;

    private final WettkampfRepository wettkampfRepository;

    private final WettkampfQueryService wettkampfQueryService;

    public WettkampfResource(
        WettkampfService wettkampfService,
        WettkampfRepository wettkampfRepository,
        WettkampfQueryService wettkampfQueryService
    ) {
        this.wettkampfService = wettkampfService;
        this.wettkampfRepository = wettkampfRepository;
        this.wettkampfQueryService = wettkampfQueryService;
    }

    /**
     * {@code POST  /wettkampfs} : Create a new wettkampf.
     *
     * @param wettkampfDTO the wettkampfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wettkampfDTO, or with status {@code 400 (Bad Request)} if the wettkampf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wettkampfs")
    public ResponseEntity<Wettkampf> createWettkampf(@Valid @RequestBody Wettkampf wettkampfDTO) throws URISyntaxException {
        log.info("REST request to save Wettkampf : {}", wettkampfDTO);
        if (wettkampfDTO.getId() != null) {
            throw new BadRequestAlertException("A new wettkampf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wettkampf result = wettkampfService.save(wettkampfDTO);
        return ResponseEntity
            .created(new URI("/api/wettkampfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wettkampfs/:id} : Updates an existing wettkampf.
     *
     * @param id           the id of the wettkampfDTO to save.
     * @param wettkampfDTO the wettkampfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wettkampfDTO,
     * or with status {@code 400 (Bad Request)} if the wettkampfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wettkampfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wettkampfs/{id}")
    public ResponseEntity<Wettkampf> updateWettkampf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Wettkampf wettkampfDTO
    ) throws URISyntaxException {
        log.info("REST request to update Wettkampf : {}, {}", id, wettkampfDTO);
        if (wettkampfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wettkampfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wettkampfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Wettkampf result = wettkampfService.save(wettkampfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wettkampfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /wettkampfs/:id} : Partial updates given fields of an existing wettkampf, field will ignore if it is null
     *
     * @param id           the id of the wettkampfDTO to save.
     * @param wettkampfDTO the wettkampfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wettkampfDTO,
     * or with status {@code 400 (Bad Request)} if the wettkampfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the wettkampfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the wettkampfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wettkampfs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Wettkampf> partialUpdateWettkampf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Wettkampf wettkampfDTO
    ) throws URISyntaxException {
        log.info("REST request to partial update Wettkampf partially : {}, {}", id, wettkampfDTO);
        if (wettkampfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wettkampfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wettkampfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wettkampf> result = wettkampfService.partialUpdate(wettkampfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wettkampfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /wettkampfs} : get all the wettkampfs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wettkampfs in body.
     */
    @GetMapping("/wettkampfs")
    public ResponseEntity<List<Wettkampf>> getAllWettkampfs(WettkampfCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Wettkampfs by criteria: {}", criteria);
        Page<Wettkampf> page = wettkampfQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/wettkampfs/jahr/{jahr}")
    public ResponseEntity<List<Wettkampf>> getAllWettkampfByJahr(@PathVariable Integer jahr) {
        log.debug("REST request to get Wettkampfs by jahr: {}", jahr);
        List<Wettkampf> wettkampfList = wettkampfService.findByJahr(jahr);
        return ResponseEntity.ok().body(wettkampfList);
    }

    /**
     * {@code GET  /wettkampfs/count} : count all the wettkampfs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wettkampfs/count")
    public ResponseEntity<Long> countWettkampfs(WettkampfCriteria criteria) {
        log.debug("REST request to count Wettkampfs by criteria: {}", criteria);
        return ResponseEntity.ok().body(wettkampfQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wettkampfs/:id} : get the "id" wettkampf.
     *
     * @param id the id of the wettkampfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wettkampfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wettkampfs/{id}")
    public ResponseEntity<Wettkampf> getWettkampf(@PathVariable Long id) {
        log.debug("REST request to get Wettkampf : {}", id);
        Optional<Wettkampf> wettkampfDTO = wettkampfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wettkampfDTO);
    }

    /**
     * {@code DELETE  /wettkampfs/:id} : delete the "id" wettkampf.
     *
     * @param id the id of the wettkampfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wettkampfs/{id}")
    public ResponseEntity<Void> deleteWettkampf(@PathVariable Long id) {
        log.debug("REST request to delete Wettkampf : {}", id);
        wettkampfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
