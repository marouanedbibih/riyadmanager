package com.marouanedbibih.riyadmanager.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marouanedbibih.riyadmanager.roomCategory.RoomCategory;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByRoomCategory(RoomCategory roomCategory);
    
}
