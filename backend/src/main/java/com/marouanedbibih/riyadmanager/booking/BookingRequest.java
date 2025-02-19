package com.marouanedbibih.riyadmanager.booking;

import java.sql.Date;
import java.time.LocalDate;

import com.marouanedbibih.riyadmanager.room.RoomType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Date checkIn;
    private Date checkOut;
    private RoomType type;

    public LocalDate getCheckInLocalDate() {
        return this.checkIn.toLocalDate();
    }

    public LocalDate getCheckOutLocalDate() {
        return this.checkOut.toLocalDate();
    }
}
