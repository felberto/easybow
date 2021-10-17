package ch.felberto.web.rest;

import ch.felberto.domain.Rangliste;
import ch.felberto.service.RanglisteService;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.felberto.service.dto.RanglisteDTO}.
 */
@RestController
@RequestMapping("/api")
public class RanglisteResource {

    private final Logger log = LoggerFactory.getLogger(RanglisteResource.class);

    private final RanglisteService ranglisteService;

    public RanglisteResource(RanglisteService ranglisteService) {
        this.ranglisteService = ranglisteService;
    }

    /**
     * {@code POST  /rangliste/:id} : get the "id" wettkampf.
     *
     * @param wettkampfId the id of the wettkampfDTO to retrieve.
     * @param runden      the runden to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ranglisteDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/rangliste/{wettkampfId}")
    public ResponseEntity<Rangliste> createRangliste(@PathVariable Long wettkampfId, @RequestBody List<Integer> runden)
        throws DocumentException, FileNotFoundException {
        //TODO create service / get all schuetzen and fill ranglisteDTO / run algo for sorting
        log.debug("REST request to get Rangliste for Wettkampf : {} and runden : {}", wettkampfId, runden);
        //TODO aufruf resultatservice
        Optional<Rangliste> rangliste = Optional.ofNullable(ranglisteService.generateRangliste(wettkampfId, runden));
        return ResponseUtil.wrapOrNotFound(rangliste);
    }
}
