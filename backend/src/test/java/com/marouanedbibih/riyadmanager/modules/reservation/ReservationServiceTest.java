package com.marouanedbibih.riyadmanager.modules.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marouanedbibih.riyadmanager.errors.exception.BusinessException;
import com.marouanedbibih.riyadmanager.modules.guest.Guest;
import com.marouanedbibih.riyadmanager.modules.guest.GuestRepository;
import com.marouanedbibih.riyadmanager.modules.reservation.ReservationRepository;
import com.marouanedbibih.riyadmanager.modules.reservation.ReservationService;
import com.marouanedbibih.riyadmanager.modules.reservation.dto.GuestReservationRequest;
import com.marouanedbibih.riyadmanager.modules.room.RoomRepository;
import com.marouanedbibih.riyadmanager.modules.room.RoomType;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReservationByGuest_RoomNotFound() {
        GuestReservationRequest request = GuestReservationRequest.builder()
                .roomId(1L)
                .dateIn("2024-09-09")
                .dateOut("2024-09-13")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .build();

        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            reservationService.reservationByGuest(request);
        });

        assertEquals("Room not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testCalculateReservationAmount() {
        RoomType roomType = RoomType.SINGLE;
        LocalDate checkIn = LocalDate.of(2024, 9, 9);
        LocalDate checkOut = LocalDate.of(2024, 9, 13);

        double amount = reservationService.calculateReservationAmount(roomType, checkIn, checkOut);

        assertEquals(400.0, amount);
    }

    @Test
    public void testCreateIfGuestNotExist_GuestExists() {
        GuestReservationRequest request = GuestReservationRequest.builder()
                .roomId(1L)
                .dateIn("2024-09-09")
                .dateOut("2024-09-13")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .build();

        Guest existingGuest = Guest.builder()
                .email("test@example.com")
                .build();

        when(guestRepository.existsByEmail("test@example.com")).thenReturn(true);
        when(guestRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingGuest));

        Guest guest = reservationService.createIfGuestNotExist(request);

        assertEquals(existingGuest, guest);
    }

    @Test
    public void testCreateIfGuestNotExist_GuestDoesNotExist() {
        GuestReservationRequest request = GuestReservationRequest.builder()
                .roomId(1L)
                .dateIn("2024-09-09")
                .dateOut("2024-09-13")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .build();

        when(guestRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        Guest newGuest = Guest.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phone("1234567890")
                .username("JohnDoe" + Math.random())
                .password("encodedPassword")
                .build();

        when(guestRepository.save(any(Guest.class))).thenReturn(newGuest);

        Guest guest = reservationService.createIfGuestNotExist(request);

        assertEquals("test@example.com", guest.getEmail());
        assertEquals("John", guest.getFirstName());
        assertEquals("Doe", guest.getLastName());
        assertEquals("1234567890", guest.getPhone());
        assertEquals("encodedPassword", guest.getPassword());
    }
}