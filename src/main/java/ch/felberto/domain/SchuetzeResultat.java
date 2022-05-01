package ch.felberto.domain;

import java.util.List;
import java.util.Objects;

public class SchuetzeResultat {

    private Schuetze schuetze;

    private List<Resultate> resultateList;

    private Integer resultat;

    private Integer resultatRunde1;

    private Integer resultatRunde2;

    private Integer resultatFinal;

    private Integer resultatRunde1Passe1;

    private Integer resultatRunde1Passe2;

    private Integer resultatRunde2Passe1;

    private Integer resultatRunde2Passe2;

    private Integer resultatFinalPasse1;

    private Integer resultatFinalPasse2;

    private Integer t11;
    private Integer t10;
    private Integer t9;
    private Integer t8;
    private Integer t7;
    private Integer t6;
    private Integer t5;
    private Integer t4;
    private Integer t3;
    private Integer t2;
    private Integer t1;
    private Integer t0;

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

    public Integer getResultatRunde1() {
        return resultatRunde1;
    }

    public void setResultatRunde1(Integer resultatRunde1) {
        this.resultatRunde1 = resultatRunde1;
    }

    public Integer getResultatRunde2() {
        return resultatRunde2;
    }

    public void setResultatRunde2(Integer resultatRunde2) {
        this.resultatRunde2 = resultatRunde2;
    }

    public Integer getResultatRunde1Passe1() {
        return resultatRunde1Passe1;
    }

    public void setResultatRunde1Passe1(Integer resultatRunde1Passe1) {
        this.resultatRunde1Passe1 = resultatRunde1Passe1;
    }

    public Integer getResultatRunde1Passe2() {
        return resultatRunde1Passe2;
    }

    public void setResultatRunde1Passe2(Integer resultatRunde1Passe2) {
        this.resultatRunde1Passe2 = resultatRunde1Passe2;
    }

    public Integer getResultatRunde2Passe1() {
        return resultatRunde2Passe1;
    }

    public void setResultatRunde2Passe1(Integer resultatRunde2Passe1) {
        this.resultatRunde2Passe1 = resultatRunde2Passe1;
    }

    public Integer getResultatRunde2Passe2() {
        return resultatRunde2Passe2;
    }

    public void setResultatRunde2Passe2(Integer resultatRunde2Passe2) {
        this.resultatRunde2Passe2 = resultatRunde2Passe2;
    }

    public Integer getResultatFinal() {
        return resultatFinal;
    }

    public void setResultatFinal(Integer resultatFinal) {
        this.resultatFinal = resultatFinal;
    }

    public Integer getResultatFinalPasse1() {
        return resultatFinalPasse1;
    }

    public void setResultatFinalPasse1(Integer resultatFinalPasse1) {
        this.resultatFinalPasse1 = resultatFinalPasse1;
    }

    public Integer getResultatFinalPasse2() {
        return resultatFinalPasse2;
    }

    public void setResultatFinalPasse2(Integer resultatFinalPasse2) {
        this.resultatFinalPasse2 = resultatFinalPasse2;
    }

    public Integer getT11() {
        return t11;
    }

    public void setT11(Integer t11) {
        this.t11 = t11;
    }

    public Integer getT10() {
        return t10;
    }

    public void setT10(Integer t10) {
        this.t10 = t10;
    }

    public Integer getT9() {
        return t9;
    }

    public void setT9(Integer t9) {
        this.t9 = t9;
    }

    public Integer getT8() {
        return t8;
    }

    public void setT8(Integer t8) {
        this.t8 = t8;
    }

    public Integer getT7() {
        return t7;
    }

    public void setT7(Integer t7) {
        this.t7 = t7;
    }

    public Integer getT6() {
        return t6;
    }

    public void setT6(Integer t6) {
        this.t6 = t6;
    }

    public Integer getT5() {
        return t5;
    }

    public void setT5(Integer t5) {
        this.t5 = t5;
    }

    public Integer getT4() {
        return t4;
    }

    public void setT4(Integer t4) {
        this.t4 = t4;
    }

    public Integer getT3() {
        return t3;
    }

    public void setT3(Integer t3) {
        this.t3 = t3;
    }

    public Integer getT2() {
        return t2;
    }

    public void setT2(Integer t2) {
        this.t2 = t2;
    }

    public Integer getT1() {
        return t1;
    }

    public void setT1(Integer t1) {
        this.t1 = t1;
    }

    public Integer getT0() {
        return t0;
    }

    public void setT0(Integer t0) {
        this.t0 = t0;
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
