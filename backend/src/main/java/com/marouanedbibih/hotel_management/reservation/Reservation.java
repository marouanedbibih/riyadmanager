package com.marouanedbibih.hotel_management.reservation;

import java.sql.Date;
import java.time.LocalDate;

import com.marouanedbibih.hotel_management.guest.Guest;
import com.marouanedbibih.hotel_management.invoice.Invoice;
import com.marouanedbibih.hotel_management.room.Room;
import com.marouanedbibih.hotel_management.utils.BasicEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation extends BasicEntity {
    private LocalDate checkIn;
    private LocalDate checkOut;
    
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    // Room
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // Guest
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    // Invoice
    @OneToOne(mappedBy = "reservation",cascade = CascadeType.MERGE)
    private Invoice invoice;
}
