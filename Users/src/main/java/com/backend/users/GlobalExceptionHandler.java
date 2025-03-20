package com.backend.users;

import com.backend.users.Security.Exceptions.UnauthorizedException;
import com.backend.users.Security.Exceptions.UserAlreadyExists;
import com.backend.users.Security.Utils.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDetail> handleUnauthorizedException(UnauthorizedException e, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setMessage(e.getMessage());
        errorDetail.setError(request.getDescription(false));
        errorDetail.setTimestamp(ZonedDateTime.now());
        errorDetail.setCode(401);
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorDetail> handleUserAlreadyExists(UserAlreadyExists e, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setMessage(e.getMessage());
        errorDetail.setError(request.getDescription(false));
        errorDetail.setTimestamp(ZonedDateTime.now());
        errorDetail.setCode(400);
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }


}
