package com.mediscreen.report.model;

import com.mediscreen.report.bean.PatientBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private PatientBean patientBean;
    private String riskLevel;
}
