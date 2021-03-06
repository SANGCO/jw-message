package dev.sangco.jwmessage.support.excel;

import dev.sangco.jwmessage.domain.CompanyDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ExcelReadComponent {
    public static final Logger log = LoggerFactory.getLogger(ExcelReadComponent.class);

    public <T> List<T> readExcelToList(final MultipartFile multipartFile,
                                       final Function<Row, T> rowFunc) throws IOException, InvalidFormatException {

        final Workbook workbook = readWorkbook(multipartFile);
        log.debug("multipartFile " + multipartFile.getName());
        final Sheet sheet = workbook.getSheetAt(0);
        log.debug("sheet " + sheet.getSheetName());
        final int rowCount = sheet.getPhysicalNumberOfRows();
        log.debug("rowCount " + rowCount);

        return IntStream
                .range(0, rowCount)
                .mapToObj(rowIndex -> rowFunc.apply(sheet.getRow(rowIndex)))
                .collect(Collectors.toList());
    }

    private Workbook readWorkbook(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        verifyFileExtension(multipartFile);
        return multipartFileToWorkbook(multipartFile);
    }

    private void verifyFileExtension(MultipartFile multipartFile) throws InvalidFormatException {
        if (!isExcelExtension(multipartFile.getOriginalFilename())) {
            throw new InvalidFormatException("This file extension is not verify");
        }
    }

    private boolean isExcelExtension(String fileName) {
        return fileName.endsWith(ExcelConstant.XLS) || fileName.endsWith(ExcelConstant.XLSX);
    }

    private Workbook multipartFileToWorkbook(MultipartFile multipartFile)
            throws IOException {
        if (isExcelXls(multipartFile.getOriginalFilename())) {
            return new HSSFWorkbook(multipartFile.getInputStream());
        } else {
            return new XSSFWorkbook(multipartFile.getInputStream());
        }
    }

    private boolean isExcelXls(String fileName) {
        return fileName.endsWith(ExcelConstant.XLS);
    }
}