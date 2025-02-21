package com.marouanedbibih.riyadmanager.modules.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByType(RoomType type);

    Room findByNumber(Integer number);
    
}
