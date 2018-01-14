package com.errorsonogsvijeta.treningomat.model.training;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Sport {

    private Integer id;
    private String name;
    private String description;

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

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sport sport = (Sport) o;
        return Objects.equals(id, sport.id) &&
                Objects.equals(name, sport.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
