package com.example.geological.service;

import com.example.geological.model.GeologicalClass;
import com.example.geological.model.Section;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    public ByteArrayInputStream exportSectionsToExcel(List<Section> sections) throws IOException {
        String[] COLUMNs = {"Section Name", "Class Name", "Class Code"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Sections");

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            int rowIdx = 1;
            for (Section section : sections) {
                for (GeologicalClass geologicalClass : section.getGeologicalClasses()) {
                    Row row = sheet.createRow(rowIdx++);

                    row.createCell(0).setCellValue(section.getName());
                    row.createCell(1).setCellValue(geologicalClass.getName());
                    row.createCell(2).setCellValue(geologicalClass.getCode());
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public List<Section> importSectionsFromExcel(ByteArrayInputStream in) throws IOException {
        List<Section> sections = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(in)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                String sectionName = row.getCell(0).getStringCellValue();
                String className = row.getCell(1).getStringCellValue();
                String classCode = row.getCell(2).getStringCellValue();

                GeologicalClass geologicalClass = new GeologicalClass();
                geologicalClass.setName(className);
                geologicalClass.setCode(classCode);

                Section section = sections.stream()
                        .filter(s -> s.getName().equals(sectionName))
                        .findFirst()
                        .orElse(new Section());
                section.setName(sectionName);
                section.getGeologicalClasses().add(geologicalClass);
                if (!sections.contains(section)) {
                    sections.add(section);
                }
            }
        }
        return sections;
    }
}
