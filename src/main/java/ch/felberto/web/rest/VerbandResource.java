package ch.felberto.web.rest;

import ch.felberto.domain.Verband;
import ch.felberto.repository.VerbandRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.felberto.domain.Verband}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VerbandResource {

    private final Logger log = LoggerFactory.getLogger(VerbandResource.class);

    private static final String ENTITY_NAME = "verband";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VerbandRepository verbandRepository;

    public VerbandResource(VerbandRepository verbandRepository) {
        this.verbandRepository = verbandRepository;
    }

    /**
     * {@code POST  /verbands} : Create a new verband.
     *
     * @param verband the verband to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verband, or with status {@code 400 (Bad Request)} if the verband has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/verbands")
    public ResponseEntity<Verband> createVerband(@Valid @RequestBody Verband verband) throws URISyntaxException {
        log.debug("REST request to save Verband : {}", verband);
        if (verband.getId() != null) {
            throw new BadRequestAlertException("A new verband cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Verband result = verbandRepository.save(verband);
        return ResponseEntity
            .created(new URI("/api/verbands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /verbands/:id} : Updates an existing verband.
     *
     * @param id the id of the verband to save.
     * @param verband the verband to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verband,
     * or with status {@code 400 (Bad Request)} if the verband is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verband couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/verbands/{id}")
    public ResponseEntity<Verband> updateVerband(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Verband verband
    ) throws URISyntaxException {
        log.debug("REST request to update Verband : {}, {}", id, verband);
        if (verband.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verband.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verbandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Verband result = verbandRepository.save(verband);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verband.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /verbands/:id} : Partial updates given fields of an existing verband, field will ignore if it is null
     *
     * @param id the id of the verband to save.
     * @param verband the verband to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verband,
     * or with status {@code 400 (Bad Request)} if the verband is not valid,
     * or with status {@code 404 (Not Found)} if the verband is not found,
     * or with status {@code 500 (Internal Server Error)} if the verband couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/verbands/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Verband> partialUpdateVerband(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Verband verband
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verband partially : {}, {}", id, verband);
        if (verband.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verband.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!verbandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verband> result = verbandRepository
            .findById(verband.getId())
            .map(
                existingVerband -> {
                    if (verband.getName() != null) {
                        existingVerband.setName(verband.getName());
                    }

                    return existingVerband;
                }
            )
            .map(verbandRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verband.getId().toString())
        );
    }

    /**
     * {@code GET  /verbands} : get all the verbands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of verbands in body.
     */
    @GetMapping("/verbands")
    public List<Verband> getAllVerbands() {
        log.debug("REST request to get all Verbands");
        return verbandRepository.findAll();
    }

    /**
     * {@code GET  /verbands/:id} : get the "id" verband.
     *
     * @param id the id of the verband to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verband, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/verbands/{id}")
    public ResponseEntity<Verband> getVerband(@PathVariable Long id) {
        log.debug("REST request to get Verband : {}", id);
        Optional<Verband> verband = verbandRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verband);
    }

    /**
     * {@code DELETE  /verbands/:id} : delete the "id" verband.
     *
     * @param id the id of the verband to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/verbands/{id}")
    public ResponseEntity<Void> deleteVerband(@PathVariable Long id) {
        log.debug("REST request to delete Verband : {}", id);
        verbandRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
