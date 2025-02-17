package com.marouanedbibih.riyadmanager.lib.interfaces;

import java.util.List;

public interface ICRUD <E,DTO,CREQ,UREQ,ID> {
    DTO create(CREQ req) throws RuntimeException;
    DTO update(ID id, UREQ req) throws RuntimeException;
    void delete(ID id) throws RuntimeException;
    DTO get(ID id) throws RuntimeException;
    List<DTO> list();
}
