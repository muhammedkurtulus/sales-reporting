package com.example.report_service.service;

import com.example.report_service.model.sale.Item;
import com.example.report_service.model.sale.Sale;
import com.example.report_service.model.template.Template;
import com.example.report_service.model.template.fieldGroup.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelReportService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ExcelReportService() {
    }

    public byte[] generateReport(Optional<Template> optionalTemplate, List<Sale> salesData) throws IOException {
        logger.info("sales data {}", salesData.getFirst().toString());

        if (optionalTemplate.isEmpty()) {
            throw new IllegalArgumentException("Template not found");
        }

        Template template = optionalTemplate.get();

        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Sales Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            int colIndex = 0;
            for (Column column : template.getFields().getFirst().getColumns()) {
                Cell cell = headerRow.createCell(colIndex++);
                cell.setCellValue(column.getHeader());
            }

            // Populate rows with data
            int rowIndex = 1;
            for (Sale sale : salesData) {
                List<Object[]> rows = getRowsForSale(sale);
                for (Object[] rowData : rows) {
                    Row row = sheet.createRow(rowIndex++);
                    colIndex = 0;
                    for (Object value : rowData) {
                        Cell cell = row.createCell(colIndex++);
                        setCellValue(cell, value);
                    }
                }
            }

            workbook.write(baos);
            baos.flush();
            byte[] byteArray = baos.toByteArray();

//            // this is for testing purposes only
//            saveByteArrayToFile(byteArray, "SalesReport.xlsx");
            logger.info("11-Create Report");
            
            return byteArray;
        }
    }

    private List<Object[]> getRowsForSale(Sale sale) {
        List<Object[]> rows = new ArrayList<>();

        for (Item item : sale.getItems()) {
            Object[] row = new Object[]{
                    sale.getSaleDate(),
                    sale.getStoreLocation(),
                    sale.getCustomer().getAge(),
                    sale.getCustomer().getGender(),
                    sale.getCustomer().getSatisfaction(),
                    sale.isCouponUsed(),
                    sale.getPurchaseMethod(),
                    item.getName(),
                    String.join(", ", item.getTags()), // Join tags into a single string
                    item.getPrice(),
                    item.getQuantity(),
                    item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) // Total Price
            };
            rows.add(row);
        }
        return rows;
    }

    //this is for testing purposes only
    private void saveByteArrayToFile(byte[] byteArray, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteArray);
            logger.info("Byte array saved to {}", file.getAbsolutePath());
        }
    }

    private void setCellValue(Cell cell, Object value) {
        switch (value) {
            case null -> {
                cell.setCellValue("");
            }
            case Date date -> {
                cell.setCellValue(date);
                cell.setCellStyle(getDateCellStyle(cell.getSheet().getWorkbook()));
            }
            case Number number -> cell.setCellValue(number.doubleValue());
            case Boolean b -> cell.setCellValue(b ? "YES" : "NO");
            default -> cell.setCellValue(value.toString());
        }
    }

    private CellStyle getDateCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        return cellStyle;
    }
}
