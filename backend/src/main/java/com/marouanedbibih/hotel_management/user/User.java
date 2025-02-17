package com.marouanedbibih.hotel_management.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.marouanedbibih.hotel_management.guest.Guest;
import com.marouanedbibih.hotel_management.utils.BasicEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BasicEntity implements UserDetails {

    // Auth fields
    @Column(unique = true)
    private String username;
    private String password;
    // User fields
    private String lastName;
    private String firstName;
    private String image;
    // Contact fields
    @Column(unique = true)
    private String email;
    @Column(length = 10)
    private String phone;
    // Role
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Guest
    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Guest guest;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * This methodes are for account management
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
