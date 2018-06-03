package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.ErrorResponse;
import dev.sangco.jwmessage.common.UnAuthenticationException;
import dev.sangco.jwmessage.domain.Account;
import dev.sangco.jwmessage.domain.AccountDto;
import dev.sangco.jwmessage.service.AccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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

    @RequestMapping(value = "/join", method = POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(createErrorResponse(bindingResult), BAD_REQUEST);
        }

        return new ResponseEntity(modelMapper.map(accountService.createAccount(create), AccountDto.Response.class), OK);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity getAccount(@PathVariable Long id) {
        return new ResponseEntity<>(modelMapper.map(
                accountService.findById(id), AccountDto.Response.class), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = GET)
    public ResponseEntity getAccounts(Pageable pageable) {
        Page<Account> page = accountService.findAll(pageable);
        List<AccountDto.Response> content = page.getContent().stream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class))
                .collect(Collectors.toList());
        PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateAccount(@PathVariable Long id, @RequestBody @Valid AccountDto.Update updateDto,
                                        Principal principal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(createErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (!accountService.findById(id).getAccountId().equalsIgnoreCase(principal.getName())) {
            throw new UnAuthenticationException(String.valueOf(id));
        }
        Account updatedAccount = accountService.updateAccount(id, updateDto);
        return new ResponseEntity<>(modelMapper.map(updatedAccount, AccountDto.Response.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ErrorResponse createErrorResponse(BindingResult bindingResult) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(msa.getMessage("badReq.c"));
        errorResponse.setCode(msa.getMessage("badReq.m"));
        errorResponse.setFieldErrors(bindingResult.getFieldErrors().stream().map(error -> {
            ErrorResponse.fieldError fieldError = new ErrorResponse.fieldError();
            fieldError.setField(error.getField());
            fieldError.setMessage(error.getDefaultMessage());
            return fieldError;
        }).collect(Collectors.toList()));

        return errorResponse;
    }
}
