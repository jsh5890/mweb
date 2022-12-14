package mweb.jmao.api.controller;

import lombok.extern.slf4j.Slf4j;
import mweb.jmao.api.exception.InvalidRequest;
import mweb.jmao.api.exception.MwebException;
import mweb.jmao.api.exception.PostNotFound;
import mweb.jmao.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidExceptionHandler(MethodArgumentNotValidException e) {
//        log.error("error : ", e);
//        MethodArgumentNotValidException
//        if(e.hasErrors()){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for(FieldError fieldError : e.getFieldErrors()){
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorResponse;

//        } else {

//        }
//        FieldError fieldError = e.getFieldError();
//        String fieldName = fieldError.getField();
//        String errorMessage = fieldError.getDefaultMessage();
//
//        Map<String, String> error = new HashMap<>();
//        error.put(fieldName, errorMessage);
//        return error;
    }


    @ResponseBody
    @ExceptionHandler(MwebException.class)
    public ResponseEntity<ErrorResponse> mwebException(MwebException e) {
        int statusCode = e.statusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getVaildation())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(errorResponse);

        return response;
    }


}
