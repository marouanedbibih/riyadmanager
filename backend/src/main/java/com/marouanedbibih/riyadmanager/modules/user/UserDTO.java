package com.marouanedbibih.riyadmanager.modules.user;

import com.marouanedbibih.riyadmanager.utils.BasicDTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BasicDTO {
    private String username;
    private String password;
    // User fields
    private String lastName;
    private String firstName;
    private String image;
    // Contact fields
    private String email;
    private String phone;
    // Role
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
