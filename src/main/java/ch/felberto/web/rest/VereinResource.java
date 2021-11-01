package ch.felberto.web.rest;

import ch.felberto.domain.Verein;
import ch.felberto.repository.VereinRepository;
import ch.felberto.service.VereinQueryService;
import ch.felberto.service.VereinService;
import ch.felberto.service.criteria.VereinCriteria;
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
 * REST controller for managing {@link ch.felberto.domain.Verein}.
 */
@RestController
@RequestMapping("/api")
public class VereinResource {

    private final Logger log = LoggerFactory.getLogger(VereinResource.class);

    private static final String ENTITY_NAME = "verein";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VereinService vereinService;

    private final VereinRepository vereinRepository;

    private final VereinQueryService vereinQueryService;

    public VereinResource(VereinService vereinService, VereinRepository vereinRepository, VereinQueryService vereinQueryService) {
        this.vereinService = vereinService;
        this.vereinRepository = vereinRepository;
        this.vereinQueryService = vereinQueryService;
    }

    /**
     * {@code POST  /vereins} : Create a new verein.
     *
     * @param vereinDTO the vereinDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vereinDTO, or with status {@code 400 (Bad Request)} if the verein has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vereins")
    public ResponseEntity<Verein> createVerein(@Valid @RequestBody Verein vereinDTO) throws URISyntaxException {
        log.debug("REST request to save Verein : {}", vereinDTO);
        if (vereinDTO.getId() != null) {
            throw new BadRequestAlertException("A new verein cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Verein result = vereinService.save(vereinDTO);
        return ResponseEntity
            .created(new URI("/api/vereins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vereins/:id} : Updates an existing verein.
     *
     * @param id        the id of the vereinDTO to save.
     * @param vereinDTO the vereinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vereinDTO,
     * or with status {@code 400 (Bad Request)} if the vereinDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vereinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vereins/{id}")
    public ResponseEntity<Verein> updateVerein(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verein vereinDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Verein : {}, {}", id, vereinDTO);
        if (vereinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vereinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vereinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Verein result = vereinService.save(vereinDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vereinDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vereins/:id} : Partial updates given fields of an existing verein, field will ignore if it is null
     *
     * @param id        the id of the vereinDTO to save.
     * @param vereinDTO the vereinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vereinDTO,
     * or with status {@code 400 (Bad Request)} if the vereinDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vereinDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vereinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vereins/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Verein> partialUpdateVerein(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verein vereinDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verein partially : {}, {}", id, vereinDTO);
        if (vereinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vereinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vereinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verein> result = vereinService.partialUpdate(vereinDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vereinDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vereins} : get all the vereins.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vereins in body.
     */
    @GetMapping("/vereins")
    public ResponseEntity<List<Verein>> getAllVereins(VereinCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vereins by criteria: {}", criteria);
        Page<Verein> page = vereinQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vereins/count} : count all the vereins.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vereins/count")
    public ResponseEntity<Long> countVereins(VereinCriteria criteria) {
        log.debug("REST request to count Vereins by criteria: {}", criteria);
        return ResponseEntity.ok().body(vereinQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vereins/:id} : get the "id" verein.
     *
     * @param id the id of the vereinDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vereinDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vereins/{id}")
    public ResponseEntity<Verein> getVerein(@PathVariable Long id) {
        log.debug("REST request to get Verein : {}", id);
        Optional<Verein> vereinDTO = vereinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vereinDTO);
    }

    /**
     * {@code DELETE  /vereins/:id} : delete the "id" verein.
     *
     * @param id the id of the vereinDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vereins/{id}")
    public ResponseEntity<Void> deleteVerein(@PathVariable Long id) {
        log.debug("REST request to delete Verein : {}", id);
        vereinService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
