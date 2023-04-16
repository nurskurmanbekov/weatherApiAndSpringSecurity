package com.example.j_project.config;


import org.apache.tomcat.util.descriptor.web.WebXml;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        security
                .formLogin().
                loginPage("/login").permitAll().and()
                .formLogin()
                .failureForwardUrl("/register")
                .permitAll().and()
                .authorizeHttpRequests((requests) ->requests
                        .requestMatchers("/", "/register","/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .logout(LogoutConfigurer::permitAll);
        return security.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(7);}
}
