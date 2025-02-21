package com.marouanedbibih.riyadmanager.modules.room;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.marouanedbibih.riyadmanager.modules.booking.BookingRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomREST {

    private final RoomService roomService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomRequest roomRequest) {
        RoomDTO roomDTO = roomService.create(roomRequest);
        return new ResponseEntity<>(roomDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomRequest roomRequest) {
        RoomDTO roomDTO = roomService.update(id, roomRequest);
        return new ResponseEntity<>(roomDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        RoomDTO roomDTO = roomService.get(id);
        return new ResponseEntity<>(roomDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/list")
    public ResponseEntity<List<RoomDTO>> listRooms() {
        List<RoomDTO> rooms = roomService.list();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/available")
    public ResponseEntity<List<RoomDTO>> listAvailableRooms(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String type) {

        BookingRequest bookingRequest = BookingRequest.builder()
                .checkIn(Date.valueOf(LocalDate.parse(startDate)))
                .checkOut(Date.valueOf(LocalDate.parse(endDate)))
                .type(RoomType.valueOf(type))
                .build();

        List<RoomDTO> rooms = roomService.checkAvailableRoomsByType(bookingRequest);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}