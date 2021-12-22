package ch.felberto.service;

import ch.felberto.domain.Passen;
import ch.felberto.domain.Rangliste;
import ch.felberto.domain.Wettkampf;

/**
 * Service Interface for managing RanglisteDTO.
 */
public interface RanglisteService {
    /**
     * Generate rangliste.
     *
     * @param wettkampfId the id of wettkampf for the rangliste.
     * @param type        the type for the rangliste.
     * @return the persisted entity.
     */
    Rangliste generateRangliste(Long wettkampfId, Integer type);

    /**
     * get all schuetze by wettkampf
     *
     * @param wettkampf
     * @param type
     * @return ranglisteDTO
     */
    Rangliste getAllSchuetzesByWettkampf(Wettkampf wettkampf, Integer type);

    /**
     * sort list by 1 runde and 1 passe
     *
     * @param rangliste
     * @return ranglisteDTO
     */
    Rangliste sortRanglisteRunde1Passe1(Rangliste rangliste);

    /**
     * sort list by 1 runde and 2 passen
     *
     * @param rangliste
     * @param runde
     * @return ranglisteDTO
     */
    Rangliste sortRanglisteRunde1Passe2(Rangliste rangliste, Integer runde);

    /**
     * sort list by 2 runden and 1 passe
     *
     * @param rangliste
     * @return ranglisteDTO
     */
    Rangliste sortRanglisteRunde2Passe1(Rangliste rangliste);

    /**
     * sort list by 2 runden and 2 passen
     *
     * @param rangliste
     * @return ranglisteDTO
     */
    Rangliste sortRanglisteRunde2Passe2(Rangliste rangliste);

    /**
     * sort list by 2 runden, final and 1 passen
     *
     * @param rangliste
     * @return ranglisteDTO
     */
    Rangliste sortRanglisteRunde2FinalPasse1(Rangliste rangliste);

    /**
     * sort list by 2 runden, final and 2 passen
     *
     * @param rangliste
     * @return ranglisteDTO
     */
    Rangliste sortRanglisteRunde2FinalPasse2(Rangliste rangliste);

    /**
     * get tiefschuesse by number
     *
     * @param passen
     * @param number
     * @return ranglisteDTO
     */
    Integer getTiefschuesseForPasse(Passen passen, Integer number);

    /**
     * Create final.
     *
     * @param wettkampfId the id of wettkampf for the rangliste.
     * @param type        the type for the rangliste.
     * @return the persisted entity.
     */
    Rangliste createFinal(Long wettkampfId, Integer type);
}
