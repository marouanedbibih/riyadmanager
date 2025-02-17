package com.marouanedbibih.riyadmanager.errors;

import java.util.List;


import org.springframework.security.core.AuthenticationException;

public class MyAuthException extends AuthenticationException {
    private List<OwnFieldError> errors;

    public MyAuthException(String message, String field) {
        super(message);
        errors.add(new OwnFieldError(field, message));

    }

    public MyAuthException(String message) {
        super(message);
    }

    public List<OwnFieldError> getResponse() {
        return errors;
    }

}