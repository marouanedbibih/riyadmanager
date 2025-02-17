package com.marouanedbibih.hotel_management.booking;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marouanedbibih.hotel_management.reservation.ReservationRepository;
import com.marouanedbibih.hotel_management.room.Room;
import com.marouanedbibih.hotel_management.room.RoomDTO;
import com.marouanedbibih.hotel_management.room.RoomRepository;
import com.marouanedbibih.hotel_management.room.RoomService;
import com.marouanedbibih.hotel_management.roomCategory.RoomCategory;
import com.marouanedbibih.hotel_management.roomCategory.RoomCategoryRepository;
import com.marouanedbibih.hotel_management.utils.BasicException;
import com.marouanedbibih.hotel_management.utils.BasicResponse;

@Service
public class BookingService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomCategoryRepository roomCategoryRepository;

    @Autowired
    private RoomService roomService;

    public BasicResponse checkAvailableRooms(BookingRequest request) throws BasicException {
        // Check if the requested room category exists
        RoomCategory roomCategory = roomCategoryRepository
                .findById(request.getRoomCategoryId())
                .orElseThrow(() -> {
                    BasicResponse response = BasicResponse.builder()
                            .message("Room category not found")
                            .build();
                    return new BasicException(response);
                });

        // Select all rooms that belong to the requested category
        List<Room> rooms = roomRepository.findByRoomCategory(roomCategory);
        // Throw an exception if no rooms are found
        if (rooms.isEmpty()) {
            BasicResponse response = BasicResponse.builder()
                    .message("No rooms found for the requested category")
                    .build();
            throw new BasicException(response);
        }
        // Filter out rooms that are already reserved during the specified period
        List<Room> availableRooms = rooms.stream()
                .filter(room -> roomService.isRoomAvailable(room.getId(), request.getCheckInLocalDate(),
                        request.getCheckOutLocalDate()))
                .collect(Collectors.toList());

        // Throw an exception if no rooms are available
        if (availableRooms.isEmpty()) { 
            BasicResponse response = BasicResponse.builder()
                    .message("No rooms available for the requested dates")
                    .build();
            throw new BasicException(response);
        }
        // Buil The Room DTO list
        List<RoomDTO> roomDTOs = availableRooms.stream()
                .map(room -> RoomDTO.builder()
                        .id(room.getId())
                        .number(room.getNumber())
                        .status(room.getStatus())
                        .roomCategoryTitle(room.getRoomCategory().getTitle())
                        .build())
                .collect(Collectors.toList());
        // Return the list of available rooms
        return BasicResponse.builder()
                .message("Rooms available")
                .content(roomDTOs)
                .build();
    }
}
