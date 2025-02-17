package com.marouanedbibih.riyadmanager.lib.interfaces;


import org.springframework.data.domain.Pageable;

import com.marouanedbibih.riyadmanager.lib.models.PageRES;

public interface IFetch <DTO> {
    PageRES<DTO> fetch(Pageable pageable);
    PageRES<DTO> search(Pageable pageable, String search);

}
