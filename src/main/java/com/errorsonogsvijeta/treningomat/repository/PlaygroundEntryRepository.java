package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaygroundEntryRepository extends JpaRepository<PlaygroundEntry, Integer> {
    List<PlaygroundEntry> findAllByOrderByCreationTime();
}
