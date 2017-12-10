package com.errorsonogsvijeta.treningomat.model.playground;

import com.errorsonogsvijeta.treningomat.model.users.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class PlaygroundEntry {
    private Integer id;
    private User creator;
    private String title;
    private String text;
    private Date creationTime;
    private List<PlaygroundComment> comments;

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

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @OneToMany(mappedBy = "entry")
    public List<PlaygroundComment> getComments() {
        return comments;
    }

    public void setComments(List<PlaygroundComment> comments) {
        this.comments = comments;
    }
}
