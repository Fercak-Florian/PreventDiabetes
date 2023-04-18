package com.mediscreen.report.controller;

import com.mediscreen.report.exception.ReportGenerationException;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<Report> getReport(@PathVariable("id") int id){
        Report report = reportService.getReport(id);
        if(report == null){
            log.warn("error during report generation");
            throw new ReportGenerationException("error during report generation");
        } else {
            log.warn("providing diabetes report");
            return ResponseEntity.ok(report);
        }
    }
}
