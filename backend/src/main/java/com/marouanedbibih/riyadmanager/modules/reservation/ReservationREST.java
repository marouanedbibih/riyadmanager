package com.marouanedbibih.riyadmanager.modules.reservation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marouanedbibih.riyadmanager.lib.models.PageRES;
import com.marouanedbibih.riyadmanager.modules.reservation.dto.GuestReservationRequest;
import com.marouanedbibih.riyadmanager.modules.reservation.dto.ReservationDTO;
import com.marouanedbibih.riyadmanager.modules.reservation.dto.ReservationRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<ReservationDetails> createReservation(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationRequest request,
            @PathVariable Long id) {
        return ResponseEntity.ok(reservationService.updateReservation(id, request));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<PageRES<ReservationDTO>> getReservations(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String orderBy,
            @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));

        PageRES<ReservationDTO> reservations;
        if (search != null) {
            // reservations = reservationService.getReservationsByPagination(pageable)
            return null;
        } else {
            reservations = reservationService.getReservationsByPagination(pageable);
            return ResponseEntity.ok(reservations);
        }
    }

}
