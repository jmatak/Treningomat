package com.errorsonogsvijeta.treningomat.model.administration;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class TrainerCommentRequest {
    private Integer id;
    private Attendant attendant;
    private Trainer trainer;

    public TrainerCommentRequest() {
    }

    public TrainerCommentRequest(Attendant attendant, Trainer trainer) {
        this.attendant = attendant;
        this.trainer = trainer;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainerCommentRequest that = (TrainerCommentRequest) o;
        return Objects.equals(attendant, that.attendant) &&
                Objects.equals(trainer, that.trainer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendant, trainer);
    }
}
