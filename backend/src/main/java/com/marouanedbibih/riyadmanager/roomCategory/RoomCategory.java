package com.marouanedbibih.riyadmanager.roomCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.marouanedbibih.riyadmanager.room.Room;
import com.marouanedbibih.riyadmanager.utils.BasicEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "room_category")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RoomCategory extends BasicEntity {
    @Column(length = 50)
    private String title;
    @Column(length = 150)
    private String description;
    private BigDecimal price;

    @OneToMany(mappedBy = "roomCategory", cascade = CascadeType.REFRESH)
    private List<Room> rooms = new ArrayList<>();
}
