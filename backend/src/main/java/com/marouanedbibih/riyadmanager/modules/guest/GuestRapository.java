package com.marouanedbibih.riyadmanager.modules.guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRapository extends JpaRepository<Guest, Long> {
    
}
