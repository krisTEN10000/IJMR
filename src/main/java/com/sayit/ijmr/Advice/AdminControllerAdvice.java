package com.sayit.ijmr.Advice;

import com.sayit.ijmr.Exceptions.VolumeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminControllerAdvice {

    @ExceptionHandler(VolumeNotFoundException.class)
    public ResponseEntity<String> handleVolumeNotFound(VolumeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

}
