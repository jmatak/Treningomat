package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Role;
import com.errorsonogsvijeta.treningomat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {
    @Autowired
    private AttendantService attendantService;
    @Autowired
    private RoleRepository roleRepository;

    public void saveAttendant(Attendant attendant, String idPhoto) {
        attendantService.editPassword(attendant);
        attendant.setIdPhoto(idPhoto);
        attendant.setIdProfilePhoto("default-profile.png");
        attendant.setActive(false);
        attendant.setCommentSubscription(true);
        Role role = roleRepository.findByRole("ATTENDANT");
        attendant.setRoles(Collections.singletonList(role));
        attendantService.save(attendant);
    }
}
