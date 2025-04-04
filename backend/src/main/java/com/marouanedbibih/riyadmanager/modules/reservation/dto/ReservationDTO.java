package com.marouanedbibih.riyadmanager.modules.reservation.dto;

import com.marouanedbibih.riyadmanager.modules.guest.GuestDTO;
import com.marouanedbibih.riyadmanager.modules.room.RoomDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;
    private String checkIn;
    private String checkOut;
    private double amount;
    private RoomDTO room;
    private GuestDTO guest;
}
