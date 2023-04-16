package com.example.j_project.service;

import com.example.j_project.config.CustomUserDetails;
import com.example.j_project.models.Role;
import com.example.j_project.models.User;
import com.example.j_project.models.Weather;
import com.example.j_project.repo.RoleRepository;
import com.example.j_project.repo.UserRepository;
import com.example.j_project.repo.WeatherRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    private final WeatherRepository weatherRepository;

    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, WeatherRepository weatherRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.weatherRepository = weatherRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
                userRepository.findByUsername(username).orElse(new User()));
    }

    @Override
    public User createUser(User newUser) {
        return createUserWithRole(newUser, "USER");
    }

    private User createUserWithRole(User newUser, String roleName) {
        //TODO: check for already existence username, if the user with username already exists throw Exception
        newUser.setId(null);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.getRoles().add(getOrCreateRole(roleName));
        return userRepository.save(newUser);
    }

    @Override
    public User createAdmin(User newAdmin) {
        return createUserWithRole(newAdmin, "ADMIN");
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Weather getWeather(String city) {

            System.out.println("jfdik");
            String units = "metric";
            String apiKey = "95877736926d93ec29e966a4b4b7c637";
            /*String city = "London";*/

            String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units="
                    +units+"&lang=en&appid="+apiKey;
        StringBuffer response = new StringBuffer();

        try {
                URL urls = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urls.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String inputLine;


                while((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();

            }
            catch (Exception e){
                System.out.println(e+" error in weather catching");
                return null;
            }


            String main = response.toString().substring(response.indexOf("\"main\"")+7,response.indexOf("\"description\""))
                    .replace(",","\n");
            String temperature_min = response.toString().substring(response.indexOf(
                    "\"temp_min\"")+11, response.indexOf("\"temp_max\"")).replace(",","°C\n");
            String temperature_max = response.toString().substring(response.indexOf(
                    "\"temp_max\"")+11, response.indexOf("\"pressure\"")).replace(",","°C\n");

            String wind_speed = response.toString().substring(response.indexOf(
                    "\"speed\"")+8, response.indexOf("\"deg\"")).replace(",","°C\n");

            System.out.println("1)"+main + "\n" + "2)"+temperature_min+
                    "3)"+temperature_max+"4)"+
                    wind_speed);

            Weather weather = new Weather();
            weather.setCity(city);
            weather.setMain(main);
            weather.setTemperature_max(temperature_max);
            weather.setTemperature_min(temperature_min);
            weather.setWind_speed(wind_speed);
            return weatherRepository.save(weather);
    }

/*    @Override
    public Optional<Weather> findAllByCity(String city) {

       Optional<Weather> weather = weatherRepository.findById(city);
        return weather;
    }*/

    protected Role getOrCreateRole(String roleName) {
        Optional<Role> optionalRole = roleRepository.findById(roleName);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }
}
