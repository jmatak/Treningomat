package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.administration.TrainerComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerCommentService {
    @Autowired
    private TrainerCommentService trainerCommentService;

    public void save(TrainerComment trainerComment) {
        trainerCommentService.save(trainerComment);
    }
}
