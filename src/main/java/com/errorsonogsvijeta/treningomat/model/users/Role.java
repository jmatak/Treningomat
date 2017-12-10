package com.errorsonogsvijeta.treningomat.model.users;

import javax.persistence.*;

@Entity
public class Role {
    private Integer id;
    private String role;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
