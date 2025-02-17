package com.marouanedbibih.hotel_management.room;

import java.util.ArrayList;
import java.util.List;

import com.marouanedbibih.hotel_management.reservation.Reservation;
import com.marouanedbibih.hotel_management.roomCategory.RoomCategory;
import com.marouanedbibih.hotel_management.utils.BasicEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BasicEntity {
    
    @Column(unique = true)
    private Integer number;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @ManyToOne
    @JoinColumn(name = "room_category_id")
    private RoomCategory roomCategory;

    // Reservations
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();
}
