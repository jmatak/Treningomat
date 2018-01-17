package com.errorsonogsvijeta.treningomat.model.users;

import com.errorsonogsvijeta.treningomat.model.geo.City;
import com.errorsonogsvijeta.treningomat.model.training.Training;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Attendant extends User {
    private String name;
    private String surname;
    private Long PID;
    private String idPhoto;
    private String idProfilePhoto;
    private City city;
    private List<TrainingGroup> trainingGroups;
    private List<Training> trainings;
    private Boolean active;
    private MultipartFile file;
    private MultipartFile fileProfile;
    private String streetAndNumber;
    private Boolean commentSubscription;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(nullable = false)
    public Long getPID() {
        return PID;
    }

    public void setPID(Long PID) {
        this.PID = PID;
    }

    @Column
    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    @Column
    public String getIdProfilePhoto() {
        return idProfilePhoto;
    }

    public void setIdProfilePhoto(String idProfilePhoto) {
        this.idProfilePhoto = idProfilePhoto;
    }

    @ManyToOne
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToMany(mappedBy = "attendants")
    public List<TrainingGroup> getTrainingGroups() {
        return trainingGroups;
    }

    public void setTrainingGroups(List<TrainingGroup> trainingGroups) {
        this.trainingGroups = trainingGroups;
    }

    @ManyToMany(mappedBy = "attendants")
    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    @Transient
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Transient
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Transient
    public MultipartFile getFileProfile() {
        return fileProfile;
    }

    public void setFileProfile(MultipartFile fileProfile) {
        this.fileProfile = fileProfile;
    }

    @Column
    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    @Column
    public Boolean getCommentSubscription() {
        return commentSubscription;
    }

    public void setCommentSubscription(Boolean commentSubscription) {
        this.commentSubscription = commentSubscription;
    }

    public String fullName() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public String toString() {
        return fullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Attendant attendant = (Attendant) o;
        return Objects.equals(name, attendant.name) &&
                Objects.equals(surname, attendant.surname) &&
                Objects.equals(PID, attendant.PID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, PID);
    }
}
