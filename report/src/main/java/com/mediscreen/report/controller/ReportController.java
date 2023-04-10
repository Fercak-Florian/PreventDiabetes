package com.mediscreen.report.controller;

import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public ResponseEntity<Report> getReport(){
        Report report = reportService.initReport();
        return ResponseEntity.ok(report);
    }
}
