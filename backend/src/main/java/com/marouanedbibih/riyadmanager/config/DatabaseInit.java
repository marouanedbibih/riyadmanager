package com.marouanedbibih.riyadmanager.config;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.marouanedbibih.riyadmanager.modules.guest.Guest;
import com.marouanedbibih.riyadmanager.modules.guest.GuestRapository;
import com.marouanedbibih.riyadmanager.modules.manager.Manager;
import com.marouanedbibih.riyadmanager.modules.reservation.Reservation;
import com.marouanedbibih.riyadmanager.modules.reservation.ReservationRepository;
import com.marouanedbibih.riyadmanager.modules.reservation.ReservationStatus;
import com.marouanedbibih.riyadmanager.modules.room.Room;
import com.marouanedbibih.riyadmanager.modules.room.RoomRepository;
import com.marouanedbibih.riyadmanager.modules.room.RoomStatus;
import com.marouanedbibih.riyadmanager.modules.room.RoomType;
import com.marouanedbibih.riyadmanager.security.user.User;
import com.marouanedbibih.riyadmanager.security.user.UserRepository;
import com.marouanedbibih.riyadmanager.security.user.UserRole;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseInit implements CommandLineRunner {

    private final String PASSWORD = "password";
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRapository guestRapository;

    private final Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
        initManagers(10);
        initGuests(50);
        initRooms();
        initReservations();
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

    @Transactional
    private void initRooms() {
        List<Room> rooms = Arrays.asList(
                Room.builder().id(1L).number(101).type(RoomType.SINGLE).status(RoomStatus.AVAILABLE).build(),
                Room.builder().id(2L).number(102).type(RoomType.SINGLE).status(RoomStatus.OCCUPIED).build(),
                Room.builder().id(3L).number(201).type(RoomType.DOUBLE).status(RoomStatus.AVAILABLE).build(),
                Room.builder().id(4L).number(202).type(RoomType.DOUBLE).status(RoomStatus.AVAILABLE).build(),
                Room.builder().id(5L).number(301).type(RoomType.SUITE).status(RoomStatus.AVAILABLE).build());
        roomRepository.saveAll(rooms);
    }

    @Transactional
    protected void initReservations() {
        List<Reservation> reservations = Arrays.asList(
                createReservation(1L, 1L, 1L, LocalDate.of(2025, 2, 9), LocalDate.of(2025, 2, 13)),
                createReservation(2L, 2L, 2L, LocalDate.of(2025, 2, 12), LocalDate.of(2025, 2, 16)),
                createReservation(3L, 3L, 3L, LocalDate.of(2025, 2, 11), LocalDate.of(2025, 2, 14)),
                createReservation(4L, 4L, 4L, LocalDate.of(2025, 2, 21), LocalDate.of(2025, 2, 24)));
        reservationRepository.saveAll(reservations);
    }

    /**
     * Helper method to create a reservation safely.
     */
    private Reservation createReservation(Long reservationId, Long roomId, Long guestId, LocalDate checkIn,
            LocalDate checkOut) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found with ID: " + guestId));

        return Reservation.builder()
                .id(reservationId)
                .room(room)
                .guest(guest)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }

}
