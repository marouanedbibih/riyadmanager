package com.marouanedbibih.riyadmanager.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marouanedbibih.riyadmanager.errors.BusinessException;
import com.marouanedbibih.riyadmanager.modules.guest.Guest;
import com.marouanedbibih.riyadmanager.modules.guest.GuestRepository;
import com.marouanedbibih.riyadmanager.modules.user.User;
import com.marouanedbibih.riyadmanager.modules.user.UserDTO;
import com.marouanedbibih.riyadmanager.security.jwt.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final GuestRepository guestRepository;
    private final JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request) {
        guestRepository.findByEmail(request.username()).ifPresent(guest -> {
            throw new BusinessException("User already exists", "username", HttpStatus.BAD_REQUEST);
        });

        guestRepository.findByEmail(request.email()).ifPresent(guest -> {
            throw new BusinessException("Email already exists", "email", HttpStatus.BAD_REQUEST);
        });

        Guest guest = Guest.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .build();
        guest = guestRepository.save(guest);

        UserDTO userDTO = this.mapperGuestToUserDTO(guest);

        String token = jwtUtils.createToken(userDTO);

        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse login(String username, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        User user = (User) authentication.getPrincipal();

        UserDTO userDTO = UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();

        String token = jwtUtils.createToken(userDTO);

        return AuthResponse.builder()
                .token(token)
                .build();

    }

    private UserDTO mapperGuestToUserDTO(Guest guest) {
        return UserDTO.builder()
                .username(guest.getUsername())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .role(guest.getRole())
                .build();
    }

}
