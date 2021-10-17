package ch.felberto.web.rest;

import ch.felberto.repository.RangierungRepository;
import ch.felberto.service.RangierungQueryService;
import ch.felberto.service.RangierungService;
import ch.felberto.service.criteria.RangierungCriteria;
import ch.felberto.service.dto.RangierungDTO;
import ch.felberto.service.mapper.WettkampfMapper;
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
 * REST controller for managing {@link ch.felberto.domain.Rangierung}.
 */
@RestController
@RequestMapping("/api")
public class RangierungResource {

    private final Logger log = LoggerFactory.getLogger(RangierungResource.class);

    private static final String ENTITY_NAME = "rangierung";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RangierungService rangierungService;

    private final RangierungRepository rangierungRepository;

    private final RangierungQueryService rangierungQueryService;

    private final WettkampfMapper wettkampfMapper;

    public RangierungResource(
        RangierungService rangierungService,
        RangierungRepository rangierungRepository,
        RangierungQueryService rangierungQueryService,
        WettkampfMapper wettkampfMapper
    ) {
        this.rangierungService = rangierungService;
        this.rangierungRepository = rangierungRepository;
        this.rangierungQueryService = rangierungQueryService;
        this.wettkampfMapper = wettkampfMapper;
    }

    /**
     * {@code POST  /rangierungs} : Create a new rangierung.
     *
     * @param rangierungDTO the rangierungDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rangierungDTO, or with status {@code 400 (Bad Request)} if the rangierung has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rangierungs")
    public ResponseEntity<RangierungDTO> createRangierung(@Valid @RequestBody RangierungDTO rangierungDTO) throws URISyntaxException {
        log.debug("REST request to save Rangierung : {}", rangierungDTO);
        if (rangierungDTO.getId() != null) {
            throw new BadRequestAlertException("A new rangierung cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RangierungDTO result = rangierungService.save(rangierungDTO);
        return ResponseEntity
            .created(new URI("/api/rangierungs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rangierungs/:id} : Updates an existing rangierung.
     *
     * @param id the id of the rangierungDTO to save.
     * @param rangierungDTO the rangierungDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rangierungDTO,
     * or with status {@code 400 (Bad Request)} if the rangierungDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rangierungDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rangierungs/{id}")
    public ResponseEntity<RangierungDTO> updateRangierung(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RangierungDTO rangierungDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rangierung : {}, {}", id, rangierungDTO);
        if (rangierungDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rangierungDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rangierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RangierungDTO result = rangierungService.save(rangierungDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rangierungDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rangierungs/:id} : Partial updates given fields of an existing rangierung, field will ignore if it is null
     *
     * @param id the id of the rangierungDTO to save.
     * @param rangierungDTO the rangierungDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rangierungDTO,
     * or with status {@code 400 (Bad Request)} if the rangierungDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rangierungDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rangierungDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rangierungs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RangierungDTO> partialUpdateRangierung(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RangierungDTO rangierungDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rangierung partially : {}, {}", id, rangierungDTO);
        if (rangierungDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rangierungDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rangierungRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RangierungDTO> result = rangierungService.partialUpdate(rangierungDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rangierungDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rangierungs} : get all the rangierungs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rangierungs in body.
     */
    @GetMapping("/rangierungs")
    public ResponseEntity<List<RangierungDTO>> getAllRangierungs(RangierungCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Rangierungs by criteria: {}", criteria);
        Page<RangierungDTO> page = rangierungQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rangierungs/count} : count all the rangierungs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rangierungs/count")
    public ResponseEntity<Long> countRangierungs(RangierungCriteria criteria) {
        log.debug("REST request to count Rangierungs by criteria: {}", criteria);
        return ResponseEntity.ok().body(rangierungQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rangierungs/:id} : get the "id" rangierung.
     *
     * @param id the id of the rangierungDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rangierungDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rangierungs/{id}")
    public ResponseEntity<RangierungDTO> getRangierung(@PathVariable Long id) {
        log.debug("REST request to get Rangierung : {}", id);
        Optional<RangierungDTO> rangierungDTO = rangierungService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rangierungDTO);
    }

    /**
     * {@code GET  /rangierungs/:wettkampf} : get the "wettkampf" rangierung.
     *
     * @param wettkampfid the id of the wettkampf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rangierungDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rangierungs/wettkampf/{wettkampfid}")
    public ResponseEntity<List<RangierungDTO>> getRangierungByWettkampf(@PathVariable Long wettkampfid) {
        log.debug("REST request to get Rangierung : {}", wettkampfid);

        return ResponseEntity.ok().body(rangierungService.findByWettkampf(wettkampfid));
    }

    /**
     * {@code DELETE  /rangierungs/:id} : delete the "id" rangierung.
     *
     * @param id the id of the rangierungDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rangierungs/{id}")
    public ResponseEntity<Void> deleteRangierung(@PathVariable Long id) {
        log.debug("REST request to delete Rangierung : {}", id);
        rangierungService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code DELETE  /rangierungs/wettkampf/:id} : delete by wettkampf.
     *
     * @param id the id of the wettkampf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rangierungs/wettkampf/{id}")
    public ResponseEntity<Void> deleteRangierungByWettkampf(@PathVariable Long id) {
        log.debug("REST request to delete Rangierung by Wettkampf : {}", id);
        rangierungService.deleteByWettkampf(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
