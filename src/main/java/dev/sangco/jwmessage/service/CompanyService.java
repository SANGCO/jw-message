package dev.sangco.jwmessage.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.sangco.jwmessage.common.CompanyNotFoundException;
import dev.sangco.jwmessage.domain.*;
import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class CompanyService {
    public static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TypeOfBIzRepository typeOfBIzRepository;

    @Autowired
    private MeatCutRepository meatCutRepository;

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private ExcelReadComponent excelReadComponent;

    @Autowired
    private ModelMapper modelMapper;

    public List<CompanyDto.ResponseS> getCompanyResponse(MultipartFile uploadfile) throws IOException, InvalidFormatException {
        return getResponses(getCompanyDtos(uploadfile));
    }

    public void updateCompanies(MultipartFile uploadfile) throws IOException, InvalidFormatException {
        deleteEarlierData();
        for (CompanyDto.Create cAccount : getCompanyDtos(uploadfile)) {
            save(getCompany(cAccount));
        }
    }

    private void deleteEarlierData() {
        companyRepository.deleteAll();
        companyRepository.flush();
    }

    private Company getCompany(CompanyDto.Create cAccount) {
        return Company.builder()
                .companyName(cAccount.getCompanyName())
                .typeOfBiz(new TypeOfBiz(cAccount.getType()))
                .personIncharge(cAccount.getPersonIncharge())
                .position(cAccount.getPosition())
                .contactNumb(cAccount.getContactNumb())
                .salesPerson(new SalesPerson(cAccount.getSalesPerson()))
                .meatCuts(getMeatCuts(cAccount.getMeatCuts()))
                .build();
    }

    private Set<MeatCut> getMeatCuts(String paramMeatCuts) {
        Set<MeatCut> meatCuts = new HashSet<>();
        for (String meatCut : paramMeatCuts.split(",")) {
            meatCuts.add(new MeatCut(meatCut));
        }

        return meatCuts;
    }

    public Company save(Company uCompany) {
//        Optional<Company> company = companyRepository.findByCompanyName(uCompany.getCompanyName());
//        if (company.isPresent()) {
//            return companyRepository.save(company.get().update(uCompany));
//        }
        return companyRepository.save(uCompany);
    }

    public List<CompanyDto.ResponseS> search(CompanyDto.Request cRequest) {

        Set<MeatCut> meatCuts = new HashSet<>();
        for (String meatCut : cRequest.getMeatCuts().split(",")) {
            meatCuts.add(new MeatCut(meatCut));
        }

        Set<String> typeOfBizs = new HashSet<>();
        for (String type : cRequest.getType().split(",")) {
            typeOfBizs.add(type);
        }

        Set<String> salesPersons = new HashSet<>();
        for (String type : cRequest.getSalesPerson().split(",")) {
            salesPersons.add(type);
        }

        QCompany qCompany = QCompany.company;

        BooleanExpression expression = qCompany.typeOfBiz.type.in(typeOfBizs)
                .and(qCompany.salesPerson.salesPersonName.in(salesPersons))
                .and(qCompany.meatCuts.any().in(meatCuts));

        Iterable<Company> companies = companyRepository.findAll(expression);
        List<CompanyDto.ResponseS> response = new ArrayList<>();
        companies.forEach(c -> {
            response.add(modelMapper.map(c, CompanyDto.ResponseS.class));
            log.debug(c.toString());
        });

        return response;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName).orElseThrow(() -> new CompanyNotFoundException(companyName));
    }

    private List<CompanyDto.Create> getCompanyDtos(MultipartFile uploadfile) throws IOException, InvalidFormatException {
        return excelReadComponent.readExcelToList(uploadfile, (row -> CompanyDto.ofRow(row)));
    }

    private List<CompanyDto.ResponseS> getResponses(List<CompanyDto.Create> companies) {
        return companies.stream().map(c -> {
            CompanyDto.ResponseS response = modelMapper.map(c, CompanyDto.ResponseS.class);
            return response;
        }).collect(Collectors.toList());
    }
}
