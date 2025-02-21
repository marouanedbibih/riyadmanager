package com.marouanedbibih.riyadmanager.modules.manager;

import com.marouanedbibih.riyadmanager.security.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "managers")
public class Manager extends User {
    private String email;
    private String phone;
}

