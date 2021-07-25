package ch.felberto.web.rest;

import ch.felberto.repository.PassenRepository;
import ch.felberto.service.PassenService;
import ch.felberto.service.dto.PassenDTO;
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
 * REST controller for managing {@link ch.felberto.domain.Passen}.
 */
@RestController
@RequestMapping("/api")
public class PassenResource {

    private final Logger log = LoggerFactory.getLogger(PassenResource.class);

    private static final String ENTITY_NAME = "passen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PassenService passenService;

    private final PassenRepository passenRepository;

    public PassenResource(PassenService passenService, PassenRepository passenRepository) {
        this.passenService = passenService;
        this.passenRepository = passenRepository;
    }

    /**
     * {@code POST  /passens} : Create a new passen.
     *
     * @param passenDTO the passenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new passenDTO, or with status {@code 400 (Bad Request)} if the passen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/passens")
    public ResponseEntity<PassenDTO> createPassen(@Valid @RequestBody PassenDTO passenDTO) throws URISyntaxException {
        log.debug("REST request to save Passen : {}", passenDTO);
        if (passenDTO.getId() != null) {
            throw new BadRequestAlertException("A new passen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PassenDTO result = passenService.save(passenDTO);
        return ResponseEntity
            .created(new URI("/api/passens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /passens/:id} : Updates an existing passen.
     *
     * @param id the id of the passenDTO to save.
     * @param passenDTO the passenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passenDTO,
     * or with status {@code 400 (Bad Request)} if the passenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the passenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/passens/{id}")
    public ResponseEntity<PassenDTO> updatePassen(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PassenDTO passenDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Passen : {}, {}", id, passenDTO);
        if (passenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PassenDTO result = passenService.save(passenDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, passenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /passens/:id} : Partial updates given fields of an existing passen, field will ignore if it is null
     *
     * @param id the id of the passenDTO to save.
     * @param passenDTO the passenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passenDTO,
     * or with status {@code 400 (Bad Request)} if the passenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the passenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the passenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/passens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PassenDTO> partialUpdatePassen(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PassenDTO passenDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Passen partially : {}, {}", id, passenDTO);
        if (passenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PassenDTO> result = passenService.partialUpdate(passenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, passenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /passens} : get all the passens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of passens in body.
     */
    @GetMapping("/passens")
    public ResponseEntity<List<PassenDTO>> getAllPassens(Pageable pageable) {
        log.debug("REST request to get a page of Passens");
        Page<PassenDTO> page = passenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /passens/:id} : get the "id" passen.
     *
     * @param id the id of the passenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the passenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/passens/{id}")
    public ResponseEntity<PassenDTO> getPassen(@PathVariable Long id) {
        log.debug("REST request to get Passen : {}", id);
        Optional<PassenDTO> passenDTO = passenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(passenDTO);
    }

    /**
     * {@code DELETE  /passens/:id} : delete the "id" passen.
     *
     * @param id the id of the passenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/passens/{id}")
    public ResponseEntity<Void> deletePassen(@PathVariable Long id) {
        log.debug("REST request to delete Passen : {}", id);
        passenService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
