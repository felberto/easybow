package ch.felberto.web.rest;

import ch.felberto.domain.Rangierung;
import ch.felberto.repository.RangierungRepository;
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
 * REST controller for managing {@link ch.felberto.domain.Rangierung}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RangierungResource {

    private final Logger log = LoggerFactory.getLogger(RangierungResource.class);

    private static final String ENTITY_NAME = "rangierung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RangierungRepository rangierungRepository;

    public RangierungResource(RangierungRepository rangierungRepository) {
        this.rangierungRepository = rangierungRepository;
    }

    /**
     * {@code POST  /rangierungs} : Create a new rangierung.
     *
     * @param rangierung the rangierung to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rangierung, or with status {@code 400 (Bad Request)} if the rangierung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rangierungs")
    public ResponseEntity<Rangierung> createRangierung(@Valid @RequestBody Rangierung rangierung) throws URISyntaxException {
        log.debug("REST request to save Rangierung : {}", rangierung);
        if (rangierung.getId() != null) {
            throw new BadRequestAlertException("A new rangierung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rangierung result = rangierungRepository.save(rangierung);
        return ResponseEntity
            .created(new URI("/api/rangierungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rangierungs/:id} : Updates an existing rangierung.
     *
     * @param id the id of the rangierung to save.
     * @param rangierung the rangierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rangierung,
     * or with status {@code 400 (Bad Request)} if the rangierung is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rangierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rangierungs/{id}")
    public ResponseEntity<Rangierung> updateRangierung(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Rangierung rangierung
    ) throws URISyntaxException {
        log.debug("REST request to update Rangierung : {}, {}", id, rangierung);
        if (rangierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rangierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rangierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rangierung result = rangierungRepository.save(rangierung);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rangierung.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rangierungs/:id} : Partial updates given fields of an existing rangierung, field will ignore if it is null
     *
     * @param id the id of the rangierung to save.
     * @param rangierung the rangierung to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rangierung,
     * or with status {@code 400 (Bad Request)} if the rangierung is not valid,
     * or with status {@code 404 (Not Found)} if the rangierung is not found,
     * or with status {@code 500 (Internal Server Error)} if the rangierung couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rangierungs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Rangierung> partialUpdateRangierung(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Rangierung rangierung
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rangierung partially : {}, {}", id, rangierung);
        if (rangierung.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rangierung.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rangierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rangierung> result = rangierungRepository
            .findById(rangierung.getId())
            .map(
                existingRangierung -> {
                    if (rangierung.getPosition() != null) {
                        existingRangierung.setPosition(rangierung.getPosition());
                    }
                    if (rangierung.getRangierungskriterien() != null) {
                        existingRangierung.setRangierungskriterien(rangierung.getRangierungskriterien());
                    }

                    return existingRangierung;
                }
            )
            .map(rangierungRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rangierung.getId().toString())
        );
    }

    /**
     * {@code GET  /rangierungs} : get all the rangierungs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rangierungs in body.
     */
    @GetMapping("/rangierungs")
    public List<Rangierung> getAllRangierungs() {
        log.debug("REST request to get all Rangierungs");
        return rangierungRepository.findAll();
    }

    /**
     * {@code GET  /rangierungs/:id} : get the "id" rangierung.
     *
     * @param id the id of the rangierung to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rangierung, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rangierungs/{id}")
    public ResponseEntity<Rangierung> getRangierung(@PathVariable Long id) {
        log.debug("REST request to get Rangierung : {}", id);
        Optional<Rangierung> rangierung = rangierungRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rangierung);
    }

    /**
     * {@code DELETE  /rangierungs/:id} : delete the "id" rangierung.
     *
     * @param id the id of the rangierung to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rangierungs/{id}")
    public ResponseEntity<Void> deleteRangierung(@PathVariable Long id) {
        log.debug("REST request to delete Rangierung : {}", id);
        rangierungRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
