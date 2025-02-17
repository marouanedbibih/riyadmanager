package com.marouanedbibih.riyadmanager.room;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.marouanedbibih.riyadmanager.reservation.ReservationRepository;
import com.marouanedbibih.riyadmanager.room.RoomService;

public class RoomServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsRoomAvailable_scenario1() {
        // Given
        Long roomId = 1L;
        LocalDate checkInDate = LocalDate.of(2024, 9, 9);
        LocalDate checkOutDate = LocalDate.of(2024, 9, 13);

        // When
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                roomId, checkOutDate, checkInDate)).thenReturn(true);

        // Then
        boolean isAvailable = roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        assertFalse(isAvailable); // Room should not be available
    }

    @Test
    void testIsRoomAvailable_scenario2() {
        // Given
        Long roomId = 1L;
        LocalDate checkInDate = LocalDate.of(2024, 9, 12);
        LocalDate checkOutDate = LocalDate.of(2024, 9, 16);

        // When
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                roomId, checkOutDate, checkInDate)).thenReturn(true);

        // Then
        boolean isAvailable = roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        assertFalse(isAvailable); // Room should not be available
    }

    @Test
    void testIsRoomAvailable_scenario3() {
        // Given
        Long roomId = 1L;
        LocalDate checkInDate = LocalDate.of(2024, 9, 11);
        LocalDate checkOutDate = LocalDate.of(2024, 9, 14);

        // When
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                roomId, checkOutDate, checkInDate)).thenReturn(false);

        // Then
        boolean isAvailable = roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        assertTrue(isAvailable); // Room should be available
    }

    @Test
    void testIsRoomAvailable_scenario4() {
        // Given
        Long roomId = 1L;
        LocalDate checkInDate = LocalDate.of(2024, 9, 21);
        LocalDate checkOutDate = LocalDate.of(2024, 9, 24);

        // When
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                roomId, checkOutDate, checkInDate)).thenReturn(false);

        // Then
        boolean isAvailable = roomService.isRoomAvailable(roomId, checkInDate, checkOutDate);
        assertTrue(isAvailable); // Room should be available
    }

}
