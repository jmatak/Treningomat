package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.Receipt;
import com.errorsonogsvijeta.treningomat.model.administration.Subscription;
import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private TrainingGroupService trainingGroupService;

    public List<Receipt> getAllReceiptsOfAttendant(Attendant attendant) {
        return receiptRepository.findAllByAttendantOrderByCreatedDateDesc(attendant);
    }

    public Receipt getLastReceipt(Attendant attendant, TrainingGroup group) {
        List<Receipt> receipts = receiptRepository.findAllByAttendantAndTrainingGroupOrderByCreatedDateDesc(attendant, group);
        if (receipts.isEmpty()) return null;
        return receipts.get(0);
    }

    public List<Receipt> getAllNonPaidReceiptsOfAttendant(Attendant attendant) {
        return receiptRepository.findByConfirmedFalseAndAttendant(attendant);
    }

    public List<Receipt> getAllNonPaidReceipts() {
        return receiptRepository.findAllByConfirmedIsFalse();
    }

    public Receipt getReceipt(int id) {
        return receiptRepository.findById(id);
    }

    public List<Receipt> getReceiptsOfTrainer(Trainer trainer) {
        return receiptRepository.findAllByTrainingGroupIn(getGroups(trainer));
    }

    public List<Receipt> getNonPaidReceiptsOfTrainer(Trainer trainer) {
        return receiptRepository.findAllByConfirmedIsFalseAndTrainingGroupIn(getGroups(trainer));
    }

    public List<Receipt> getReceiptsOfTrainerAlphabetical(Trainer trainer) {
        List<Receipt> receipts = getReceiptsOfTrainer(trainer);
        receipts.sort(
                Comparator.comparing((Receipt o3) -> o3.getAttendant().getSurname()).thenComparingInt(Receipt::getId));
        receipts.sort(Comparator.comparing(o -> o.getAttendant().getSurname()));
        return receipts;
    }

    public List<Receipt> getReceiptsOfTrainerByDate(Trainer trainer) {
        List<Receipt> receipts = getReceiptsOfTrainer(trainer);
        receipts.sort(
                Comparator
                        .comparing(Receipt::getCreatedDate).reversed()
                        .thenComparing(a -> a.getAttendant().getSurname())
                        .thenComparing(Receipt::getId)
        );

        return receipts;
    }

    public void markAsPaid(Integer id, Boolean paid) {
        Receipt receipt = receiptRepository.findById(id);
        receipt.setConfirmed(paid);
        receiptRepository.save(receipt);
    }

    public void saveReceipt(Receipt receipt) {
        receiptRepository.save(receipt);
    }

    public void generateReceipt(Subscription subscription, Date date) {
        Receipt receipt = new Receipt();
        receipt.setAttendant(subscription.getAttendant());
        receipt.setTrainingGroup(subscription.getGroup());
        receipt.setCreatedDate(date);
        receipt.setConfirmed(false);

        saveReceipt(receipt);
    }

    public boolean hasUnpaid(Attendant attendant, TrainingGroup group) {
        return !receiptRepository.findAllByAttendantAndTrainingGroupAndConfirmedIsFalse(attendant, group).isEmpty();
    }

    public void deleteReceipt(int id) {
        receiptRepository.delete(id);
    }

    private List<TrainingGroup> getGroups(Trainer trainer) {
        return trainingGroupService.getTrainersTrainingGroups(trainer);
    }
}
