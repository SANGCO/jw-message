package dev.sangco.jwmessage.web;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/companies")
public class ApiCompanyController {
    public static final Logger log = LoggerFactory.getLogger(ApiCompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ExcelReadComponent excelReadComponent;

    // TODO 일관성 있게 로직이 없어도 서비스를 만들어야 할까?
    @Autowired
    private SendResultRepository sendResultRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    // TODO URI 줄일까?
    @RequestMapping(value = "/update", method = POST)
    public ResponseEntity updateCompanies(@RequestParam("file") MultipartFile uploadfile) throws IOException, InvalidFormatException {
        List<Company> companies = getCompanies(uploadfile);
        companyService.updateCompanies(companies);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/upload", method = POST)
    public ResponseEntity uploadCompanies(@RequestParam("file") MultipartFile uploadfile) throws IOException, InvalidFormatException {
        List<Company> companies = getCompanies(uploadfile);
        return new ResponseEntity(getResponses(companies), HttpStatus.OK);
    }

    @RequestMapping(value = "/send", method = POST)
    public ResponseEntity sendMessage(@RequestBody @Valid Message message, Principal principal, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(ErrorResponse.createErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        log.debug("Message : " + message.toString());
        Account account = accountService.findByAccId(principal.getName());
        HttpEntity<MultiValueMap<String, Object>> request =
                Message.builder()
                        .key(account.getAligoKey())
                        .userid(account.getAligoId())
                        .sender(account.getPhoneNumb())
                        .receiver(message.getReceiver())
                        .msg(message.getMsg())
                        .title(message.getTitle())
                        .testmode_yn(message.getTestmode_yn()).build().ofEntity();
        SendResult sendResult =
                restTemplate.postForObject("https://apis.aligo.in/send/", request, SendResult.class);
        sendResult.setAcc_id(principal.getName());
        sendResultRepository.save(sendResult);
        return new ResponseEntity(sendResult, HttpStatus.OK);
    }

    private List<Company> getCompanies(MultipartFile uploadfile) throws IOException, InvalidFormatException {
        return excelReadComponent.readExcelToList(uploadfile, (row -> Company.ofRow(row)));
    }

    private List<CompanyDto.Response> getResponses(List<Company> companies) {
        return companies.stream().map(c -> {
            CompanyDto.Response response = modelMapper.map(c, CompanyDto.Response.class);
            return response;
        }).collect(Collectors.toList());
    }
}


