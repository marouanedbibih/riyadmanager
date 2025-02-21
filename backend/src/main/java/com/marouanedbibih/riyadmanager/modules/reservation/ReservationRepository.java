package com.marouanedbibih.riyadmanager.modules.reservation;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marouanedbibih.riyadmanager.modules.room.Room;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Check if the room is reserved during the specified period
    boolean existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.room = :room AND r.checkIn = :checkIn")
    boolean existsByRoomAndCheckIn(@Param("room") Room room, @Param("checkIn") LocalDate checkIn);
}
