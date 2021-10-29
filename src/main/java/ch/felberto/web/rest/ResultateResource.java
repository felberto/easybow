package ch.felberto.web.rest;

import ch.felberto.domain.Resultate;
import ch.felberto.repository.ResultateRepository;
import ch.felberto.service.ResultateQueryService;
import ch.felberto.service.ResultateService;
import ch.felberto.service.criteria.ResultateCriteria;
import ch.felberto.service.dto.ResultateDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.felberto.domain.Resultate}.
 */
@RestController
@RequestMapping("/api")
public class ResultateResource {

    private final Logger log = LoggerFactory.getLogger(ResultateResource.class);

    private static final String ENTITY_NAME = "resultate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultateService resultateService;

    private final ResultateRepository resultateRepository;

    private final ResultateQueryService resultateQueryService;

    public ResultateResource(
        ResultateService resultateService,
        ResultateRepository resultateRepository,
        ResultateQueryService resultateQueryService
    ) {
        this.resultateService = resultateService;
        this.resultateRepository = resultateRepository;
        this.resultateQueryService = resultateQueryService;
    }

    //TODO gemsamtresultat updaten wenn passe hinzugefügt oder geupdatet wird

    /**
     * {@code POST  /resultates} : Create a new resultate.
     *
     * @param resultateDTO the resultateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultateDTO, or with status {@code 400 (Bad Request)} if the resultate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultates")
    public ResponseEntity<ResultateDTO> createResultate(@Valid @RequestBody ResultateDTO resultateDTO) throws URISyntaxException {
        log.debug("REST request to save Resultate : {}", resultateDTO);
        if (resultateDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultateDTO result = resultateService.save(resultateDTO);
        return ResponseEntity
            .created(new URI("/api/resultates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultates/:id} : Updates an existing resultate.
     *
     * @param id the id of the resultateDTO to save.
     * @param resultateDTO the resultateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultateDTO,
     * or with status {@code 400 (Bad Request)} if the resultateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultates/{id}")
    public ResponseEntity<ResultateDTO> updateResultate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ResultateDTO resultateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Resultate : {}, {}", id, resultateDTO);
        if (resultateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResultateDTO result = resultateService.save(resultateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /resultates/:id} : Partial updates given fields of an existing resultate, field will ignore if it is null
     *
     * @param id the id of the resultateDTO to save.
     * @param resultateDTO the resultateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultateDTO,
     * or with status {@code 400 (Bad Request)} if the resultateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the resultateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/resultates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResultateDTO> partialUpdateResultate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ResultateDTO resultateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Resultate partially : {}, {}", id, resultateDTO);
        if (resultateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResultateDTO> result = resultateService.partialUpdate(resultateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /resultates} : get all the resultates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultates in body.
     */
    @GetMapping("/resultates")
    public ResponseEntity<List<ResultateDTO>> getAllResultates(ResultateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Resultates by criteria: {}", criteria);
        Page<ResultateDTO> page = resultateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resultates/count} : count all the resultates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/resultates/count")
    public ResponseEntity<Long> countResultates(ResultateCriteria criteria) {
        log.debug("REST request to count Resultates by criteria: {}", criteria);
        return ResponseEntity.ok().body(resultateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /resultates/:id} : get the "id" resultate.
     *
     * @param id the id of the resultateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultates/{id}")
    public ResponseEntity<Resultate> getResultate(@PathVariable Long id) {
        log.debug("REST request to get Resultate : {}", id);
        Optional<Resultate> resultate = resultateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultate);
    }

    /**
     * {@code GET  /resultates/:wettkampf} : get the "wettkampf" resultate.
     *
     * @param wettkampfId the id of the wettkampf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultates/wettkampf/{wettkampfId}")
    public ResponseEntity<List<ResultateDTO>> getResultateByWettkampf(@PathVariable Long wettkampfId) {
        log.debug("REST request to get Resultate : {}", wettkampfId);

        return ResponseEntity.ok().body(resultateService.findByWettkampf(wettkampfId));
    }

    /**
     * {@code DELETE  /resultates/:id} : delete the "id" resultate.
     *
     * @param id the id of the resultateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultates/{id}")
    public ResponseEntity<Void> deleteResultate(@PathVariable Long id) {
        log.debug("REST request to delete Resultate : {}", id);
        resultateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code DELETE  /resultates/schuetze/:id} : delete the schuetze resultate.
     *
     * @param id the id of schuetze of the resultateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultates/schuetze/{id}")
    public ResponseEntity<Void> deleteResultateBySchuetze(@PathVariable Long id) {
        log.debug("REST request to delete Resultate : {}", id);
        resultateService.deleteBySchuetze(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
