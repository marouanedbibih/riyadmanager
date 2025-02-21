package com.marouanedbibih.riyadmanager.modules.room;

import lombok.Builder;

@Builder
public record RoomRequest(
    Integer number,
    RoomType roomType,
    RoomStatus status
) {
    
}
