package com.mediscreen.report.service;

import com.mediscreen.report.bean.PatientBean;
import com.mediscreen.report.model.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    public Report initReport(){
        PatientBean patientBean = new PatientBean("1", "z", "z", "family", "given", "now", "M", "2 rue du test", "222");
        String riskLevel = "Test Level";
        Report report = new Report(patientBean, riskLevel);
        return report;
    }
}
