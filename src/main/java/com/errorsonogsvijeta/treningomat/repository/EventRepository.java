package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findByName(String name);
}
