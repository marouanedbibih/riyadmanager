package com.marouanedbibih.riyadmanager.booking;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marouanedbibih.riyadmanager.room.RoomDTO;
import com.marouanedbibih.riyadmanager.room.RoomType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/available-rooms/")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(
            @RequestParam Date checkIn,
            @RequestParam Date checkOut,
            @RequestParam String roomType) {

        return ResponseEntity.ok(
                bookingService
                        .checkAvailableRooms(BookingRequest.builder()
                                .checkIn(checkIn)
                                .checkOut(checkOut)
                                .type(RoomType.valueOf(roomType))
                                .build()));
    }
}
