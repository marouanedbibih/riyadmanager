package com.marouanedbibih.riyadmanager.modules.reservation.dto;

import lombok.Builder;

@Builder
public record ReservationRequest(
        String checkIn,
        String checkOut,
        Long roomId,
        Long guestId) {

}
