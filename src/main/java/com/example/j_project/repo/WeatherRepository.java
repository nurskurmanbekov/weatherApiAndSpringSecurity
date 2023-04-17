package com.example.j_project.repo;

import com.example.j_project.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, String> {
    @Query(value = "select * from weather s where s.City like %:keyword%", nativeQuery = true)
    List<Weather> findByKeyword(@Param("keyword") String keyword);
}
