package com.marouanedbibih.riyadmanager.utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BasicDTO {
    protected Long id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
