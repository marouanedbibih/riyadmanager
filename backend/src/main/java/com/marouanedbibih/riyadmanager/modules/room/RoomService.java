package com.marouanedbibih.riyadmanager.modules.room;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marouanedbibih.riyadmanager.errors.exception.BusinessException;
import com.marouanedbibih.riyadmanager.lib.interfaces.ICRUD;
import com.marouanedbibih.riyadmanager.lib.interfaces.IFetch;
import com.marouanedbibih.riyadmanager.lib.models.PageRES;
import com.marouanedbibih.riyadmanager.modules.booking.BookingRequest;
import com.marouanedbibih.riyadmanager.modules.reservation.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService implements
        ICRUD<Room, RoomDTO, RoomRequest, RoomRequest, Long>,
        IFetch<RoomDTO> {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public RoomDTO create(RoomRequest req) throws BusinessException {
        if (roomRepository.findByNumber(req.number()) != null) {
            throw new BusinessException("Room with number " + req.number() + " already exists", HttpStatus.BAD_REQUEST);
        }

        Room room = roomMapper.toEntity(req);
        room = roomRepository.save(room);
        return roomMapper.toDTO(room);
    }

    @Override
    @Transactional
    public RoomDTO update(Long id, RoomRequest req) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Room with id " + id + " not found", HttpStatus.NOT_FOUND));

        Room existingRoom = roomRepository.findByNumber(req.number());
        if (existingRoom != null && !existingRoom.getId().equals(id)) {
            throw new BusinessException("Room with number " + req.number() + " already exists", HttpStatus.BAD_REQUEST);
        }

        room.setNumber(req.number());
        room.setType(req.roomType());
        Room updatedRoom = roomRepository.save(room);
        return roomMapper.toDTO(updatedRoom);
    }

    @Override
    @Transactional
    public void delete(Long id) throws BusinessException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Room with id " + id + " not found", HttpStatus.NOT_FOUND));

        // Check if room is reserved from today to the future
        if (room.getReservations().stream().anyMatch(r -> r.getCheckIn().isAfter(LocalDate.now()))) {
            throw new BusinessException("Room with id " + id + " is reserved", HttpStatus.BAD_REQUEST);
        }

        roomRepository.delete(room);
    }

    @Override
    public RoomDTO get(Long id) throws BusinessException {
        return roomRepository.findById(id)
                .map(roomMapper::toDTO)
                .orElseThrow(() -> new BusinessException("Room with id " + id + " not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<RoomDTO> list() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .toList();
    }

    @Override
    public PageRES<RoomDTO> fetch(Pageable pageable) {
        return null;
    }

    @Override
    public PageRES<RoomDTO> search(Pageable pageable, String search) {
        return null;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRoomStatus() {
        List<Room> rooms = roomRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Room room : rooms) {
            if (reservationRepository.existsByRoomAndCheckIn(room, today)) {
                room.setStatus(RoomStatus.RESERVED);
                roomRepository.save(room);
            }
        }
    }

    public List<RoomDTO> checkAvailableRoomsByType(BookingRequest request) {
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
