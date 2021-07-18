package ch.felberto.web.rest;

import ch.felberto.domain.Resultate;
import ch.felberto.repository.ResultateRepository;
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
 * REST controller for managing {@link ch.felberto.domain.Resultate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResultateResource {

    private final Logger log = LoggerFactory.getLogger(ResultateResource.class);

    private static final String ENTITY_NAME = "resultate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultateRepository resultateRepository;

    public ResultateResource(ResultateRepository resultateRepository) {
        this.resultateRepository = resultateRepository;
    }

    /**
     * {@code POST  /resultates} : Create a new resultate.
     *
     * @param resultate the resultate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultate, or with status {@code 400 (Bad Request)} if the resultate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultates")
    public ResponseEntity<Resultate> createResultate(@Valid @RequestBody Resultate resultate) throws URISyntaxException {
        log.debug("REST request to save Resultate : {}", resultate);
        if (resultate.getId() != null) {
            throw new BadRequestAlertException("A new resultate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resultate result = resultateRepository.save(resultate);
        return ResponseEntity
            .created(new URI("/api/resultates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultates/:id} : Updates an existing resultate.
     *
     * @param id the id of the resultate to save.
     * @param resultate the resultate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultate,
     * or with status {@code 400 (Bad Request)} if the resultate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultates/{id}")
    public ResponseEntity<Resultate> updateResultate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Resultate resultate
    ) throws URISyntaxException {
        log.debug("REST request to update Resultate : {}, {}", id, resultate);
        if (resultate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Resultate result = resultateRepository.save(resultate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resultates/:id} : Partial updates given fields of an existing resultate, field will ignore if it is null
     *
     * @param id the id of the resultate to save.
     * @param resultate the resultate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultate,
     * or with status {@code 400 (Bad Request)} if the resultate is not valid,
     * or with status {@code 404 (Not Found)} if the resultate is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resultates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Resultate> partialUpdateResultate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Resultate resultate
    ) throws URISyntaxException {
        log.debug("REST request to partial update Resultate partially : {}, {}", id, resultate);
        if (resultate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Resultate> result = resultateRepository
            .findById(resultate.getId())
            .map(
                existingResultate -> {
                    if (resultate.getRunde() != null) {
                        existingResultate.setRunde(resultate.getRunde());
                    }

                    return existingResultate;
                }
            )
            .map(resultateRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultate.getId().toString())
        );
    }

    /**
     * {@code GET  /resultates} : get all the resultates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultates in body.
     */
    @GetMapping("/resultates")
    public List<Resultate> getAllResultates() {
        log.debug("REST request to get all Resultates");
        return resultateRepository.findAll();
    }

    /**
     * {@code GET  /resultates/:id} : get the "id" resultate.
     *
     * @param id the id of the resultate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultates/{id}")
    public ResponseEntity<Resultate> getResultate(@PathVariable Long id) {
        log.debug("REST request to get Resultate : {}", id);
        Optional<Resultate> resultate = resultateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultate);
    }

    /**
     * {@code DELETE  /resultates/:id} : delete the "id" resultate.
     *
     * @param id the id of the resultate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultates/{id}")
    public ResponseEntity<Void> deleteResultate(@PathVariable Long id) {
        log.debug("REST request to delete Resultate : {}", id);
        resultateRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
