package com.errorsonogsvijeta.treningomat.model.administration;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class GroupRequest {
    private Integer id;
    private Attendant attendant;
    private TrainingGroup toTrainingGroup;

    public GroupRequest() {
    }

    public GroupRequest(Attendant attendant, TrainingGroup toTrainingGroup) {
        this.attendant = attendant;
        this.toTrainingGroup = toTrainingGroup;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public Attendant getAttendant() {
        return attendant;
    }

    public void setAttendant(Attendant attendant) {
        this.attendant = attendant;
    }

    @ManyToOne
    public TrainingGroup getToTrainingGroup() {
        return toTrainingGroup;
    }

    public void setToTrainingGroup(TrainingGroup toTrainingGroup) {
        this.toTrainingGroup = toTrainingGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupRequest that = (GroupRequest) o;
        return Objects.equals(attendant, that.attendant) &&
                Objects.equals(toTrainingGroup, that.toTrainingGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendant, toTrainingGroup);
    }
}
