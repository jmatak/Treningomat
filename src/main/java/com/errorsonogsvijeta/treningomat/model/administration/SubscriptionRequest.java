package com.errorsonogsvijeta.treningomat.model.administration;

import com.errorsonogsvijeta.treningomat.model.calendar.MonthYear;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;

import javax.persistence.*;

@Entity
public class SubscriptionRequest {
    private Integer id;
    private Attendant attendant;
    private MonthYear month;


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
    public MonthYear getMonth() {
        return month;
    }

    public void setMonth(MonthYear month) {
        this.month = month;
    }
}
