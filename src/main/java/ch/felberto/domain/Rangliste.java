package ch.felberto.domain;

import java.util.List;
import java.util.Objects;

public class Rangliste {

    private Wettkampf wettkampf;

    private List<SchuetzeResultat> schuetzeResultatList;

    public Wettkampf getWettkampf() {
        return wettkampf;
    }

    public void setWettkampf(Wettkampf wettkampf) {
        this.wettkampf = wettkampf;
    }

    public List<SchuetzeResultat> getSchuetzeResultatList() {
        return schuetzeResultatList;
    }

    public void setSchuetzeResultatList(List<SchuetzeResultat> schuetzeResultatList) {
        this.schuetzeResultatList = schuetzeResultatList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rangliste rangliste = (Rangliste) o;
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
