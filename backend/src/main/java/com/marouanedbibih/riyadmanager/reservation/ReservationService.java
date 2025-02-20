package com.marouanedbibih.riyadmanager.reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marouanedbibih.riyadmanager.errors.BusinessException;
import com.marouanedbibih.riyadmanager.modules.guest.Guest;
import com.marouanedbibih.riyadmanager.modules.guest.GuestRepository;
import com.marouanedbibih.riyadmanager.room.Room;
import com.marouanedbibih.riyadmanager.room.RoomRepository;
import com.marouanedbibih.riyadmanager.room.RoomType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ReservationDetails reservationByGuest(GuestReservationRequest request) {
        Guest guest = this.createIfGuestNotExist(request);

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new BusinessException("Room not found", HttpStatus.NOT_FOUND));

        double amount = this.calculateReservationAmount(room.getType(), LocalDate.parse(request.dateIn()),
                LocalDate.parse(request.dateOut()));

        Reservation reservation = Reservation.builder()
                .checkIn(LocalDate.parse(request.dateIn()))
                .checkOut(LocalDate.parse(request.dateOut()))
                .amount(amount)
                .status(ReservationStatus.BOOCKED)
                .room(room)
                .guest(guest)
                .build();
        reservation = reservationRepository.save(reservation);

        return ReservationDetails.builder()
                .dateIn(reservation.getCheckIn().toString())
                .dateOut(reservation.getCheckOut().toString())
                .roomType(room.getType())
                .amount(reservation.getAmount())
                .build();
    }

    private boolean idGuestAlreadyRegistered(String email) {
        return guestRepository.existsByEmail(email);
    }

    private String generateRandomUsername(String lastName, String firstName) {
        return lastName + firstName + Math.random();
    }

    public Guest createIfGuestNotExist(GuestReservationRequest request) {
        Guest guest;
        if (idGuestAlreadyRegistered(request.email())) {
            guest = guestRepository.findByEmail(request.email()).get();
            return guest;
        } else {
            guest = Guest.builder()
                    .email(request.email())
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .phone(request.phoneNumber())
                    .username(this.generateRandomUsername(request.lastName(), request.firstName()))
                    .password(passwordEncoder.encode("password"))
                    .build();

            guest = guestRepository.save(guest);
            return guest;
        }

    }

    public double calculateReservationAmount(RoomType roomType, LocalDate checkIn, LocalDate checkOut) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        return roomType.getPrice() * days;
    }
}
