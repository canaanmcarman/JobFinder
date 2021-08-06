package com.example.week5challenge_jobslist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void run(String... args) {
        User user = new User("user", "user@domain.com", "user", "userFN", "userLN", true);
        Role userRole = new Role("user", "ROLE_USER");

        userRepository.save(user);
        roleRepository.save(userRole);
    }
}
