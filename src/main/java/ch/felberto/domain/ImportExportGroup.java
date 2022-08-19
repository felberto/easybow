package ch.felberto.domain;

import java.util.List;
import java.util.Objects;

public class ImportExportGroup {

    private Competition competition;

    private List<Round> roundList;

    private List<GroupAthleteResult> groupAthleteResultList;

    private Integer type;

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

    public List<GroupAthleteResult> getGroupAthleteResultList() {
        return groupAthleteResultList;
    }

    public void setGroupAthleteResultList(List<GroupAthleteResult> groupAthleteResultList) {
        this.groupAthleteResultList = groupAthleteResultList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportExportGroup rangliste = (ImportExportGroup) o;
        return (
            Objects.equals(competition, rangliste.competition) && Objects.equals(groupAthleteResultList, rangliste.groupAthleteResultList)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(competition, groupAthleteResultList);
    }

    @Override
    public String toString() {
        return "RankingList{" + "competition=" + competition + ", groupAthleteResultList=" + groupAthleteResultList + '}';
    }
}
