package ch.felberto.domain;

import java.util.List;
import java.util.Objects;

public class GroupAthleteResult {

    private Group group;

    private List<AthleteResult> athleteResultList;

    private Integer result;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<AthleteResult> getAthleteResultList() {
        return athleteResultList;
    }

    public void setAthleteResultList(List<AthleteResult> athleteResultList) {
        this.athleteResultList = athleteResultList;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupAthleteResult groupAthleteResult = (GroupAthleteResult) o;
        return Objects.equals(group, groupAthleteResult.group) && Objects.equals(athleteResultList, groupAthleteResult.athleteResultList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, athleteResultList);
    }

    @Override
    public String toString() {
        return "GroupAthleteList{" + "group=" + group + ", athleteResultList=" + athleteResultList + '}';
    }
}
