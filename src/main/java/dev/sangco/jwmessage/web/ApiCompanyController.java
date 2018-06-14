package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.domain.Company;
import dev.sangco.jwmessage.domain.CompanyDto;
import dev.sangco.jwmessage.domain.MessageDto;
import dev.sangco.jwmessage.service.AccountService;
import dev.sangco.jwmessage.service.CompanyService;
import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ModelMapper modelMapper;


    //	TODO 파일 업로드한
    @RequestMapping(value = "/upload", method = POST, produces = "application/json")
    public ResponseEntity uploadCompanies(@RequestParam("file") MultipartFile uploadfile, Principal principal) throws IOException, InvalidFormatException {
        List<Company> companies = excelReadComponent.readExcelToList(uploadfile, (row -> Company.ofRow(row)));
//		log.debug("uploadCompanies() - Principal - getName() : {}", principal.getName());
//		TODO 일단 업로드 파일 읽어서 뿌리고 메일 보내기
//		Account account = accountService.findByAccId(principal.getName());
//		for (Company company : companies) {
//			company.setAccount(account);
//			companyService.save(company);
//		}

        CompanyDto companyDto = new CompanyDto();


        List<CompanyDto.Response> companyDtos = companies.stream().map(c -> {
            CompanyDto.Response response = modelMapper.map(c, CompanyDto.Response.class);
            return response;
        }).collect(Collectors.toList());

        return new ResponseEntity(companyDtos, HttpStatus.OK);
    }


    @RequestMapping(value = "/send", method = POST)
    public ResponseEntity sendMessage(@RequestBody MessageDto.Create messageDto) {
        log.debug("어떤 값이 들어왔는고~~~~ " + messageDto.toString());
        return new ResponseEntity(HttpStatus.OK);
    }


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


