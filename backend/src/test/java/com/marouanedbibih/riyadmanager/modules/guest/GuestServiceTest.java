package com.marouanedbibih.riyadmanager.modules.guest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.marouanedbibih.riyadmanager.errors.BusinessException;
import com.marouanedbibih.riyadmanager.lib.models.PageRES;

class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private GuestMapper guestMapper;

    @InjectMocks
    private GuestService guestService;

    private GuestDTO guestDTO;

    private Guest guest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        guestDTO = GuestDTO.builder()
                .lastName("Marouane")
                .firstName("Dbibih")
                .username("marouanedbibih")
                .email("marouane.dbibih@gmail.com")
                .phone("1234567890")
                .build();

        guest = Guest.builder()
                .lastName("Marouane")
                .firstName("Dbibih")
                .username("marouanedbibih")
                .email("marouane.dbibih@gmail.com")
                .phone("1234567890")
                .build();
    }

    @Test
    void testCreateGuestSuccess() throws BusinessException {
        GuestREQ req = this.initGuestREQ();

        when(guestRepository.findByEmail(req.email())).thenReturn(Optional.empty());
        when(guestRepository.findByUsername(req.username())).thenReturn(Optional.empty());
        when(guestMapper.toEntity(req)).thenReturn(guest);
        when(guestRepository.save(guest)).thenReturn(guest);
        when(guestMapper.toDTO(guest)).thenReturn(guestDTO);

        GuestDTO result = guestService.create(req);

        assertEquals(guestDTO, result);
    }

    @Test
    void testCreateGuestFailedEmailExists() {
        GuestREQ req = this.initGuestREQ();

        when(guestRepository.findByEmail(req.email())).thenReturn(Optional.of(guest));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            guestService.create(req);
        });

        assertEquals("Email already exists", exception.getFieldErrors().get(0).getMessage());
        assertEquals("email", exception.getFieldErrors().get(0).getField());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void testCreateGuestFailedUsernameExists() {
        GuestREQ req = this.initGuestREQ();

        when(guestRepository.findByEmail(req.email())).thenReturn(Optional.empty());
        when(guestRepository.findByUsername(req.username())).thenReturn(Optional.of(guest));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            guestService.create(req);
        });

        assertEquals("Username already exists", exception.getFieldErrors().get(0).getMessage());
        assertEquals("username", exception.getFieldErrors().get(0).getField());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void testUpdateGuest() throws BusinessException {
        Long id = 1L;
        GuestREQ req = this.initGuestREQ();

        when(guestRepository.findById(id)).thenReturn(Optional.of(guest));
        when(guestRepository.findByEmail(req.email())).thenReturn(Optional.empty());
        when(guestRepository.findByUsername(req.username())).thenReturn(Optional.empty());
        when(guestMapper.toEntity(req, guest)).thenReturn(guest);
        when(guestRepository.save(guest)).thenReturn(guest);
        when(guestMapper.toDTO(guest)).thenReturn(guestDTO);

        GuestDTO result = guestService.update(id, req);

        assertEquals(guestDTO, result);
    }

    @Test
    void testUpdateGuestFailedEmailExists() {
        GuestREQ req = this.initGuestREQ();
        Long guestId = 1L;

        Guest existingGuest = new Guest();
        existingGuest.setId(2L);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(new Guest()));
        when(guestRepository.findByEmail(req.email())).thenReturn(Optional.of(existingGuest));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            guestService.update(guestId, req);
        });

        assertEquals("Email already exists", exception.getFieldErrors().get(0).getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void testUpdateGuestFailedUsernameExists() {
        GuestREQ req = this.initGuestREQ();
        Long guestId = 1L;

        Guest existingGuest = new Guest();
        existingGuest.setId(2L);

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(new Guest()));
        when(guestRepository.findByEmail(req.email())).thenReturn(Optional.empty());
        when(guestRepository.findByUsername(req.username())).thenReturn(Optional.of(existingGuest));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            guestService.update(guestId, req);
        });

        assertEquals("Username already exists", exception.getFieldErrors().get(0).getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void testUpdateGuestFailedGuestNotFound() {
        GuestREQ req = this.initGuestREQ();
        Long guestId = 1L;

        when(guestRepository.findById(guestId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            guestService.update(guestId, req);
        });

        assertEquals("Guest not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testDeleteGuest() {
        Long id = 1L;
        Guest guest = new Guest();

        when(guestRepository.findById(id)).thenReturn(Optional.of(guest));

        guestService.delete(id);

        verify(guestRepository, times(1)).delete(guest);
    }

    @Test
    void testDeleteGuestFailedGuestNotFound() {
        Long id = 1L;

        when(guestRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            guestService.delete(id);
        });

        assertEquals("Guest not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testGetGuest() {
        Long id = 1L;

        when(guestRepository.findById(id)).thenReturn(Optional.of(guest));
        when(guestMapper.toDTO(guest)).thenReturn(guestDTO);

        GuestDTO result = guestService.get(id);

        assertEquals(guestDTO, result);
    }

    @Test
    void testGetGuestFailedGuestNotFound() {
        Long id = 1L;

        when(guestRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            guestService.get(id);
        });

        assertEquals("Guest not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testListGuests() {
        List<Guest> guests = List.of(new Guest());
        List<GuestDTO> guestDTOs = List.of(new GuestDTO());

        when(guestRepository.findAll()).thenReturn(guests);
        when(guestMapper.toSelectGuest(any(Guest.class))).thenReturn(guestDTOs.get(0));

        List<GuestDTO> result = guestService.list();

        assertEquals(guestDTOs, result);
    }

    @Test
    void testListGuestsEmpty() {
        List<Guest> guests = List.of();

        when(guestRepository.findAll()).thenReturn(guests);

        List<GuestDTO> result = guestService.list();

        assertEquals(List.of(), result);
    }

    @Test
    void testFetchGuests() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Guest> guests = List.of(new Guest());
        Page<Guest> guestPage = new PageImpl<>(guests);
        List<GuestDTO> guestDTOs = List.of(new GuestDTO());

        PageRES<GuestDTO> expectedPageRES = PageRES.<GuestDTO>builder()
                .content(guestDTOs)
                .currentPage(guestPage.getNumber() + 1)
                .size(guestPage.getSize())
                .totalPages(guestPage.getTotalPages())
                .sortBy(pageable.getSort().toString())
                .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name()
                        : null)
                .totalElements(guestPage.getTotalElements())
                .build();

        when(guestRepository.findAll(pageable)).thenReturn(guestPage);
        when(guestMapper.toDTO(any(Guest.class))).thenReturn(guestDTOs.get(0));

        PageRES<GuestDTO> result = guestService.fetch(pageable);

        assertEquals(expectedPageRES.getContent(), result.getContent());
        assertEquals(expectedPageRES.getCurrentPage(), result.getCurrentPage());
        assertEquals(expectedPageRES.getSize(), result.getSize());
        assertEquals(expectedPageRES.getTotalPages(), result.getTotalPages());
        assertEquals(expectedPageRES.getSortBy(), result.getSortBy());
        assertEquals(expectedPageRES.getOrderBy(), result.getOrderBy());
        assertEquals(expectedPageRES.getTotalElements(), result.getTotalElements());
    }

    @Test
    void testFetchGuestsIsEmpty() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Guest> guestPage = Page.empty(pageable);
    
        PageRES<GuestDTO> expectedPageRES = PageRES.<GuestDTO>builder()
                .content(List.of())
                .currentPage(guestPage.getNumber() + 1)
                .size(guestPage.getSize())
                .totalPages(guestPage.getTotalPages())
                .sortBy(pageable.getSort().toString())
                .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name() : null)
                .totalElements(guestPage.getTotalElements())
                .build();
    
        when(guestRepository.findAll(pageable)).thenReturn(guestPage);
    
        PageRES<GuestDTO> result = guestService.fetch(pageable);
    
        assertEquals(expectedPageRES.getContent(), result.getContent());
        assertEquals(expectedPageRES.getCurrentPage(), result.getCurrentPage());
        assertEquals(expectedPageRES.getSize(), result.getSize());
        assertEquals(expectedPageRES.getTotalPages(), result.getTotalPages());
        assertEquals(expectedPageRES.getSortBy(), result.getSortBy());
        assertEquals(expectedPageRES.getOrderBy(), result.getOrderBy());
        assertEquals(expectedPageRES.getTotalElements(), result.getTotalElements());
    }

    @Test
    void testSearchGuests() {
        Pageable pageable = PageRequest.of(0, 10);
        String search = "test";
    
        // Test search success
        List<Guest> guests = List.of(new Guest());
        Page<Guest> guestPage = new PageImpl<>(guests);
        List<GuestDTO> guestDTOs = List.of(new GuestDTO());
        PageRES<GuestDTO> expectedPageRES = PageRES.<GuestDTO>builder()
                .content(guestDTOs)
                .currentPage(guestPage.getNumber() + 1)
                .size(guestPage.getSize())
                .totalPages(guestPage.getTotalPages())
                .sortBy(pageable.getSort().toString())
                .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name() : null)
                .totalElements(guestPage.getTotalElements())
                .build();
    
        when(guestRepository.searchGuest(search, pageable)).thenReturn(guestPage);
        when(guestMapper.toDTO(any(Guest.class))).thenReturn(guestDTOs.get(0));
    
        PageRES<GuestDTO> result = guestService.search(pageable, search);
    
        assertEquals(expectedPageRES.getContent(), result.getContent());
        assertEquals(expectedPageRES.getCurrentPage(), result.getCurrentPage());
        assertEquals(expectedPageRES.getSize(), result.getSize());
        assertEquals(expectedPageRES.getTotalPages(), result.getTotalPages());
        assertEquals(expectedPageRES.getSortBy(), result.getSortBy());
        assertEquals(expectedPageRES.getOrderBy(), result.getOrderBy());
        assertEquals(expectedPageRES.getTotalElements(), result.getTotalElements());
    
        // Test search empty
        Page<Guest> emptyGuestPage = Page.empty(pageable);
        PageRES<GuestDTO> expectedEmptyPageRES = PageRES.<GuestDTO>builder()
                .content(List.of())
                .currentPage(emptyGuestPage.getNumber() + 1)
                .size(emptyGuestPage.getSize())
                .totalPages(emptyGuestPage.getTotalPages())
                .sortBy(pageable.getSort().toString())
                .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name() : null)
                .totalElements(emptyGuestPage.getTotalElements())
                .build();
    
        when(guestRepository.searchGuest(search, pageable)).thenReturn(emptyGuestPage);
    
        PageRES<GuestDTO> emptyResult = guestService.search(pageable, search);
    
        assertEquals(expectedEmptyPageRES.getContent(), emptyResult.getContent());
        assertEquals(expectedEmptyPageRES.getCurrentPage(), emptyResult.getCurrentPage());
        assertEquals(expectedEmptyPageRES.getSize(), emptyResult.getSize());
        assertEquals(expectedEmptyPageRES.getTotalPages(), emptyResult.getTotalPages());
        assertEquals(expectedEmptyPageRES.getSortBy(), emptyResult.getSortBy());
        assertEquals(expectedEmptyPageRES.getOrderBy(), emptyResult.getOrderBy());
        assertEquals(expectedEmptyPageRES.getTotalElements(), emptyResult.getTotalElements());
    }

    private GuestREQ initGuestREQ() {
        return GuestREQ.builder()
                .lastName("Marouane")
                .firstName("Dbibih")
                .username("marouanedbibih")
                .email("marouane.dbibih@gmail.com")
                .phone("1234567890")
                .password("password")
                .build();
    }
}