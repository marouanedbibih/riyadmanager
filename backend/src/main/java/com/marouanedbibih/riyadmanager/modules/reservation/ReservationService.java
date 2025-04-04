package com.marouanedbibih.riyadmanager.modules.reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marouanedbibih.riyadmanager.errors.exception.BusinessException;
import com.marouanedbibih.riyadmanager.lib.models.PageRES;
import com.marouanedbibih.riyadmanager.lib.utils.PageMapper;
import com.marouanedbibih.riyadmanager.modules.guest.Guest;
import com.marouanedbibih.riyadmanager.modules.guest.GuestDTO;
import com.marouanedbibih.riyadmanager.modules.guest.GuestRepository;
import com.marouanedbibih.riyadmanager.modules.reservation.dto.GuestReservationRequest;
import com.marouanedbibih.riyadmanager.modules.reservation.dto.ReservationDTO;
import com.marouanedbibih.riyadmanager.modules.reservation.dto.ReservationRequest;
import com.marouanedbibih.riyadmanager.modules.room.Room;
import com.marouanedbibih.riyadmanager.modules.room.RoomDTO;
import com.marouanedbibih.riyadmanager.modules.room.RoomRepository;
import com.marouanedbibih.riyadmanager.modules.room.RoomType;

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

    @Transactional
    public ReservationDetails createReservation(ReservationRequest request) {
        Guest guest = guestRepository.findById(request.guestId())
                .orElseThrow(() -> new BusinessException("Guest not found", HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new BusinessException("Room not found", HttpStatus.NOT_FOUND));

        double amount = this.calculateReservationAmount(room.getType(), LocalDate.parse(request.checkIn()),
                LocalDate.parse(request.checkIn()));

        Reservation reservation = Reservation.builder()
                .checkIn(LocalDate.parse(request.checkIn()))
                .checkOut(LocalDate.parse(request.checkOut()))
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

    public ReservationDTO getReservationById(Long reservatioId) {
        return reservationRepository.findById(reservatioId)
                .map(reservation -> fromEntityToDto(reservation))
                .orElseThrow(() -> new BusinessException("Reservation not found", HttpStatus.NOT_FOUND));
    }

    public PageRES<ReservationDTO> getReservationsByPagination(Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findAll(pageable);
        return PageMapper.toPageRES(reservations, pageable, this::fromEntityToDto);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessException("Reservation not found", HttpStatus.NOT_FOUND));
        reservationRepository.delete(reservation);
    }

    @Transactional
    public ReservationDTO updateReservation(Long reservationId, ReservationRequest request) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessException("Reservation not found", HttpStatus.NOT_FOUND));

        Guest guest = guestRepository.findById(request.guestId())
                .orElseThrow(() -> new BusinessException("Guest not found", HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new BusinessException("Room not found", HttpStatus.NOT_FOUND));

        double amount = this.calculateReservationAmount(room.getType(), LocalDate.parse(request.checkIn()),
                LocalDate.parse(request.checkOut()));

        reservation.setCheckIn(LocalDate.parse(request.checkIn()));
        reservation.setCheckOut(LocalDate.parse(request.checkOut()));
        reservation.setAmount(amount);
        reservation.setRoom(room);
        reservation.setGuest(guest);

        reservation = reservationRepository.save(reservation);

        return fromEntityToDto(reservation);
    }

    private ReservationDTO fromEntityToDto(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .checkIn(reservation.getCheckIn().toString())
                .checkOut(reservation.getCheckOut().toString())
                .amount(reservation.getAmount())
                .room(RoomDTO.builder()
                        .id(reservation.getRoom().getId())
                        .number(reservation.getRoom().getNumber())
                        .status(reservation.getRoom().getStatus())
                        .roomType(reservation.getRoom().getType())
                        .build())
                .guest(GuestDTO.builder()
                        .id(reservation.getGuest().getId())
                        .lastName(reservation.getGuest().getLastName())
                        .firstName(reservation.getGuest().getFirstName())
                        .username(reservation.getGuest().getUsername())
                        .email(reservation.getGuest().getEmail())
                        .phone(reservation.getGuest().getPhone())
                        .build())
                .build();
    }
}
