package com.example.mushroomapi.mushroom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedMushroomException extends RuntimeException {
    public DuplicatedMushroomException(String message) {
        super(message);
    }
}
