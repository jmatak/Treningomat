package com.errorsonogsvijeta.treningomat.model.administration;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;

import javax.persistence.*;

@Entity
public class SubscriptionWarning {
    private int id;
    private Attendant attendant;
    private TrainingGroup trainingGroup;

    public SubscriptionWarning() {
    }

    public SubscriptionWarning(Attendant attendant, TrainingGroup trainingGroup) {
        this.attendant = attendant;
        this.trainingGroup = trainingGroup;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public TrainingGroup getTrainingGroup() {
        return trainingGroup;
    }

    public void setTrainingGroup(TrainingGroup trainingGroup) {
        this.trainingGroup = trainingGroup;
    }

    public String message() {
        return String.format("Molimo platite pretplatu za grupu %s - %s",
                trainingGroup.getName(),
                trainingGroup.getTrainer().fullName()
        );
    }
}
