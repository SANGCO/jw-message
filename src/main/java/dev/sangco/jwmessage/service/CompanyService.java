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
    private MeatCutRepository meatCutRepository;

    @Autowired
    private TypeOfBIzRepository typeOfBIzRepository;

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private ExcelReadComponent excelReadComponent;

    @Autowired
    private ModelMapper modelMapper;

    public List<CompanyDto.ResponseS> getCompanyResponse(MultipartFile uploadfile) throws IOException, InvalidFormatException {
        return getResponse(getCompanyDtos(uploadfile));
    }

    public void updateCompanies(MultipartFile uploadfile) throws IOException, InvalidFormatException {
        deleteEarlierData();
        for (CompanyDto.Create cAccount : getCompanyDtos(uploadfile)) {
            save(getCompany(cAccount));
        }
    }

    public List<CompanyDto.ResponseS> search(CompanyDto.Request cRequest) {
        List<CompanyDto.ResponseS> response = new ArrayList<>();
        companyRepository.findAll(getBooleanExpression(cRequest)).forEach(c -> {
            response.add(modelMapper.map(c, CompanyDto.ResponseS.class));
        });

        return response;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName).orElseThrow(() -> new CompanyNotFoundException(companyName));
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

    private Company save(Company uCompany) {
        return companyRepository.save(uCompany);
    }

    private BooleanExpression getBooleanExpression(CompanyDto.Request cRequest) {
        Set<MeatCut> meatCuts = setDefaultMeatCuts(cRequest.getMeatCuts());
        Set<String> typeOfBizs = setDefaultType(cRequest.getType());
        Set<String> salesPersons = setDefaultSalesPerson(cRequest.getSalesPerson());
        QCompany qCompany = QCompany.company;
        return qCompany.salesPerson.salesPersonName.in(salesPersons)
                .and(qCompany.typeOfBiz.type.in(typeOfBizs))
                .and(qCompany.meatCuts.any().in(meatCuts));
    }

    private Set<MeatCut> setDefaultMeatCuts(String paramMeatCuts) {
        if (paramMeatCuts.equalsIgnoreCase("")) {
            return meatCutRepository.findAll().stream().collect(Collectors.toSet());
        }
        return getMeatCuts(paramMeatCuts);
    }

    private Set<String> setDefaultType(String paramType) {
        if (paramType.equalsIgnoreCase("")) {
            return typeOfBIzRepository.findAll().stream().map(c -> { return c.getType(); }).collect(Collectors.toSet());
        }
        return getStringSet(paramType);
    }

    private Set<String> setDefaultSalesPerson(String paramSalesPerson) {
        if (paramSalesPerson.equalsIgnoreCase("")) {
            return salesPersonRepository.findAll().stream().map(c -> { return c.getSalesPersonName(); }).collect(Collectors.toSet());
        }
        return getStringSet(paramSalesPerson);
    }

    private Set<MeatCut> getMeatCuts(String paramMeatCuts) {
        Set<MeatCut> meatCuts = new HashSet<>();
        for (String meatCut : paramMeatCuts.split(",")) {
            meatCuts.add(new MeatCut(meatCut));
        }

        return meatCuts;
    }

    private Set<String> getStringSet(String param) {
        Set<String> typeOfBizs = new HashSet<>();
        for (String type : param.split(",")) {
            typeOfBizs.add(type);
        }

        return typeOfBizs;
    }

    private List<CompanyDto.Create> getCompanyDtos(MultipartFile uploadfile) throws IOException, InvalidFormatException {
        return excelReadComponent.readExcelToList(uploadfile, (row -> CompanyDto.ofRow(row)));
    }

    private List<CompanyDto.ResponseS> getResponse(List<CompanyDto.Create> companies) {
        return companies.stream().map(c -> {
            return modelMapper.map(c, CompanyDto.ResponseS.class);
        }).collect(Collectors.toList());
    }
}
