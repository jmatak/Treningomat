package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.playground.PlaygroundComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaygroundCommentRepository extends JpaRepository<PlaygroundComment, Integer> {

}
