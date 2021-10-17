package ch.felberto.domain;

import java.util.List;
import java.util.Objects;

public class SchuetzeResultat {

    private Schuetze schuetze;

    private List<Resultate> resultateList;

    private Integer resultat;

    public Schuetze getSchuetze() {
        return schuetze;
    }

    public void setSchuetze(Schuetze schuetze) {
        this.schuetze = schuetze;
    }

    public List<Resultate> getResultateList() {
        return resultateList;
    }

    public void setResultateList(List<Resultate> resultateList) {
        this.resultateList = resultateList;
    }

    public Integer getResultat() {
        return resultat;
    }

    public void setResultat(Integer resultat) {
        this.resultat = resultat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchuetzeResultat that = (SchuetzeResultat) o;
        return (
            Objects.equals(schuetze, that.schuetze) &&
            Objects.equals(resultateList, that.resultateList) &&
            Objects.equals(resultat, that.resultat)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(schuetze, resultateList, resultat);
    }

    @Override
    public String toString() {
        return "SchuetzeResultat{" + "schuetze=" + schuetze + ", resultateList=" + resultateList + ", resultat=" + resultat + '}';
    }
}
