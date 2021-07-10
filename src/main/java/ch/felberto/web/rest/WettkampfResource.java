package ch.felberto.web.rest;

import ch.felberto.domain.Wettkampf;
import ch.felberto.repository.WettkampfRepository;
import ch.felberto.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.felberto.domain.Wettkampf}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WettkampfResource {

    private final Logger log = LoggerFactory.getLogger(WettkampfResource.class);

    private static final String ENTITY_NAME = "wettkampf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WettkampfRepository wettkampfRepository;

    public WettkampfResource(WettkampfRepository wettkampfRepository) {
        this.wettkampfRepository = wettkampfRepository;
    }

    /**
     * {@code POST  /wettkampfs} : Create a new wettkampf.
     *
     * @param wettkampf the wettkampf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wettkampf, or with status {@code 400 (Bad Request)} if the wettkampf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wettkampfs")
    public ResponseEntity<Wettkampf> createWettkampf(@RequestBody Wettkampf wettkampf) throws URISyntaxException {
        log.debug("REST request to save Wettkampf : {}", wettkampf);
        if (wettkampf.getId() != null) {
            throw new BadRequestAlertException("A new wettkampf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wettkampf result = wettkampfRepository.save(wettkampf);
        return ResponseEntity
            .created(new URI("/api/wettkampfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wettkampfs/:id} : Updates an existing wettkampf.
     *
     * @param id the id of the wettkampf to save.
     * @param wettkampf the wettkampf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wettkampf,
     * or with status {@code 400 (Bad Request)} if the wettkampf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wettkampf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wettkampfs/{id}")
    public ResponseEntity<Wettkampf> updateWettkampf(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wettkampf wettkampf
    ) throws URISyntaxException {
        log.debug("REST request to update Wettkampf : {}, {}", id, wettkampf);
        if (wettkampf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wettkampf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wettkampfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Wettkampf result = wettkampfRepository.save(wettkampf);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wettkampf.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /wettkampfs/:id} : Partial updates given fields of an existing wettkampf, field will ignore if it is null
     *
     * @param id the id of the wettkampf to save.
     * @param wettkampf the wettkampf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wettkampf,
     * or with status {@code 400 (Bad Request)} if the wettkampf is not valid,
     * or with status {@code 404 (Not Found)} if the wettkampf is not found,
     * or with status {@code 500 (Internal Server Error)} if the wettkampf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wettkampfs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Wettkampf> partialUpdateWettkampf(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Wettkampf wettkampf
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wettkampf partially : {}, {}", id, wettkampf);
        if (wettkampf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wettkampf.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wettkampfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wettkampf> result = wettkampfRepository
            .findById(wettkampf.getId())
            .map(
                existingWettkampf -> {
                    if (wettkampf.getName() != null) {
                        existingWettkampf.setName(wettkampf.getName());
                    }
                    if (wettkampf.getJahr() != null) {
                        existingWettkampf.setJahr(wettkampf.getJahr());
                    }
                    if (wettkampf.getAnzahlPassen() != null) {
                        existingWettkampf.setAnzahlPassen(wettkampf.getAnzahlPassen());
                    }
                    if (wettkampf.getTeam() != null) {
                        existingWettkampf.setTeam(wettkampf.getTeam());
                    }
                    if (wettkampf.getTemplate() != null) {
                        existingWettkampf.setTemplate(wettkampf.getTemplate());
                    }

                    return existingWettkampf;
                }
            )
            .map(wettkampfRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wettkampf.getId().toString())
        );
    }

    /**
     * {@code GET  /wettkampfs} : get all the wettkampfs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wettkampfs in body.
     */
    @GetMapping("/wettkampfs")
    public List<Wettkampf> getAllWettkampfs() {
        log.debug("REST request to get all Wettkampfs");
        return wettkampfRepository.findAll();
    }

    /**
     * {@code GET  /wettkampfs/:id} : get the "id" wettkampf.
     *
     * @param id the id of the wettkampf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wettkampf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wettkampfs/{id}")
    public ResponseEntity<Wettkampf> getWettkampf(@PathVariable Long id) {
        log.debug("REST request to get Wettkampf : {}", id);
        Optional<Wettkampf> wettkampf = wettkampfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(wettkampf);
    }

    /**
     * {@code DELETE  /wettkampfs/:id} : delete the "id" wettkampf.
     *
     * @param id the id of the wettkampf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wettkampfs/{id}")
    public ResponseEntity<Void> deleteWettkampf(@PathVariable Long id) {
        log.debug("REST request to delete Wettkampf : {}", id);
        wettkampfRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
