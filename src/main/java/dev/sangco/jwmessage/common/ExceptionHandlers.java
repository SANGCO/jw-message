package dev.sangco.jwmessage.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {
    public static final Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

    @Autowired
    private MessageSourceAccessor msa;

    @ExceptionHandler(AccountDuplicatedException.class)
    public ResponseEntity handleAccountDuplicatedException(AccountDuplicatedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(msa.getMessage("e.accountDupl.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.accountDupl.m"));
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
        errorResponse.setCode(msa.getMessage("e.UnAuthentication.c"));
        errorResponse.setMessage("[ " + e.getMessage() + " ] " + msa.getMessage("e.UnAuthentication.m"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // TODO AccountNotFoundException
}
