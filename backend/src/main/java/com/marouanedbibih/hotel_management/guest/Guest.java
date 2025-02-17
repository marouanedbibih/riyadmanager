package com.marouanedbibih.hotel_management.guest;

import java.util.ArrayList;
import java.util.List;

import com.marouanedbibih.hotel_management.reservation.Reservation;
import com.marouanedbibih.hotel_management.user.User;
import com.marouanedbibih.hotel_management.utils.BasicEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guests")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guest extends BasicEntity {

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "user_id")
    private User user;

    // Rservations
    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reservation> reservations = new ArrayList<>();

}
