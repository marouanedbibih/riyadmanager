package com.marouanedbibih.riyadmanager.modules.guest;

import java.util.Set;

import com.marouanedbibih.riyadmanager.modules.reservation.Reservation;
import com.marouanedbibih.riyadmanager.security.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Guest extends User {

    @Column(unique = true, nullable = false)
    private String email;
    private String phone;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations;
}