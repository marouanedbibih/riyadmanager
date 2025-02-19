package com.marouanedbibih.riyadmanager.modules.guest;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.marouanedbibih.riyadmanager.lib.interfaces.IRESTController;
import com.marouanedbibih.riyadmanager.lib.models.PageRES;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestREST implements IRESTController<GuestDTO, GuestREQ, GuestREQ, Long> {

    private final GuestService guestService;

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<GuestDTO> fetchById(@PathVariable Long id) {
        GuestDTO guestDTO = guestService.get(id);
        return ResponseEntity.ok(guestDTO);
    }

    @Override
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<List<GuestDTO>> fetchAll() {
        List<GuestDTO> guests = guestService.list();
        return ResponseEntity.ok(guests);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<GuestDTO> create(@RequestBody @Valid GuestREQ req) {
        GuestDTO guestDTO = guestService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(guestDTO);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<GuestDTO> update(@RequestBody @Valid GuestREQ req, @PathVariable Long id) {
        GuestDTO guestDTO = guestService.update(id, req);
        return ResponseEntity.ok(guestDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<PageRES<GuestDTO>> fetchAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String orderBy,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<String> filter) {
    
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(orderBy), sortBy));
        
        PageRES<GuestDTO> guests;
        if (search != null && !search.isEmpty()) {
            guests = guestService.search(pageable, search);
        } else {
            guests = guestService.fetch(pageable);
        }
        return ResponseEntity.ok(guests);
    }
}