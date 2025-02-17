package com.marouanedbibih.riyadmanager.lib.interfaces;

public interface IMapper<E,DTO,CREQ,UREQ> {
    DTO toDTO(E entity);
    E toEntity(CREQ createRequest);
    E toEntity(UREQ updateRequest, E entity);
}