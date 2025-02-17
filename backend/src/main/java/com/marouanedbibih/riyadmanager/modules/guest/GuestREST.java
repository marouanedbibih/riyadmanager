package com.marouanedbibih.riyadmanager.modules.guest;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marouanedbibih.riyadmanager.lib.interfaces.IRESTController;
import com.marouanedbibih.riyadmanager.lib.models.PageRES;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestREST implements IRESTController<GuestDTO, GuestREQ, GuestREQ, Long> {

    private final GuestService guestService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> fetchById(@PathVariable Long id) {
        GuestDTO guestDTO = guestService.get(id);
        return ResponseEntity.ok(guestDTO);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<List<GuestDTO>> fetchAll() {
        List<GuestDTO> guests = guestService.list();
        return ResponseEntity.ok(guests);
    }

    @Override
    @PostMapping
    public ResponseEntity<GuestDTO> create(@RequestBody GuestREQ req) {
        GuestDTO guestDTO = guestService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(guestDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> update(@RequestBody GuestREQ req, @PathVariable Long id) {
        GuestDTO guestDTO = guestService.update(id, req);
        return ResponseEntity.ok(guestDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
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