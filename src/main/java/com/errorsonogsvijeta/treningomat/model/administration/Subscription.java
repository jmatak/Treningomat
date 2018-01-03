package com.errorsonogsvijeta.treningomat.model.administration;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Subscription {
    private int id;
    private TrainingGroup group;
    private Attendant attendant;
    private Date subscriptionStart;
    private Date subscriptionEnd;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public TrainingGroup getGroup() {
        return group;
    }

    public void setGroup(TrainingGroup group) {
        this.group = group;
    }

    @ManyToOne
    public Attendant getAttendant() {
        return attendant;
    }

    public void setAttendant(Attendant attendant) {
        this.attendant = attendant;
    }

    @Temporal(TemporalType.DATE)
    public Date getSubscriptionStart() {
        return subscriptionStart;
    }

    public void setSubscriptionStart(Date subscriptionStart) {
        this.subscriptionStart = subscriptionStart;
    }

    @Temporal(TemporalType.DATE)
    public Date getSubscriptionEnd() {
        return subscriptionEnd;
    }

    public void setSubscriptionEnd(Date subscriptionEnd) {
        this.subscriptionEnd = subscriptionEnd;
    }
}
