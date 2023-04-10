package com.mediscreen.report.service;

import com.mediscreen.report.bean.PatientBean;
import com.mediscreen.report.model.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    public Report initReport(){
        String riskLevel = "Test Level";
        Report report = new Report("Boyd", "Jacob", 40, riskLevel);
        return report;
    }
}
