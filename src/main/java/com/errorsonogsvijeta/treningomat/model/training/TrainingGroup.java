package com.errorsonogsvijeta.treningomat.model.training;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class TrainingGroup {
    private Integer id;
    private String name;
    private Sport sport;
    private String place;
    private Trainer trainer;
    private List<Attendant> attendants;
    private Integer capacity;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Column
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @ManyToOne
    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @ManyToMany
    public List<Attendant> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<Attendant> attendants) {
        this.attendants = attendants;
    }

    @Column(nullable = false)
    @Min(0)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
