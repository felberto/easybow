package ch.felberto.service.dto;

import java.util.List;

public class SchuetzeResultatDTO {

    private SchuetzeDTO schuetzeDTO;

    private List<ResultateDTO> resultateDTOList;

    private Integer resultat;

    public SchuetzeDTO getSchuetzeDTO() {
        return schuetzeDTO;
    }

    public void setSchuetzeDTO(SchuetzeDTO schuetzeDTO) {
        this.schuetzeDTO = schuetzeDTO;
    }

    public List<ResultateDTO> getResultateDTOList() {
        return resultateDTOList;
    }

    public void setResultateDTOList(List<ResultateDTO> resultateDTOList) {
        this.resultateDTOList = resultateDTOList;
    }

    public Integer getResultat() {
        return resultat;
    }

    public void setResultat(Integer resultat) {
        this.resultat = resultat;
    }
}
