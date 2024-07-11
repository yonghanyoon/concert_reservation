package com.hhplus.concert.exception.list;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomForbiddenException extends RuntimeException{
    private HttpStatus errorCode;

    public CustomForbiddenException(HttpStatus errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
