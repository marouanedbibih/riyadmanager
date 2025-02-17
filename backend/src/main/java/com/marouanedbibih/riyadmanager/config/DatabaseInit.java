package com.marouanedbibih.riyadmanager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.marouanedbibih.riyadmanager.modules.guest.Guest;
import com.marouanedbibih.riyadmanager.modules.manager.Manager;
import com.marouanedbibih.riyadmanager.modules.user.User;
import com.marouanedbibih.riyadmanager.modules.user.UserRepository;
import com.marouanedbibih.riyadmanager.modules.user.UserRole;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseInit implements CommandLineRunner {

    private final String PASSWORD = "password";
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
        initManagers(10);
        initGuests(50);
    }

    @Transactional
    private void initAdmin() {
        User admin = User.builder()
                .username("admin")
                .lastName("admin")
                .firstName("admin")
                .password(passwordEncoder.encode(PASSWORD))
                .role(UserRole.ADMIN)
                .build();
        userRepository.save(admin);
    }

    @Transactional
    private void initManagers(int numbersOfManagers) {
        for (int i = 0; i < numbersOfManagers; i++) {
            Manager manager = Manager.builder()
                    .username("manager" + i)
                    .email("manager" + i + "@riyadmanager.com")
                    .phone(faker.phoneNumber().cellPhone())
                    .lastName(faker.name().lastName())
                    .firstName(faker.name().firstName())
                    .password(passwordEncoder.encode(PASSWORD))
                    .role(UserRole.MANAGER)
                    .build();
            userRepository.save(manager);
        }
    }
    @Transactional
    private void initGuests(int numbersOfGuests) {
        for (int i = 0; i < numbersOfGuests; i++) {
            Guest guest = Guest.builder()
                    .username("guest" + i)
                    .email("guest" + i + "@riyadmanager.com")
                    .phone(faker.phoneNumber().cellPhone())
                    .lastName(faker.name().lastName())
                    .firstName(faker.name().firstName())
                    .password(passwordEncoder.encode(PASSWORD))
                    .role(UserRole.GUEST)
                    .build();
            userRepository.save(guest);
        }
    }

}
