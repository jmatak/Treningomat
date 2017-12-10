package com.errorsonogsvijeta.treningomat.model.calendar;

import com.errorsonogsvijeta.treningomat.model.training.Training;

import javax.persistence.*;
import java.util.List;

@Entity
public class Calendar {
    private Integer id;
    private List<Training> terms;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany
    public List<Training> getTerms() {
        return terms;
    }

    public void setTerms(List<Training> terms) {
        this.terms = terms;
    }
}
