package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.Receipt;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    Receipt findById(Integer id);
    List<Receipt> findAllByConfirmedIsFalse();
    List<Receipt> findAll();
    List<Receipt> findAllByAttendantOrderByCreatedDateDesc(Attendant attendant);
    List<Receipt> findByConfirmedFalseAndAttendant(Attendant attendant);
    List<Receipt> findAllByTrainingGroupIn(Collection<TrainingGroup> groups);
    List<Receipt> findAllByConfirmedIsFalseAndTrainingGroupIn(Collection<TrainingGroup> groups);
    List<Receipt> findAllByAttendantAndTrainingGroupAndConfirmedIsFalse(Attendant attendant, TrainingGroup group);
    List<Receipt> findAllByAttendantAndTrainingGroupOrderByCreatedDateDesc(Attendant attendant, TrainingGroup group);
}
