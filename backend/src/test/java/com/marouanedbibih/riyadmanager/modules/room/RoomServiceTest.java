package com.marouanedbibih.riyadmanager.modules.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import com.marouanedbibih.riyadmanager.errors.exception.BusinessException;
import com.marouanedbibih.riyadmanager.modules.booking.BookingRequest;
import com.marouanedbibih.riyadmanager.modules.reservation.ReservationRepository;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoom_success() throws BusinessException {
        RoomRequest request = RoomRequest.builder().number(101).roomType(RoomType.SINGLE).build();
        Room room = new Room();
        room.setNumber(101);
        RoomDTO roomDTO = new RoomDTO();

        when(roomRepository.findByNumber(101)).thenReturn(null);
        when(roomMapper.toEntity(request)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);
        when(roomMapper.toDTO(room)).thenReturn(roomDTO);

        RoomDTO result = roomService.create(request);

        assertEquals(roomDTO, result);
    }

    @Test
    void testCreateRoom_roomAlreadyExists() {
        RoomRequest request = RoomRequest.builder().number(101).roomType(RoomType.SINGLE).build();

        when(roomRepository.findByNumber(101)).thenReturn(new Room());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            roomService.create(request);
        });

        assertEquals("Room with number 101 already exists", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    // @Test
    // void testUpdateRoom_success() {
    //     Long roomId = 1L;
    //     RoomRequest request = RoomRequest.builder().number(101).roomType(RoomType.SINGLE).build();
    //     Room room = Room.builder().id(roomId).number(101).build();
    //     room.setId(roomId);
    //     room.setNumber(101);
    //     RoomDTO roomDTO = RoomDTO.builder().number(101).roomType(RoomType.SINGLE).build();

    //     when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
    //     when(roomRepository.findByNumber(101)).thenReturn(room);
    //     when(roomMapper.toDTO(room)).thenReturn(roomDTO);

    //     RoomDTO result = roomService.update(roomId, request);

    //     assertEquals(roomDTO, result);
    // }

    @Test
    void testUpdateRoom_roomNotFound() {
        Long roomId = 1L;
        RoomRequest request = RoomRequest.builder().number(101).roomType(RoomType.SINGLE).build();

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            roomService.update(roomId, request);
        });

        assertEquals("Room with id 1 not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testDeleteRoom_success() throws BusinessException {
        Long roomId = 1L;
        Room room = mock(Room.class);
        room.setId(roomId);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(room.getReservations()).thenReturn(Collections.emptyList());

        roomService.delete(roomId);

        verify(roomRepository, times(1)).delete(room);
    }

    @Test
    void testDeleteRoom_roomNotFound() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            roomService.delete(roomId);
        });

        assertEquals("Room with id 1 not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testGetRoom_success() throws BusinessException {
        Long roomId = 1L;
        Room room = new Room();
        RoomDTO roomDTO = new RoomDTO();

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomMapper.toDTO(room)).thenReturn(roomDTO);

        RoomDTO result = roomService.get(roomId);

        assertEquals(roomDTO, result);
    }

    @Test
    void testGetRoom_roomNotFound() {
        Long roomId = 1L;

        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            roomService.get(roomId);
        });

        assertEquals("Room with id 1 not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testListRooms() {
        Room room = new Room();
        RoomDTO roomDTO = new RoomDTO();

        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomMapper.toDTO(room)).thenReturn(roomDTO);

        List<RoomDTO> result = roomService.list();

        assertEquals(1, result.size());
        assertEquals(roomDTO, result.get(0));
    }

    @Test
    void testCheckAvailableRooms_noRoomsAvailable() {
        BookingRequest request = BookingRequest.builder().type(RoomType.SINGLE).checkIn(Date.valueOf(LocalDate.now()))
                .checkOut(Date.valueOf(LocalDate.now().plusDays(1))).build();

        when(roomRepository.findByType(RoomType.SINGLE)).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            roomService.checkAvailableRoomsByType(request);
        });

        assertEquals("No rooms available", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testCheckAvailableRooms_roomsAvailable() {
        BookingRequest request = BookingRequest.builder().type(RoomType.SINGLE).checkIn(Date.valueOf(LocalDate.now()))
                .checkOut(Date.valueOf(LocalDate.now().plusDays(1))).build();
        Room room = new Room();
        room.setId(1L);
        room.setNumber(101);
        room.setType(RoomType.SINGLE);
        RoomDTO roomDTO = RoomDTO.builder().id(1L).number(101).roomType(RoomType.SINGLE).build();

        when(roomRepository.findByType(RoomType.SINGLE)).thenReturn(List.of(room));
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                room.getId(), request.getCheckInLocalDate(), request.getCheckOutLocalDate())).thenReturn(false);
        when(roomMapper.toDTO(room)).thenReturn(roomDTO);

        List<RoomDTO> result = roomService.checkAvailableRoomsByType(request);

        assertEquals(1, result.size());
        assertEquals(roomDTO, result.get(0));
    }
}