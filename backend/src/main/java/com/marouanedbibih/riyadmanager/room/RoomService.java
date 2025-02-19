package com.marouanedbibih.riyadmanager.room;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marouanedbibih.riyadmanager.reservation.ReservationRepository;

@Service
public class RoomService {
    
    @Autowired
    private ReservationRepository reservationRepository;

    public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
    boolean isReserved = reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            roomId, checkOutDate, checkInDate);

    return !isReserved;
}

}
