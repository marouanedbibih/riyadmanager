package com.marouanedbibih.hotel_management.booking;

import java.sql.Date;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marouanedbibih.hotel_management.utils.BasicException;
import com.marouanedbibih.hotel_management.utils.BasicResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/available-rooms/")
    public ResponseEntity<BasicResponse> getAvailableRooms(
            @RequestParam Date checkIn,
            @RequestParam Date checkOut,
            @RequestParam Long roomCategoryId) {
        // Build the request
        BookingRequest request = BookingRequest.builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .roomCategoryId(roomCategoryId)
                .build();
        // Try to check the available rooms
        try {
            BasicResponse response = bookingService.checkAvailableRooms(request);
            return ResponseEntity.ok(response);
        }
        // Catch any exception and return a bad request response
        catch (BasicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getResponse());
        }
    }
}
