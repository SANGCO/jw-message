package dev.sangco.jwmessage.common;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;


@ControllerAdvice
public class ExceptionHandlers {
    public static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

    @Autowired
    private MessageSourceAccessor msa;

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity handleInvalidFormatException (InvalidFormatException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(msa.getMessage("e.invalidFormat.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.invalidFormat.m"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity handleIOException (IOException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(msa.getMessage("e.io.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.io.m"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity handleAccountNotFoundException(AccountNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(msa.getMessage("e.accountNotFound.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.accountNotFound.m"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleDuplicatedException.class)
    public ResponseEntity handleRoleDuplicatedException(RoleDuplicatedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(msa.getMessage("e.roleDupl.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.roleDupl.m"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity handleRoleNotFoundException(RoleNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(msa.getMessage("e.roleNotFound.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.roleNotFound.m"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity handleUnAuthenticationException(UnAuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(msa.getMessage("e.unAuthentication.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.unAuthentication.m"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
