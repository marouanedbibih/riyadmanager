package com.marouanedbibih.riyadmanager.modules.guest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

class GuestRESTTest {

    private MockMvc mockMvc;

    @Mock
    private GuestService guestService;

    @InjectMocks
    private GuestREST guestREST;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(guestREST).build();
    }

    @Test
    void testFetchById() throws Exception {
        Long id = 1L;
        GuestDTO guestDTO = new GuestDTO();

        when(guestService.get(id)).thenReturn(guestDTO);

        mockMvc.perform(get("/api/guests/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(guestDTO.getId()));

        verify(guestService, times(1)).get(id);
    }

    // @Test
    // void testFetchAll() throws Exception {
    //     GuestDTO guestDTO = new GuestDTO();
    //     guestDTO.setId(1L); 

    //     List<GuestDTO> guests = List.of(guestDTO);

    //     when(guestService.list()).thenReturn(guests);

    //     mockMvc.perform(get("/api/guests"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$[0].id").value(guestDTO.getId()));

    //     verify(guestService, times(1)).list();
    // }

    @Test
    void testCreate() throws Exception {
        GuestREQ req = GuestREQ.builder()
                .email("tes@example.com")
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .build();
        GuestDTO guestDTO = new GuestDTO();

        when(guestService.create(any(GuestREQ.class))).thenReturn(guestDTO);

        mockMvc.perform(post("/api/guests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(guestDTO.getId()));

        verify(guestService, times(1)).create(any(GuestREQ.class));
    }

    @Test
    void testUpdate() throws Exception {
        Long id = 1L;
        GuestREQ req = GuestREQ.builder()
                .email("tes@example.com")
                .username("testuser")
                .firstName("Test")
                .lastName("User")
                .build();
        GuestDTO guestDTO = new GuestDTO();

        when(guestService.update(eq(id), any(GuestREQ.class))).thenReturn(guestDTO);

        mockMvc.perform(put("/api/guests/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(guestDTO.getId()));

        verify(guestService, times(1)).update(eq(id), any(GuestREQ.class));
    }

    @Test
    void testDelete() throws Exception {
        Long id = 1L;

        doNothing().when(guestService).delete(id);

        mockMvc.perform(delete("/api/guests/{id}", id))
                .andExpect(status().isNoContent());

        verify(guestService, times(1)).delete(id);
    }

    // @Test
    // void testFetchAllWithPagination() throws Exception {
    //     Pageable pageable = PageRequest.of(0, 10);
    //     List<Guest> guests = List.of(new Guest());
    //     Page<Guest> guestPage = new PageImpl<>(guests);
    //     List<GuestDTO> guestDTOs = List.of(new GuestDTO());
    //     PageRES<GuestDTO> pageRES = PageRES.<GuestDTO>builder()
    //             .content(guestDTOs)
    //             .currentPage(guestPage.getNumber() + 1)
    //             .size(guestPage.getSize())
    //             .totalPages(guestPage.getTotalPages())
    //             .sortBy(pageable.getSort().toString())
    //             .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name()
    //                     : null)
    //             .totalElements(guestPage.getTotalElements())
    //             .build();

    //     when(guestService.fetch(pageable)).thenReturn(pageRES);

    //     mockMvc.perform(get("/api/guests")
    //             .param("page", "1")
    //             .param("size", "10")
    //             .param("sortBy", "id")
    //             .param("orderBy", "desc"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.content[0].id").value(guestDTOs.get(0).getId()));

    //     verify(guestService, times(1)).fetch(pageable);
    // }

    // @Test
    // void testSearchGuests() throws Exception {
    //     Pageable pageable = PageRequest.of(0, 10);
    //     String search = "test";
    //     List<Guest> guests = List.of(new Guest());
    //     Page<Guest> guestPage = new PageImpl<>(guests);
    //     List<GuestDTO> guestDTOs = List.of(new GuestDTO());
    //     PageRES<GuestDTO> pageRES = PageRES.<GuestDTO>builder()
    //             .content(guestDTOs)
    //             .currentPage(guestPage.getNumber() + 1)
    //             .size(guestPage.getSize())
    //             .totalPages(guestPage.getTotalPages())
    //             .sortBy(pageable.getSort().toString())
    //             .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name()
    //                     : null)
    //             .totalElements(guestPage.getTotalElements())
    //             .build();

    //     when(guestService.search(pageable, search)).thenReturn(pageRES);

    //     mockMvc.perform(get("/api/guests")
    //             .param("page", "1")
    //             .param("size", "10")
    //             .param("sortBy", "id")
    //             .param("orderBy", "desc")
    //             .param("search", search))
    //             .andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.content[0].id").value(guestDTOs.get(0).getId()));

    //     verify(guestService, times(1)).search(pageable, search);
    // }
}