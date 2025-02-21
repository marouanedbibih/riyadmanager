package com.marouanedbibih.riyadmanager.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.marouanedbibih.riyadmanager.security.CustomAuthProvider;
import com.marouanedbibih.riyadmanager.security.CustomUserDetailsService;
import com.marouanedbibih.riyadmanager.security.jwt.AuthEntryPointJwt;
import com.marouanedbibih.riyadmanager.security.jwt.JwtFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";
    private static final String MANAGER = "MANAGER";
    
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtFilter jwtFilter;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request -> request
                // Public endpoints
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/booking/available-rooms/").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/reservations/guest").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Guests management endpoints - accessible only by ADMIN & MANAGER
                .requestMatchers(HttpMethod.POST, "/api/guests").hasAnyAuthority(ADMIN, MANAGER)
                .requestMatchers(HttpMethod.PUT, "/api/guests/{id}").hasAnyAuthority(ADMIN, MANAGER)
                .requestMatchers(HttpMethod.GET, "/api/guests/{id}").hasAnyAuthority(ADMIN,MANAGER)
                .requestMatchers(HttpMethod.GET, "/api/guests").hasAnyAuthority(ADMIN,MANAGER)
                .requestMatchers(HttpMethod.DELETE, "/api/guests/{id}").hasAnyAuthority(ADMIN,MANAGER)
                .requestMatchers(HttpMethod.GET, "/api/guests/list").hasAnyAuthority(ADMIN,MANAGER)

                .anyRequest().authenticated());

        http.sessionManagement(
                sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthProvider(customUserDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

