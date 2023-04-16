package com.example.j_project.repo;

import com.example.j_project.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, String> {

}
