package com.minhtn.bankservice.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ServiceException extends ResponseStatusException {
    private Object data;

    public ServiceException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public ServiceException(String reason, Object data) {
        super(HttpStatus.BAD_REQUEST, reason);
        this.data = data;
    }

    public ServiceException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
