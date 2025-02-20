package com.marouanedbibih.riyadmanager.modules.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.marouanedbibih.riyadmanager.booking.BookingRequest;
import com.marouanedbibih.riyadmanager.booking.BookingService;
import com.marouanedbibih.riyadmanager.errors.BusinessException;
import com.marouanedbibih.riyadmanager.reservation.ReservationRepository;
import com.marouanedbibih.riyadmanager.room.Room;
import com.marouanedbibih.riyadmanager.room.RoomDTO;
import com.marouanedbibih.riyadmanager.room.RoomRepository;
import com.marouanedbibih.riyadmanager.room.RoomStatus;
import com.marouanedbibih.riyadmanager.room.RoomType;

public class BookingServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckAvailableRooms_scenario1() {
        BookingRequest request = new BookingRequest();
        request.setType(RoomType.SINGLE);
        request.setCheckIn(createDate(2024, 9, 9));
        request.setCheckOut(createDate(2024, 9, 13));

        Room room = new Room();
        room.setId(1L);
        room.setNumber(101);
        room.setStatus(RoomStatus.AVAILABLE);
        room.setType(RoomType.SINGLE);

        when(roomRepository.findByType(RoomType.SINGLE)).thenReturn(Arrays.asList(room));
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                1L, LocalDate.of(2024, 9, 9), LocalDate.of(2024, 9, 13))).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bookingService.checkAvailableRooms(request);
        });

        assertEquals("No rooms available", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testCheckAvailableRooms_scenario2() {
        BookingRequest request = new BookingRequest();
        request.setType(RoomType.SINGLE);
        request.setCheckIn(createDate(2024, 9, 12));
        request.setCheckOut(createDate(2024, 9, 16));

        Room room = new Room();
        room.setId(1L);
        room.setNumber(101);
        room.setStatus(RoomStatus.AVAILABLE);
        room.setType(RoomType.SINGLE);

        when(roomRepository.findByType(RoomType.SINGLE)).thenReturn(Arrays.asList(room));
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                1L, LocalDate.of(2024, 9, 12), LocalDate.of(2024, 9, 16))).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            bookingService.checkAvailableRooms(request);
        });

        assertEquals("No rooms available", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testCheckAvailableRooms_scenario3() {
        BookingRequest request = new BookingRequest();
        request.setType(RoomType.SINGLE);
        request.setCheckIn(createDate(2024, 9, 11));
        request.setCheckOut(createDate(2024, 9, 14));


        Room room = new Room();
        room.setId(1L);
        room.setNumber(101);
        room.setStatus(RoomStatus.AVAILABLE);
        room.setType(RoomType.SINGLE);

        when(roomRepository.findByType(RoomType.SINGLE)).thenReturn(Arrays.asList(room));
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                1L, LocalDate.of(2024, 9, 11), LocalDate.of(2024, 9, 14))).thenReturn(false);

        List<RoomDTO> availableRooms = bookingService.checkAvailableRooms(request);

        assertEquals(1, availableRooms.size());
        assertEquals(101, availableRooms.get(0).getNumber());
    }

    @Test
    public void testCheckAvailableRooms_scenario4() {
        BookingRequest request = new BookingRequest();
        request.setType(RoomType.SINGLE);
        request.setCheckIn(createDate(2024, 9, 21));
        request.setCheckOut(createDate(2024, 9, 24));

        Room room = new Room();
        room.setId(1L);
        room.setNumber(101);
        room.setStatus(RoomStatus.AVAILABLE);
        room.setType(RoomType.SINGLE);

        when(roomRepository.findByType(RoomType.SINGLE)).thenReturn(Arrays.asList(room));
        when(reservationRepository.existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                1L, LocalDate.of(2024, 9, 21), LocalDate.of(2024, 9, 24))).thenReturn(false);

        List<RoomDTO> availableRooms = bookingService.checkAvailableRooms(request);

        assertEquals(1, availableRooms.size());
        assertEquals(101, availableRooms.get(0).getNumber());
    }


    private static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0); 
        calendar.set(Calendar.MILLISECOND, 0);
        
        return new Date(calendar.getTimeInMillis());
    }
}