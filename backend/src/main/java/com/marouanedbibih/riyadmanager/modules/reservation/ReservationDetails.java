package com.marouanedbibih.riyadmanager.modules.reservation;

import com.marouanedbibih.riyadmanager.modules.room.RoomType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDetails {
    private String dateIn;
    private String dateOut;
    private RoomType roomType;
    private double amount;
}
