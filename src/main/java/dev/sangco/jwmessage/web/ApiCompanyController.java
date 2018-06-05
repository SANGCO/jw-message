package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.domain.Company;
import dev.sangco.jwmessage.service.AccountService;
import dev.sangco.jwmessage.service.CompanyService;
import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/companies")
public class ApiCompanyController {
    public static final Logger log =  LoggerFactory.getLogger(ApiCompanyController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ExcelReadComponent excelReadComponent;

    @RequestMapping(value = (""), method = POST)
    public ResponseEntity updateCompany(MultipartFile file, Principal principal) throws IOException, InvalidFormatException {
        List<Company> companies = excelReadComponent.readExcelToList(file, (row -> Company.ofRow(row)));
        log.debug("updateCompany() - Principal - getName() : {}", principal.getName());
        for (Company company : companies) {
            accountService.updateAccountCompany(principal.getName(), companyService.save(company));
            log.debug(company.toString());
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
