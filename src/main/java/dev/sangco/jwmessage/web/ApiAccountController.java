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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/accounts")
public class ApiAccountController {
    public static final Logger log = LoggerFactory.getLogger(ApiAccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/{accId}", method = GET)
    public ResponseEntity getAccount(@PathVariable String accId) {
        return new ResponseEntity(modelMapper.map(
                accountService.findByAccId(accId), AccountDto.Response.class), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = GET)
    public ResponseEntity getAccounts(Pageable pageable) {
        Page<Account> page = accountService.findAll(pageable);
        List<AccountDto.Response> content = page.getContent().stream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class))
                .collect(Collectors.toList());
        PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{accId}", method = DELETE)
    public ResponseEntity deleteAccount(@PathVariable String accId) {
        accountService.deleteByAccId(accId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
