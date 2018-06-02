package dev.sangco.jwmessage.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataIntegrityViolationException;
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
        errorResponse.setCode(msa.getMessage("duplicated.accountId.code"));
        errorResponse.setMessage("[" + e.getMessage() + "]" + msa.getMessage("duplicated.accountId.message"));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
