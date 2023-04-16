package com.example.j_project.service;

import com.example.j_project.models.User;
import com.example.j_project.models.Weather;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User newUser);

    User createAdmin(User newAdmin);

    List<User> findAllUsers();

    Weather getWeather(String city);
/*
    Optional<Weather> findAllByCity(String city);
*/
}
