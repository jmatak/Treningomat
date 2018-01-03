package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.users.Attendant;
import com.errorsonogsvijeta.treningomat.model.users.Role;
import com.errorsonogsvijeta.treningomat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {
    @Autowired
    private AttendantRepository attendantRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveAttendant(Attendant attendant, String idPhoto) {
        attendant.setPassword(bCryptPasswordEncoder.encode(attendant.getPassword()));
        attendant.setIdPhoto(idPhoto);
        attendant.setActive(false);
        Role role = roleRepository.findByRole("ATTENDANT");
        attendant.setRoles(Collections.singletonList(role));
        attendantRepository.save(attendant);
    }

}
