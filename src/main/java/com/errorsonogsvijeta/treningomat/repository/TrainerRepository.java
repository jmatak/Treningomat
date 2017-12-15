package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.users.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//TODO: dodaj @Repository ali u hotfixu nekom
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Trainer findTrainerById(Integer id);

    Trainer findTrainerByUsername(String username);

    @Override
    List<Trainer> findAll();
}
