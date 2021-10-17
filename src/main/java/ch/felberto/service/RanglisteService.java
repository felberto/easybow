package ch.felberto.service;

import ch.felberto.domain.Rangliste;
import ch.felberto.domain.Wettkampf;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Service Interface for managing RanglisteDTO.
 */
public interface RanglisteService {
    /**
     * Generate rangliste.
     *
     * @param wettkampfId the id of wettkampf for the rangliste.
     * @param runden      the id of runden for the rangliste.
     * @return the persisted entity.
     */
    Rangliste generateRangliste(Long wettkampfId, List<Integer> runden) throws DocumentException, FileNotFoundException;

    /**
     * get all schuetze by wettkampf
     *
     * @param wettkampf
     * @param runden
     * @return ranglisteDTO
     */
    Rangliste getAllSchuetzesByWettkampf(Wettkampf wettkampf, List<Integer> runden);

    /**
     * sort list by rangierung
     *
     * @param rangliste
     * @param wettkampf
     * @return ranglisteDTO
     */
    Rangliste sortRangliste(Rangliste rangliste, Wettkampf wettkampf);

    /**
     * generate pdf document
     *
     * @param rangliste
     */
    void generatePdf(Rangliste rangliste, List<Integer> runden) throws FileNotFoundException, DocumentException;
}
