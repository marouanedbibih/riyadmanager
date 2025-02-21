package com.marouanedbibih.riyadmanager.modules.room;

import java.util.List;

import com.marouanedbibih.riyadmanager.modules.reservation.Reservation;
import com.marouanedbibih.riyadmanager.utils.BasicEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "rooms")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Room extends BasicEntity {

    @Column(unique = true)
    private Integer number;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
