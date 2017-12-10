package com.errorsonogsvijeta.treningomat.model.playground;

import com.errorsonogsvijeta.treningomat.model.users.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PlaygroundComment {
    private Integer id;
    private User creator;
    private PlaygroundEntry entry;
    private String text;
    private Date creationTime;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToOne
    public PlaygroundEntry getEntry() {
        return entry;
    }

    public void setEntry(PlaygroundEntry entry) {
        this.entry = entry;
    }

    @Column(length = 4096)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
