package com.student.searchroom.exception;

import org.springframework.security.core.AuthenticationException;

public class BannedUserException extends AuthenticationException {
    public BannedUserException(String msg) {
        super(msg);
    }
}

