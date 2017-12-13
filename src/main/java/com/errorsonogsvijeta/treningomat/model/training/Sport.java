package com.errorsonogsvijeta.treningomat.model.training;

import javax.persistence.*;

@Entity
public class Sport {

    private Integer id;
    private String name;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
