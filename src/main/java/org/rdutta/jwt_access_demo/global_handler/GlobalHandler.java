package org.rdutta.jwt_access_demo.global_handler;

import org.rdutta.jwt_access_demo.exceptions.UserExceptions;
import org.rdutta.jwt_access_demo.shared.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(UserExceptions.class)
    public ResponseEntity<ErrorDto> handlerUserBadGateway(UserExceptions userExceptions){
        HttpStatus status = determineHttpStatus(userExceptions);
        ErrorDto errorDto = ErrorDto.builder()
                            .errorCode(status.value())
                            .errorMessage(userExceptions.getMessage())
                            .build();

        return new ResponseEntity<ErrorDto>(errorDto, status);
    }

    private HttpStatus determineHttpStatus(UserExceptions ex) {
        if (ex.getMessage().contains("User not found")) {
            return HttpStatus.BAD_GATEWAY;
        } if(ex.getMessage().contains("Username cannot be null or empty") || ex.getMessage().contains("Email already exists")) {
            return HttpStatus.BAD_REQUEST;
        }if(ex.getMessage().contains("User not present with the id::")){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }if(ex.getMessage().contains("User id not present in the database") || ex.getMessage().contains("There's no user present currently in database")){
            return HttpStatus.NOT_FOUND;
        }
        else{
            return HttpStatus.OK;
        }
    }
}
