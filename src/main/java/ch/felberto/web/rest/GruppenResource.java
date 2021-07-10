package ch.felberto.web.rest;

import ch.felberto.domain.Gruppen;
import ch.felberto.repository.GruppenRepository;
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
 * REST controller for managing {@link ch.felberto.domain.Gruppen}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GruppenResource {

    private final Logger log = LoggerFactory.getLogger(GruppenResource.class);

    private static final String ENTITY_NAME = "gruppen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GruppenRepository gruppenRepository;

    public GruppenResource(GruppenRepository gruppenRepository) {
        this.gruppenRepository = gruppenRepository;
    }

    /**
     * {@code POST  /gruppens} : Create a new gruppen.
     *
     * @param gruppen the gruppen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gruppen, or with status {@code 400 (Bad Request)} if the gruppen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gruppens")
    public ResponseEntity<Gruppen> createGruppen(@RequestBody Gruppen gruppen) throws URISyntaxException {
        log.debug("REST request to save Gruppen : {}", gruppen);
        if (gruppen.getId() != null) {
            throw new BadRequestAlertException("A new gruppen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gruppen result = gruppenRepository.save(gruppen);
        return ResponseEntity
            .created(new URI("/api/gruppens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gruppens/:id} : Updates an existing gruppen.
     *
     * @param id the id of the gruppen to save.
     * @param gruppen the gruppen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gruppen,
     * or with status {@code 400 (Bad Request)} if the gruppen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gruppen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gruppens/{id}")
    public ResponseEntity<Gruppen> updateGruppen(@PathVariable(value = "id", required = false) final Long id, @RequestBody Gruppen gruppen)
        throws URISyntaxException {
        log.debug("REST request to update Gruppen : {}, {}", id, gruppen);
        if (gruppen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gruppen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gruppenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Gruppen result = gruppenRepository.save(gruppen);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gruppen.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gruppens/:id} : Partial updates given fields of an existing gruppen, field will ignore if it is null
     *
     * @param id the id of the gruppen to save.
     * @param gruppen the gruppen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gruppen,
     * or with status {@code 400 (Bad Request)} if the gruppen is not valid,
     * or with status {@code 404 (Not Found)} if the gruppen is not found,
     * or with status {@code 500 (Internal Server Error)} if the gruppen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gruppens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Gruppen> partialUpdateGruppen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Gruppen gruppen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gruppen partially : {}, {}", id, gruppen);
        if (gruppen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gruppen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gruppenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gruppen> result = gruppenRepository
            .findById(gruppen.getId())
            .map(
                existingGruppen -> {
                    if (gruppen.getName() != null) {
                        existingGruppen.setName(gruppen.getName());
                    }

                    return existingGruppen;
                }
            )
            .map(gruppenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gruppen.getId().toString())
        );
    }

    /**
     * {@code GET  /gruppens} : get all the gruppens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gruppens in body.
     */
    @GetMapping("/gruppens")
    public List<Gruppen> getAllGruppens() {
        log.debug("REST request to get all Gruppens");
        return gruppenRepository.findAll();
    }

    /**
     * {@code GET  /gruppens/:id} : get the "id" gruppen.
     *
     * @param id the id of the gruppen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gruppen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gruppens/{id}")
    public ResponseEntity<Gruppen> getGruppen(@PathVariable Long id) {
        log.debug("REST request to get Gruppen : {}", id);
        Optional<Gruppen> gruppen = gruppenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gruppen);
    }

    /**
     * {@code DELETE  /gruppens/:id} : delete the "id" gruppen.
     *
     * @param id the id of the gruppen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gruppens/{id}")
    public ResponseEntity<Void> deleteGruppen(@PathVariable Long id) {
        log.debug("REST request to delete Gruppen : {}", id);
        gruppenRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
