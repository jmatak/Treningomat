package com.errorsonogsvijeta.treningomat.services;

import com.errorsonogsvijeta.treningomat.model.geo.City;
import com.errorsonogsvijeta.treningomat.model.training.Sport;
import com.errorsonogsvijeta.treningomat.repository.CityRepository;
import com.errorsonogsvijeta.treningomat.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
