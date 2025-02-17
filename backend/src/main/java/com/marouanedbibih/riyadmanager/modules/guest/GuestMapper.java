package com.marouanedbibih.riyadmanager.modules.guest;

import com.marouanedbibih.riyadmanager.lib.interfaces.IMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuestMapper implements IMapper<Guest, GuestDTO, GuestREQ, GuestREQ> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public GuestDTO toDTO(Guest entity) {
        if (entity == null) {
            return null;
        }
        return GuestDTO.builder()
                .lastName(entity.getLastName())
                .firstName(entity.getFirstName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }

    @Override
    public Guest toEntity(GuestREQ createRequest) {
        if (createRequest == null) {
            return null;
        }

        Guest guest = Guest.builder()
                .lastName(createRequest.lastName())
                .firstName(createRequest.firstName())
                .username(createRequest.username())
                .email(createRequest.email())
                .phone(createRequest.phone())
                .build();

        if (createRequest.password() != null) {
            guest.setPassword(passwordEncoder.encode(createRequest.password()));
        } else {
            guest.setPassword(passwordEncoder.encode("password"));
        }

        return guest;
    }

    @Override
    public Guest toEntity(GuestREQ updateRequest, Guest entity) {
        if (updateRequest == null || entity == null) {
            return null;
        }
        entity.setLastName(updateRequest.lastName());
        entity.setFirstName(updateRequest.firstName());
        entity.setUsername(updateRequest.username());
        entity.setEmail(updateRequest.email());
        entity.setPhone(updateRequest.phone());

        if (updateRequest.password() != null) {
            entity.setPassword(passwordEncoder.encode(updateRequest.password()));
        }

        return entity;
    }

    /**
     * This method is used for creating a default GuestDTO for the select option in
     * the frontend
     * containing the list of guests.
     */
    public GuestDTO toSelectGuest(Guest guest) {
        return GuestDTO.builder()
                .lastName(guest.getLastName())
                .firstName(guest.getFirstName())
                .email(guest.getEmail())
                .build();
    }
}
