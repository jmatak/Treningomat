package com.errorsonogsvijeta.treningomat.model.geo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Region {

    private Integer id;
    private String name;
    private List<City> cities;

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @OneToMany(mappedBy = "region")
    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
