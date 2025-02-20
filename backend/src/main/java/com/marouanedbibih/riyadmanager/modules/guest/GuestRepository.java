package com.marouanedbibih.riyadmanager.modules.guest;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmail(String email);

    Optional<Guest> findByUsername(String username);

    @Query("SELECT g FROM Guest g WHERE g.lastName LIKE %:search% OR g.firstName LIKE %:search% OR g.username LIKE %:search% OR g.email LIKE %:search%")
    Page<Guest> searchGuest(@Param("search") String search, Pageable pageable);

    boolean existsByEmail(String email);

}
