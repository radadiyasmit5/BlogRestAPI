package com.springboot.blog.exception;


import com.springboot.blog.payload.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalEXceptionhandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResosurceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundEception(ResosurceNotFoundException exception, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorMessage> handleblogapiexception(BlogApiException exception, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleglobalexception(Exception exception, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,

                                                                WebRequest request) {
        Map<String,String> errors = new HashMap<>();
                ex.getBindingResult().getAllErrors().forEach((error) ->
                {
                    String fieldname = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                 errors.put(fieldname,message);
                }
        );

                return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
