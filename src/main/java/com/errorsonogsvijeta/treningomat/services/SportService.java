package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.training.Sport;
import com.errorsonogsvijeta.treningomat.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportService {
    @Autowired
    private SportRepository sportRepository;

    public List<Sport> findAll() {
        return sportRepository.findAll();
    }

    public void saveSport(Sport sport){
        sportRepository.save(sport);
    }

    public void deleteSport(int id) {
        sportRepository.delete(id);
    }

    public Sport getSport(Integer id) {
        return sportRepository.getOne(id);
    }
}
