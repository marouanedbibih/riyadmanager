package com.marouanedbibih.riyadmanager.modules.reservation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationREST {

    private final ReservationService reservationService;

    @PostMapping("/guest")
    public ResponseEntity<ReservationDetails> ReservByGuest(@RequestBody GuestReservationRequest request) {
        return ResponseEntity.ok(reservationService.reservationByGuest(request));
    }

}
