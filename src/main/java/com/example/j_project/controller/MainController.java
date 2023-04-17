package com.example.j_project.controller;


import com.example.j_project.models.User;
import com.example.j_project.models.Weather;
import com.example.j_project.repo.RoleRepository;
import com.example.j_project.repo.UserRepository;
import com.example.j_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model model){

        return "register";
    }
    @GetMapping("/")
    public String mainPage(){
        return "mainPage";
    }
    @GetMapping("/users")
    public String usersPage(Model model){

        model.addAttribute("users",userService.findAllUsers());
        log.debug("\n\n\n Registering user: " + userService.findAllUsers()+"\n\n\n");

        return "users";

    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user){
        log.debug("Registering user: " + user.toString());
        userService.createUser(user);
        return "login";
    }
    @GetMapping("/weather")
    public String getweather(Model model){
        model.addAttribute("weather",userService.getWeather(""));

        return "weather";
    }

    @RequestMapping(path = {"/search"})
    public String home(Weather shop, Model model, String keyword) {
        System.out.println(keyword + "\n\n\n");
        if(keyword!=null) {
            Weather list = userService.getWeather(keyword);
            model.addAttribute("weather", list);
        }else {
            Weather list = userService.getWeather("London");
            model.addAttribute("weather", list);}
        return "weather";
    }

}
