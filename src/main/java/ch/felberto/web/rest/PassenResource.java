package ch.felberto.web.rest;

import ch.felberto.domain.Passen;
import ch.felberto.repository.PassenRepository;
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
 * REST controller for managing {@link ch.felberto.domain.Passen}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PassenResource {

    private final Logger log = LoggerFactory.getLogger(PassenResource.class);

    private static final String ENTITY_NAME = "passen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PassenRepository passenRepository;

    public PassenResource(PassenRepository passenRepository) {
        this.passenRepository = passenRepository;
    }

    /**
     * {@code POST  /passens} : Create a new passen.
     *
     * @param passen the passen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new passen, or with status {@code 400 (Bad Request)} if the passen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/passens")
    public ResponseEntity<Passen> createPassen(@RequestBody Passen passen) throws URISyntaxException {
        log.debug("REST request to save Passen : {}", passen);
        if (passen.getId() != null) {
            throw new BadRequestAlertException("A new passen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Passen result = passenRepository.save(passen);
        return ResponseEntity
            .created(new URI("/api/passens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /passens/:id} : Updates an existing passen.
     *
     * @param id the id of the passen to save.
     * @param passen the passen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passen,
     * or with status {@code 400 (Bad Request)} if the passen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the passen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/passens/{id}")
    public ResponseEntity<Passen> updatePassen(@PathVariable(value = "id", required = false) final Long id, @RequestBody Passen passen)
        throws URISyntaxException {
        log.debug("REST request to update Passen : {}, {}", id, passen);
        if (passen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Passen result = passenRepository.save(passen);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, passen.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /passens/:id} : Partial updates given fields of an existing passen, field will ignore if it is null
     *
     * @param id the id of the passen to save.
     * @param passen the passen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passen,
     * or with status {@code 400 (Bad Request)} if the passen is not valid,
     * or with status {@code 404 (Not Found)} if the passen is not found,
     * or with status {@code 500 (Internal Server Error)} if the passen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/passens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Passen> partialUpdatePassen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Passen passen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Passen partially : {}, {}", id, passen);
        if (passen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Passen> result = passenRepository
            .findById(passen.getId())
            .map(
                existingPassen -> {
                    if (passen.getp1() != null) {
                        existingPassen.setp1(passen.getp1());
                    }
                    if (passen.getp2() != null) {
                        existingPassen.setp2(passen.getp2());
                    }
                    if (passen.getp3() != null) {
                        existingPassen.setp3(passen.getp3());
                    }
                    if (passen.getp4() != null) {
                        existingPassen.setp4(passen.getp4());
                    }
                    if (passen.getp5() != null) {
                        existingPassen.setp5(passen.getp5());
                    }
                    if (passen.getp6() != null) {
                        existingPassen.setp6(passen.getp6());
                    }
                    if (passen.getp7() != null) {
                        existingPassen.setp7(passen.getp7());
                    }
                    if (passen.getp8() != null) {
                        existingPassen.setp8(passen.getp8());
                    }
                    if (passen.getp9() != null) {
                        existingPassen.setp9(passen.getp9());
                    }
                    if (passen.getp10() != null) {
                        existingPassen.setp10(passen.getp10());
                    }
                    if (passen.getResultat() != null) {
                        existingPassen.setResultat(passen.getResultat());
                    }

                    return existingPassen;
                }
            )
            .map(passenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, passen.getId().toString())
        );
    }

    /**
     * {@code GET  /passens} : get all the passens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of passens in body.
     */
    @GetMapping("/passens")
    public List<Passen> getAllPassens() {
        log.debug("REST request to get all Passens");
        return passenRepository.findAll();
    }

    /**
     * {@code GET  /passens/:id} : get the "id" passen.
     *
     * @param id the id of the passen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the passen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/passens/{id}")
    public ResponseEntity<Passen> getPassen(@PathVariable Long id) {
        log.debug("REST request to get Passen : {}", id);
        Optional<Passen> passen = passenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(passen);
    }

    /**
     * {@code DELETE  /passens/:id} : delete the "id" passen.
     *
     * @param id the id of the passen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/passens/{id}")
    public ResponseEntity<Void> deletePassen(@PathVariable Long id) {
        log.debug("REST request to delete Passen : {}", id);
        passenRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
