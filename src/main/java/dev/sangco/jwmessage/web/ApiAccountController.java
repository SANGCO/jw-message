package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.ErrorResponse;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.service.AccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class ApiAccountController {
    public static final Logger log = LoggerFactory.getLogger(ApiAccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageSourceAccessor msa;

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(msa.getMessage("badRequestMessage"));
            errorResponse.setCode(msa.getMessage("badRequestCode"));

            errorResponse.setFieldErrors(bindingResult.getFieldErrors().stream().map(error -> {
                ErrorResponse.fieldError fieldError = new ErrorResponse.fieldError();
                fieldError.setField(error.getField());
                fieldError.setMessage(error.getDefaultMessage());
                return fieldError;
            }).collect(Collectors.toList()));

            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Account newAccount = accountService.createAccount(create);
        return new ResponseEntity(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.OK);
    }
}
