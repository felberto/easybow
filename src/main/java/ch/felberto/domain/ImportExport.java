package ch.felberto.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ImportExport {

    private Competition competition;

    private List<Round> roundList;

    private List<AthleteResult> athleteResultList;

    private Integer type;

    private Date vfDate;

    private Integer vfTime;

    private String vfOrt;

    private Number vfAnzahl;

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Round> getRoundList() {
        return roundList;
    }

    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }

    public List<AthleteResult> getAthleteResultList() {
        return athleteResultList;
    }

    public void setAthleteResultList(List<AthleteResult> athleteResultList) {
        this.athleteResultList = athleteResultList;
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
        return Objects.equals(competition, rangliste.competition) && Objects.equals(athleteResultList, rangliste.athleteResultList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competition, athleteResultList);
    }

    @Override
    public String toString() {
        return "RankingList{" + "competition=" + competition + ", athleteResultList=" + athleteResultList + '}';
    }
}
