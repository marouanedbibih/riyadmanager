package com.marouanedbibih.hotel_management.utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicDTO {
    protected Long id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
