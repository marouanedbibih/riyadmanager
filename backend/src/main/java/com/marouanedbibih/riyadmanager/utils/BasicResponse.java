package com.marouanedbibih.riyadmanager.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    private Object content;
    private String message;

    private BasicError error;
    private List<BasicError> errors;

}
