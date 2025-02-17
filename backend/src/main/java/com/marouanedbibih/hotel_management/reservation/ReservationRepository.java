package com.marouanedbibih.hotel_management.reservation;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Check if the room is reserved during the specified period
    boolean existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut);
}
