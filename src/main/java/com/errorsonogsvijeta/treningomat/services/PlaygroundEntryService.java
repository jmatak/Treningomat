package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundComment;
import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundEntry;
import com.errorsonogsvijeta.treningomat.repository.PlaygroundCommentRepository;
import com.errorsonogsvijeta.treningomat.repository.PlaygroundEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlaygroundEntryService {
    @Autowired
    private PlaygroundEntryRepository playgroundEntryRepository;

    @Autowired
    private PlaygroundCommentRepository playgroundCommentRepository;

    public void saveEntry(PlaygroundEntry entry) {
        playgroundEntryRepository.save(entry);
    }

    public List<PlaygroundEntry> findAll() {
        return playgroundEntryRepository.findAllByOrderByCreationTimeDesc();
    }

    public PlaygroundEntry findPlaygroundEntryById(Integer id) {
        return playgroundEntryRepository.findPlaygroundEntryById(id);
    }

    public void addComment(PlaygroundEntry entry, PlaygroundComment comment) {
        List<PlaygroundComment> comments = entry.getComments();
        if (comments == null) {
            comments = new ArrayList<>(Collections.singletonList(comment));
        } else {
            comments.add(comment);
        }
        entry.setComments(comments);
        comment.setEntry(entry);
        playgroundCommentRepository.save(comment);
        playgroundEntryRepository.save(entry);
    }

    public PlaygroundComment findPlaygroundCommentById(Integer id){
        return playgroundCommentRepository.findPlaygroundCommentById(id);
    }

    public void deleteComment(PlaygroundComment comment){
        playgroundCommentRepository.delete(comment.getId());
    }
}
