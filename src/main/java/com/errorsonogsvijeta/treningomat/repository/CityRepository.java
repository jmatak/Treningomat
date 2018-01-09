package com.errorsonogsvijeta.treningomat.repository;

import com.errorsonogsvijeta.treningomat.model.geo.City;
import com.errorsonogsvijeta.treningomat.model.training.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer>{
    List<City> findAll();
    List<City> findAllByOrderByName();
}
