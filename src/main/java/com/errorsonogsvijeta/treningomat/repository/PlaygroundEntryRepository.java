package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaygroundEntryRepository extends JpaRepository<PlaygroundEntry, Integer> {
    List<PlaygroundEntry> findAllByOrderByCreationTimeDesc();

    PlaygroundEntry findPlaygroundEntryById(Integer id);
}
