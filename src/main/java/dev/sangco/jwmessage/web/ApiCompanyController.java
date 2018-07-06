package dev.sangco.jwmessage.web;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
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

    @Autowired
    private ModelMapper modelMapper;


// TODO uploadCompanies 메소드 만들기
    @RequestMapping(value = "/upload", method = POST)
    public ResponseEntity uploadCompanies(@RequestParam("file") MultipartFile uploadfile, Principal principal) throws IOException, InvalidFormatException {
        System.out.println("여기들어오는지");
        List<Company> companies = excelReadComponent.readExcelToList(uploadfile, (row -> Company.ofRow(row)));
//		log.debug("uploadCompanies() - Principal - getName() : {}", principal.getName());
//		TODO 일단 업로드 파일 읽어서 뿌리고 메일 보내기
//		Account account = accountService.findByAccId(principal.getName());
//		for (Company company : companies) {
//			company.setAccount(account);
//			companyService.save(company);
//		}



        List<CompanyDto.Response> companyDtos = companies.stream().map(c -> {
            CompanyDto.Response response = modelMapper.map(c, CompanyDto.Response.class);
            return response;
        }).collect(Collectors.toList());

        return new ResponseEntity(companyDtos, HttpStatus.OK);
    }

// TODO .testmode_yn("Y").build().ofEntity(); 이부분이 MessageDto.Create 통해서 들어와야 한다.
    @RequestMapping(value = "/send", method = POST)
    public ResponseEntity sendMessage(@RequestBody MessageDto.Create messageDto, Principal principal) {
        log.debug("어떤 값이 들어왔는고~~~~ " + messageDto.toString());
        Account account = accountService.findByAccId(principal.getName());
        log.debug("getAligoId() : " + account.getAligoId());
        log.debug("getAligoKey() : " + account.getAligoKey());
        RestTemplate restTemplate = new RestTemplate();
// TODO [STEP 01] 원격에서 빌드시 테스트를 돌리는데 그때 테스트 모드로 돌릴 수 있게 만들어야 한다.
        HttpEntity<MultiValueMap<String, Object>> request =
                Message.builder()
                        .key(account.getAligoKey())
                        .userid(account.getAligoId())
                        .sender(account.getPhoneNumb())
                        .receiver(messageDto.getContactNumb())
                        .msg(messageDto.getMsg())
                        .title(messageDto.getTitle())
                        .testmode_yn("Y").build().ofEntity();
//                        .build().ofEntity();

        ResponseEntity<String> response =
        restTemplate.postForEntity("https://apis.aligo.in/send/", request, String.class);
        System.out.println("Response StatusCode : " + response.getStatusCode().toString());
        System.out.println("Response Body : " + response.getBody());
        // TODO JSON으로 떨구기

        return new ResponseEntity(response.getBody(), HttpStatus.OK);
    }


    //	TODO [STEP 01] 파일 업로드해서 Company 업데이트
//
//	@RequestMapping(value = (""), method = POST)
//	public ResponseEntity updateCompany(MultipartFile file, Principal principal) throws IOException, InvalidFormatException {
//		List<Company> companies = excelReadComponent.readExcelToList(file, (row -> Company.ofRow(row)));
//		log.debug("updateCompany() - Principal - getName() : {}", principal.getName());
//		for (Company company : companies) {
//			accountService.updateAccountCompany(principal.getName(), companyService.save(company));
//			log.debug(company.toString());
//		}
//
//		return new ResponseEntity(HttpStatus.OK);
//	}
}


