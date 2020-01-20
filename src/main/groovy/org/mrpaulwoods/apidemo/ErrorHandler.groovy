package org.mrpaulwoods.apidemo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(ContactNotFoundException)
    public ResponseEntity<Map> handle(ContactNotFoundException ex) {
        ResponseEntity.status(HttpStatus.NOT_FOUND).body([message:ex.message])
    }

}
