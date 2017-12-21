package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundEntry;
import com.errorsonogsvijeta.treningomat.repository.PlaygroundEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlaygroundEntryService {
    @Autowired
    private PlaygroundEntryRepository playgroundEntryRepository;

    public void saveEntry(PlaygroundEntry entry) {
        playgroundEntryRepository.save(entry);
    }

    public List<PlaygroundEntry> findAllOrderByCreationTime() {
        return playgroundEntryRepository.findAllByOrderByCreationTime();
    }
}
