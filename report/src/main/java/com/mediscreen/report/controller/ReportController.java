package com.mediscreen.report.controller;

import com.mediscreen.report.exception.ReportGenerationException;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@Api("API permettant la récupération d'un rapport de diabete")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @ApiOperation("Endpoint permettant la récupération d'un rapport de diabete")
    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
