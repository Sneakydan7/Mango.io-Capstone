package it.danielecagnoni.mangoio.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsPayload handleBadRequest(BadRequestException ex) {
        if (ex.getErrorsList() != null) {
            List<String> errorsList = ex.getErrorsList().stream().map(objectError -> objectError.getDefaultMessage()).toList();
            return new ErrorsPayloadWithList(ex.getMessage(), LocalDateTime.now(), errorsList);
        } else {
            return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsPayload handleNotFound(NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UUIDNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsPayload handleNotFound(UUIDNotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorsPayload handleUnauthorized(NotFoundException ex) {
        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsPayload handleGenericErrors(Exception ex) {
        ex.printStackTrace();
        return new ErrorsPayload("Problem from server!", LocalDateTime.now());
    }
}
