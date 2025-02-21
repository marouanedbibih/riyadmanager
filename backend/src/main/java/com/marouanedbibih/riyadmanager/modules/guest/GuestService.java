package com.marouanedbibih.riyadmanager.modules.guest;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.marouanedbibih.riyadmanager.errors.exception.BusinessException;
import com.marouanedbibih.riyadmanager.lib.interfaces.ICRUD;
import com.marouanedbibih.riyadmanager.lib.interfaces.IFetch;
import com.marouanedbibih.riyadmanager.lib.models.PageRES;
import com.marouanedbibih.riyadmanager.lib.utils.PageMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class GuestService implements
        ICRUD<Guest, GuestDTO, GuestREQ, GuestREQ, Long>,
        IFetch<GuestDTO> {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    @Override
    @Transactional
    public GuestDTO create(GuestREQ req) throws BusinessException {
        guestRepository.findByEmail(req.email())
                .ifPresent(guest -> {
                    throw new BusinessException("email", "Email already exists", HttpStatus.CONFLICT);
                });

        guestRepository.findByUsername(req.username())
                .ifPresent(guest -> {
                    throw new BusinessException("username", "Username already exists", HttpStatus.CONFLICT);
                });

        Guest guest = guestMapper.toEntity(req);
        guest = guestRepository.save(guest);

        return guestMapper.toDTO(guest);
    }

    @Override
    @Transactional
    public GuestDTO update(Long id, GuestREQ req) throws BusinessException {
        return guestRepository.findById(id)
                .map(guest -> {
                    guestRepository.findByEmail(req.email())
                            .ifPresent(compareGuest -> {
                                if (!compareGuest.getId().equals(id)) {
                                    throw new BusinessException("email", "Email already exists", HttpStatus.CONFLICT);
                                }
                            });

                    guestRepository.findByUsername(req.username())
                            .ifPresent(compareGuest -> {
                                if (!compareGuest.getId().equals(id)) {
                                    throw new BusinessException("username", "Username already exists",
                                            HttpStatus.CONFLICT);
                                }
                            });

                    guestMapper.toEntity(req, guest);
                    guest = guestRepository.save(guest);
                    return guestMapper.toDTO(guest);
                })
                .orElseThrow(() -> new BusinessException("Guest not found", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Guest not found", HttpStatus.NOT_FOUND));
        guestRepository.delete(guest);
    }

    @Override
    @Transactional
    public GuestDTO get(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Guest not found", HttpStatus.NOT_FOUND));
        return guestMapper.toDTO(guest);
    }

    /*
     * This methos is return a list of guests (lastName,firstName & email) for the
     * select option in the frontend
     */
    @Override
    public List<GuestDTO> list() {
        return guestRepository.findAll().stream()
                .map(guestMapper::toSelectGuest)
                .toList();
    }

    @Override
    public PageRES<GuestDTO> fetch(Pageable pageable) {
        Page<Guest> guests = guestRepository.findAll(pageable);
        return PageMapper.toPageRES(guests, pageable, guestMapper::toDTO);
    }

    @Override
    public PageRES<GuestDTO> search(Pageable pageable, String search) {
        Page<Guest> guests = guestRepository.searchGuest(search, pageable);
        return PageMapper.toPageRES(guests, pageable, guestMapper::toDTO);
    }
}