package com.marouanedbibih.riyadmanager.reservation;

import lombok.Builder;

@Builder
public record GuestReservationRequest(
        String lastName,
        String firstName,
        String email,
        String phoneNumber,
        String dateIn,
        String dateOut,
        Long roomId

) {

}
