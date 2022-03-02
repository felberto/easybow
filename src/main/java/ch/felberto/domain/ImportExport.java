package ch.felberto.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ImportExport {

    private Wettkampf wettkampf;

    private List<Runde> runden;

    private List<SchuetzeResultat> schuetzeResultatList;

    private Integer type;

    private Date vfDate;

    private Integer vfTime;

    private String vfOrt;

    private Number vfAnzahl;

    public Wettkampf getWettkampf() {
        return wettkampf;
    }

    public void setWettkampf(Wettkampf wettkampf) {
        this.wettkampf = wettkampf;
    }

    public List<Runde> getRunden() {
        return runden;
    }

    public void setRunden(List<Runde> runden) {
        this.runden = runden;
    }

    public List<SchuetzeResultat> getSchuetzeResultatList() {
        return schuetzeResultatList;
    }

    public void setSchuetzeResultatList(List<SchuetzeResultat> schuetzeResultatList) {
        this.schuetzeResultatList = schuetzeResultatList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getVfDate() {
        return vfDate;
    }

    public void setVfDate(Date vfDate) {
        this.vfDate = vfDate;
    }

    public Integer getVfTime() {
        return vfTime;
    }

    public void setVfTime(Integer vfTime) {
        this.vfTime = vfTime;
    }

    public String getVfOrt() {
        return vfOrt;
    }

    public void setVfOrt(String vfOrt) {
        this.vfOrt = vfOrt;
    }

    public Number getVfAnzahl() {
        return vfAnzahl;
    }

    public void setVfAnzahl(Number vfAnzahl) {
        this.vfAnzahl = vfAnzahl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportExport rangliste = (ImportExport) o;
        return Objects.equals(wettkampf, rangliste.wettkampf) && Objects.equals(schuetzeResultatList, rangliste.schuetzeResultatList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wettkampf, schuetzeResultatList);
    }

    @Override
    public String toString() {
        return "Rangliste{" + "wettkampf=" + wettkampf + ", schuetzeResultatList=" + schuetzeResultatList + '}';
    }
}
