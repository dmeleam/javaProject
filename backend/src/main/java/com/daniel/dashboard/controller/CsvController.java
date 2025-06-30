package com.daniel.dashboard.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/csv")
public class CsvController {

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadCsv(@RequestParam("file") MultipartFile file) {
        Map<String, Object> summary = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<CSVRecord> records = parser.getRecords();
            Set<String> headers = parser.getHeaderMap().keySet();

            summary.put("columnHeaders", headers);
            summary.put("rowCount", records.size());

            return ResponseEntity.ok(summary);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to parse CSV: " + e.getMessage()));
        }
    }
}
