package com.errorsonogsvijeta.treningomat.model.training;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Training {
    private Integer id;
    private TrainingGroup trainingGroup;
    private Date startAt;
    private Date endsAt;
    private List<Attendant> attendants;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public TrainingGroup getTrainingGroup() {
        return trainingGroup;
    }

    public void setTrainingGroup(TrainingGroup trainingGroup) {
        this.trainingGroup = trainingGroup;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    @ManyToMany
    public List<Attendant> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<Attendant> attendants) {
        this.attendants = attendants;
    }

    @Column(length = 4096)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
