package com.marouanedbibih.riyadmanager.modules.user;

import com.marouanedbibih.riyadmanager.utils.BasicDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDTO extends BasicDTO {
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
