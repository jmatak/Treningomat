package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO: opis
 *
 * @author Matej Pipalović
 * @version 1.0
 */
public interface AttendantRepository extends JpaRepository<Attendant, Integer> {
    public Attendant findAttendantByUsername(String username);
}
