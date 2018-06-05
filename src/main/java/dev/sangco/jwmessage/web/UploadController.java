package dev.sangco.jwmessage.web;

import dev.sangco.jwmessage.domain.Company;
import dev.sangco.jwmessage.domain.CompanyRepository;
import dev.sangco.jwmessage.support.excel.ExcelReadComponent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/upload")
public class UploadController {
	public static final Logger log = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private ExcelReadComponent excelReadComponent;

	@Autowired
	private CompanyRepository companyRepository;

	@GetMapping("/form")
	public String uploadForm() {
		return "upload";
	}

	@PostMapping("/excel")
	public String readExcel(MultipartFile file) throws IOException, InvalidFormatException {
		List<Company> companies = excelReadComponent.readExcelToList(file, (row -> Company.ofRow(row)));
		for (Company company : companies) {
			companyRepository.save(company);
		}
		return "redirect:/upload/form";
	}
}
