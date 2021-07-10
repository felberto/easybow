package ch.felberto.web.rest;

import ch.felberto.domain.Schuetze;
import ch.felberto.repository.SchuetzeRepository;
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
 * REST controller for managing {@link ch.felberto.domain.Schuetze}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SchuetzeResource {

    private final Logger log = LoggerFactory.getLogger(SchuetzeResource.class);

    private static final String ENTITY_NAME = "schuetze";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchuetzeRepository schuetzeRepository;

    public SchuetzeResource(SchuetzeRepository schuetzeRepository) {
        this.schuetzeRepository = schuetzeRepository;
    }

    /**
     * {@code POST  /schuetzes} : Create a new schuetze.
     *
     * @param schuetze the schuetze to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schuetze, or with status {@code 400 (Bad Request)} if the schuetze has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schuetzes")
    public ResponseEntity<Schuetze> createSchuetze(@Valid @RequestBody Schuetze schuetze) throws URISyntaxException {
        log.debug("REST request to save Schuetze : {}", schuetze);
        if (schuetze.getId() != null) {
            throw new BadRequestAlertException("A new schuetze cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Schuetze result = schuetzeRepository.save(schuetze);
        return ResponseEntity
            .created(new URI("/api/schuetzes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schuetzes/:id} : Updates an existing schuetze.
     *
     * @param id the id of the schuetze to save.
     * @param schuetze the schuetze to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schuetze,
     * or with status {@code 400 (Bad Request)} if the schuetze is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schuetze couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schuetzes/{id}")
    public ResponseEntity<Schuetze> updateSchuetze(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Schuetze schuetze
    ) throws URISyntaxException {
        log.debug("REST request to update Schuetze : {}, {}", id, schuetze);
        if (schuetze.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schuetze.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schuetzeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Schuetze result = schuetzeRepository.save(schuetze);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schuetze.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /schuetzes/:id} : Partial updates given fields of an existing schuetze, field will ignore if it is null
     *
     * @param id the id of the schuetze to save.
     * @param schuetze the schuetze to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schuetze,
     * or with status {@code 400 (Bad Request)} if the schuetze is not valid,
     * or with status {@code 404 (Not Found)} if the schuetze is not found,
     * or with status {@code 500 (Internal Server Error)} if the schuetze couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/schuetzes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Schuetze> partialUpdateSchuetze(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Schuetze schuetze
    ) throws URISyntaxException {
        log.debug("REST request to partial update Schuetze partially : {}, {}", id, schuetze);
        if (schuetze.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schuetze.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schuetzeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Schuetze> result = schuetzeRepository
            .findById(schuetze.getId())
            .map(
                existingSchuetze -> {
                    if (schuetze.getName() != null) {
                        existingSchuetze.setName(schuetze.getName());
                    }
                    if (schuetze.getJahrgang() != null) {
                        existingSchuetze.setJahrgang(schuetze.getJahrgang());
                    }
                    if (schuetze.getStellung() != null) {
                        existingSchuetze.setStellung(schuetze.getStellung());
                    }

                    return existingSchuetze;
                }
            )
            .map(schuetzeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schuetze.getId().toString())
        );
    }

    /**
     * {@code GET  /schuetzes} : get all the schuetzes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schuetzes in body.
     */
    @GetMapping("/schuetzes")
    public List<Schuetze> getAllSchuetzes() {
        log.debug("REST request to get all Schuetzes");
        return schuetzeRepository.findAll();
    }

    /**
     * {@code GET  /schuetzes/:id} : get the "id" schuetze.
     *
     * @param id the id of the schuetze to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schuetze, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schuetzes/{id}")
    public ResponseEntity<Schuetze> getSchuetze(@PathVariable Long id) {
        log.debug("REST request to get Schuetze : {}", id);
        Optional<Schuetze> schuetze = schuetzeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(schuetze);
    }

    /**
     * {@code DELETE  /schuetzes/:id} : delete the "id" schuetze.
     *
     * @param id the id of the schuetze to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schuetzes/{id}")
    public ResponseEntity<Void> deleteSchuetze(@PathVariable Long id) {
        log.debug("REST request to delete Schuetze : {}", id);
        schuetzeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
