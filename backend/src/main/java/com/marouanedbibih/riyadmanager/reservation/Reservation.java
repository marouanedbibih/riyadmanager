package com.marouanedbibih.riyadmanager.reservation;

import java.time.LocalDate;

import com.marouanedbibih.riyadmanager.modules.guest.Guest;
import com.marouanedbibih.riyadmanager.room.Room;
import com.marouanedbibih.riyadmanager.utils.BasicEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reservations")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Reservation extends BasicEntity {
    private LocalDate checkIn;
    private LocalDate checkOut;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    private double amount; 
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

}
