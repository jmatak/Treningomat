package com.errorsonogsvijeta.treningomat.model.administration;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;

import javax.persistence.*;

@Entity
public class GroupRequest {
    private Integer id;
    private Attendant attendant;
    private TrainingGroup fromTrainingGroup;
    private TrainingGroup toTrainingGroup;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
    public TrainingGroup getFromTrainingGroup() {
        return fromTrainingGroup;
    }

    public void setFromTrainingGroup(TrainingGroup fromTrainingGroup) {
        this.fromTrainingGroup = fromTrainingGroup;
    }

    @ManyToOne
    public TrainingGroup getToTrainingGroup() {
        return toTrainingGroup;
    }

    public void setToTrainingGroup(TrainingGroup toTrainingGroup) {
        this.toTrainingGroup = toTrainingGroup;
    }
}
