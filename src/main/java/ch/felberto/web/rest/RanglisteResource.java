package ch.felberto.web.rest;

import ch.felberto.domain.Rangliste;
import ch.felberto.service.RanglistePrintService;
import ch.felberto.service.RanglisteService;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ch.felberto.domain.Rangliste}.
 */
@RestController
@RequestMapping("/api")
public class RanglisteResource {

    private final Logger log = LoggerFactory.getLogger(RanglisteResource.class);

    private static final String ENTITY_NAME = "rangliste";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RanglisteService ranglisteService;
    private final RanglistePrintService ranglistePrintService;

    public RanglisteResource(RanglisteService ranglisteService, RanglistePrintService ranglistePrintService) {
        this.ranglisteService = ranglisteService;
        this.ranglistePrintService = ranglistePrintService;
    }

    /**
     * {@code POST  /rangliste/:id} : get the "id" wettkampf.
     *
     * @param wettkampfId the id of the wettkampfDTO to retrieve.
     * @param type        the type to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ranglisteDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/rangliste/{wettkampfId}")
    public ResponseEntity<Rangliste> createRangliste(@PathVariable Long wettkampfId, @RequestBody Integer type) {
        log.debug("REST request to get Rangliste for Wettkampf : {} and type : {}", wettkampfId, type);
        Optional<Rangliste> rangliste = Optional.ofNullable(ranglisteService.generateRangliste(wettkampfId, type));
        return ResponseUtil.wrapOrNotFound(rangliste);
    }

    /**
     * {@code POST  /rangliste/final/:id} : get the "id" wettkampf.
     *
     * @param wettkampfId the id of the wettkampfDTO to retrieve.
     * @param type        the type to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ranglisteDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/rangliste/final/{wettkampfId}")
    public ResponseEntity<Rangliste> createFinal(@PathVariable Long wettkampfId, @RequestBody Integer type) {
        log.debug("REST request to create final for Wettkampf : {} and type : {}", wettkampfId, type);
        Optional<Rangliste> rangliste = Optional.ofNullable(ranglisteService.createFinal(wettkampfId, type));
        return ResponseUtil.wrapOrNotFound(rangliste);
    }

    /**
     * {@code POST  /rangliste/print} : print rangliste.
     *
     * @param rangliste rangliste
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ranglisteDTO, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/rangliste/print")
    public ResponseEntity<Resource> printRangliste(@RequestBody Rangliste rangliste) throws IOException, DocumentException {
        log.debug("REST request to print Rangliste : {}", rangliste);
        try {
            Path file = Paths.get(ranglistePrintService.generatePdf(rangliste).getAbsolutePath());
            Resource resource = new UrlResource((file.toUri()));
            Path path = resource.getFile().toPath();
            if (Files.exists(file)) {
                return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
                /*response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();*/

            }
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
