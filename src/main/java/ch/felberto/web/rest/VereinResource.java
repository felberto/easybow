package ch.felberto.web.rest;

import ch.felberto.domain.Verein;
import ch.felberto.repository.VereinRepository;
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
 * REST controller for managing {@link ch.felberto.domain.Verein}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VereinResource {

    private final Logger log = LoggerFactory.getLogger(VereinResource.class);

    private static final String ENTITY_NAME = "verein";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VereinRepository vereinRepository;

    public VereinResource(VereinRepository vereinRepository) {
        this.vereinRepository = vereinRepository;
    }

    /**
     * {@code POST  /vereins} : Create a new verein.
     *
     * @param verein the verein to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new verein, or with status {@code 400 (Bad Request)} if the verein has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vereins")
    public ResponseEntity<Verein> createVerein(@RequestBody Verein verein) throws URISyntaxException {
        log.debug("REST request to save Verein : {}", verein);
        if (verein.getId() != null) {
            throw new BadRequestAlertException("A new verein cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Verein result = vereinRepository.save(verein);
        return ResponseEntity
            .created(new URI("/api/vereins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vereins/:id} : Updates an existing verein.
     *
     * @param id the id of the verein to save.
     * @param verein the verein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verein,
     * or with status {@code 400 (Bad Request)} if the verein is not valid,
     * or with status {@code 500 (Internal Server Error)} if the verein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vereins/{id}")
    public ResponseEntity<Verein> updateVerein(@PathVariable(value = "id", required = false) final Long id, @RequestBody Verein verein)
        throws URISyntaxException {
        log.debug("REST request to update Verein : {}, {}", id, verein);
        if (verein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vereinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Verein result = vereinRepository.save(verein);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verein.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vereins/:id} : Partial updates given fields of an existing verein, field will ignore if it is null
     *
     * @param id the id of the verein to save.
     * @param verein the verein to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated verein,
     * or with status {@code 400 (Bad Request)} if the verein is not valid,
     * or with status {@code 404 (Not Found)} if the verein is not found,
     * or with status {@code 500 (Internal Server Error)} if the verein couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vereins/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Verein> partialUpdateVerein(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Verein verein
    ) throws URISyntaxException {
        log.debug("REST request to partial update Verein partially : {}, {}", id, verein);
        if (verein.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, verein.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vereinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Verein> result = vereinRepository
            .findById(verein.getId())
            .map(
                existingVerein -> {
                    if (verein.getName() != null) {
                        existingVerein.setName(verein.getName());
                    }

                    return existingVerein;
                }
            )
            .map(vereinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, verein.getId().toString())
        );
    }

    /**
     * {@code GET  /vereins} : get all the vereins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vereins in body.
     */
    @GetMapping("/vereins")
    public List<Verein> getAllVereins() {
        log.debug("REST request to get all Vereins");
        return vereinRepository.findAll();
    }

    /**
     * {@code GET  /vereins/:id} : get the "id" verein.
     *
     * @param id the id of the verein to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the verein, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vereins/{id}")
    public ResponseEntity<Verein> getVerein(@PathVariable Long id) {
        log.debug("REST request to get Verein : {}", id);
        Optional<Verein> verein = vereinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(verein);
    }

    /**
     * {@code DELETE  /vereins/:id} : delete the "id" verein.
     *
     * @param id the id of the verein to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vereins/{id}")
    public ResponseEntity<Void> deleteVerein(@PathVariable Long id) {
        log.debug("REST request to delete Verein : {}", id);
        vereinRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
