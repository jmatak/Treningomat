package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.training.TrainingGroup;
import com.errorsonogsvijeta.treningomat.model.users.Role;
import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import com.errorsonogsvijeta.treningomat.repository.RoleRepository;
import com.errorsonogsvijeta.treningomat.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Trainer findTrainerById(Integer id) {
        return trainerRepository.findTrainerById(id);
    }
    public Trainer findTrainerByUsername(String username) {
        return trainerRepository.findTrainerByUsername(username);
    }

    public void saveTrainer(Trainer trainer, String idPhoto) {
        editPassword(trainer);
        trainer.setIdPhoto(idPhoto);
        Role role = roleRepository.findByRole("TRAINER");
        trainer.setRoles(new ArrayList<>(Collections.singletonList(role)));
        trainerRepository.save(trainer);
    }

    public List<Trainer> findTrainersByTrainingGroupsIn(List<TrainingGroup> groups) {
        return trainerRepository.findTrainersByTrainingGroupsIn(groups);
    }

    public void save(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    public void editPassword(Trainer trainer) {
        trainer.setPassword(bCryptPasswordEncoder.encode(trainer.getPassword()));
    }

    public List<Trainer> findAll() {
        return trainerRepository.findAll();
    }

    public void delete(Trainer trainer) {
        trainerRepository.delete(trainer);
    }
}
