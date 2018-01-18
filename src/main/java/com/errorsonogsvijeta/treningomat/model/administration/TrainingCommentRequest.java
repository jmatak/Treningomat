package com.errorsonogsvijeta.treningomat.model.administration;

import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class TrainingCommentRequest {
    private Integer id;
    private Attendant attendant;
    private Training training;

    public TrainingCommentRequest() {
    }

    public TrainingCommentRequest(Attendant attendant, Training training) {
        this.attendant = attendant;
        this.training = training;
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
    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingCommentRequest request = (TrainingCommentRequest) o;
        return Objects.equals(attendant, request.attendant) &&
                Objects.equals(training, request.training);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attendant, training);
    }
}
