package com.marouanedbibih.hotel_management.room;

import java.time.LocalDateTime;

import com.marouanedbibih.hotel_management.utils.BasicDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RoomDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer number;
    private RoomStatus status;
    private String roomCategoryTitle;
}
