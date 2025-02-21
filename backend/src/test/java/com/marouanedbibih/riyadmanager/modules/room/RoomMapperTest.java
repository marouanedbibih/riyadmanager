package com.marouanedbibih.riyadmanager.modules.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RoomMapperTest {

    private RoomMapper roomMapper;

    @BeforeEach
    void setUp() {
        roomMapper = new RoomMapper();
    }

    @Test
    void testToDTO_withValidRoom() {
        Room room = Room.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .number(101)
                .status(RoomStatus.AVAILABLE)
                .type(RoomType.SINGLE)
                .build();

        RoomDTO roomDTO = roomMapper.toDTO(room);

        assertEquals(room.getId(), roomDTO.getId());
        assertEquals(room.getCreatedAt(), roomDTO.getCreatedAt());
        assertEquals(room.getUpdatedAt(), roomDTO.getUpdatedAt());
        assertEquals(room.getNumber(), roomDTO.getNumber());
        assertEquals(room.getStatus(), roomDTO.getStatus());
        assertEquals(room.getType(), roomDTO.getRoomType());
    }

    @Test
    void testToDTO_withNullRoom() {
        RoomDTO roomDTO = roomMapper.toDTO(null);
        assertNull(roomDTO);
    }

    @Test
    void testToEntity_withValidCreateRequest() {
        RoomRequest request = RoomRequest.builder()
                .number(101)
                .roomType(RoomType.SINGLE)
                .build();

        Room room = roomMapper.toEntity(request);

        assertEquals(request.number(), room.getNumber());
        assertEquals(RoomStatus.AVAILABLE, room.getStatus());
        assertEquals(request.roomType(), room.getType());
    }

    @Test
    void testToEntity_withNullCreateRequest() {
        Room room = roomMapper.toEntity(null);
        assertNull(room);
    }

    @Test
    void testToEntity_withValidUpdateRequest() {
        RoomRequest request = RoomRequest.builder()
                .number(102)
                .roomType(RoomType.DOUBLE)
                .build();
        Room room = Room.builder()
                .id(1L)
                .number(101)
                .status(RoomStatus.AVAILABLE)
                .type(RoomType.SINGLE)
                .build();

        Room updatedRoom = roomMapper.toEntity(request, room);

        assertEquals(request.number(), updatedRoom.getNumber());
        assertEquals(request.roomType(), updatedRoom.getType());
    }

    @Test
    void testToEntity_withNullUpdateRequest() {
        Room room = Room.builder()
                .id(1L)
                .number(101)
                .status(RoomStatus.AVAILABLE)
                .type(RoomType.SINGLE)
                .build();

        Room updatedRoom = roomMapper.toEntity(null, room);

        assertNull(updatedRoom);
    }

    @Test
    void testToEntity_withNullRoomEntity() {
        RoomRequest request = RoomRequest.builder()
                .number(102)
                .roomType(RoomType.DOUBLE)
                .build();

        Room updatedRoom = roomMapper.toEntity(request, null);

        assertNull(updatedRoom);
    }
}