package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.TrainerComment;
import com.errorsonogsvijeta.treningomat.repository.TrainerCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerCommentService {
    @Autowired
    private TrainerCommentRepository trainerCommentRepository;

    public void save(TrainerComment trainerComment) {
        trainerCommentRepository.save(trainerComment);
    }
}
