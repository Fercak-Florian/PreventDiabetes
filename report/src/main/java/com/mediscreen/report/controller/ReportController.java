package com.mediscreen.report.controller;

import com.mediscreen.report.exception.ReportGenerationException;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<Report> getReport(@PathVariable String id){
        Report report = reportService.getReport(id);
        if(report == null){
            throw new ReportGenerationException("Error during report generation");
        } else {
            return ResponseEntity.ok(report);
        }
    }
}
