package com.example.geological.controller;

import com.example.geological.model.Section;
import com.example.geological.service.ExcelService;
import com.example.geological.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/sections")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @Autowired
    private ExcelService excelService;

    @PostMapping
    public Section createSection(@RequestBody Section section) {
        return sectionService.saveSection(section);
    }

    @GetMapping
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/by-code")
    public List<Section> getSectionsByCode(@RequestParam String code) {
        return sectionService.getSectionsByCode(code);
    }

    @PostMapping("/import")
    public CompletableFuture<ResponseEntity<String>> importSections(@RequestParam("file") MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Section> sections = excelService.importSectionsFromExcel(new ByteArrayInputStream(file.getBytes()));
                sections.forEach(sectionService::saveSection);
                return new ResponseEntity<>("Import successful", HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>("Failed to import file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @GetMapping("/export")
    public CompletableFuture<ResponseEntity<InputStreamResource>> exportSections() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ByteArrayInputStream in = excelService.exportSectionsToExcel(sectionService.getAllSections());
                InputStreamResource file = new InputStreamResource(in);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sections.xlsx")
                        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                        .body(file);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }
}
