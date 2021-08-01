package com.hu.Virtualize.exception;

import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

public class ResponseStatusException extends Exception{
    public ResponseStatusException (String message) {
        super(message);
    }

}
