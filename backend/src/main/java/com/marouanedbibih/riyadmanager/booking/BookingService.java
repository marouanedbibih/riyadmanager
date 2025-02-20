package com.marouanedbibih.riyadmanager.booking;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.marouanedbibih.riyadmanager.errors.BusinessException;
import com.marouanedbibih.riyadmanager.reservation.ReservationRepository;
import com.marouanedbibih.riyadmanager.room.RoomDTO;
import com.marouanedbibih.riyadmanager.room.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

        private final RoomRepository roomRepository;
        private final ReservationRepository reservationRepository;

        /**
         * Checks for available rooms based on the specified booking request.
         * 
         * This method filters rooms by the specified room type and checks their
         * availability
         * for the given date range. If no rooms are available, it throws a
         * BusinessException.
         * 
         * @param request The booking request containing the room type and date range.
         * @return A list of available rooms as RoomDTO objects.
         * @throws BusinessException if no rooms are available for the specified date
         *                           range.
         */
        public List<RoomDTO> checkAvailableRooms(BookingRequest request) {
                return roomRepository.findByType(request.getType()).stream()
                                .filter(room -> !reservationRepository
                                                .existsByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                                                                room.getId(),
                                                                request.getCheckInLocalDate(),
                                                                request.getCheckOutLocalDate()))
                                .map(room -> RoomDTO.builder()
                                                .id(room.getId())
                                                .number(room.getNumber())
                                                .status(room.getStatus())
                                                .roomType(room.getType())
                                                .build())
                                .collect(Collectors.collectingAndThen(
                                                Collectors.toList(),
                                                list -> {
                                                        if (list.isEmpty()) {
                                                                throw new BusinessException("No rooms available",
                                                                                HttpStatus.NOT_FOUND);
                                                        }
                                                        return list;
                                                }));
        }
}