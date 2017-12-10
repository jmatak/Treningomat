package com.errorsonogsvijeta.treningomat.model.calendar;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
public class MonthYear {
    private Integer id;
    private Integer month;
    private Integer year;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    @Range(min = 1, max = 12)
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Column
    @Range(min = 2000, max = 2100)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
