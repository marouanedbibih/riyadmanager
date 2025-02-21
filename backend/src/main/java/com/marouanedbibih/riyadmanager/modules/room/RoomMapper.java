package com.marouanedbibih.riyadmanager.modules.room;

import org.springframework.stereotype.Component;

import com.marouanedbibih.riyadmanager.lib.interfaces.IMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoomMapper implements IMapper<Room, RoomDTO, RoomRequest, RoomRequest> {

    @Override
    public RoomDTO toDTO(Room entity) {
        if (entity == null) {
            return null;
        }

        return RoomDTO.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .number(entity.getNumber())
                .status(entity.getStatus())
                .roomType(entity.getType())
                .build();
    }

    @Override
    public Room toEntity(RoomRequest createRequest) {
        if (createRequest == null) {
            return null;
        }

        return Room.builder()
                .number(createRequest.number())
                .status(RoomStatus.AVAILABLE)
                .type(createRequest.roomType())
                .build();
    }

    @Override
    public Room toEntity(RoomRequest updateRequest, Room entity) {
        if (updateRequest == null || entity == null) {
            return null;
        }

        entity.setNumber(updateRequest.number());
        entity.setType(updateRequest.roomType());
        return entity;
    }

}
