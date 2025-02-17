package com.marouanedbibih.riyadmanager.modules.guest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class GuestMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private GuestMapper guestMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDTO() {
        Guest guest = Guest.builder()
                .lastName("Doe")
                .firstName("John")
                .username("johndoe")
                .email("johndoe@example.com")
                .phone("+1234567890")
                .build();

        GuestDTO guestDTO = guestMapper.toDTO(guest);

        assertNotNull(guestDTO);
        assertEquals("Doe", guestDTO.getLastName());
        assertEquals("John", guestDTO.getFirstName());
        assertEquals("johndoe", guestDTO.getUsername());
        assertEquals("johndoe@example.com", guestDTO.getEmail());
        assertEquals("+1234567890", guestDTO.getPhone());
    }

    @Test
    void testToEntity_CreateRequest() {
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        
        GuestREQ request = GuestREQ.builder()
                .lastName("Doe")
                .firstName("John")
                .username("johndoe")
                .email("johndoe@example.com")
                .phone("+1234567890")
                .password("password123")
                .build();

        Guest guest = guestMapper.toEntity(request);

        assertNotNull(guest);
        assertEquals("Doe", guest.getLastName());
        assertEquals("John", guest.getFirstName());
        assertEquals("johndoe", guest.getUsername());
        assertEquals("johndoe@example.com", guest.getEmail());
        assertEquals("+1234567890", guest.getPhone());
        assertEquals("encodedPassword", guest.getPassword());
    }

    @Test
    void testToEntity_UpdateRequest() {
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");

        Guest existingGuest = Guest.builder()
                .lastName("OldLast")
                .firstName("OldFirst")
                .username("olduser")
                .email("old@example.com")
                .phone("+9876543210")
                .password("oldPassword")
                .build();

        GuestREQ updateRequest = GuestREQ.builder()
                .lastName("NewLast")
                .firstName("NewFirst")
                .username("newuser")
                .email("new@example.com")
                .phone("+1122334455")
                .password("newpassword")
                .build();

        Guest updatedGuest = guestMapper.toEntity(updateRequest, existingGuest);

        assertNotNull(updatedGuest);
        assertEquals("NewLast", updatedGuest.getLastName());
        assertEquals("NewFirst", updatedGuest.getFirstName());
        assertEquals("newuser", updatedGuest.getUsername());
        assertEquals("new@example.com", updatedGuest.getEmail());
        assertEquals("+1122334455", updatedGuest.getPhone());
        assertEquals("encodedNewPassword", updatedGuest.getPassword());
    }
}
