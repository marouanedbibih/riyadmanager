package com.marouanedbibih.riyadmanager.modules.guest;

import com.marouanedbibih.riyadmanager.utils.BasicDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GuestDTO extends BasicDTO {
    private String lastName;
    private String firstName;
    private String username;
    private String email;
    private String phone;
}
