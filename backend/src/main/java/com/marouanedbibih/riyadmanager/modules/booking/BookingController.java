package com.marouanedbibih.riyadmanager.modules.booking;

import java.sql.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marouanedbibih.riyadmanager.modules.reservation.ReservationService;
import com.marouanedbibih.riyadmanager.modules.room.RoomDTO;
import com.marouanedbibih.riyadmanager.modules.room.RoomType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

        private final BookingService bookingService;
        private final ReservationService reservationService;

        @GetMapping("/available-rooms/")
        public ResponseEntity<Map<String,Object>> getAvailableRooms(
                        @RequestParam Date checkIn,
                        @RequestParam Date checkOut,
                        @RequestParam String roomType) {

                RoomDTO room = bookingService
                                .checkAvailableRooms(BookingRequest.builder()
                                                .checkIn(checkIn)
                                                .checkOut(checkOut)
                                                .type(RoomType.valueOf(roomType))
                                                .build()).get(0);
                
                
                double amount =  reservationService.calculateReservationAmount(room.getRoomType(), checkIn.toLocalDate(), checkOut.toLocalDate());

                return ResponseEntity.ok(Map.of("room", room, "amount", amount, "checkIn", checkIn, "checkOut", checkOut));

        }
}
