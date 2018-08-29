package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.common.ErrorResponse;
import dev.sangco.jwmessage.domain.*;
import dev.sangco.jwmessage.service.AccountService;
import dev.sangco.jwmessage.service.CompanyService;
import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/companies")
public class ApiCompanyController {
    public static final Logger log = LoggerFactory.getLogger(ApiCompanyController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SendResultRepository sendResultRepository;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "", method = POST)
    public ResponseEntity uploadCompanies(@RequestParam("file") MultipartFile uploadfile) throws IOException, InvalidFormatException {
        return new ResponseEntity(companyService.getCompanyResponse(uploadfile), OK);
    }

    @RequestMapping(value = "", method = PUT)
    public ResponseEntity updateCompanies(@RequestParam("file") MultipartFile uploadfile) throws IOException, InvalidFormatException {
        companyService.updateCompanies(uploadfile);
        return new ResponseEntity(OK);
    }

    @RequestMapping(value = "/search", method = POST)
    public ResponseEntity searchCompanyData(@RequestBody CompanyDto.Request cRequest) {
        return new ResponseEntity(companyService.search(cRequest), OK);
    }

    @RequestMapping(value = "/send", method = POST)
    public ResponseEntity sendMessage(@RequestBody @Valid Message message, Principal principal, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(ErrorResponse.createErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        SendResult sendResult = restTemplate.postForObject("https://apis.aligo.in/send/", getMessage(message, accountService.findByAccId(principal.getName())), SendResult.class);
        sendResult.setAcc_id(principal.getName());
        sendResultRepository.save(sendResult);
        return new ResponseEntity(sendResult, OK);
    }

    private HttpEntity<MultiValueMap<String, Object>> getMessage(@Valid @RequestBody Message message, Account account) {
        return Message.builder()
                .key(account.getAligoKey())
                .userid(account.getAligoId())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .msg(message.getMsg())
                .title(message.getTitle())
                .testmode_yn(message.getTestmode_yn()).build().ofEntity();
    }
}