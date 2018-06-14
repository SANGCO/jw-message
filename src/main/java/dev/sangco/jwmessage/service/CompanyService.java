package dev.sangco.jwmessage.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.sangco.jwmessage.common.CompanyNotFoundException;
import dev.sangco.jwmessage.domain.Company;
import dev.sangco.jwmessage.domain.CompanyRepository;

@Transactional
@Service
public class CompanyService {
    public static final Logger log =  LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyRepository companyRepository;

    public Company save(Company paramCompany) {
// TODO @ManyToMany면 달라져야 한다. 지금은 사용자가 엎어 써진다.
    	Optional<Company> company = companyRepository.findByCompanyName(paramCompany.getCompanyName());
    	if (company.isPresent()) {
    		return company.get().update(paramCompany);
    	}
    	
        return companyRepository.save(paramCompany);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName).orElseThrow(() -> new CompanyNotFoundException(companyName));
    }
}
