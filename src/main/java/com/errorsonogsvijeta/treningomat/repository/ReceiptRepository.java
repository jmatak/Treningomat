package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.administration.Receipt;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    Receipt findById(Integer id);
    List<Receipt> findAllByConfirmedIsFalse();
    List<Receipt> findAll();
    List<Receipt> findAllByAttendantOrderByCreatedDateDesc(Attendant attendant);
    List<Receipt> findByConfirmedFalseAndAttendant(Attendant attendant);
    List<Receipt> findAllByTrainingGroupIn(Collection<TrainingGroup> groups);
}
