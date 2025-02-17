package com.marouanedbibih.hotel_management.room;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marouanedbibih.hotel_management.reservation.ReservationRepository;

@Service
public class RoomService {
    
    @Autowired
    private ReservationRepository reservationRepository;

    public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
    // This method checks if there is any existing reservation that overlaps with the given dates
    boolean isReserved = reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            roomId, checkOutDate, checkInDate);

    // If the method returns false, the room is available for the given dates
    return !isReserved;
}

}
