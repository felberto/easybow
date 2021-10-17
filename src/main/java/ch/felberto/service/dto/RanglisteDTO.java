package ch.felberto.service.dto;

import java.util.List;

public class RanglisteDTO {

    private WettkampfDTO wettkampfDTO;

    private List<SchuetzeResultatDTO> schuetzeResultatDTOList;

    public WettkampfDTO getWettkampfDTO() {
        return wettkampfDTO;
    }

    public void setWettkampfDTO(WettkampfDTO wettkampfDTO) {
        this.wettkampfDTO = wettkampfDTO;
    }

    public List<SchuetzeResultatDTO> getSchuetzeResultatDTOList() {
        return schuetzeResultatDTOList;
    }

    public void setSchuetzeResultatDTOList(List<SchuetzeResultatDTO> schuetzeResultatDTOList) {
        this.schuetzeResultatDTOList = schuetzeResultatDTOList;
    }
}
