package com.marouanedbibih.riyadmanager.modules.reservation;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marouanedbibih.riyadmanager.modules.room.Room;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Check if the room is reserved during the specified period
    boolean existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut);

    boolean existsByRoomAndCheckIn(Room room, LocalDate today);
}
